/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

// ESTO LO USO PARA QUE SEA AUTOCLOSEABLE
public class EntityManagerProvider implements AutoCloseable {
    
    private final EntityManager entityManager;

    public EntityManagerProvider(EntityManagerFactory entityManagerFactory) {
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    public EntityManager getEntityManager() {
        return this.entityManager;
    }

    @Override
    public void close() {
        if (this.entityManager.isOpen()) {
            this.entityManager.close();
        }
    }
}