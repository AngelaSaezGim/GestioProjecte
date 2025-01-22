/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import DAO.Clientas01DAO;
import Entity.Clientas01;
import Entity.Projecteas01;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import javax.persistence.EntityManager;
import java.util.List;

/**
 *
 * @author angsaegim
 */
public class Clientas01Service {

    private Clientas01DAO clientDAO;

    public Clientas01Service(EntityManager em) {
        this.clientDAO = new Clientas01DAO(em);
    }

    // Crear un cliente
    //VALIDACIONES AQUI 
    public void createClient(Clientas01 client) {
        try {
            clientDAO.create(client);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear cliente", e);
        }
    }

    // Actualizar un cliente
    public void updateClient(Clientas01 client) {
        try {
            clientDAO.update(client);
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar cliente", e);
        }
    }

    // Encontrar un cliente por su ID
    public Clientas01 findClientById(Object id) {
        try {
            return clientDAO.findById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar cliente por ID", e);
        }
    }

    // Obtener todos los clientes
    public List<Clientas01> findAllClients() {
        try {
            return clientDAO.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener todos los clientes", e);
        }
    }

    public List<Clientas01> findAllWithDetails() {
        try {
            return clientDAO.findAllWithDetails();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener todos los clientes y las tablas relacionadas", e);
        }
    }

    public String deleteClientVerification(Clientas01 cliente) {
        // Obtener la lista de proyectos del cliente
        Collection<Projecteas01> proyectos = cliente.getProjecteas01Collection();

        for (Projecteas01 proyecto : proyectos) {

            // Verificar si el proyecto está en estado "En procés"
            if (proyecto.getEstat() != null && proyecto.getEstat().equalsIgnoreCase("En procés")) {
                return "El cliente no se puede eliminar porque tiene proyectos en curso.";
            }

            // Verificar si el proyecto está finalizado y si la fecha es hace más de 5 años
            if (proyecto.getEstat() != null && proyecto.getEstat().equalsIgnoreCase("Finalitzat")) {
                if (proyecto.getFechaFinalitzacio() != null) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(proyecto.getFechaFinalitzacio());
                    calendar.add(Calendar.YEAR, 5);
                    Date fechaLimite = calendar.getTime();

                    // Verificar si la fecha de finalización está dentro de los últimos 5 años
                    if (fechaLimite.after(new Date())) {
                        return "El cliente no se puede eliminar porque tiene proyectos finalizados hace menos de 5 años.";
                    }
                }
            }

            // Verificar si el proyecto está en estado "No iniciat"
            if (proyecto.getEstat() != null && proyecto.getEstat().equalsIgnoreCase("No iniciat")) {
                continue; //continua revisando el siguiente pq se puede eliminar
            }
        }
        // Si todas las condiciones de los proyectos son válidas, permitir la eliminación
        return null;
    }

    // Eliminar un cliente
    // SE IFORMA SI SE PUEDE ELIMIANR
    //--Se puede borrar un cliente si no tiene ningún projecte iniciado o todos están finalizados hace más de 5 años.
    public void deleteClient(Clientas01 client) {
        String info = deleteClientVerification(client);
        if (info == null) {
            try {
                clientDAO.delete(client);
            } catch (Exception e) {
                throw new RuntimeException("Error al eliminar cliente", e);
            }
        } else {
            System.out.println(info);
        }
    }

    public void deleteTable() {
        try {
            clientDAO.deleteTable();
        } catch (Exception e) {
            throw new RuntimeException("Error al vaciar la tabla de clientes", e);
        }
    }
}
