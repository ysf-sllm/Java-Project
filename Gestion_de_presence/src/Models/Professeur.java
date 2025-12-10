// TODO: Implement full attributes and methods as per README.md
package Models;

import java.util.List;

public class Professeur extends Utilisateur {
    private String departement;
    private List<Cours> emploiDuTemps;
    private Cours coursActuel;

    public Professeur(String id, String nom, String prenom, String email, String empreinte, String departement) {
        super(id, nom, prenom, email, empreinte);
        this.departement = departement;
    }

    // Methods as per README
    public List<Cours> getCoursDisponibles(java.util.Date date) {
        // Implementation
        return null;
    }

    public void demarrerCours(Cours cours) {
        this.coursActuel = cours;
    }

    public void arreterCours() {
        this.coursActuel = null;
    }

    public void marquerSortieManuelle(Etudiant etudiant) {
        // Implementation
    }

    public RapportSeance genererRapport() {
        // Implementation
        return null;
    }
}