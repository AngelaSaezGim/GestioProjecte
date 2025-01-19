/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import DAO.Projecteas01DAO;
import Entity.Projecteas01;
import javax.persistence.EntityManager;
import java.util.List;
/**
 *
 * @author angsaegim
 */
public class Projecteas01Service  {
    
    private Projecteas01DAO projecteDAO;

    public Projecteas01Service(EntityManager em) {
        this.projecteDAO = new Projecteas01DAO(em);
    }

    // Crear un proyecto
    public void createProject(Projecteas01 project) {
        try {
            projecteDAO.create(project);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear proyecto", e);
        }
    }

    // Actualizar un proyecto
    public void updateProject(Projecteas01 project) {
        try {
            projecteDAO.update(project);
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar proyecto", e);
        }
    }

    // Encontrar un proyecto por su ID
    public Projecteas01 findProjectById(Object id) {
        try {
            return projecteDAO.findById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar proyecto por ID", e);
        }
    }

    // Obtener todos los proyectos
    public List<Projecteas01> findAllProjects() {
        try {
            return projecteDAO.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener todos los proyectos", e);
        }
    }
    
    public List<Projecteas01> findAllWithDetails() {
         try {
            return projecteDAO.findAllWithDetails();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener todos los proyectos y las tablas relacionadas", e);
        }
         
     }
    
     public List<Projecteas01> findAllByState(String state) {
         try {
            return projecteDAO.findAllByState(state);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener todos los proyectos con estado " + state, e);
        }
         
     }
    
    // Eliminar un proyecto
    public void deleteProject(Projecteas01 project) {
        try {
            projecteDAO.delete(project);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar proyecto", e);
        }
    }
    
     public void deleteTable() {
    try {
        projecteDAO.deleteTable();
    } catch (Exception e) {
        throw new RuntimeException("Error al vaciar la tabla de projectes", e);
    }
    }
}
