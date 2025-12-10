package Controllers;

import Models.*;
import java.util.*;

/**
 * Controller for managing student presences.
 * Handles verification, listing present/absent students, and modifying presence status.
 *
 * TODO: Implement the model classes (Utilisateur, Professeur, Etudiant, Cours, Seance, etc.)
 * with full attributes and methods as described in README.md
 */
public class PresenceController {

    /**
     * Verifies the presences for a session.
     * @param seance the session to verify
     */
    public void verifierPresences(Seance seance) {
        // Verify presences
    }

    /**
     * Gets the list of present students.
     * @return list of present students
     */
    public List<Etudiant> getListePresents() {
        return new ArrayList<>();
    }

    /**
     * Gets the list of absent students.
     * @return list of absent students
     */
    public List<Etudiant> getListeAbsents() {
        return new ArrayList<>();
    }

    /**
     * Modifies the presence status of a student.
     * @param etudiant the student
     * @param statut true for present, false for absent
     */
    public void modifierPresence(Etudiant etudiant, boolean statut) {
        // Modify presence status
    }

}
