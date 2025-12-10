// TODO: Implement full attributes and methods as per README.md
package Models;

import java.time.LocalDateTime;

public class Minuteur {
    private int dureeInitiale; // en minutes
    private int tempsEcoule;
    private int tempsRestant;
    private boolean estActif;
    private LocalDateTime dateDebut;

    public Minuteur(int dureeInitiale) {
        this.dureeInitiale = dureeInitiale;
        this.tempsEcoule = 0;
        this.tempsRestant = dureeInitiale;
        this.estActif = false;
    }

    public void demarrer() {
        if (!estActif) {
            this.estActif = true;
            this.dateDebut = LocalDateTime.now();
        }
    }

    public void arreter() {
        this.estActif = false;
        this.tempsRestant = 0;
        this.tempsEcoule = dureeInitiale;
    }

    public void pause() {
        this.estActif = false;
    }

    public void reprendre() {
        if (!estActif && tempsRestant > 0) {
            this.estActif = true;
        }
    }

    public int getTempsRestant() {
        return tempsRestant;
    }

    public int getTempsEcoule() {
        return tempsEcoule;
    }

    // Method to update timer (should be called periodically)
    public void tick() {
        if (estActif && tempsRestant > 0) {
            tempsRestant--;
            tempsEcoule++;
        }
    }

    // Getters and setters
    public int getDureeInitiale() { return dureeInitiale; }
    public void setDureeInitiale(int dureeInitiale) { this.dureeInitiale = dureeInitiale; }
    public boolean isEstActif() { return estActif; }
    public void setEstActif(boolean estActif) { this.estActif = estActif; }
    public LocalDateTime getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDateTime dateDebut) { this.dateDebut = dateDebut; }
}