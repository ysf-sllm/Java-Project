// TODO: Implement full attributes and methods as per README.md
package Models;

import java.time.LocalDateTime;

public class Seance {
    private String id;
    private Cours cours;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private ListePresence listePresence;
    private Minuteur minuteur;
    private boolean estActive;

    public Seance(String id, Cours cours) {
        this.id = id;
        this.cours = cours;
        this.listePresence = new ListePresence(this);
        this.minuteur = new Minuteur(cours.getDuree());
        this.estActive = false;
    }

    public void enregistrerEntree(Etudiant etudiant, LocalDateTime heureEntree) {
        Presence presence = new Presence(etudiant);
        presence.marquerEntree(heureEntree);
        listePresence.ajouterPresence(presence);
    }

    public void enregistrerSortie(Etudiant etudiant, LocalDateTime heureSortie) {
        // Find existing presence and mark exit
        for (Presence p : listePresence.getPresences()) {
            if (p.getEtudiant().equals(etudiant)) {
                p.marquerSortie(heureSortie);
                break;
            }
        }
    }

    public StatistiquesSeance calculerStatistiques() {
        StatistiquesSeance stats = new StatistiquesSeance();
        stats.calculer(listePresence);
        return stats;
    }

    public void demarrer() {
        this.estActive = true;
        this.dateDebut = LocalDateTime.now();
        minuteur.demarrer();
    }

    public void arreter() {
        this.estActive = false;
        this.dateFin = LocalDateTime.now();
        minuteur.arreter();
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public Cours getCours() { return cours; }
    public void setCours(Cours cours) { this.cours = cours; }
    public LocalDateTime getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDateTime dateDebut) { this.dateDebut = dateDebut; }
    public LocalDateTime getDateFin() { return dateFin; }
    public void setDateFin(LocalDateTime dateFin) { this.dateFin = dateFin; }
    public ListePresence getListePresence() { return listePresence; }
    public void setListePresence(ListePresence listePresence) { this.listePresence = listePresence; }
    public Minuteur getMinuteur() { return minuteur; }
    public void setMinuteur(Minuteur minuteur) { this.minuteur = minuteur; }
    public boolean isEstActive() { return estActive; }
    public void setEstActive(boolean estActive) { this.estActive = estActive; }
}