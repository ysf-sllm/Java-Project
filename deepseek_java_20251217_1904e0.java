package fr.presenceuniversitaire.services;

import fr.presenceuniversitaire.dao.PresenceDAO;
import fr.presenceuniversitaire.dao.EtudiantDAO;
import fr.presenceuniversitaire.dao.SeanceEmploiDuTempsDAO;
import fr.presenceuniversitaire.entities.*;
import fr.presenceuniversitaire.exceptions.*;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Implémentation du service de gestion des présences
 * Applique le principe de polymorphisme via l'interface PresenceService
 */
public class PresenceServiceImpl implements PresenceService {
    
    private final PresenceDAO presenceDAO;
    private final EtudiantDAO etudiantDAO;
    private final SeanceEmploiDuTempsDAO seanceDAO;
    private static final int DELAI_RETARD_MINUTES = 15;
    
    public PresenceServiceImpl(PresenceDAO presenceDAO, EtudiantDAO etudiantDAO, 
                              SeanceEmploiDuTempsDAO seanceDAO) {
        this.presenceDAO = presenceDAO;
        this.etudiantDAO = etudiantDAO;
        this.seanceDAO = seanceDAO;
    }
    
    @Override
    public Presence marquerPresence(Etudiant etudiant, SeanceEmploiDuTemps seance) 
            throws PresenceException {
        
        // Vérifications préalables
        if (etudiant == null || seance == null) {
            throw new PresenceException("Étudiant ou séance invalide");
        }
        
        // Vérifier si l'étudiant est inscrit au cours
        if (!estEtudiantInscrit(etudiant, seance.getCours())) {
            throw new PresenceException("L'étudiant n'est pas inscrit à ce cours");
        }
        
        // Vérifier la cohérence horaire
        LocalDateTime maintenant = LocalDateTime.now();
        if (!estDansPlageHoraire(maintenant, seance)) {
            throw new PresenceException("Hors des heures de cours");
        }
        
        // Vérifier la présence existante
        Presence presenceExistante = trouverPresenceExistante(etudiant, seance, 
                                                              maintenant.toLocalDate());
        if (presenceExistante != null) {
            throw new PresenceDejaEnregistreeException("Présence déjà enregistrée");
        }
        
        // Déterminer le statut
        StatutPresence statut = determinerStatutPresence(maintenant, seance);
        
        // Créer l'objet Presence
        Presence presence = new Presence();
        presence.setId(UUID.randomUUID().toString());
        presence.setEtudiant(etudiant);
        presence.setSeance(seance);
        presence.setHorodatageEntree(maintenant);
        presence.setStatut(statut);
        presence.setValide(true);
        
        // Persister
        return presenceDAO.creer(presence);
    }
    
    @Override
    public void marquerSortie(Presence presence) throws PresenceException {
        if (presence == null) {
            throw new PresenceException("Présence invalide");
        }
        
        LocalDateTime maintenant = LocalDateTime.now();
        
        // Vérifier si la sortie est cohérente
        if (maintenant.isBefore(presence.getHorodatageEntree())) {
            throw new PresenceException("Horodatage de sortie invalide");
        }
        
        presence.setHorodatageSortie(maintenant);
        presenceDAO.mettreAJour(presence);
    }
    
    @Override
    public List<Presence> obtenirPresencesParCours(Cours cours, LocalDate date) {
        // Récupérer toutes les présences pour ce cours à cette date
        return presenceDAO.listerTout().stream()
            .filter(p -> p.getSeance().getCours().equals(cours))
            .filter(p -> p.getHorodatageEntree().toLocalDate().equals(date))
            .collect(Collectors.toList());
    }
    
    @Override
    public double calculerTauxPresence(Etudiant etudiant, Cours cours) {
        List<Presence> presences = presenceDAO.listerTout().stream()
            .filter(p -> p.getEtudiant().equals(etudiant))
            .filter(p -> p.getSeance().getCours().equals(cours))
            .collect(Collectors.toList());
        
        if (presences.isEmpty()) {
            return 0.0;
        }
        
        long presencesValides = presences.stream()
            .filter(p -> p.getStatut() == StatutPresence.PRESENT 
                      || p.getStatut() == StatutPresence.RETARD)
            .count();
        
        return (double) presencesValides / presences.size() * 100;
    }
    
