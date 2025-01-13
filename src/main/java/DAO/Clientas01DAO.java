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
    public void create(Clientas01 entity) {
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            em.persist(entity);
            et.commit();
        } catch (Exception e) {
            if (et.isActive()) {
                et.rollback();
            }
            throw new PersistenceException("Error al crear cliente", e);
        }
    }

    @Override
    public void update(Clientas01 entity) {
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            em.merge(entity);
            et.commit();
        } catch (Exception e) {
            if (et.isActive()) {
                et.rollback();
            }
            throw new PersistenceException("Error al actualizar cliente", e);
        }
    }

    @Override
    public void delete(Clientas01 entity) {
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            em.remove(em.contains(entity) ? entity : em.merge(entity));
            et.commit();
        } catch (Exception e) {
            if (et.isActive()) {
                et.rollback();
            }
            throw new PersistenceException("Error al eliminar cliente", e);
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

    //Falta manejar la entidad
    public void truncateTable() {
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            // Para tablas con claves foráneas, usar DELETE en lugar de TRUNCATE.
            em.createQuery("DELETE FROM Clientas01").executeUpdate(); 
            et.commit();
        } catch (Exception e) {
            if (et.isActive()) {
                et.rollback();
            }
            throw new PersistenceException("Error al vaciar la tabla de clientes", e);
        }
    }
}
