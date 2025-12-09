Architecture MVC pour Système de Gestion de Présence par Empreinte
📋 MODÈLE (Model) - Classes Métier
1. Utilisateur (classe abstraite)
Attributs :

id : String
nom : String
prenom : String
email : String
empreinte : String (représentation de l'empreinte digitale)

Méthodes :

authentifier(empreinte: String) : boolean
getId() : String
getNomComplet() : String


2. Professeur (hérite de Utilisateur)
Attributs :

departement : String
emploiDuTemps : List<Cours>
coursActuel : Cours

Méthodes :

getCoursDisponibles(date: Date) : List<Cours>
demarrerCours(cours: Cours) : void
arreterCours() : void
marquerSortieManuelle(etudiant: Etudiant) : void
genererRapport() : RapportSeance


3. Etudiant (hérite de Utilisateur)
Attributs :

numeroEtudiant : String
niveau : String
groupe : Groupe
fichePresence : FichePresence

Méthodes :

scannerEntree(cours: Cours) : boolean
scannerSortie(cours: Cours) : boolean
getNombreAbsences(matiere: Matiere) : int


4. Cours
Attributs :

id : String
matiere : Matiere
professeur : Professeur
groupe : Groupe
dateDebut : DateTime
dateFin : DateTime
duree : int (en minutes, par défaut 90)
statut : StatutCours (PLANIFIE, EN_COURS, TERMINE)
typeSeance : TypeSeance (PRESENTIEL, EN_LIGNE)

Méthodes :

demarrer() : void
terminer() : void
getDureeRestante() : int
getInformations() : String


5. Matiere
Attributs :

id : String
nom : String
code : String
nombreHeuresAbsencesMax : int
coefficient : double

Méthodes :

getNom() : String
getSeuilAbsence() : int


6. Groupe
Attributs :

id : String
nom : String
niveau : String
etudiants : List<Etudiant>
nombreEtudiants : int

Méthodes :

getEtudiants() : List<Etudiant>
getNombreEtudiants() : int
ajouterEtudiant(etudiant: Etudiant) : void


7. Seance
Attributs :

id : String
cours : Cours
dateDebut : DateTime
dateFin : DateTime
listePresence : ListePresence
minuteur : Minuteur
estActive : boolean

Méthodes :

enregistrerEntree(etudiant: Etudiant, heureEntree: DateTime) : void
enregistrerSortie(etudiant: Etudiant, heureSortie: DateTime) : void
calculerStatistiques() : StatistiquesSeance
demarrer() : void
arreter() : void


8. ListePresence
Attributs :

seance : Seance
presences : List<Presence>
nombrePresents : int
nombreAbsents : int

Méthodes :

ajouterPresence(presence: Presence) : void
verifierPresence() : void
getEtudiantsPresents() : List<Etudiant>
getEtudiantsAbsents() : List<Etudiant>
calculerStatistiques() : void


9. Presence
Attributs :

etudiant : Etudiant
heureEntree : DateTime
heureSortie : DateTime
estPresent : boolean
estMarqueManuellement : boolean

Méthodes :

marquerEntree(heure: DateTime) : void
marquerSortie(heure: DateTime) : void
validerPresence() : boolean
isComplet() : boolean


10. RapportSeance
Attributs :

seance : Seance
cours : Cours
professeur : Professeur
dateGeneration : DateTime
nombreTotalEtudiants : int
nombrePresents : int
nombreAbsents : int
dureeReelle : int
listePresences : List<Presence>

Méthodes :

generer() : void
exporter(format: String) : File
afficherDetails() : String


11. FichePresence
Attributs :

etudiant : Etudiant
presences : Map<Matiere, List<Presence>>
tauxPresence : Map<Matiere, double>

Méthodes :

ajouterPresence(matiere: Matiere, presence: Presence) : void
calculerTauxPresence(matiere: Matiere) : double
getNombreAbsences(matiere: Matiere) : int
verifierSeuilAlerte(matiere: Matiere) : boolean


12. Minuteur
Attributs :

dureeInitiale : int (en minutes)
tempsEcoule : int
tempsRestant : int
estActif : boolean
dateDebut : DateTime

Méthodes :

demarrer() : void
arreter() : void
pause() : void
reprendre() : void
getTempsRestant() : int
getTempsEcoule() : int


13. StatistiquesSeance
Attributs :

nombrePresents : int
nombreAbsents : int
tauxPresence : double
retards : int

Méthodes :

calculer(listePresence: ListePresence) : void
getTauxPresence() : double


14. AppareilEmpreinte (Singleton)
Attributs :

instance : AppareilEmpreinte (static)
estConnecte : boolean
dernierScan : DateTime

Méthodes :

getInstance() : AppareilEmpreinte
connecter() : boolean
deconnecter() : void
scannerEmpreinte() : String
verifierConnexion() : boolean


15. Enums
StatutCours :

PLANIFIE
EN_COURS
TERMINE
ANNULE

TypeSeance :

PRESENTIEL
EN_LIGNE


🎮 CONTRÔLEUR (Controller)
1. AuthentificationController
Méthodes :

authentifier(empreinte: String) : Professeur
deconnecter() : void
verifierSession() : boolean


2. CoursController
Méthodes :

getCoursDisponibles(professeur: Professeur, date: Date) : List<Cours>
selectionnerCours(cours: Cours) : void
demarrerCours(cours: Cours) : Seance
arreterCours(seance: Seance) : void


3. SeanceController
Méthodes :

lancerSeance(cours: Cours) : Seance
enregistrerEntree(empreinte: String) : boolean
enregistrerSortie(empreinte: String) : boolean
marquerSortieManuelle(etudiant: Etudiant) : void
getStatistiquesEnTempsReel() : StatistiquesSeance
terminerSeance() : void


4. PresenceController
Méthodes :

verifierPresences(seance: Seance) : void
getListePresents() : List<Etudiant>
getListeAbsents() : List<Etudiant>
modifierPresence(etudiant: Etudiant, statut: boolean) : void


5. RapportController
Méthodes :

genererRapport(seance: Seance) : RapportSeance
exporterRapport(rapport: RapportSeance, format: String) : File
afficherRapport(rapport: RapportSeance) : void


6. MinuteurController
Méthodes :

demarrerMinuteur(duree: int) : void
arreterMinuteur() : void
getTempsRestant() : int
pauseMinuteur() : void


🖼️ VUE (View) - Interfaces Graphiques
Interface 1 : VueAuthentification
Contenu :

Zone de scan d'empreinte (animation de scan)
Message de bienvenue
Indicateur de connexion de l'appareil
Bouton "Scanner l'empreinte"
Message d'erreur/succès


Interface 2 : VueSelectionCours
Contenu :

Informations du professeur connecté (nom, photo)
Liste déroulante/grille des cours disponibles
Pour chaque cours :

Nom de la matière
Groupe concerné
Niveau
Horaire
Nombre d'étudiants
Durée


Bouton "Sélectionner le cours"
Bouton "Se déconnecter"


Interface 3 : VueDetailsCours
Contenu :

En-tête avec informations du cours :

Nom de la matière
Groupe
Niveau
Nombre total d'étudiants
Durée


Bouton principal : "Lancer le cours" (grand, visible)
Bouton "Retour" vers sélection


Interface 4 : VueSeanceEnCours
Contenu :

Section supérieure :

Informations du cours (matière, groupe)
Minuteur grand format (temps restant : HH:MM:SS)
Indicateur visuel du temps (barre de progression)


Section centrale - Statistiques en temps réel :

Nombre total d'étudiants
Nombre de présents (avec icône verte)
Nombre d'absents (avec icône rouge)
Taux de présence en %


Section inférieure :

Zone de scan active (animation)
Liste en temps réel des étudiants qui scannent (nom + heure)
Notification visuelle/sonore à chaque scan


Boutons d'action :

"Vérifier la présence"
"Arrêter le cours"




Interface 5 : VueVerificationPresence
Contenu :

Deux listes côte à côte :
Liste des présents :

Nom de l'étudiant
Heure d'entrée
Heure de sortie
Icône de validation (✓)

Liste des absents :

Nom de l'étudiant
Bouton "Marquer sortie manuelle"


Filtres :

Rechercher un étudiant
Trier par nom/heure


Statistiques résumées :

Total présents / Total absents
Pourcentage de présence


Boutons :

"Confirmer et arrêter le cours"
"Continuer le cours"




Interface 6 : VueRapportSeance
Contenu :

En-tête du rapport :

Titre "Rapport de séance"
Date et heure de la séance
Professeur
Matière et groupe
Durée réelle de la séance


Statistiques globales :

Nombre total d'étudiants
Nombre de présents (pourcentage)
Nombre d'absents (pourcentage)
Taux de présence global


Tableau détaillé des présences :
| Nom Étudiant | Heure Entrée | Heure Sortie | Statut | Remarque |
Section des absents :

Liste complète des étudiants absents


Boutons d'action :

"Exporter en PDF"
"Exporter en Excel"
"Imprimer"
"Retour à l'accueil"




Interface 7 : VueGestionSortieManuelle
Contenu :

Liste des étudiants présents sans sortie enregistrée
Pour chaque étudiant :

Nom et photo
Heure d'entrée
Case à cocher "Marquer la sortie"
Champ "Heure de sortie" (modifiable)


Bouton "Valider les sorties"
Bouton "Annuler"


🔗 Interfaces (au sens Java - Contrats)
1. IAuthentifiable
Méthodes :

authentifier(identifiant: String) : boolean
deconnecter() : void

Implémenté par : Professeur, Etudiant

2. IScannable
Méthodes :

scannerEmpreinte() : String
validerEmpreinte(empreinte: String) : boolean

Implémenté par : AppareilEmpreinte

3. IGenerateurRapport
Méthodes :

genererRapport() : RapportSeance
exporterRapport(format: String) : File

Implémenté par : RapportSeance

4. IObservateur (Pattern Observer)
Méthodes :

actualiser(donnees: Object) : void

Implémenté par : Toutes les vues pour recevoir les mises à jour en temps réel

5. IGestionnaireTemps
Méthodes :

demarrer() : void
arreter() : void
pause() : void
getTempsRestant() : int

Implémenté par : Minuteur

📊 Récapitulatif
Nombre total d'interfaces graphiques : 7

VueAuthentification
VueSelectionCours
VueDetailsCours
VueSeanceEnCours
VueVerificationPresence
VueRapportSeance
VueGestionSortieManuelle

Nombre d'interfaces Java (contrats) : 5

IAuthentifiable
IScannable
IGenerateurRapport
IObservateur
IGestionnaireTemps


