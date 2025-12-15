
package model;

public class Matiere {
    // Attributs
    private String id;
    private String nom;
    private String code;
    private int nombreHeuresAbsencesMax;
    private double coefficient;
    
    // Constructeur
    public Matiere(String id, String nom, String code, int nombreHeuresAbsencesMax, double coefficient) {
        this.id = id;
        this.nom = nom;
        this.code = code;
        this.nombreHeuresAbsencesMax = nombreHeuresAbsencesMax;
        this.coefficient = coefficient;
    }
    
    // Getters
    public String getId() {
        return id;
    }
    
    public String getNom() {
        return nom;
    }
    
    public String getCode() {
        return code;
    }
    
    public int getSeuilAbsence() {
        return nombreHeuresAbsencesMax;
    }
    
    public double getCoefficient() {
        return coefficient;
    }
    
    public int getNombreHeuresAbsencesMax() {
        return nombreHeuresAbsencesMax;
    }
    
    // Setters
    public void setId(String id) {
        this.id = id;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public void setNombreHeuresAbsencesMax(int nombreHeuresAbsencesMax) {
        this.nombreHeuresAbsencesMax = nombreHeuresAbsencesMax;
    }
    
    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }
    
    // Méthode pour vérifier si un nombre d'absences dépasse le seuil
    public boolean depasseSeuilAbsence(int nombreAbsences) {
        return nombreAbsences > nombreHeuresAbsencesMax;
    }
    
    @Override
    public String toString() {
        return nom + " (" + code + ") - Coef: " + coefficient + " - Seuil absences: " + nombreHeuresAbsencesMax + "h";
    }
}