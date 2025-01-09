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

/**
 *
 * @author angsaegim
 */

public class MainApp {
    
    //para no mostrar hora y tal y los mensajes molestos he cambiado 
    //patter de pattern = "%d{HH:mm:ss}[%t] %-5level %logger{36}-%msg%n" a pattern="%msg%n"/>/>
    static Logger log = LogManager.getFormatterLogger();
    
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("GestioProjectePU");

        EntityManager em = emf.createEntityManager();

        EntityTransaction et = em.getTransaction();
        
          try {
            // Iniciar transacción
            et.begin();
            
            // Mostrar los diferentes elementos
            showAllClients(em);
            showAllFacturas(em);
            showAllOperariosResponsables(em);
            showAllProyectos(em);
            showAllTareas(em);

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
    
    // Método genérico para obtener datos de cualquier entidad
    private static <T> List<T> ShowAllFrom(EntityManager em, String namedQuery, Class<T> entityClass) {
        Query query = em.createNamedQuery(namedQuery, entityClass);
        return query.getResultList();
    }
    
    private static void showAllClients(EntityManager em) {
        log.info("=== MOSTRANDO CLIENTES ===");
        List<Clientas01> clientes = ShowAllFrom(em, "Clientas01.findAll", Clientas01.class);
        clientes.forEach(cliente -> log.info(cliente));
        log.info("\n");
    }

    private static void showAllFacturas(EntityManager em) {
        log.info("=== MOSTRANDO FACTURAS ===");
        List<Facturaas01> facturas = ShowAllFrom(em, "Facturaas01.findAll", Facturaas01.class);
        facturas.forEach(factura -> log.info(factura));
        log.info("\n");
    }

    private static void showAllOperariosResponsables(EntityManager em) {
        log.info("=== MOSTRANDO OPERARIOS RESPONSABLES ===");
        List<Operariresponsableas01> operarios = ShowAllFrom(em, "Operariresponsableas01.findAll", Operariresponsableas01.class);
        operarios.forEach(operario -> log.info(operario));
        log.info("\n");
    }

    private static void showAllProyectos(EntityManager em) {
        log.info("=== MOSTRANDO PROYECTOS ===");
        List<Projecteas01> proyectos = ShowAllFrom(em, "Projecteas01.findAll", Projecteas01.class);
        proyectos.forEach(proyecto -> log.info(proyecto));
        log.info("\n");
    }

    // Método para obtener y mostrar las tareas
    private static void showAllTareas(EntityManager em) {
        log.info("=== MOSTRANDO TAREAS ===");
        List<Tascaas01> tareas = ShowAllFrom(em, "Tascaas01.findAll", Tascaas01.class);
        tareas.forEach(tarea -> log.info(tarea));
        log.info("\n"); 
    }
}
