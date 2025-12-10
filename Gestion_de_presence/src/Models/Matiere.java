// TODO: Implement full attributes and methods as per README.md
package Models;

public class Matiere {
    private String id;
    private String nom;
    private String code;
    private int nombreHeuresAbsencesMax;
    private double coefficient;

    public Matiere(String id, String nom, String code, int nombreHeuresAbsencesMax, double coefficient) {
        this.id = id;
        this.nom = nom;
        this.code = code;
        this.nombreHeuresAbsencesMax = nombreHeuresAbsencesMax;
        this.coefficient = coefficient;
    }

    public String getNom() {
        return nom;
    }

    public int getSeuilAbsence() {
        return nombreHeuresAbsencesMax;
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public int getNombreHeuresAbsencesMax() { return nombreHeuresAbsencesMax; }
    public void setNombreHeuresAbsencesMax(int nombreHeuresAbsencesMax) { this.nombreHeuresAbsencesMax = nombreHeuresAbsencesMax; }
    public double getCoefficient() { return coefficient; }
    public void setCoefficient(double coefficient) { this.coefficient = coefficient; }
}