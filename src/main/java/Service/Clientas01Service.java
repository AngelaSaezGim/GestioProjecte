/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import DAO.Clientas01DAO;
import Entity.Clientas01;
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

    // Eliminar un cliente
    public void deleteClient(Clientas01 client) {
        try {
            clientDAO.delete(client);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar cliente", e);
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
    public List<Clientas01> getAllClients() {
        try {
            return clientDAO.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener todos los clientes", e);
        }
    }
    
    public void truncateTable() {
    try {
        clientDAO.truncateTable();
    } catch (Exception e) {
        throw new RuntimeException("Error al vaciar la tabla de clientes", e);
    }
}
}
