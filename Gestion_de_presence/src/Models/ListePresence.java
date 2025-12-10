// TODO: Implement full attributes and methods as per README.md
package Models;

import java.util.List;
import java.util.ArrayList;

public class ListePresence {
    private Seance seance;
    private List<Presence> presences;
    private int nombrePresents;
    private int nombreAbsents;

    public ListePresence(Seance seance) {
        this.seance = seance;
        this.presences = new ArrayList<>();
        this.nombrePresents = 0;
        this.nombreAbsents = 0;
    }

    public void ajouterPresence(Presence presence) {
        presences.add(presence);
        calculerStatistiques();
    }

    public void verifierPresence() {
        // Logic to verify presences
        calculerStatistiques();
    }

    public List<Etudiant> getEtudiantsPresents() {
        List<Etudiant> presents = new ArrayList<>();
        for (Presence p : presences) {
            if (p.isEstPresent()) {
                presents.add(p.getEtudiant());
            }
        }
        return presents;
    }

    public List<Etudiant> getEtudiantsAbsents() {
        List<Etudiant> absents = new ArrayList<>();
        for (Presence p : presences) {
            if (!p.isEstPresent()) {
                absents.add(p.getEtudiant());
            }
        }
        return absents;
    }

    public void calculerStatistiques() {
        nombrePresents = 0;
        nombreAbsents = 0;
        for (Presence p : presences) {
            if (p.isEstPresent()) {
                nombrePresents++;
            } else {
                nombreAbsents++;
            }
        }
    }

    // Getters and setters
    public Seance getSeance() { return seance; }
    public void setSeance(Seance seance) { this.seance = seance; }
    public List<Presence> getPresences() { return presences; }
    public void setPresences(List<Presence> presences) { this.presences = presences; }
    public int getNombrePresents() { return nombrePresents; }
    public void setNombrePresents(int nombrePresents) { this.nombrePresents = nombrePresents; }
    public int getNombreAbsents() { return nombreAbsents; }
    public void setNombreAbsents(int nombreAbsents) { this.nombreAbsents = nombreAbsents; }
}