    @Override
    public RapportPresence genererRapport(Classe classe, LocalDate dateDebut, 
                                         LocalDate dateFin) {
        RapportPresence rapport = new RapportPresence();
        rapport.setClasse(classe);
        rapport.setPeriodeDebut(dateDebut);
        rapport.setPeriodeFin(dateFin);
        rapport.setDateGeneration(LocalDateTime.now());
        
        // Calculer les statistiques
        Map<Etudiant, Map<StatutPresence, Long>> statistiques = 
            calculerStatistiquesEtudiants(classe, dateDebut, dateFin);
        
        rapport.setStatistiquesEtudiants(statistiques);
        rapport.setTauxPresenceClasse(
            calculerTauxPresenceClasse(classe, dateDebut, dateFin)
        );
        
        return rapport;
    }
    
    // Méthodes privées utilitaires
    private boolean estEtudiantInscrit(Etudiant etudiant, Cours cours) {
        return etudiant.getClasse().getCoursInscrits().contains(cours);
    }
    
    private boolean estDansPlageHoraire(LocalDateTime horodatage, 
                                       SeanceEmploiDuTemps seance) {
        LocalTime horaire = horodatage.toLocalTime();
        return !horaire.isBefore(seance.getHeureDebut().minusMinutes(30)) &&
               !horaire.isAfter(seance.getHeureFin().plusMinutes(30));
    }
    
    private Presence trouverPresenceExistante(Etudiant etudiant, 
                                             SeanceEmploiDuTemps seance, 
                                             LocalDate date) {
        return presenceDAO.listerTout().stream()
            .filter(p -> p.getEtudiant().equals(etudiant))
            .filter(p -> p.getSeance().equals(seance))
            .filter(p -> p.getHorodatageEntree().toLocalDate().equals(date))
            .findFirst()
            .orElse(null);
    }
    
    private StatutPresence determinerStatutPresence(LocalDateTime horodatage, 
                                                   SeanceEmploiDuTemps seance) {
        Duration delai = Duration.between(seance.getHeureDebut(), 
                                         horodatage.toLocalTime());
        
        if (delai.toMinutes() <= DELAI_RETARD_MINUTES) {
            return StatutPresence.PRESENT;
        } else {
            return StatutPresence.RETARD;
        }
    }
    
    private Map<Etudiant, Map<StatutPresence, Long>> calculerStatistiquesEtudiants(
            Classe classe, LocalDate dateDebut, LocalDate dateFin) {
        
        Map<Etudiant, Map<StatutPresence, Long>> statistiques = new HashMap<>();
        
        for (Etudiant etudiant : classe.getEtudiants()) {
            Map<StatutPresence, Long> statsEtudiant = new EnumMap<>(StatutPresence.class);
            
            // Initialiser les compteurs
            for (StatutPresence statut : StatutPresence.values()) {
                statsEtudiant.put(statut, 0L);
            }
            
            // Compter les présences par statut
            List<Presence> presencesEtudiant = presenceDAO.listerTout().stream()
                .filter(p -> p.getEtudiant().equals(etudiant))
                .filter(p -> !p.getHorodatageEntree().toLocalDate().isBefore(dateDebut))
                .filter(p -> !p.getHorodatageEntree().toLocalDate().isAfter(dateFin))
                .collect(Collectors.toList());
            
            for (Presence presence : presencesEtudiant) {
                StatutPresence statut = presence.getStatut();
                statsEtudiant.put(statut, statsEtudiant.get(statut) + 1);
            }
            
            statistiques.put(etudiant, statsEtudiant);
        }
        
        return statistiques;
    }
    
    private double calculerTauxPresenceClasse(Classe classe, 
                                             LocalDate dateDebut, 
                                             LocalDate dateFin) {
        int totalSeances = 0;
        int totalPresences = 0;
        
        // Logique de calcul du taux de présence de la classe
        // (Simplifiée pour l'exemple)
        for (Etudiant etudiant : classe.getEtudiants()) {
            totalSeances += 10; // Exemple: 10 séances dans la période
            totalPresences += 8; // Exemple: 8 présences en moyenne
        }
        
        return totalSeances > 0 ? (double) totalPresences / totalSeances * 100 : 0.0;
    }
}