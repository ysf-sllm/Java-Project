// TODO: Implement full attributes and methods as per README.md
package Models;

import java.time.LocalDateTime;

public class Cours {
    private String id;
    private Matiere matiere;
    private Professeur professeur;
    private Groupe groupe;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private int duree; // en minutes, par défaut 90
    private StatutCours statut;
    private TypeSeance typeSeance;

    public Cours(String id, Matiere matiere, Professeur professeur, Groupe groupe, LocalDateTime dateDebut, LocalDateTime dateFin, TypeSeance typeSeance) {
        this.id = id;
        this.matiere = matiere;
        this.professeur = professeur;
        this.groupe = groupe;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.duree = 90; // default
        this.statut = StatutCours.PLANIFIE;
        this.typeSeance = typeSeance;
    }

    public void demarrer() {
        this.statut = StatutCours.EN_COURS;
        this.dateDebut = LocalDateTime.now();
    }

    public void terminer() {
        this.statut = StatutCours.TERMINE;
        this.dateFin = LocalDateTime.now();
    }

    public int getDureeRestante() {
        if (statut == StatutCours.EN_COURS && dateDebut != null) {
            long minutesEcoulees = java.time.Duration.between(dateDebut, LocalDateTime.now()).toMinutes();
            return Math.max(0, duree - (int) minutesEcoulees);
        }
        return duree;
    }

    public String getInformations() {
        return "Cours: " + matiere.getNom() + " - Prof: " + professeur.getNomComplet() + " - Groupe: " + groupe.getNom();
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public Matiere getMatiere() { return matiere; }
    public void setMatiere(Matiere matiere) { this.matiere = matiere; }
    public Professeur getProfesseur() { return professeur; }
    public void setProfesseur(Professeur professeur) { this.professeur = professeur; }
    public Groupe getGroupe() { return groupe; }
    public void setGroupe(Groupe groupe) { this.groupe = groupe; }
    public LocalDateTime getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDateTime dateDebut) { this.dateDebut = dateDebut; }
    public LocalDateTime getDateFin() { return dateFin; }
    public void setDateFin(LocalDateTime dateFin) { this.dateFin = dateFin; }
    public int getDuree() { return duree; }
    public void setDuree(int duree) { this.duree = duree; }
    public StatutCours getStatut() { return statut; }
    public void setStatut(StatutCours statut) { this.statut = statut; }
    public TypeSeance getTypeSeance() { return typeSeance; }
    public void setTypeSeance(TypeSeance typeSeance) { this.typeSeance = typeSeance; }
}