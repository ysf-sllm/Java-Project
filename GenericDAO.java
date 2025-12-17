package fr.presenceuniversitaire.dao;

import javax.persistence.*;
import java.util.List;

/**
 * Implémentation générique du pattern DAO utilisant JPA/Hibernate
 * Applique le principe d'abstraction et de généricité
 * @param <T> Type de l'entité
 */
public abstract class GenericDAO<T> implements DAO<T> {
    
    protected EntityManagerFactory emf;
    protected Class<T> entityClass;
    
    protected GenericDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
        if (emf == null) {
            this.emf = Persistence.createEntityManagerFactory("PresenceUnivPU");
        }
    }
    
    @Override
    public T creer(T entity) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = null;
        
        try {
            tx = em.getTransaction();
            tx.begin();
            em.persist(entity);
            tx.commit();
            return entity;
            
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Erreur lors de la création: " + 
                                      e.getMessage(), e);
        } finally {
            em.close();
        }
    }
    
    @Override
    public T lire(String id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(entityClass, id);
        } finally {
            em.close();
        }
    }
    
    @Override
    public T mettreAJour(T entity) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = null;
        
        try {
            tx = em.getTransaction();
            tx.begin();
            T merged = em.merge(entity);
            tx.commit();
            return merged;
            
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Erreur lors de la mise à jour: " + 
                                      e.getMessage(), e);
        } finally {
            em.close();
        }
    }
    
    @Override
    public boolean supprimer(String id) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = null;
        
        try {
            tx = em.getTransaction();
            tx.begin();
            
            T entity = em.find(entityClass, id);
            if (entity != null) {
                em.remove(entity);
                tx.commit();
                return true;
            }
            return false;
            
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Erreur lors de la suppression: " + 
                                      e.getMessage(), e);
        } finally {
            em.close();
        }
    }
    
    @Override
    public List<T> listerTout() {
        EntityManager em = emf.createEntityManager();
        try {
            String className = entityClass.getSimpleName();
            String jpql = "SELECT e FROM " + className + " e";
            TypedQuery<T> query = em.createQuery(jpql, entityClass);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    // Méthodes utilitaires supplémentaires
    protected List<T> rechercherParCritere(String whereClause, Object... params) {
        EntityManager em = emf.createEntityManager();
        try {
            String className = entityClass.getSimpleName();
            String jpql = "SELECT e FROM " + className + " e WHERE " + whereClause;
            
            TypedQuery<T> query = em.createQuery(jpql, entityClass);
            
            for (int i = 0; i < params.length; i++) {
                query.setParameter(i + 1, params[i]);
            }
            
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    protected T rechercherUnique(String whereClause, Object... params) {
        List<T> resultats = rechercherParCritere(whereClause, params);
        return resultats.isEmpty() ? null : resultats.get(0);
    }
    
    // Fermeture de l'EntityManagerFactory
    public void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}