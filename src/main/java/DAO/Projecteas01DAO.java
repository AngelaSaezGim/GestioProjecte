/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entity.Projecteas01;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import javax.persistence.PersistenceException;

/**
 *
 * @author angsaegim
 */
public class Projecteas01DAO implements GenericDAO<Projecteas01> {

    private final EntityManager em;

    public Projecteas01DAO(EntityManager em) {
        this.em = em;
    }

    @Override
    public void create(Projecteas01 entity) {
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            em.persist(entity);
            et.commit();
        } catch (Exception e) {
            if (et.isActive()) {
                et.rollback();
            }
            throw new PersistenceException("Error al crear proyecto", e);
        }
    }

    @Override
    public void update(Projecteas01 entity) {
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            em.merge(entity);
            et.commit();
        } catch (Exception e) {
            if (et.isActive()) {
                et.rollback();
            }
            throw new PersistenceException("Error al actualizar proyecto", e);
        }
    }

    @Override
    public Projecteas01 findById(Object id) {
        return em.find(Projecteas01.class, id);
    }

    @Override
    public List<Projecteas01> findAll() {
        return em.createNamedQuery("Projecteas01.findAll", Projecteas01.class).getResultList();
    }

    public List<Projecteas01> findAllWithDetails() {
        return em.createQuery(
                "SELECT DISTINCT p FROM Projecteas01 p "
                + "LEFT JOIN FETCH p.tascaas01Collection t", 
                Projecteas01.class
        ).getResultList();
    }

    @Override
    public void delete(Projecteas01 entity) {
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            em.remove(em.contains(entity) ? entity : em.merge(entity));
            et.commit();
        } catch (Exception e) {
            if (et.isActive()) {
                et.rollback();
            }
            throw new PersistenceException("Error al eliminar proyecto", e);
        }
    }

    public void deleteTable() {
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            em.createQuery("DELETE FROM Projecteas01").executeUpdate();
            et.commit();
        } catch (Exception e) {
            if (et.isActive()) {
                et.rollback();
            }
            throw new PersistenceException("Error al vaciar la tabla de projectes", e);
        }
    }
}
