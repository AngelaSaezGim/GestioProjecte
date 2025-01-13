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

        //el EntityManager es autocloseable - aunque no lo cierre explicitamente no se perderán recursos
        try ( EntityManagerProvider emp = new EntityManagerProvider(emf)) {

            EntityManager em = emp.getEntityManager();
            EntityTransaction et = em.getTransaction();

            // Inicio servicios (dividos en entidades)
            Clientas01Service clientService = new Clientas01Service(em);
            Facturaas01Service facturaService = new Facturaas01Service(em);
            Operariresponsableas01Service operariResponsableService = new Operariresponsableas01Service(em);
            Projecteas01Service projecteService = new Projecteas01Service(em);
            Tascaas01Service tascaService = new Tascaas01Service(em);

            do {
                showContador(clientService, facturaService, operariResponsableService, projecteService, tascaService);
                printOptions();
                opcionElegidaPrincipal = readChoiceMain();

                switch (opcionElegidaPrincipal) {
                    case QUERY_CLEAN_ALL:
                        truncateAllTables(clientService, facturaService, operariResponsableService, projecteService, tascaService);
                        esperarIntro();
                        break;
                    case QUERY_INSERT:
                        do {
                            showContador(clientService, facturaService, operariResponsableService, projecteService, tascaService);
                            printOptionsEntity();
                            opcionElegidaInsert = readChoiceInsert();
                            switch (opcionElegidaInsert) {
                                case QUERY_INSERT_CLIENT:
                                    agregarCliente(clientService);
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
                            showContador(clientService, facturaService, operariResponsableService, projecteService, tascaService);
                            printOptionsEntity();
                            opcionElegidaList = readChoiceList();
                            switch (opcionElegidaList) {
                                case QUERY_LIST_CLIENT:
                                    listClients(clientService);
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
                            showContador(clientService, facturaService, operariResponsableService, projecteService, tascaService);
                            printOptionsEntity();
                            opcionElegidaDelete = readChoiceDelete();
                            switch (opcionElegidaDelete) {
                                case QUERY_DELETE_CLIENT:
                                    eliminarClientes(clientService);
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
    
     protected static void esperarIntro() {
        System.out.println("Presione Enter para continuar...");
        tcl.nextLine();
    }

    protected static void printOptions() {
        StringBuilder sb = new StringBuilder()
                .append(" - GESTOR DE ENTIDADES - ")
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
    
    protected static MainApp.MenuOption readChoiceMain() {
        try {
            int choiceInt = Integer.valueOf(tcl.nextLine());
            if (choiceInt == 0) {
                return MainApp.MenuOption.EXIT;
            }
            return MainApp.MenuOption.values()[choiceInt - 1];
        } catch (RuntimeException re) {
            System.out.println("Opción inválida... Inténtelo otra vez.");
            return readChoiceMain();
        }
    }

    protected static MainApp.MenuOptionInsert readChoiceInsert() {
        try {
            int choiceInt = Integer.valueOf(tcl.nextLine());
            if (choiceInt == 0) {
                return MainApp.MenuOptionInsert.EXIT;
            }
            return MainApp.MenuOptionInsert.values()[choiceInt - 1];
        } catch (RuntimeException re) {
            System.out.println("Opción de añadir inválida... Inténtelo otra vez.");
            return readChoiceInsert();
        }
    }

    protected static MainApp.MenuOptionList readChoiceList() {
        try {
            int choiceInt = Integer.valueOf(tcl.nextLine());
            if (choiceInt == 0) {
                return MainApp.MenuOptionList.EXIT;
            }
            return MainApp.MenuOptionList.values()[choiceInt - 1];
        } catch (RuntimeException re) {
            System.out.println("Opción de listado inválida... Inténtelo otra vez.");
            return readChoiceList();
        }
    }

    protected static MainApp.MenuOptionDelete readChoiceDelete() {
        try {
            int choiceInt = Integer.valueOf(tcl.nextLine());
            if (choiceInt == 0) {
                return MainApp.MenuOptionDelete.EXIT;
            }
            return MainApp.MenuOptionDelete.values()[choiceInt - 1];
        } catch (RuntimeException re) {
            System.out.println("Opción de borrado inválida... Inténtelo otra vez.");
            return readChoiceDelete();
        }
    }
}
