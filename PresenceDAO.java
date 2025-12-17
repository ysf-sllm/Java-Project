package fr.presenceuniversitaire.dao;

import fr.presenceuniversitaire.entities.*;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DAO spécifique pour la gestion des présences
 */
public class PresenceDAO extends GenericDAO<Presence> {
    
    private static PresenceDAO instance;
    
    private PresenceDAO() {
        super(Presence.class);
    }
    
    // Singleton pour éviter les multiples instances
    public static synchronized PresenceDAO getInstance() {
        if (instance == null) {
            instance = new PresenceDAO();
        }
        return instance;
    }
    
    // Méthodes spécifiques aux présences
    
    public List<Presence> trouverParEtudiantEtPeriode(Etudiant etudiant, 
                                                     LocalDate dateDebut, 
                                                     LocalDate dateFin) {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT p FROM Presence p WHERE p.etudiant = :etudiant " +
                         "AND p.horodatageEntree BETWEEN :debut AND :fin " +
                         "ORDER BY p.horodatageEntree";
            
            TypedQuery<Presence> query = em.createQuery(jpql, Presence.class);
            query.setParameter("etudiant", etudiant);
            query.setParameter("debut", dateDebut.atStartOfDay());
            query.setParameter("fin", dateFin.plusDays(1).atStartOfDay());
            
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<Presence> trouverParSeance(SeanceEmploiDuTemps seance) {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT p FROM Presence p WHERE p.seance = :seance " +
                         "ORDER BY p.etudiant.nom, p.etudiant.prenom";
            
            TypedQuery<Presence> query = em.createQuery(jpql, Presence.class);
            query.setParameter("seance", seance);
            
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<Presence> trouverParCoursEtDate(Cours cours, LocalDate date) {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT p FROM Presence p WHERE p.seance.cours = :cours " +
                         "AND DATE(p.horodatageEntree) = :date " +
                         "ORDER BY p.horodatageEntree";
            
            TypedQuery<Presence> query = em.createQuery(jpql, Presence.class);
            query.setParameter("cours", cours);
            query.setParameter("date", date);
            
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public boolean presenceExistePourEtudiantEtSeance(Etudiant etudiant, 
                                                     SeanceEmploiDuTemps seance, 
                                                     LocalDate date) {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT COUNT(p) FROM Presence p " +
                         "WHERE p.etudiant = :etudiant " +
                         "AND p.seance = :seance " +
                         "AND DATE(p.horodatageEntree) = :date";
            
            TypedQuery<Long> query = em.createQuery(jpql, Long.class);
            query.setParameter("etudiant", etudiant);
            query.setParameter("seance", seance);
            query.setParameter("date", date);
            
            return query.getSingleResult() > 0;
        } finally {
            em.close();
        }
    }
    
    public List<Presence> trouverRetardsParClasse(Classe classe, 
                                                 LocalDate dateDebut, 
                                                 LocalDate dateFin) {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT p FROM Presence p " +
                         "WHERE p.etudiant.classe = :classe " +
                         "AND p.statut = 'RETARD' " +
                         "AND p.horodatageEntree BETWEEN :debut AND :fin " +
                         "ORDER BY p.horodatageEntree DESC";
            
            TypedQuery<Presence> query = em.createQuery(jpql, Presence.class);
            query.setParameter("classe", classe);
            query.setParameter("debut", dateDebut.atStartOfDay());
            query.setParameter("fin", dateFin.plusDays(1).atStartOfDay());
            
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public long compterPresencesValides(Etudiant etudiant, Cours cours) {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT COUNT(p) FROM Presence p " +
                         "WHERE p.etudiant = :etudiant " +
                         "AND p.seance.cours = :cours " +
                         "AND p.valide = true " +
                         "AND (p.statut = 'PRESENT' OR p.statut = 'RETARD')";
            
            TypedQuery<Long> query = em.createQuery(jpql, Long.class);
            query.setParameter("etudiant", etudiant);
            query.setParameter("cours", cours);
            
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }
}