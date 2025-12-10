// TODO: Implement full attributes and methods as per README.md
package Models;

import java.util.List;
import java.util.ArrayList;

public class Groupe {
    private String id;
    private String nom;
    private String niveau;
    private List<Etudiant> etudiants;
    private int nombreEtudiants;

    public Groupe(String id, String nom, String niveau) {
        this.id = id;
        this.nom = nom;
        this.niveau = niveau;
        this.etudiants = new ArrayList<>();
        this.nombreEtudiants = 0;
    }

    public List<Etudiant> getEtudiants() {
        return etudiants;
    }

    public int getNombreEtudiants() {
        return nombreEtudiants;
    }

    public void ajouterEtudiant(Etudiant etudiant) {
        if (!etudiants.contains(etudiant)) {
            etudiants.add(etudiant);
            nombreEtudiants++;
        }
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getNiveau() { return niveau; }
    public void setNiveau(String niveau) { this.niveau = niveau; }
}