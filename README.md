# Java-Project
Statistiques du Projet
• 15 Classes Modèle
• 6 Contrôleurs
• 7 Interfaces Vue
• 5 Interfaces Java
MODÈLE - Classes Métier
• Utilisateur (Abstraite)
Classe de base avec id, nom, prenom, email, empreinte
• Professeur
Hérite de Utilisateur. Gère departement, emploiDuTemps, coursActuel
• Etudiant
Hérite de Utilisateur. Gère numeroEtudiant, niveau, groupe, fichePresence
• Cours
Gère matiere, professeur, groupe, dateDebut, dateFin, duree, statut
• Matiere
Contient nom, code, nombreHeuresAbsencesMax, coefficient
• Groupe
Gère la liste des etudiants, nom, niveau, nombreEtudiants
• Seance
Gère le cours, dateDebut, dateFin, listePresence, minuteur
• ListePresence
Contient presences, nombrePresents, nombreAbsents
• Presence
Enregistre heureEntree, heureSortie, estPresent
• RapportSeance
Génère et exporte les rapports de séance
• FichePresence
Suit les presences par matiere et calcule le tauxPresence
• Minuteur
Gère dureeInitiale, tempsEcoule, tempsRestant
• AppareilEmpreinte (Singleton)
Gère la connexion et le scan des empreintes
Page 1 / 2
Architecture MVC - Système de Gestion de Présence
CONTRÔLEUR
• AuthentificationController
Gestion authentification et sessions
• CoursController
Sélection et démarrage des cours
• SeanceController
Gestion séances et scans empreintes
• PresenceController
Vérification et modification présences
• RapportController
Génération et export rapports
• MinuteurController
Gestion du temps de séance
VUE - Interfaces Graphiques
• VueAuthentification - Connexion par empreinte
• VueSelectionCours - Liste des cours disponibles
• VueDetailsCours - Détails avant lancement
• VueSeanceEnCours - Suivi temps réel avec minuteur
• VueVerificationPresence - Gestion manuelle présences
• VueRapportSeance - Rapport détaillé avec export
• VueGestionSortieManuelle - Gestion sorties
Interfaces Java (Contrats)
• IAuthentifiable - Professeur, Etudiant
• IScannable - AppareilEmpreinte
• IGenerateurRapport - RapportSeance
• IObservateur - Pattern Observer (mises à jour temps réel)
• IGestionnaireTemps - Minuteur
Patterns de Conception
• Singleton - AppareilEmpreinte
• Observer - Mises à jour en temps réel
• Factory - Création rapports
• Strategy - Types de séances
• MVC - Architecture globale