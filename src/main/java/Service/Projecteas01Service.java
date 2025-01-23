/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import DAO.Projecteas01DAO;
import Entity.Facturaas01;
import Entity.Projecteas01;
import Entity.Tascaas01;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author angsaegim
 */
public class Projecteas01Service {

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

    public List<Projecteas01> findProjectsByClientId(int clientId) {
        try {
            return projecteDAO.findProjectsByClientId(clientId);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener los proyectos del cliente con ID " + clientId, e);
        }
    }

    // Método de verificación
    public String deleteProjectVerification(Projecteas01 project) {
        // 1- Verificar si el proyecto tiene tareas con facturas asociadas
        if (hasFacturedTasks(project)) {
            // 2 Si tiene; - Verificar que el proyecto esté en estado 'Finalitzat'
            if ("Finalitzat".equals(project.getEstat())) {
                // Si esta finalizat - Verificar si todas las tareas asociadas han sido facturadas hace más de 5 años
                if (areTasksFacturedMoreThanFiveYearsAgo(project)) {
                    return null;  // Si se cumple; El proyecto puede ser eliminado
                } else {
                     return "El projecte no pot ser eliminat perquè algunes tasques no han sigut facturades fa més de 5 anys.";
                }
            } else {
                 return "El projecte no pot ser eliminat, té factures associades i a més no està en estat 'Finalitzat'.";
            }
        } else { // En general;
            return null;  // El proyecto no tiene facturas asociadas, puede eliminarse
        }
    }

    // Verificar si el proyecto tiene tareas con facturas asociadas
    private boolean hasFacturedTasks(Projecteas01 project) {
        // Comprobamos si alguna de las tareas del proyecto tiene una factura asociada
        for (Tascaas01 tasca : project.getTascaas01Collection()) {
            if (tasca.getFactura() != null) {  // Si la tarea tiene una factura asociada
                return true;
            }
        }
        return false;  // No tiene facturas asociadas
    }

    // Verificar si todas las tareas asociadas han sido facturadas hace más de 5 años y están 'Finalitzat'
    private boolean areTasksFacturedMoreThanFiveYearsAgo(Projecteas01 project) {
        for (Tascaas01 tasca : project.getTascaas01Collection()) {
            // Verificar que la tarea esté en estado 'Finalitzat'
            if (!"Finalitzat".equals(tasca.getEstat())) {
                return false;  // No se puede eliminar porque la tarea no está 'Finalitzat'
            }

            // Verificar si la tarea tiene facturas asociadas
            Facturaas01 factura = tasca.getFactura();
            if (factura != null) {
                // Verificar si la factura tiene más de 5 años desde la fecha actual
                if (factura.getData().after(getDateFiveYearsAgo())) {
                    return false;  // No se puede eliminar porque la factura no tiene más de 5 años
                }
            }
        }
        return true;  // Todas las tareas han sido facturadas hace más de 5 años y están 'Finalitzat'
    }

    // Obtener la fecha actual menos 5 años
    private Date getDateFiveYearsAgo() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -5);
        return calendar.getTime();
    }

    // Eliminar un proyecto
    public void deleteProject(Projecteas01 project) {
        String info = deleteProjectVerification(project);
        if (info == null) {
            try {
                projecteDAO.delete(project);
            } catch (Exception e) {
                throw new RuntimeException("Error al eliminar proyecto", e);
            }
        } else {
            System.out.println(info);
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
