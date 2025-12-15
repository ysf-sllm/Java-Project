package model;

public abstract class Utilisateur {
    
    // Attributs
    private String id;
    private String nom;
    private String prenom;
    private String email;
    private String empreinte;
    
   
    
 // Constructeur par défaut
    public Utilisateur() { 
    	
    } 
    
    // Constructeur
    public Utilisateur(String id, String nom, String prenom, String email, String empreinte) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.empreinte = empreinte;
    }
    
    // Méthodes
    
    /**
     * Authentifie l'utilisateur avec son empreinte
     * @param empreinte L'empreinte à vérifier
     * @return true si l'empreinte correspond, false sinon
     */
    public boolean authentifier(String empreinte) {
        if (empreinte == null || this.empreinte == null) {
            return false;
        }
        return this.empreinte.equals(empreinte);
    }
    
    /**
     * Retourne l'identifiant de l'utilisateur
     * @return L'ID
     */
    public String getId() {
        return id;
    }
    
    /**
     * Retourne le nom complet de l'utilisateur
     * @return Le nom complet (prénom + nom)
     */
    public String getNomComplet() {
        return prenom + " " + nom;
    }
    
    // Getters et Setters
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getNom() {
        return nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public String getPrenom() {
        return prenom;
    }
    
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getEmpreinte() {
        return empreinte;
    }
    
    public void setEmpreinte(String empreinte) {
        this.empreinte = empreinte;
    }
    
    @Override
    public String toString() {
    	return getNomComplet() + " (" + id + ")";
    }
}
