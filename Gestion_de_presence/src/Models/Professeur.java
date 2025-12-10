// TODO: Implement full attributes and methods as per README.md
package Models;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;

public class Professeur extends Utilisateur {
    private String departement;
    private List<Cours> emploiDuTemps;
    private Cours coursActuel;

    public Professeur(String id, String nom, String prenom, String email, String empreinte, String departement) {
        super(id, nom, prenom, email, empreinte);
        this.departement = departement;
        this.emploiDuTemps = new ArrayList<>();
    }

    public List<Cours> getCoursDisponibles(Date date) {
        List<Cours> disponibles = new ArrayList<>();
        for (Cours cours : emploiDuTemps) {
            // Simplified logic - check if course is scheduled for the date
            if (cours.getStatut() == StatutCours.PLANIFIE) {
                disponibles.add(cours);
            }
        }
        return disponibles;
    }

    public void demarrerCours(Cours cours) {
        this.coursActuel = cours;
        cours.demarrer();
    }

    public void arreterCours() {
        if (coursActuel != null) {
            coursActuel.terminer();
            coursActuel = null;
        }
    }

    public void marquerSortieManuelle(Etudiant etudiant) {
        // Logic to mark manual exit for student
        if (coursActuel != null && coursActuel.getStatut() == StatutCours.EN_COURS) {
            // Find presence and mark exit
        }
    }

    public RapportSeance genererRapport() {
        if (coursActuel != null) {
            // Create a mock seance for the current course
            Seance seance = new Seance("seance_" + System.currentTimeMillis(), coursActuel);
            return new RapportSeance(seance);
        }
        return null;
    }

    public void ajouterCours(Cours cours) {
        if (!emploiDuTemps.contains(cours)) {
            emploiDuTemps.add(cours);
        }
    }

    // Getters and setters
    public String getDepartement() { return departement; }
    public void setDepartement(String departement) { this.departement = departement; }
    public List<Cours> getEmploiDuTemps() { return emploiDuTemps; }
    public void setEmploiDuTemps(List<Cours> emploiDuTemps) { this.emploiDuTemps = emploiDuTemps; }
    public Cours getCoursActuel() { return coursActuel; }
    public void setCoursActuel(Cours coursActuel) { this.coursActuel = coursActuel; }
}