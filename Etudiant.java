// Etudiant.java
package model;

import java.util.HashMap;
import java.util.Map;

public class Etudiant extends Utilisateur {
    private String numeroEtudiant;
    private String niveau;
    private Groupe groupe;
    private FichePresence fichePresence;
    private Map<Cours, Boolean> presenceCours; // Pour suivre la présence par cours
    
    // Constructeur
    public Etudiant(String id, String nom, String prenom, String email, 
                   String empreinte, String numeroEtudiant, String niveau, Groupe groupe) {
        super(id, nom, prenom, email, empreinte);
        this.numeroEtudiant = numeroEtudiant;
        this.niveau = niveau;
        this.groupe = groupe;
        this.fichePresence = new FichePresence(this);
        this.presenceCours = new HashMap<>();
    }
    
    // Méthode pour scanner l'entrée à un cours
    public boolean scannerEntree(Cours cours) {
        if (cours != null) {
            presenceCours.put(cours, true);
            fichePresence.ajouterPresence(cours);
            System.out.println(getNomComplet() + " a scanné son entrée pour le cours: " + cours.getMatiere().getNom());
            return true;
        }
        return false;
    }
    
    // Méthode pour scanner la sortie d'un cours
    public boolean scannerSortie(Cours cours) {
        if (cours != null && presenceCours.containsKey(cours)) {
            // Marquer la sortie
            System.out.println(getNomComplet() + " a scanné sa sortie pour le cours: " + cours.getMatiere().getNom());
            return true;
        }
        return false;
    }
    
    // Méthode pour obtenir le nombre d'absences pour une matière
    public int getNombreAbsences(Matiere matiere) {
        return fichePresence.getNombreAbsences(matiere);
    }
    
    // Getters et Setters
    public String getNumeroEtudiant() {
        return numeroEtudiant;
    }
    
    public void setNumeroEtudiant(String numeroEtudiant) {
        this.numeroEtudiant = numeroEtudiant;
    }
    
    public String getNiveau() {
        return niveau;
    }
    
    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }
    
    public Groupe getGroupe() {
        return groupe;
    }
    
    public void setGroupe(Groupe groupe) {
        this.groupe = groupe;
    }
    
    public FichePresence getFichePresence() {
        return fichePresence;
    }
    
    public void setFichePresence(FichePresence fichePresence) {
        this.fichePresence = fichePresence;
    }
    
    public boolean estPresent(Cours cours) {
        return presenceCours.getOrDefault(cours, false);
    }
    
    @Override
    public String toString() {
        return getNomComplet() + " - " + numeroEtudiant + " - " + niveau;
    }
}