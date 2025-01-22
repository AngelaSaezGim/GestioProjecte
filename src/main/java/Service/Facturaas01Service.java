/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import DAO.Facturaas01DAO;
import Entity.Facturaas01;
import java.util.Calendar;
import java.util.Date;
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

    // Encontrar una factura por su ID
    public Facturaas01 findFacturaById(Object id) {
        try {
            return facturaDAO.findById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar factura por ID", e);
        }
    }

    // Obtener todas las facturas
    public List<Facturaas01> findAllFacturas() {
        try {
            return facturaDAO.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener todas las facturas", e);
        }
    }

    public List<Facturaas01> findAllWithDetails() {
        try {
            return facturaDAO.findAllWithDetails();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener todas las facturas y las tablas relacionadas", e);
        }
    }

    public String deleteFacturaVerification(Facturaas01 factura) {
        // Verificar si la fecha de creación de la factura es hace más de 5 años
        if (factura.getData() != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(factura.getData());
            calendar.add(Calendar.YEAR, 5);
            Date fechaLimite = calendar.getTime();

            // Verificar si han pasado más de 5 años desde la fecha de creación
            if (fechaLimite.after(new Date())) {
                return "La factura no se puede eliminar porque fue creada hace menos de 5 años.";
            }
        }
        return null;
    }

    // Eliminar una factura
    public void deleteFactura(Facturaas01 factura) {
        String info = deleteFacturaVerification(factura);
        if (info == null) {
            try {
                facturaDAO.delete(factura);
            } catch (Exception e) {
                throw new RuntimeException("Error al eliminar factura", e);
            }
        } else {
            System.out.println(info);
        }
    }

    public void deleteTable() {
        try {
            facturaDAO.deleteTable();
        } catch (Exception e) {
            throw new RuntimeException("Error al vaciar la tabla de facturas", e);
        }
    }
}
