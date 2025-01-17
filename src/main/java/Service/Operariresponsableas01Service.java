/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import DAO.Operariresponsableas01DAO;
import Entity.Operariresponsableas01;
import javax.persistence.EntityManager;
import java.util.List;
/**
 *
 * @author angsaegim
 */
public class Operariresponsableas01Service {

    private Operariresponsableas01DAO operariDAO;

    public Operariresponsableas01Service(EntityManager em) {
        this.operariDAO = new Operariresponsableas01DAO(em);
    }

    // Crear un operario responsable
    public void createOperari(Operariresponsableas01 operari) {
        try {
            operariDAO.create(operari);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear operario responsable", e);
        }
    }
    
    // Actualizar un operario responsable
    public void updateOperari(Operariresponsableas01 operari) {
        try {
            operariDAO.update(operari);
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar operario responsable", e);
        }
    }
    
    // Encontrar un operario responsable por su ID
    public Operariresponsableas01 findOperariById(Object id) {
        try {
            return operariDAO.findById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar operario responsable por ID", e);
        }
    }

    // Obtener todos los operarios responsables
    public List<Operariresponsableas01> findAllOperaris() {
        try {
            return operariDAO.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener todos los operarios responsables", e);
        }
    }
     public List<Operariresponsableas01> findAllWithDetails() {
         try {
            return operariDAO.findAllWithDetails();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener todos los operarios y las tablas relacionadas", e);
        }
         
     }

    // Eliminar un operario responsable
    public void deleteOperari(Operariresponsableas01 operari) {
        try {
            operariDAO.delete(operari);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar operario responsable", e);
        }
    }
    
    public void deleteTable() {
    try {
        operariDAO.deleteTable();
    } catch (Exception e) {
        throw new RuntimeException("Error al vaciar la tabla de operaris", e);
    }
    }
    
}
