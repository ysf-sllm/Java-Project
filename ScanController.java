package fr.presenceuniversitaire.controllers;

import fr.presenceuniversitaire.entities.*;
import fr.presenceuniversitaire.services.*;
import fr.presenceuniversitaire.dao.*;
import fr.presenceuniversitaire.exceptions.*;
import fr.presenceuniversitaire.views.ScanView;
import javafx.application.Platform;
import javafx.beans.property.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.*;

/**
 * Contrôleur principal pour la gestion des scans d'empreintes
 * Gère la communication entre la vue et les services
 */
public class ScanController {
    
    // Services injectés
    private final BiometricService biometricService;
    private final PresenceService presenceService;
    private final EmploiDuTempsService emploiDuTempsService;
    
    // DAOs
    private final SalleDAO salleDAO;
    private final SeanceEmploiDuTempsDAO seanceDAO;
    
    // Vue associée
    private ScanView scanView;
    
    // Propriétés observables pour le binding JavaFX
    private final StringProperty messageProperty;
    private final BooleanProperty scanActifProperty;
    private final ObjectProperty<SeanceEmploiDuTemps> seanceActuelleProperty;
    private final ListProperty<Etudiant> etudiantsPresentsProperty;
    
    // Thread pool pour les opérations asynchrones
    private final ExecutorService executorService;
    
    // État du contrôleur
    private Salle salleCourante;
    private ScheduledExecutorService scheduler;
    
    public ScanController(BiometricService biometricService, 
                         PresenceService presenceService,
                         EmploiDuTempsService emploiDuTempsService,
                         SalleDAO salleDAO,
                         SeanceEmploiDuTempsDAO seanceDAO) {
        
        this.biometricService = biometricService;
        this.presenceService = presenceService;
        this.emploiDuTempsService = emploiDuTempsService;
        this.salleDAO = salleDAO;
        this.seanceDAO = seanceDAO;
        
        // Initialisation des propriétés
        this.messageProperty = new SimpleStringProperty("");
        this.scanActifProperty = new SimpleBooleanProperty(false);
        this.seanceActuelleProperty = new SimpleObjectProperty<>();
        this.etudiantsPresentsProperty = new SimpleListProperty<>();
        
        this.executorService = Executors.newFixedThreadPool(2);
    }
    
    // ========== MÉTHODES PUBLIQUES ==========
    
    public void initialiserScanner(String idSalle) {
        try {
            // Charger la salle
            salleCourante = salleDAO.lire(idSalle);
            if (salleCourante == null) {
                throw new IllegalStateException("Salle non trouvée: " + idSalle);
            }
            
            // Initialiser la vue
            if (scanView != null) {
                scanView.afficherInfoSalle(salleCourante);
            }
            
            // Démarrer le service de vérification de séance
            demarrerSurveillanceSeance();
            
            // Activer le scan
            scanActifProperty.set(true);
            mettreAJourMessage("Scanner initialisé pour " + salleCourante.getNumero());
            
        } catch (Exception e) {
            gererErreur(new ScanException("Erreur d'initialisation: " + e.getMessage(), e));
        }
    }
    
    public void traiterScan(byte[] empreinteData) {
        if (!scanActifProperty.get()) {
            mettreAJourMessage("Scanner non actif");
            return;
        }
        
        if (seanceActuelleProperty.get() == null) {
            mettreAJourMessage("Aucune séance en cours");
            return;
        }
        
        // Exécuter le traitement de façon asynchrone
        executorService.submit(() -> {
            try {
                // Identification biométrique
                Etudiant etudiant = biometricService.verifierEmpreinte(empreinteData);
                
                // Marquer la présence
                Presence presence = presenceService.marquerPresence(
                    etudiant, seanceActuelleProperty.get()
                );
                
                // Mettre à jour l'interface
                Platform.runLater(() -> {
                    afficherConfirmation(etudiant);
                    ajouterEtudiantPresent(etudiant);
                    mettreAJourMessage("Présence enregistrée pour " + 
                                      etudiant.getNomComplet());
                });
                
            } catch (BiometricException e) {
                Platform.runLater(() -> 
                    gererErreur(new ScanException("Erreur biométrique: " + 
                                                e.getMessage(), e)));
            } catch (PresenceException e) {
                Platform.runLater(() -> 
                    gererErreur(new ScanException("Erreur de présence: " + 
                                                e.getMessage(), e)));
            } catch (Exception e) {
                Platform.runLater(() -> 
                    gererErreur(new ScanException("Erreur inattendue: " + 
                                                e.getMessage(), e)));
            }
        });
    }
    
