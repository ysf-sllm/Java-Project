package Controllers;

import Models.*;
import java.util.*;

/**
 * Controller for managing courses.
 * Handles course selection, starting, and stopping courses.
 *
 * TODO: Implement the model classes (Utilisateur, Professeur, Etudiant, Cours, Seance, etc.)
 * with full attributes and methods as described in README.md
 */
public class CoursController {

    private Cours selectedCours = null;

    /**
     * Retrieves the list of available courses for a professor on a given date.
     * @param professeur the professor
     * @param date the date
     * @return list of available courses
     */
    public List<Cours> getCoursDisponibles(Professeur professeur, Date date) {
        // Assuming professeur has the method, but since stub, return empty list
        return new ArrayList<>();
    }

    /**
     * Selects a course.
     * @param cours the course to select
     */
    public void selectionnerCours(Cours cours) {
        this.selectedCours = cours;
    }

    /**
     * Starts a course by creating a new session.
     * @param cours the course to start
     * @return the new Seance object
     */
    public Seance demarrerCours(Cours cours) {
        // Create a new Seance
        return new Seance();
    }

    /**
     * Stops a course session.
     * @param seance the session to stop
     */
    public void arreterCours(Seance seance) {
        // Call seance.arreter() if implemented
    }

}
