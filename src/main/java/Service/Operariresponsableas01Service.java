/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import DAO.Operariresponsableas01DAO;
import Entity.Operariresponsableas01;
import Entity.Tascaas01;
import java.util.Collection;
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

    public String deleteOperariVerification(Operariresponsableas01 operari) {
        // Verificar si el operario tiene tareas en estado "en procés"
        for (Tascaas01 tarea : operari.getTasques()) {
            if ("En procés".equalsIgnoreCase(tarea.getEstat())) {
                return "L'operari responsable no es pot eliminar perquè té tasques en estat 'en procés'.";
            }
        }
        return null;
    }

    public Boolean hasTasquesVerification(Operariresponsableas01 operari) {

        // Obtener la lista de tareas asociadas al operario
        Collection<Tascaas01> tasquesOperari = operari.getTasques();

        // Verificar si el operario tiene tareas
        if (tasquesOperari != null && !tasquesOperari.isEmpty()) {
            // Si tiene tareas, mostrar las tareas asociadas
            System.out.println("L'operari amb ID " + operari.getIdOperariTasca() + " té aquestes tasques:");
            System.out.println("|--------------------------------------|");

            for (Tascaas01 tasca : tasquesOperari) {
                System.out.println("---> [" + tasca.getIdTasca() + "] "
                        + tasca.getDescripcio() + " | "
                        + tasca.getEstat() + " | ");
            }
            System.out.println("|--------------------------------------|");
            return true;
        } else {
            // Si no tiene tareas, informar que no tiene tareas asociadas
            System.out.println("L'operari amb ID " + operari.getIdOperariTasca() + " no té tasques associades.");
            return false;
        }
    }

    // Eliminar un operario responsable
    public void deleteOperari(Operariresponsableas01 operari) {
        String info = deleteOperariVerification(operari);
        if (info == null) {
            try {
                operariDAO.delete(operari);
            } catch (Exception e) {
                throw new RuntimeException("Error al eliminar operario responsable", e);
            }
        } else {
            System.out.println(info);
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
