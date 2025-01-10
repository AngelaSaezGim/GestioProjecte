/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import DAO.Facturaas01DAO;
import Entity.Facturaas01;
import javax.persistence.EntityManager;
import java.util.List;
/**
 *
 * @author angsaegim
 */
public class Facturaas01Service {

    private Facturaas01DAO facturaDAO;

    public Facturaas01Service(EntityManager em) {
        this.facturaDAO = new Facturaas01DAO(em);
    }

    // Crear una factura
    public void createFactura(Facturaas01 factura) {
        try {
            facturaDAO.create(factura);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear factura", e);
        }
    }

    // Actualizar una factura
    public void updateFactura(Facturaas01 factura) {
        try {
            facturaDAO.update(factura);
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar factura", e);
        }
    }

    // Eliminar una factura
    public void deleteFactura(Facturaas01 factura) {
        try {
            facturaDAO.delete(factura);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar factura", e);
        }
    }

    // Encontrar una factura por su ID
    public Facturaas01 findFacturaById(Object id) {
        try {
            return facturaDAO.findById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar factura por ID", e);
        }
    }

    // Obtener todas las facturas
    public List<Facturaas01> getAllFacturas() {
        try {
            return facturaDAO.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener todas las facturas", e);
        }
    }
}
