/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import DAO.Tascaas01DAO;
import Entity.Tascaas01;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author angsaegim
 */
public class Tascaas01Service {

    private final Tascaas01DAO tascaas01DAO;

    /**
     * Constructor que inicializa el servicio con un EntityManager.
     *
     * @param em EntityManager para gestionar la persistencia
     */
    public Tascaas01Service(EntityManager em) {
        this.tascaas01DAO = new Tascaas01DAO(em);
    }

    /**
     * Crea una nueva tarea en la base de datos.
     *
     * @param tascaas01 la entidad Tascaas01 a crear
     */
    public void createTasca(Tascaas01 tascaas01) {
        tascaas01DAO.create(tascaas01);
    }

    /**
     * Actualiza una tarea existente en la base de datos.
     *
     * @param tascaas01 la entidad Tascaas01 con los nuevos datos
     */
    public void updateTasca(Tascaas01 tascaas01) {
        tascaas01DAO.update(tascaas01);
    }

    /**
     * Elimina una tarea de la base de datos.
     *
     * @param tascaas01 la entidad Tascaas01 a eliminar
     */
    public void deleteTasca(Tascaas01 tascaas01) {
        tascaas01DAO.delete(tascaas01);
    }

    /**
     * Busca una tarea por su ID.
     *
     * @param id el identificador de la tarea
     * @return la entidad Tascaas01 encontrada, o null si no existe
     */
    public Tascaas01 findTascaById(Object id) {
        return tascaas01DAO.findById(id);
    }

    /**
     * Obtiene todas las tareas de la base de datos.
     *
     * @return una lista de entidades Tascaas01
     */
    public List<Tascaas01> getAllTasques() {
        return tascaas01DAO.findAll();
    }
}
