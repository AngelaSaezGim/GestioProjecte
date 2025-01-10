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
import java.util.Scanner;

/**
 *
 * @author angsaegim
 */
public class MainApp {

    private enum MenuOption {
        QUERY_CLEAN_ALL, QUERY_INSERT, QUERY_LIST, QUERY_DELETE, EXIT
    };

    private enum MenuOptionInsert {
        QUERY_INSERT_CLIENT, QUERY_INSERT_FACTURAS, QUERY_INSERT_OPERARI, QUERY_INSERT_PROJECTE, QUERY_INSERT_TASCA, EXIT
    };

    //para no mostrar hora y tal y los mensajes molestos he cambiado 
    //patter de pattern = "%d{HH:mm:ss}[%t] %-5level %logger{36}-%msg%n" a pattern="%msg%n"/>/>
    static Logger log = LogManager.getFormatterLogger();

    static Scanner tcl = new Scanner(System.in);

    public static void main(String[] args) {

        MenuOption opcionElegidaPrincipal = null;
        MenuOptionInsert opcionElegidaInsert = null;

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("GestioProjectePU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();

        try {
            // Iniciar transacción
            et.begin();

            // Iniciamos los servicios
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

            do {
                printOptions();
                opcionElegidaPrincipal = readChoiceMain();

                switch (opcionElegidaPrincipal) {
                    case QUERY_CLEAN_ALL:
                        esperarIntro();
                        break;
                    case QUERY_INSERT:
                        do {
                            printOptionsEntity();
                            opcionElegidaInsert = readChoiceInsert();

                            switch (opcionElegidaInsert) {
                                case QUERY_INSERT_CLIENT:
                                    esperarIntro();
                                    break;
                                case QUERY_INSERT_FACTURAS:
                                    do {
                                        printOptionsEntity();
                                        opcionElegidaInsert = readChoiceInsert();
                                        esperarIntro();
                                    } while (opcionElegidaInsert != MenuOptionInsert.EXIT);
                                    break;
                                case QUERY_INSERT_OPERARI:
                                    esperarIntro();
                                    break;
                                case QUERY_INSERT_PROJECTE:
                                    esperarIntro();
                                    break;
                                case QUERY_INSERT_TASCA:
                                    esperarIntro();
                                    break;
                                case EXIT:
                                    break;
                            }

                        } while (opcionElegidaInsert != MenuOptionInsert.EXIT);
                        break;

                    case QUERY_LIST:

                        break;

                    case QUERY_DELETE:

                        break;

                    case EXIT:

                        et.commit();
                        break;
                }
            } while (opcionElegidaPrincipal != MenuOption.EXIT);

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

    protected static void showContador() {
        System.out.println("Entidades Contador");
    }

    protected static void esperarIntro() {
        System.out.println("Presione Enter para continuar...");
        tcl.nextLine();
    }

    protected static void printOptions() {
        StringBuilder sb = new StringBuilder()
                .append("\nElija una opción:\n")
                .append("\t1) Vaciar TODAS las tablas \n")
                .append("\t2) Añadir nuevos elementos \n")
                .append("\t3) Listar elementos \n")
                .append("\t4) Borrar elementos \n")
                .append("\t0) Salir \n")
                .append("Opción: ");
        System.out.print(sb.toString());
    }

    protected static void printOptionsEntity() {
        StringBuilder sb = new StringBuilder()
                .append("\n Elija una entidad a gestionar :\n")
                .append("\t1) Cliente \n")
                .append("\t2) Factura \n")
                .append("\t3) Operari Responsable \n")
                .append("\t4) Projecte \n")
                .append("\t5) Tarea \n")
                .append("\t0) Atrás \n")
                .append("Opción: ");
        System.out.print(sb.toString());
    }

    protected static MenuOption readChoiceMain() {
        try {
            int choiceInt = Integer.valueOf(tcl.nextLine());
            return MenuOption.values()[choiceInt - 1];
        } catch (RuntimeException re) {
            System.out.println("Opción inválida... Inténtelo otra vez.");
            return readChoiceMain();
        }
    }

    protected static MenuOptionInsert readChoiceInsert() {
        try {
            int choiceInt = Integer.valueOf(tcl.nextLine());
            return MenuOptionInsert.values()[choiceInt - 1];
        } catch (RuntimeException re) {
            System.out.println("Opción inválida... Inténtelo otra vez.");
            return readChoiceInsert();
        }
    }
}
