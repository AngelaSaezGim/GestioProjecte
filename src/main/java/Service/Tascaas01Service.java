/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import DAO.Tascaas01DAO;
import Entity.Facturaas01;
import Entity.Tascaas01;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author angsaegim
 */
public class Tascaas01Service {

    private final Tascaas01DAO tascaas01DAO;

    public Tascaas01Service(EntityManager em) {
        this.tascaas01DAO = new Tascaas01DAO(em);
    }

    public void createTasca(Tascaas01 tascaas01) {
        try {
            tascaas01DAO.create(tascaas01);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear tarea", e);
        }
    }

    public Tascaas01 findTascaById(Object id) {
        try {
            return tascaas01DAO.findById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar una tarea por ID", e);
        }
    }

    public List<Tascaas01> findAllTasques() {
        try {
            return tascaas01DAO.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener todas las tareas", e);
        }
    }

    public List<Tascaas01> findAllWithDetails() {
        try {
            return tascaas01DAO.findAllWithDetails();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener todas las tareas y las tablas relacionadas", e);
        }

    }
    
    public Boolean HasFacturesVerification(Tascaas01 tasca) {
        // Obtener la lista de facturas de la tasca
        Facturaas01 facturaTasca = tasca.getFactura();

        if (facturaTasca != null) {
            System.out.println("La tasca amb ID " + tasca.getIdTasca() + " tiene esta factura:");
            System.out.println("|--------------------------------------|");

                System.out.println("---> [" + facturaTasca.getIdFactura() + "] "
                        + facturaTasca.getObservacions() + " | "
                        + facturaTasca.getData() + " | "
                        + facturaTasca.getImportTotal());
            System.out.println("|--------------------------------------|");
            return true;
        } else {
            System.out.println("La tasca amb ID " + tasca.getIdTasca() + " no té factures associades.");
            return false;
        }
    }

    public String deleteTascaVerification(Tascaas01 tasca) {
        // Verificar si el estado de la tarea es "en procés"
        if ("En procés".equalsIgnoreCase(tasca.getEstat())) {
              return "La tasca no es pot eliminar perquè està en estat 'en procés'.";
        }
        // Se puede eliminar
        return null;
    }

    public void deleteTasca(Tascaas01 tasca) {
        String info = deleteTascaVerification(tasca);
        if (info == null) {
            try {
                tascaas01DAO.delete(tasca);
                System.out.println("La tarea con ID " + tasca.getIdTasca() + " ha sido eliminada.");
            } catch (Exception e) {
                throw new RuntimeException("Error al eliminar tarea", e);
            }
        } else {
            System.out.println(info);
        }
    }

    public void deleteTable() {
        try {
            tascaas01DAO.deleteTable();
        } catch (Exception e) {
            throw new RuntimeException("Error al vaciar la tabla de tasques", e);
        }
    }
}
