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
import static Test.MethodsMainMenu.*;
import static Test.MehtodsMainEntities.*;
import Utils.EntityManagerProvider;

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
    
    private enum MenuOptionList {
        QUERY_LIST_CLIENT, QUERY_LIST_FACTURAS, QUERY_LIST_OPERARI, QUERY_LIST_PROJECTE, QUERY_LIST_TASCA, EXIT
    };
    
    private enum MenuOptionDelete {
        QUERY_DELETE_CLIENT, QUERY_DELETE_FACTURAS, QUERY_DELETE_OPERARI, QUERY_DELETE_PROJECTE, QUERY_DELETE_TASCA, EXIT
    };

    //para no mostrar hora y tal y los mensajes molestos he cambiado 
    //patter de pattern = "%d{HH:mm:ss}[%t] %-5level %logger{36}-%msg%n" a pattern="%msg%n"/>/>
    static Logger log = LogManager.getFormatterLogger();

    static Scanner tcl = new Scanner(System.in);

    public static void main(String[] args) {

        MenuOption opcionElegidaPrincipal = null;
        MenuOptionInsert opcionElegidaInsert = null;
        MenuOptionList opcionElegidaList = null;
        MenuOptionDelete opcionElegidaDelete = null;

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("GestioProjectePU");

        try (EntityManagerProvider emp = new EntityManagerProvider(emf)){

            EntityManager em = emp.getEntityManager();
            EntityTransaction et = em.getTransaction();
            
            /*
            // Usar el EntityManager
            em.getTransaction().begin();
            // Operaciones de persistencia (consultas, actualizaciones, etc.)
            em.getTransaction().commit();
            */
             
            // Iniciamos los servicios
            Clientas01Service clientService = new Clientas01Service(em);
            Facturaas01Service facturaService = new Facturaas01Service(em);
            Operariresponsableas01Service operariResponsableService = new Operariresponsableas01Service(em);
            Projecteas01Service projecteService = new Projecteas01Service(em);
            Tascaas01Service tascaService = new Tascaas01Service(em);

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
                                    esperarIntro();
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
                                    System.out.println("Atrás");
                                    break;
                            }
                        } while (opcionElegidaInsert != MenuOptionInsert.EXIT);
                        break;
                    case QUERY_LIST:
                        do {
                            printOptionsEntity();
                            opcionElegidaList = readChoiceList();
                            switch (opcionElegidaList) {
                                case QUERY_LIST_CLIENT:
                                    esperarIntro();
                                    break;
                                case QUERY_LIST_FACTURAS:
                                    esperarIntro();
                                    break;
                                case QUERY_LIST_OPERARI:
                                    esperarIntro();
                                    break;
                                case QUERY_LIST_PROJECTE:
                                    esperarIntro();
                                    break;
                                case QUERY_LIST_TASCA:
                                    esperarIntro();
                                    break;
                                case EXIT:
                                    System.out.println("Atrás");
                                    break;
                            }
                        } while (opcionElegidaList != MenuOptionList.EXIT);
                        break;
                    case QUERY_DELETE:
                        do {
                            printOptionsEntity();
                            opcionElegidaDelete = readChoiceDelete();
                            switch (opcionElegidaDelete) {
                                case QUERY_DELETE_CLIENT:
                                    esperarIntro();
                                    break;
                                case QUERY_DELETE_FACTURAS:
                                    esperarIntro();
                                    break;
                                case QUERY_DELETE_OPERARI:
                                    esperarIntro();
                                    break;
                                case QUERY_DELETE_PROJECTE:
                                    esperarIntro();
                                    break;
                                case QUERY_DELETE_TASCA:
                                    esperarIntro();
                                    break;
                                case EXIT:
                                    System.out.println("Atrás");
                                    break;
                            }
                        } while (opcionElegidaDelete != MenuOptionDelete.EXIT);
                        break;
                    case EXIT:
                        System.out.println("Saliendo del programa");
                        break;
                }
            } while (opcionElegidaPrincipal != MenuOption.EXIT);

        } catch (Exception e) {
            log.error("Error durante la ejecución: ", e);
        } finally {
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
            if (choiceInt == 0) {
                return MenuOption.EXIT;
            }
            return MenuOption.values()[choiceInt - 1];
        } catch (RuntimeException re) {
            System.out.println("Opción inválida... Inténtelo otra vez.");
            return readChoiceMain();
        }
    }

    protected static MenuOptionInsert readChoiceInsert() {
        try {
            int choiceInt = Integer.valueOf(tcl.nextLine());
            if (choiceInt == 0) {
                return MenuOptionInsert.EXIT;
            }
            return MenuOptionInsert.values()[choiceInt - 1];
        } catch (RuntimeException re) {
            System.out.println("Opción de añadir inválida... Inténtelo otra vez.");
            return readChoiceInsert();
        }
    }
    
    protected static MenuOptionList readChoiceList() {
        try {
            int choiceInt = Integer.valueOf(tcl.nextLine());
            if (choiceInt == 0) {
                return MenuOptionList.EXIT;
            }
            return MenuOptionList.values()[choiceInt - 1];
        } catch (RuntimeException re) {
            System.out.println("Opción de listado inválida... Inténtelo otra vez.");
            return readChoiceList();
        }
    }
    
    protected static MenuOptionDelete readChoiceDelete() {
        try {
            int choiceInt = Integer.valueOf(tcl.nextLine());
            if (choiceInt == 0) {
                return MenuOptionDelete.EXIT;
            }
            return MenuOptionDelete.values()[choiceInt - 1];
        } catch (RuntimeException re) {
            System.out.println("Opción de borrado inválida... Inténtelo otra vez.");
            return readChoiceDelete();
        }
    }
}
