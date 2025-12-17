package fr.presenceuniversitaire.services;

import fr.presenceuniversitaire.dao.EtudiantDAO;
import fr.presenceuniversitaire.entities.Etudiant;
import fr.presenceuniversitaire.exceptions.BiometricException;
import org.sourceafis.FingerprintTemplate;
import org.sourceafis.FingerprintMatcher;
import java.util.*;

/**
 * Implémentation du service biométrique utilisant la bibliothèque SourceAFIS
 */
public class FingerprintServiceImpl implements BiometricService {
    
    private final EtudiantDAO etudiantDAO;
    private final Map<String, FingerprintTemplate> templatesCache;
    private static final double SEUIL_CONFIANCE = 40.0;
    
    public FingerprintServiceImpl(EtudiantDAO etudiantDAO) {
        this.etudiantDAO = etudiantDAO;
        this.templatesCache = new HashMap<>();
        initialiserCache();
    }
    
    @Override
    public boolean enregistrerEmpreinte(Etudiant etudiant, byte[] empreinteData) 
            throws BiometricException {
        
        try {
            // Valider les données
            if (empreinteData == null || empreinteData.length == 0) {
                throw new BiometricException("Données d'empreinte invalides");
            }
            
            // Créer le template SourceAFIS
            FingerprintTemplate template = new FingerprintTemplate()
                .dpi(500)
                .create(empreinteData);
            
            // Stocker dans l'étudiant
            etudiant.setEmpreinteDigitale(empreinteData);
            
            // Mettre à jour le cache
            templatesCache.put(etudiant.getId(), template);
            
            // Persister
            etudiantDAO.mettreAJour(etudiant);
            
            return true;
            
        } catch (Exception e) {
            throw new BiometricException("Erreur lors de l'enregistrement: " + 
                                        e.getMessage(), e);
        }
    }
    
    @Override
    public Etudiant verifierEmpreinte(byte[] empreinteData) throws BiometricException {
        try {
            // Créer le template à vérifier
            FingerprintTemplate templateAVerifier = new FingerprintTemplate()
                .dpi(500)
                .create(empreinteData);
            
            // Créer le matcher
            FingerprintMatcher matcher = new FingerprintMatcher()
                .index(templateAVerifier);
            
            Etudiant meilleurMatch = null;
            double meilleurScore = 0.0;
            
            // Comparer avec tous les templates en cache
            for (Map.Entry<String, FingerprintTemplate> entry : 
                 templatesCache.entrySet()) {
                
                double score = matcher.match(entry.getValue());
                
                if (score > meilleurScore && score >= SEUIL_CONFIANCE) {
                    meilleurScore = score;
                    meilleurMatch = etudiantDAO.lire(entry.getKey());
                }
            }
            
            if (meilleurMatch != null) {
                System.out.println("Match trouvé: " + meilleurMatch.getNom() + 
                                 " - Score: " + meilleurScore);
                return meilleurMatch;
            } else {
                throw new BiometricException("Aucune correspondance trouvée");
            }
            
        } catch (Exception e) {
            throw new BiometricException("Erreur de vérification: " + 
                                        e.getMessage(), e);
        }
    }
    
    @Override
    public double comparerEmpreintes(byte[] empreinte1, byte[] empreinte2) 
            throws BiometricException {
        
        try {
            FingerprintTemplate template1 = new FingerprintTemplate()
                .dpi(500)
                .create(empreinte1);
            
            FingerprintTemplate template2 = new FingerprintTemplate()
                .dpi(500)
                .create(empreinte2);
            
            FingerprintMatcher matcher = new FingerprintMatcher()
                .index(template1);
            
            return matcher.match(template2);
            
        } catch (Exception e) {
            throw new BiometricException("Erreur de comparaison: " + 
                                        e.getMessage(), e);
        }
    }
    
    // Méthode privée pour initialiser le cache
    private void initialiserCache() {
        List<Etudiant> etudiants = etudiantDAO.listerTout();
        
        for (Etudiant etudiant : etudiants) {
            if (etudiant.getEmpreinteDigitale() != null && 
                etudiant.getEmpreinteDigitale().length > 0) {
                
                try {
                    FingerprintTemplate template = new FingerprintTemplate()
                        .dpi(500)
                        .create(etudiant.getEmpreinteDigitale());
                    
                    templatesCache.put(etudiant.getId(), template);
                    
                } catch (Exception e) {
                    System.err.println("Erreur lors du chargement de l'empreinte " + 
                                     "pour " + etudiant.getNom() + ": " + 
                                     e.getMessage());
                }
            }
        }
        
        System.out.println("Cache biométrique initialisé: " + 
                          templatesCache.size() + " templates chargés");
    }
    
    // Méthode pour rafraîchir le cache
    public void rafraichirCache() {
        templatesCache.clear();
        initialiserCache();
    }
}