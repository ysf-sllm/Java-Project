// Cours.java
package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Cours {
    // Énumérations
    public enum StatutCours {
        PLANIFIE,
        EN_COURS,
        TERMINE
    }
    
    public enum TypeSeance {
        PRESENTIEL,
        EN_LIGNE
    }
    
    // Attributs
    private String id;
    private Matiere matiere;
    private Professeur professeur;
    private Groupe groupe;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private int duree; // en minutes, par défaut 90
    private StatutCours statut;
    private TypeSeance typeSeance;
    private List<Etudiant> etudiantsPresents;
    private List<Etudiant> etudiantsAbsents;
    
    // Constantes
    public static final int DUREE_PAR_DEFAUT = 90;
    
    // Constructeur
    public Cours(String id, Matiere matiere, Professeur professeur, Groupe groupe, 
                LocalDateTime dateDebut, TypeSeance typeSeance) {
        this.id = id;
        this.matiere = matiere;
        this.professeur = professeur;
        this.groupe = groupe;
        this.dateDebut = dateDebut;
        this.duree = DUREE_PAR_DEFAUT;
        this.dateFin = dateDebut.plusMinutes(duree);
        this.statut = StatutCours.PLANIFIE;
        this.typeSeance = typeSeance;
        this.etudiantsPresents = new ArrayList<>();
        this.etudiantsAbsents = new ArrayList<>();
    }
    
    // Méthode pour démarrer le cours
    public void demarrer() {
        if (statut == StatutCours.PLANIFIE) {
            statut = StatutCours.EN_COURS;
            dateDebut = LocalDateTime.now();
            dateFin = dateDebut.plusMinutes(duree);
            System.out.println("Cours démarré: " + matiere.getNom());
        }
    }
    
    // Méthode pour terminer le cours
    public void terminer() {
        if (statut == StatutCours.EN_COURS) {
            statut = StatutCours.TERMINE;
            // Calculer les absents
            calculerAbsents();
            System.out.println("Cours terminé: " + matiere.getNom());
        }
    }
    
    // Méthode pour obtenir la durée restante
    public int getDureeRestante() {
        if (statut != StatutCours.EN_COURS) {
            return 0;
        }
        
        LocalDateTime maintenant = LocalDateTime.now();
        long minutesRestantes = java.time.Duration.between(maintenant, dateFin).toMinutes();
        return (int) Math.max(0, minutesRestantes);
    }
    
    // Méthode pour obtenir les informations du cours
    public String getInformations() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        StringBuilder info = new StringBuilder();
        
        info.append("Cours: ").append(matiere.getNom()).append("\n");
        info.append("Professeur: ").append(professeur.getNomComplet()).append("\n");
        info.append("Groupe: ").append(groupe.getNom()).append("\n");
        info.append("Niveau: ").append(groupe.getNiveau()).append("\n");
        info.append("Nombre d'étudiants: ").append(groupe.getNombreEtudiants()).append("\n");
        info.append("Durée: ").append(duree).append(" minutes\n");
        info.append("Date: ").append(dateDebut.format(formatter)).append("\n");
        info.append("Type: ").append(typeSeance).append("\n");
        info.append("Statut: ").append(statut).append("\n");
        
        return info.toString();
    }
    
    // Méthode pour calculer les absents
    private void calculerAbsents() {
        etudiantsAbsents.clear();
        for (Etudiant etudiant : groupe.getEtudiants()) {
            if (!etudiantsPresents.contains(etudiant)) {
                etudiantsAbsents.add(etudiant);
            }
        }
    }
    
    // Méthode pour enregistrer la présence d'un étudiant
    public boolean enregistrerPresence(Etudiant etudiant) {
        if (statut == StatutCours.EN_COURS && !etudiantsPresents.contains(etudiant)) {
            etudiantsPresents.add(etudiant);
            return true;
        }
        return false;
    }
    
    // Méthode pour marquer la sortie manuelle
    public void marquerSortieManuelle(Etudiant etudiant) {
        // Logique pour marquer la sortie manuelle
        System.out.println("Sortie manuelle pour " + etudiant.getNomComplet());
    }
    
    // Getters et Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public Matiere getMatiere() {
        return matiere;
    }
    
    public void setMatiere(Matiere matiere) {
        this.matiere = matiere;
    }
    
    public Professeur getProfesseur() {
        return professeur;
    }
    
    public void setProfesseur(Professeur professeur) {
        this.professeur = professeur;
    }
    
    public Groupe getGroupe() {
        return groupe;
    }
    
    public void setGroupe(Groupe groupe) {
        this.groupe = groupe;
    }
    
    public LocalDateTime getDateDebut() {
        return dateDebut;
    }
    
    public void setDateDebut(LocalDateTime dateDebut) {
        this.dateDebut = dateDebut;
    }
    
    public LocalDateTime getDateFin() {
        return dateFin;
    }
    
    public void setDateFin(LocalDateTime dateFin) {
        this.dateFin = dateFin;
    }
    
    public int getDuree() {
        return duree;
    }
    
    public void setDuree(int duree) {
        this.duree = duree;
        if (dateDebut != null) {
            dateFin = dateDebut.plusMinutes(duree);
        }
    }
    
    public StatutCours getStatut() {
        return statut;
    }
    
    public void setStatut(StatutCours statut) {
        this.statut = statut;
    }
    
    public TypeSeance getTypeSeance() {
        return typeSeance;
    }
    
    public void setTypeSeance(TypeSeance typeSeance) {
        this.typeSeance = typeSeance;
    }
    
    public List<Etudiant> getEtudiantsPresents() {
        return new ArrayList<>(etudiantsPresents);
    }
    
    public List<Etudiant> getEtudiantsAbsents() {
        return new ArrayList<>(etudiantsAbsents);
    }
    
    public int getNombrePresents() {
        return etudiantsPresents.size();
    }
    
    public int getNombreAbsents() {
        return etudiantsAbsents.size();
    }
    
    @Override
    public String toString() {
        return matiere.getNom() + " - " + groupe.getNom() + " (" + statut + ")";
    }
}