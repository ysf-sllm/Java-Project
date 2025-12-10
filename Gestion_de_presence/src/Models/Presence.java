// TODO: Implement full attributes and methods as per README.md
package Models;

import java.time.LocalDateTime;

public class Presence {
    private Etudiant etudiant;
    private LocalDateTime heureEntree;
    private LocalDateTime heureSortie;
    private boolean estPresent;
    private boolean estMarqueManuellement;

    public Presence(Etudiant etudiant) {
        this.etudiant = etudiant;
        this.estPresent = false;
        this.estMarqueManuellement = false;
    }

    public void marquerEntree(LocalDateTime heure) {
        this.heureEntree = heure;
        this.estPresent = true;
    }

    public void marquerSortie(LocalDateTime heure) {
        this.heureSortie = heure;
    }

    public boolean validerPresence() {
        return heureEntree != null && heureSortie != null;
    }

    public boolean isComplet() {
        return heureEntree != null && heureSortie != null;
    }

    // Getters and setters
    public Etudiant getEtudiant() { return etudiant; }
    public void setEtudiant(Etudiant etudiant) { this.etudiant = etudiant; }
    public LocalDateTime getHeureEntree() { return heureEntree; }
    public void setHeureEntree(LocalDateTime heureEntree) { this.heureEntree = heureEntree; }
    public LocalDateTime getHeureSortie() { return heureSortie; }
    public void setHeureSortie(LocalDateTime heureSortie) { this.heureSortie = heureSortie; }
    public boolean isEstPresent() { return estPresent; }
    public void setEstPresent(boolean estPresent) { this.estPresent = estPresent; }
    public boolean isEstMarqueManuellement() { return estMarqueManuellement; }
    public void setEstMarqueManuellement(boolean estMarqueManuellement) { this.estMarqueManuellement = estMarqueManuellement; }
}