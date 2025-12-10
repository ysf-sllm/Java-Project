// TODO: Implement full attributes and methods as per README.md
package Models;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class FichePresence {
    private Etudiant etudiant;
    private Map<Matiere, List<Presence>> presences;
    private Map<Matiere, Double> tauxPresence;

    public FichePresence(Etudiant etudiant) {
        this.etudiant = etudiant;
        this.presences = new HashMap<>();
        this.tauxPresence = new HashMap<>();
    }

    public void ajouterPresence(Matiere matiere, Presence presence) {
        presences.computeIfAbsent(matiere, k -> new ArrayList<>()).add(presence);
        calculerTauxPresence(matiere);
    }

    public double calculerTauxPresence(Matiere matiere) {
        List<Presence> presencesMatiere = presences.get(matiere);
        if (presencesMatiere == null || presencesMatiere.isEmpty()) {
            return 0.0;
        }
        int total = presencesMatiere.size();
        int presents = 0;
        for (Presence p : presencesMatiere) {
            if (p.isEstPresent()) {
                presents++;
            }
        }
        double taux = (double) presents / total * 100;
        tauxPresence.put(matiere, taux);
        return taux;
    }

    public int getNombreAbsences(Matiere matiere) {
        List<Presence> presencesMatiere = presences.get(matiere);
        if (presencesMatiere == null) {
            return 0;
        }
        int absences = 0;
        for (Presence p : presencesMatiere) {
            if (!p.isEstPresent()) {
                absences++;
            }
        }
        return absences;
    }

    public boolean verifierSeuilAlerte(Matiere matiere) {
        return getNombreAbsences(matiere) >= matiere.getSeuilAbsence();
    }

    // Getters and setters
    public Etudiant getEtudiant() { return etudiant; }
    public void setEtudiant(Etudiant etudiant) { this.etudiant = etudiant; }
    public Map<Matiere, List<Presence>> getPresences() { return presences; }
    public void setPresences(Map<Matiere, List<Presence>> presences) { this.presences = presences; }
    public Map<Matiere, Double> getTauxPresence() { return tauxPresence; }
    public void setTauxPresence(Map<Matiere, Double> tauxPresence) { this.tauxPresence = tauxPresence; }
}