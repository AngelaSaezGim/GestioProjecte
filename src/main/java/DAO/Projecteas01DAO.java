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
import javax.transaction.Transactional;

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
    @Transactional
    public void create(Projecteas01 entity) {
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            em.persist(entity);
            em.flush();
            et.commit();
        } catch (Exception e) {
            if (et.isActive()) {
                et.rollback();
            }
            throw new PersistenceException("Error al crear proyecto", e);
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

    public List<Projecteas01> findAllByState(String state) {
        return em.createQuery(
                "SELECT p FROM Projecteas01 p WHERE p.estat = :state", Projecteas01.class)
                .setParameter("state", state)
                .getResultList();
    }
    
    public List<Projecteas01> findProjectsByClientId(int clientId) {
    return em.createQuery(
            "SELECT p FROM Projecteas01 p WHERE p.idClient = :clientId", Projecteas01.class)
            .setParameter("clientId", clientId)
            .getResultList();
}

    @Override
    @Transactional
    public void delete(Projecteas01 entity) {
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            em.remove(em.contains(entity) ? entity : em.merge(entity));
            em.flush();
            et.commit();
        } catch (Exception e) {
            if (et.isActive()) {
                et.rollback();
            }
            throw new PersistenceException("Error al eliminar proyecto", e);
        }
    }

    @Transactional
    public void deleteTable() {
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            em.createQuery("DELETE FROM Projecteas01").executeUpdate();
            em.flush();
            et.commit();
        } catch (Exception e) {
            if (et.isActive()) {
                et.rollback();
            }
            throw new PersistenceException("Error al vaciar la tabla de projectes", e);
        }
    }
}
