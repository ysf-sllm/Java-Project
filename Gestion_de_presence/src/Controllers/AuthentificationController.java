package Controllers;

import Models.Professeur;
import java.util.*;

/**
 * Controller for handling professor authentication.
 * Manages login, logout, and session verification.
 *
 * TODO: Implement the model classes (Utilisateur, Professeur, Etudiant, Cours, Seance, etc.)
 * with full attributes and methods as described in README.md
 */
public class AuthentificationController {

    private List<Professeur> professeurs = new ArrayList<>();
    private Professeur currentProfesseur = null;

    /**
     * Constructor that initializes the controller with dummy professors.
     * In a real application, this would load from a database.
     */
    public AuthentificationController() {
        // Initialize some dummy professeurs
        professeurs.add(new Professeur("1", "Dupont", "Jean", "jean.dupont@email.com", "empreinte1", "Informatique"));
        professeurs.add(new Professeur("2", "Martin", "Marie", "marie.martin@email.com", "empreinte2", "Mathématiques"));
    }

    /**
     * Authenticates a professor using their fingerprint.
     * @param empreinte the fingerprint string to match
     * @return the authenticated Professeur object if found, null otherwise
     */
    public Professeur authentifier(String empreinte) {
        for (Professeur prof : professeurs) {
            if (prof.authentifier(empreinte)) {
                currentProfesseur = prof;
                return prof;
            }
        }
        return null;
    }

    /**
     * Logs out the current professor by clearing the session.
     */
    public void deconnecter() {
        currentProfesseur = null;
    }

    /**
     * Verifies if there is an active session.
     * @return true if a professor is logged in, false otherwise
     */
    public boolean verifierSession() {
        return currentProfesseur != null;
    }

}
