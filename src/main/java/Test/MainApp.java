/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Test;

import Entity.Clientas01;
import Entity.Facturaas01;
import Entity.Operariresponsableas01;
import Entity.Projecteas01;
import Entity.Tascaas01;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.apache.logging.log4j.*;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author angsaegim
 */
public class MainApp {
    
    static Logger log = LogManager.getFormatterLogger();
    
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("GestioProjectePU");

        EntityManager em = emf.createEntityManager();

        EntityTransaction et = em.getTransaction();
        
          try {
            // Iniciar transacción
            et.begin();

            log.info("=== MOSTRANDO CLIENTES ===");
            Query showAllClients = em.createNamedQuery("Clientas01.findAll");
            List<Clientas01> clientes = showAllClients.getResultList();
            clientes.forEach(cliente -> log.info(cliente));

            log.info("=== MOSTRANDO FACTURAS ===");
            Query showAllFacturas = em.createNamedQuery("Facturaas01.findAll");
            List<Facturaas01> facturas = showAllFacturas.getResultList();
            facturas.forEach(factura -> log.info(factura));

            log.info("=== MOSTRANDO OPERARIOS RESPONSABLES ===");
            Query showAllOperariResponsable = em.createNamedQuery("Operariresponsableas01.findAll");
            List<Operariresponsableas01> operarios = showAllOperariResponsable.getResultList();
            operarios.forEach(operario -> log.info(operario));

            log.info("=== MOSTRANDO PROYECTOS ===");
            Query showAllProjects = em.createNamedQuery("Projecteas01.findAll");
            List<Projecteas01> proyectos = showAllProjects.getResultList();
            proyectos.forEach(proyecto -> log.info(proyecto));

            log.info("=== MOSTRANDO TAREAS ===");
            Query showAllTascas = em.createNamedQuery("Tascaas01.findAll");
            List<Tascaas01> tareas = showAllTascas.getResultList();
            tareas.forEach(tarea -> log.info(tarea));

            // Confirmar la transacción
            et.commit();
        } catch (Exception e) {
            if (et.isActive()) {
                et.rollback();
            }
            log.error("Error durante la ejecución: ", e);
        } finally {
            em.close();
            emf.close();
        }
    }
}
