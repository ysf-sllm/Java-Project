// TODO: Implement full attributes and methods as per README.md
package Models;

public class Etudiant extends Utilisateur {
    private String numeroEtudiant;
    private String niveau;
    private Groupe groupe;
    private FichePresence fichePresence;

    public Etudiant(String id, String nom, String prenom, String email, String empreinte, String numeroEtudiant, String niveau, Groupe groupe) {
        super(id, nom, prenom, email, empreinte);
        this.numeroEtudiant = numeroEtudiant;
        this.niveau = niveau;
        this.groupe = groupe;
        this.fichePresence = new FichePresence(this);
    }

    public boolean scannerEntree(Cours cours) {
        // Logic to scan entry
        return true;
    }

    public boolean scannerSortie(Cours cours) {
        // Logic to scan exit
        return true;
    }

    public int getNombreAbsences(Matiere matiere) {
        return fichePresence.getNombreAbsences(matiere);
    }

    // Getters and setters
    public String getNumeroEtudiant() { return numeroEtudiant; }
    public void setNumeroEtudiant(String numeroEtudiant) { this.numeroEtudiant = numeroEtudiant; }
    public String getNiveau() { return niveau; }
    public void setNiveau(String niveau) { this.niveau = niveau; }
    public Groupe getGroupe() { return groupe; }
    public void setGroupe(Groupe groupe) { this.groupe = groupe; }
    public FichePresence getFichePresence() { return fichePresence; }
    public void setFichePresence(FichePresence fichePresence) { this.fichePresence = fichePresence; }
}