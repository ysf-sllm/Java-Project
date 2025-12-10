// TODO: Implement full attributes and methods as per README.md
package Models;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

public class RapportSeance {
    private Seance seance;
    private Cours cours;
    private Professeur professeur;
    private LocalDateTime dateGeneration;
    private int nombreTotalEtudiants;
    private int nombrePresents;
    private int nombreAbsents;
    private int dureeReelle;
    private List<Presence> listePresences;

    public RapportSeance(Seance seance) {
        this.seance = seance;
        this.cours = seance.getCours();
        this.professeur = cours.getProfesseur();
        this.dateGeneration = LocalDateTime.now();
        this.listePresences = seance.getListePresence().getPresences();
        this.nombreTotalEtudiants = cours.getGroupe().getNombreEtudiants();
        this.nombrePresents = seance.getListePresence().getNombrePresents();
        this.nombreAbsents = seance.getListePresence().getNombreAbsents();
        // Calculate real duration
        if (seance.getDateDebut() != null && seance.getDateFin() != null) {
            this.dureeReelle = (int) java.time.Duration.between(seance.getDateDebut(), seance.getDateFin()).toMinutes();
        } else {
            this.dureeReelle = cours.getDuree();
        }
    }

    public void generer() {
        // Logic to generate the report
    }

    public File exporter(String format) {
        // Logic to export the report
        return new File("rapport_seance." + format);
    }

    public String afficherDetails() {
        return "Rapport de séance - " + cours.getInformations() +
               "\nTotal étudiants: " + nombreTotalEtudiants +
               "\nPrésents: " + nombrePresents +
               "\nAbsents: " + nombreAbsents +
               "\nDurée réelle: " + dureeReelle + " min";
    }

    // Getters and setters
    public Seance getSeance() { return seance; }
    public void setSeance(Seance seance) { this.seance = seance; }
    public Cours getCours() { return cours; }
    public void setCours(Cours cours) { this.cours = cours; }
    public Professeur getProfesseur() { return professeur; }
    public void setProfesseur(Professeur professeur) { this.professeur = professeur; }
    public LocalDateTime getDateGeneration() { return dateGeneration; }
    public void setDateGeneration(LocalDateTime dateGeneration) { this.dateGeneration = dateGeneration; }
    public int getNombreTotalEtudiants() { return nombreTotalEtudiants; }
    public void setNombreTotalEtudiants(int nombreTotalEtudiants) { this.nombreTotalEtudiants = nombreTotalEtudiants; }
    public int getNombrePresents() { return nombrePresents; }
    public void setNombrePresents(int nombrePresents) { this.nombrePresents = nombrePresents; }
    public int getNombreAbsents() { return nombreAbsents; }
    public void setNombreAbsents(int nombreAbsents) { this.nombreAbsents = nombreAbsents; }
    public int getDureeReelle() { return dureeReelle; }
    public void setDureeReelle(int dureeReelle) { this.dureeReelle = dureeReelle; }
    public List<Presence> getListePresences() { return listePresences; }
    public void setListePresences(List<Presence> listePresences) { this.listePresences = listePresences; }
}