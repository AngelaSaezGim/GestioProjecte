/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entity.Operariresponsableas01;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
/**
 *
 * @author angsaegim
 */
public class Operariresponsableas01DAO implements GenericDAO<Operariresponsableas01> {

    private EntityManager em;

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
            if (et.isActive()) et.rollback();
            throw new RuntimeException("Error al crear operario responsable", e);
        }
    }

    @Override
    public void update(Operariresponsableas01 entity) {
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            em.merge(entity);
            et.commit();
        } catch (Exception e) {
            if (et.isActive()) et.rollback();
            throw new RuntimeException("Error al actualizar operario responsable", e);
        }
    }

    @Override
    public void delete(Operariresponsableas01 entity) {
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            em.remove(em.contains(entity) ? entity : em.merge(entity));
            et.commit();
        } catch (Exception e) {
            if (et.isActive()) et.rollback();
            throw new RuntimeException("Error al eliminar operario responsable", e);
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
}