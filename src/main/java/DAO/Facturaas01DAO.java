/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entity.Facturaas01;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import javax.persistence.PersistenceException;

/**
 *
 * @author angsaegim
 */
public class Facturaas01DAO implements GenericDAO<Facturaas01> {

    private final EntityManager em;

    public Facturaas01DAO(EntityManager em) {
        this.em = em;
    }

    @Override
    public void create(Facturaas01 entity) {
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            em.persist(entity);
            et.commit();
        } catch (Exception e) {
            if (et.isActive()) et.rollback();
            throw new PersistenceException("Error al crear factura", e);
        }
    }

    @Override
    public Facturaas01 findById(Object id) {
        return em.find(Facturaas01.class, id);
    }

    @Override
    public List<Facturaas01> findAll() {
        return em.createNamedQuery("Facturaas01.findAll", Facturaas01.class).getResultList();
    }
    
    public List<Facturaas01> findAllWithDetails() {
        return em.createQuery(
            "SELECT DISTINCT f FROM Facturaas01 f "
            + "LEFT JOIN FETCH f.idTasca "
            + "LEFT JOIN FETCH f.idClient",
            Facturaas01.class
        ).getResultList();
    }
    
    @Override
    public void delete(Facturaas01 entity) {
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            em.remove(em.contains(entity) ? entity : em.merge(entity));
            et.commit();
        } catch (Exception e) {
            if (et.isActive()) et.rollback();
            throw new PersistenceException("Error al eliminar factura", e);
        }
    }
    
     public void deleteTable() {
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            em.createQuery("DELETE FROM Facturaas01").executeUpdate();
            et.commit();
        } catch (Exception e) {
            if (et.isActive()) {
                et.rollback();
            }
            throw new PersistenceException("Error al vaciar la tabla de facturas", e);
        }
    }
}
