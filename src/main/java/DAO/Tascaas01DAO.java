/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entity.Tascaas01;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import javax.persistence.PersistenceException;

/**
 *
 * @author angsaegim
 */
public class Tascaas01DAO implements GenericDAO<Tascaas01> {

    private final EntityManager em;

    public Tascaas01DAO(EntityManager em) {
        this.em = em;
    }

    @Override
    public void create(Tascaas01 entity) {
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            em.persist(entity);
            et.commit();
        } catch (Exception e) {
            if (et.isActive()) {
                et.rollback();
            }
            throw new PersistenceException("Error al crear tarea", e);
        }
    }

    @Override
    public void update(Tascaas01 entity) {
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            em.merge(entity);
            et.commit();
        } catch (Exception e) {
            if (et.isActive()) {
                et.rollback();
            }
            throw new PersistenceException("Error al actualizar tarea", e);
        }
    }

    @Override
    public Tascaas01 findById(Object id) {
        return em.find(Tascaas01.class, id);
    }

    @Override
    public List<Tascaas01> findAll() {
        return em.createNamedQuery("Tascaas01.findAll", Tascaas01.class).getResultList();
    }

    public List<Tascaas01> findAllWithDetails() {
        return em.createQuery(
                "SELECT DISTINCT t FROM Tascaas01 t "
                + "LEFT JOIN FETCH t.idProjecte p "
                + "LEFT JOIN FETCH t.facturaas01Collection f "
                + "LEFT JOIN FETCH t.idOperari o",
                Tascaas01.class
        ).getResultList();
    }

    @Override
    public void delete(Tascaas01 entity) {
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            em.remove(em.contains(entity) ? entity : em.merge(entity));
            et.commit();
        } catch (Exception e) {
            if (et.isActive()) {
                et.rollback();
            }
            throw new PersistenceException("Error al eliminar tarea", e);
        }
    }

    public void deleteTable() {
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            em.createQuery("DELETE FROM Tascaas01").executeUpdate();
            et.commit();
        } catch (Exception e) {
            if (et.isActive()) {
                et.rollback();
            }
            throw new PersistenceException("Error al vaciar la tabla de tasques", e);
        }
    }
}
