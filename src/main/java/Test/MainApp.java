/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Test;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.apache.logging.log4j.*;

import Service.Clientas01Service;
import Service.Facturaas01Service;
import Service.Operariresponsableas01Service;
import Service.Projecteas01Service;
import Service.Tascaas01Service;
import static Test.MehtodsMainEntities.*;
import Utils.EntityManagerProvider;
import Utils.ResetDatabase;

import java.util.Scanner;
/**
 *
 * @author angsaegim
 */
public class MainApp {
    
    private enum MenuOption {
        QUERY_CLEAN_ALL, QUERY_INSERT, QUERY_LIST, QUERY_DELETE, QUERY_RESET, EXIT
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

            // Inicio servicios (dividos en entidades)
            Clientas01Service clientService = new Clientas01Service(em);
            Facturaas01Service facturaService = new Facturaas01Service(em);
            Operariresponsableas01Service operariService = new Operariresponsableas01Service(em);
            Projecteas01Service projecteService = new Projecteas01Service(em);
            Tascaas01Service tascaService = new Tascaas01Service(em);
            do {
                showContador(clientService, facturaService, operariService, projecteService, tascaService);
                printOptions();
                opcionElegidaPrincipal = readChoiceMain();

                switch (opcionElegidaPrincipal) {
                    case QUERY_CLEAN_ALL:
                        truncateAllTables(clientService, facturaService, operariService, projecteService, tascaService);
                        esperarIntro();
                        break;
                    case QUERY_INSERT:
                        do {
                            showContador(clientService, facturaService, operariService, projecteService, tascaService);
                            System.out.println("-- INSERTAR DATOS --");
                            printOptionsEntity();
                            opcionElegidaInsert = readChoiceInsert();
                            switch (opcionElegidaInsert) {
                                case QUERY_INSERT_CLIENT:
                                    MethodsMainClient.agregarClienteBasic(clientService);
                                    esperarIntro();
                                    break;
                                case QUERY_INSERT_FACTURAS:
                                    MethodsMainFactura.agregarFacturaMenu(facturaService, tascaService, clientService, projecteService, operariService);
                                    esperarIntro();
                                    break;
                                case QUERY_INSERT_OPERARI:
                                    MethodsMainOperari.agregarOperariBasic(operariService);
                                    esperarIntro();
                                    break;
                                case QUERY_INSERT_PROJECTE:
                                    MethodsMainProjecte.agregarProjecteMenu(projecteService, clientService, tascaService);
                                    esperarIntro();
                                    break;
                                case QUERY_INSERT_TASCA:
                                    MethodsMainTasca.agregarTascaMenu(tascaService, projecteService, facturaService, operariService);
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
                            showContador(clientService, facturaService, operariService, projecteService, tascaService);
                            System.out.println("-- LISTAR DATOS --");
                            printOptionsEntity();
                            opcionElegidaList = readChoiceList();
                            switch (opcionElegidaList) {
                                case QUERY_LIST_CLIENT:
                                    MethodsMainClient.listClients(clientService);
                                    esperarIntro();
                                    break;
                                case QUERY_LIST_FACTURAS:
                                    MethodsMainFactura.listFacturas(facturaService);
                                    esperarIntro();
                                    break;
                                case QUERY_LIST_OPERARI:
                                    MethodsMainOperari.listOperariosResponsables(operariService);
                                    esperarIntro();
                                    break;
                                case QUERY_LIST_PROJECTE:
                                    MethodsMainProjecte.listProyectos(projecteService);
                                    esperarIntro();
                                    break;
                                case QUERY_LIST_TASCA:
                                    MethodsMainTasca.listTasques(tascaService);
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
                            showContador(clientService, facturaService, operariService, projecteService, tascaService);
                            System.out.println("-- BORRAR DATOS --");
                            printOptionsEntity();
                            opcionElegidaDelete = readChoiceDelete();
                            switch (opcionElegidaDelete) {
                                case QUERY_DELETE_CLIENT:
                                    MethodsMainClient.eliminarClientes(clientService);
                                    esperarIntro();
                                    break;
                                case QUERY_DELETE_FACTURAS:
                                    MethodsMainFactura.eliminarFacturas(facturaService);
                                    esperarIntro();
                                    break;
                                case QUERY_DELETE_OPERARI:
                                    MethodsMainOperari.eliminarOperariosResponsables(operariService);
                                    esperarIntro();
                                    break;
                                case QUERY_DELETE_PROJECTE:
                                    MethodsMainProjecte.eliminarProjectes(projecteService);
                                    esperarIntro();
                                    break;
                                case QUERY_DELETE_TASCA:
                                    MethodsMainTasca.eliminarTasques(tascaService);
                                    esperarIntro();
                                    break;
                                case EXIT:
                                    System.out.println("Atrás");
                                    break;
                            }
                        } while (opcionElegidaDelete != MenuOptionDelete.EXIT);
                        break;
                    case QUERY_RESET:
                        System.out.println("Restaurando bd a la configuración inicial..");
                        ResetDatabase.dropAllTablesSQL(em);
                        ResetDatabase.crearTablasSQLde0(em);
                        ResetDatabase.insertarDadesPredeterminats(em);
                        esperarIntro();
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
        String border = "========================================";
        String title = "          GESTOR DE ENTIDADES           ";

        System.out.println("\n" + border);
        System.out.println(title);
        System.out.println(border);
        System.out.println("Elija una opción:");
        System.out.println("\t1) Vaciar TODAS las tablas");
        System.out.println("\t2) Añadir nuevos elementos");
        System.out.println("\t3) Listar elementos");
        System.out.println("\t4) Borrar elementos");
        System.out.println("\t5) Restaurar BD con datos predeterminados");
        System.out.println("\t0) Salir");
        System.out.println(border);
        System.out.print("Opción: ");
    }

    protected static void printOptionsEntity() {
        String border = "----------------------------------------";
        String corner = "+--------------------------------------+";
        String title = "|    >> SELECCIÓN DE ENTIDAD <<        |";

        System.out.println(corner);
        System.out.println(title);
        System.out.println(corner);
        System.out.println("| Elija una entidad a gestionar:       |");
        System.out.println("|--------------------------------------|");
        System.out.println("|  1) Cliente                          |");
        System.out.println("|  2) Factura                          |");
        System.out.println("|  3) Operario Responsable             |");
        System.out.println("|  4) Proyecto                         |");
        System.out.println("|  5) Tarea                            |");
        System.out.println("|  0) Atrás                            |");
        System.out.println(corner);
        System.out.print("Opción: ");
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
