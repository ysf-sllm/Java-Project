// TODO: Implement full attributes and methods as per README.md
package Models;

public class StatistiquesSeance {
    private int nombrePresents;
    private int nombreAbsents;
    private double tauxPresence;
    private int retards;

    public StatistiquesSeance() {
        this.nombrePresents = 0;
        this.nombreAbsents = 0;
        this.tauxPresence = 0.0;
        this.retards = 0;
    }

    public void calculer(ListePresence listePresence) {
        this.nombrePresents = listePresence.getNombrePresents();
        this.nombreAbsents = listePresence.getNombreAbsents();
        int total = nombrePresents + nombreAbsents;
        if (total > 0) {
            this.tauxPresence = (double) nombrePresents / total * 100;
        }
        // Calculate retards - simplified
        this.retards = 0; // TODO: implement retards calculation
    }

    public double getTauxPresence() {
        return tauxPresence;
    }

    // Getters and setters
    public int getNombrePresents() { return nombrePresents; }
    public void setNombrePresents(int nombrePresents) { this.nombrePresents = nombrePresents; }
    public int getNombreAbsents() { return nombreAbsents; }
    public void setNombreAbsents(int nombreAbsents) { this.nombreAbsents = nombreAbsents; }
    public int getRetards() { return retards; }
    public void setRetards(int retards) { this.retards = retards; }
}