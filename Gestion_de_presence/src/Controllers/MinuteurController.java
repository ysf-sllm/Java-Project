package Controllers;

/**
 * Controller for managing the timer (Minuteur).
 * Handles starting, stopping, pausing, and getting remaining time.
 *
 * TODO: Implement the model classes (Utilisateur, Professeur, Etudiant, Cours, Seance, etc.)
 * with full attributes and methods as described in README.md
 */
public class MinuteurController {

    private int tempsRestant = 0;
    private boolean estActif = false;

    /**
     * Starts the timer with the given duration.
     * @param duree the duration in minutes
     */
    public void demarrerMinuteur(int duree) {
        this.tempsRestant = duree;
        this.estActif = true;
    }

    /**
     * Stops the timer.
     */
    public void arreterMinuteur() {
        this.tempsRestant = 0;
        this.estActif = false;
    }

    /**
     * Gets the remaining time.
     * @return remaining time in minutes
     */
    public int getTempsRestant() {
        return tempsRestant;
    }

    /**
     * Pauses the timer.
     */
    public void pauseMinuteur() {
        this.estActif = false;
    }

}