    public void afficherConfirmation(Etudiant etudiant) {
        if (scanView != null) {
            scanView.afficherConfirmation(etudiant);
        }
        
        // Log l'action
        System.out.println(LocalDateTime.now() + " - Présence confirmée: " + 
                          etudiant.getNomComplet());
    }
    
    public void gererErreur(Exception exception) {
        String messageErreur = "Erreur: " + exception.getMessage();
        
        if (scanView != null) {
            scanView.afficherErreur(messageErreur);
        }
        
        mettreAJourMessage(messageErreur);
        
        // Log détaillé
        System.err.println(LocalDateTime.now() + " - ERREUR: " + 
                          exception.getClass().getName() + " - " + 
                          exception.getMessage());
        exception.printStackTrace();
    }
    
    public void nettoyer() {
        // Arrêter les services
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
        }
        
        executorService.shutdown();
        
        try {
            if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
        
        mettreAJourMessage("Scanner arrêté");
        scanActifProperty.set(false);
    }
    
    // ========== MÉTHODES PRIVÉES ==========
    
    private void demarrerSurveillanceSeance() {
        scheduler = Executors.newScheduledThreadPool(1);
        
        // Vérifier la séance toutes les minutes
        scheduler.scheduleAtFixedRate(() -> {
            try {
                verifierSeanceActuelle();
            } catch (Exception e) {
                gererErreur(new ScanException("Erreur de surveillance: " + 
                                            e.getMessage(), e));
            }
        }, 0, 1, TimeUnit.MINUTES);
    }
    
    private void verifierSeanceActuelle() {
        if (salleCourante == null) return;
        
        SeanceEmploiDuTemps nouvelleSeance = 
            emploiDuTempsService.obtenirSeanceActuelle(salleCourante);
        
        Platform.runLater(() -> {
            if (nouvelleSeance != null && 
                !nouvelleSeance.equals(seanceActuelleProperty.get())) {
                
                // Nouvelle séance détectée
                seanceActuelleProperty.set(nouvelleSeance);
                etudiantsPresentsProperty.clear();
                
                if (scanView != null) {
                    scanView.afficherSeance(nouvelleSeance);
                }
                
                mettreAJourMessage("Séance active: " + 
                                  nouvelleSeance.getCours().getNom());
                
                // Charger la liste des étudiants attendus
                chargerEtudiantsAttendus(nouvelleSeance);
            }
        });
    }
    
    private void chargerEtudiantsAttendus(SeanceEmploiDuTemps seance) {
        executorService.submit(() -> {
            try {
                List<Etudiant> etudiantsAttendus = 
                    emploiDuTempsService.obtenirEtudiantsAttendus(seance);
                
                Platform.runLater(() -> {
                    if (scanView != null) {
                        scanView.afficherEtudiantsAttendus(etudiantsAttendus);
                    }
                });
                
            } catch (Exception e) {
                gererErreur(new ScanException("Erreur chargement étudiants: " + 
                                            e.getMessage(), e));
            }
        });
    }
    
    private void ajouterEtudiantPresent(Etudiant etudiant) {
        // Implémentation simplifiée
        if (scanView != null) {
            scanView.ajouterEtudiantPresent(etudiant);
        }
    }
    
    private void mettreAJourMessage(String message) {
        Platform.runLater(() -> messageProperty.set(message));
    }
    
    // ========== GETTERS/SETTERS ==========
    
    public void setScanView(ScanView scanView) {
        this.scanView = scanView;
    }
    
    public StringProperty messageProperty() {
        return messageProperty;
    }
    
    public BooleanProperty scanActifProperty() {
        return scanActifProperty;
    }
    
    public ObjectProperty<SeanceEmploiDuTemps> seanceActuelleProperty() {
        return seanceActuelleProperty;
    }
    
    public ListProperty<Etudiant> etudiantsPresentsProperty() {
        return etudiantsPresentsProperty;
    }
}