// TODO: Implement full attributes and methods as per README.md
package Models;

public abstract class Utilisateur {
    protected String id;
    protected String nom;
    protected String prenom;
    protected String email;
    protected String empreinte;

    public Utilisateur(String id, String nom, String prenom, String email, String empreinte) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.empreinte = empreinte;
    }

    public boolean authentifier(String empreinte) {
        return this.empreinte.equals(empreinte);
    }

    public String getId() {
        return id;
    }

    public String getNomComplet() {
        return prenom + " " + nom;
    }
}