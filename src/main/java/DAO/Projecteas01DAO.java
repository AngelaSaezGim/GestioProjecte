/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entity.Projecteas01;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

/**
 *
 * @author angsaegim
 */
public class Projecteas01DAO implements GenericDAO<Projecteas01> {

    private EntityManager em;

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
            if (et.isActive()) et.rollback();
            throw new RuntimeException("Error al crear proyecto", e);
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
            if (et.isActive()) et.rollback();
            throw new RuntimeException("Error al actualizar proyecto", e);
        }
    }

    @Override
    public void delete(Projecteas01 entity) {
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            em.remove(em.contains(entity) ? entity : em.merge(entity));
            et.commit();
        } catch (Exception e) {
            if (et.isActive()) et.rollback();
            throw new RuntimeException("Error al eliminar proyecto", e);
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
}