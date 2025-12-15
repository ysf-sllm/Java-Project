// Professeur.java
package model;

import java.util.ArrayList;
import java.util.List;

public class Professeur extends Utilisateur {
    private String departement;
    private List<Cours> emploiDuTemps;  // Les cours que ce prof enseigne
    private Cours coursActuel;           // Le cours en cours
    
    public Professeur(String id, String nom, String prenom, String email, 
                     String empreinte, String departement) {
        super(id, nom, prenom, email, empreinte);
        this.departement = departement;
        this.emploiDuTemps = new ArrayList<>();
        this.coursActuel = null;
    }
    
    // Méthode CORRECTE : Retourne les cours que ce prof enseigne
    public List<Cours> getCoursDisponibles() {
        // Retourne une copie pour éviter les modifications externes
        return new ArrayList<>(emploiDuTemps);
    }
    
    // Démarrer un de SES cours
    public void demarrerCours(Cours cours) {
        // Vérifier que c'est bien un de SES cours
        if (cours != null && emploiDuTemps.contains(cours)) {
            this.coursActuel = cours;
            cours.demarrer();
        }
    }
    
    // ... le reste des méthodes reste inchangé
    
    public void ajouterCours(Cours cours) {
        if (cours != null && !emploiDuTemps.contains(cours)) {
            emploiDuTemps.add(cours);
        }
    }
    
    // Pour l'interface : obtenir les infos des cours disponibles
    public List<String> getNomsCoursDisponibles() {
        List<String> noms = new ArrayList<>();
        for (Cours cours : emploiDuTemps) {
            noms.add(cours.getMatiere().getNom() + " - " + cours.getGroupe().getNom());
        }
        return noms;
    }
}