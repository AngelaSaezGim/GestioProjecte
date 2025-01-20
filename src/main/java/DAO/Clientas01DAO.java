/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entity.Clientas01;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

/**
 *
 * @author angsaegim
 */
public class Clientas01DAO implements GenericDAO<Clientas01> {

    private final EntityManager em;

    public Clientas01DAO(EntityManager em) {
        this.em = em;
    }

    @Override
    @Transactional
    public void create(Clientas01 entity) {
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            em.persist(entity);
            em.flush(); // SINCRONIZACION BD
            et.commit();
        } catch (Exception e) {
            if (et.isActive()) {
                et.rollback();
            }
            throw new PersistenceException("Error al crear cliente", e);
        }
    }

    @Override
    @Transactional
    public void update(Clientas01 entity) {
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            em.merge(entity);
            em.flush();
            et.commit();
        } catch (Exception e) {
            if (et.isActive()) {
                et.rollback();
            }
            throw new PersistenceException("Error al actualizar cliente", e);
        }
    }

    @Override
    public Clientas01 findById(Object id) {
        return em.find(Clientas01.class, id);
    }

    @Override
    public List<Clientas01> findAll() {
        return em.createNamedQuery("Clientas01.findAll", Clientas01.class).getResultList();
    }

    public List<Clientas01> findAllWithDetails() {
        return em.createQuery(
                "SELECT DISTINCT c FROM Clientas01 c "
                + "LEFT JOIN FETCH c.projecteas01Collection "
                + "LEFT JOIN FETCH c.facturaas01Collection",
                Clientas01.class
        ).getResultList();
    }

    @Override
    @Transactional
    public void delete(Clientas01 entity) {
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
            throw new PersistenceException("Error al eliminar cliente", e);
        }
    }

    @Transactional
    public void deleteTable() {
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            em.createQuery("DELETE FROM Clientas01").executeUpdate();
            em.flush();
            et.commit();
        } catch (Exception e) {
            if (et.isActive()) {
                et.rollback();
            }
            throw new PersistenceException("Error al vaciar la tabla de clientes", e);
        }
    }
}
