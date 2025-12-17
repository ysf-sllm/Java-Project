package fr.presenceuniversitaire.controllers;

import fr.presenceuniversitaire.entities.*;
import fr.presenceuniversitaire.services.*;
import fr.presenceuniversitaire.dao.*;
import fr.presenceuniversitaire.export.*;
import javafx.collections.*;
import javafx.concurrent.Task;
import java.time.LocalDate;
import java.util.*;
import java.io.File;

/**
 * Contrôleur pour le dashboard administrateur
 */
public class AdminController {
    
    private final PresenceService presenceService;
    private final EmploiDuTempsService emploiDuTempsService;
    private final EtudiantDAO etudiantDAO;
    private final CoursDAO coursDAO;
    private final ClasseDAO classeDAO;
    
    // Propriétés observables
    private final ObservableList<Classe> classesObservable;
    private final ObservableList<Etudiant> etudiantsObservable;
    private final ObservableList<RapportPresence> rapportsObservable;
    
    public AdminController(PresenceService presenceService,
                          EmploiDuTempsService emploiDuTempsService,
                          EtudiantDAO etudiantDAO,
                          CoursDAO coursDAO,
                          ClasseDAO classeDAO) {
        
        this.presenceService = presenceService;
        this.emploiDuTempsService = emploiDuTempsService;
        this.etudiantDAO = etudiantDAO;
        this.coursDAO = coursDAO;
        this.classeDAO = classeDAO;
        
        this.classesObservable = FXCollections.observableArrayList();
        this.etudiantsObservable = FXCollections.observableArrayList();
        this.rapportsObservable = FXCollections.observableArrayList();
    }
    
    public void chargerDashboard() {
        // Charger les données initiales
        chargerClasses();
        chargerEtudiants();
        chargerStatistiquesGlobales();
    }
    
    public Task<Void> synchroniserDonnees() {
        return new Task<>() {
            @Override
            protected Void call()