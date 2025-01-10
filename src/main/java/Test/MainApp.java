/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Test;

import Entity.Clientas01;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.apache.logging.log4j.*;
import java.util.List;

import Service.Clientas01Service;
import Service.Facturaas01Service;
import Service.Operariresponsableas01Service;
import Service.Projecteas01Service;
import Service.Tascaas01Service;
import static Test.MethodsMain.*;

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
            
            // Crear los servicios
            Clientas01Service clientService = new Clientas01Service(em);
            Facturaas01Service facturaService = new Facturaas01Service(em);
            Operariresponsableas01Service operariResponsableService = new Operariresponsableas01Service(em);
            Projecteas01Service projecteService = new Projecteas01Service(em);
            Tascaas01Service tascaService = new Tascaas01Service(em);

            showAllClients(clientService);
            showAllFacturas(facturaService);
            showAllOperariosResponsables(operariResponsableService);
            showAllProyectos(projecteService);
            showAllTareas(tascaService);

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
