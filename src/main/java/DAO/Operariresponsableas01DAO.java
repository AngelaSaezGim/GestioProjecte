/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entity.Operariresponsableas01;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import javax.persistence.PersistenceException;

/**
 *
 * @author angsaegim
 */
public class Operariresponsableas01DAO implements GenericDAO<Operariresponsableas01> {

    private final EntityManager em;

    public Operariresponsableas01DAO(EntityManager em) {
        this.em = em;
    }

    @Override
    public void create(Operariresponsableas01 entity) {
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            em.persist(entity);
            et.commit();
        } catch (Exception e) {
            if (et.isActive()) {
                et.rollback();
            }
            throw new PersistenceException("Error al crear operario responsable", e);
        }
    }

    @Override
    public Operariresponsableas01 findById(Object id) {
        return em.find(Operariresponsableas01.class, id);
    }

    @Override
    public List<Operariresponsableas01> findAll() {
        return em.createNamedQuery("Operariresponsableas01.findAll", Operariresponsableas01.class).getResultList();
    }

    public List<Operariresponsableas01> findAllWithDetails() {
        return em.createQuery(
                "SELECT DISTINCT o FROM Operariresponsableas01 o "
                + "LEFT JOIN FETCH o.tasques t", 
                Operariresponsableas01.class
        ).getResultList();
    }

    @Override
    public void delete(Operariresponsableas01 entity) {
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            em.remove(em.contains(entity) ? entity : em.merge(entity));
            et.commit();
        } catch (Exception e) {
            if (et.isActive()) {
                et.rollback();
            }
            throw new PersistenceException("Error al eliminar operario responsable", e);
        }
    }
    
    public void deleteTable() {
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            em.createQuery("DELETE FROM Operariresponsableas01").executeUpdate();
            et.commit();
        } catch (Exception e) {
            if (et.isActive()) {
                et.rollback();
            }
            throw new PersistenceException("Error al vaciar la tabla de operari", e);
        }
    }

}
