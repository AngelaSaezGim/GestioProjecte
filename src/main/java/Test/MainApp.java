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
                            System.out.println("-- AFEGIR DADES --");
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
                                    MethodsMainProjecte.agregarProjecteMenu(projecteService, clientService, facturaService, operariService, tascaService);
                                    esperarIntro();
                                    break;
                                case QUERY_INSERT_TASCA:
                                    MethodsMainTasca.agregarTascaMenu(tascaService, projecteService, facturaService, operariService, clientService);
                                    esperarIntro();
                                    break;
                                case EXIT:
                                    System.out.println("Arrere");
                                    break;
                            }
                        } while (opcionElegidaInsert != MenuOptionInsert.EXIT);
                        break;
                    case QUERY_LIST:
                        do {
                            showContador(clientService, facturaService, operariService, projecteService, tascaService);
                            System.out.println("-- LLISTAR DADES --");
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
                                    System.out.println("Arrere");
                                    break;
                            }
                        } while (opcionElegidaList != MenuOptionList.EXIT);
                        break;
                    case QUERY_DELETE:
                        do {
                            showContador(clientService, facturaService, operariService, projecteService, tascaService);
                            System.out.println("-- ESBORRAR DADES --");
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
                                    System.out.println("Arrere");
                                    break;
                            }
                        } while (opcionElegidaDelete != MenuOptionDelete.EXIT);
                        break;
                    case QUERY_RESET:
                        System.out.println("Restaurant la BD a la configuració inicial...");
                        ResetDatabase.dropAllTablesSQL(em);
                        ResetDatabase.crearTablasSQLde0(em);
                        ResetDatabase.insertarDadesPredeterminats(em);
                        esperarIntro();
                        break;
                    case EXIT:
                        System.out.println("Eixint del programa..");
                        break;
                }
            } while (opcionElegidaPrincipal != MenuOption.EXIT);

        } catch (Exception e) {
            log.error("Error durant l'execució: ", e);
        } finally {
            emf.close();
        }
    }

    protected static void esperarIntro() {
        System.out.println("Prem Enter per a continuar...");
        tcl.nextLine();
    }

    protected static void printOptions() {
        String border = "========================================";
        String title = "          GESTOR D'ENTITATS             ";

        System.out.println("\n" + border);
        System.out.println(title);
        System.out.println(border);
        System.out.println("Tria una opció:");
        System.out.println("\t1) Buidar TOTES les taules");
        System.out.println("\t2) Afegir nous elements");
        System.out.println("\t3) Llistar elements");
        System.out.println("\t4) Esborrar elements");
        System.out.println("\t5) Restaurar BD amb dades predeterminades");
        System.out.println("\t0) Eixir");
        System.out.println(border);
        System.out.print("Opció: ");
    }

    protected static void printOptionsEntity() {
        String border = "----------------------------------------";
        String corner = "+--------------------------------------+";
        String title = "|    >> SELECCIÓ D'ENTITAT <<          |";

        System.out.println(corner);
        System.out.println(title);
        System.out.println(corner);
        System.out.println("| Tria una entitat a gestionar:        |");
        System.out.println("|--------------------------------------|");
        System.out.println("|  1) Client                           |");
        System.out.println("|  2) Factura                          |");
        System.out.println("|  3) Operari Responsable              |");
        System.out.println("|  4) Projecte                         |");
        System.out.println("|  5) Tasca                            |");
        System.out.println("|  0) Enrere                           |");
        System.out.println(corner);
        System.out.print("Opció: ");
    }

    protected static MainApp.MenuOption readChoiceMain() {
        try {
            int choiceInt = Integer.valueOf(tcl.nextLine());
            if (choiceInt == 0) {
                return MainApp.MenuOption.EXIT;
            }
            return MainApp.MenuOption.values()[choiceInt - 1];
        } catch (RuntimeException re) {
            System.out.println("Opció no vàlida... Torna a intentar-ho.");
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
            System.out.println("Opció d'afegir no vàlida... Torna a intentar-ho.");
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
            System.out.println("Opció de llistat no vàlida... Torna a intentar-ho.");
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
            System.out.println("Opció d'esborrat no vàlida... Torna a intentar-ho.");
            return readChoiceDelete();
        }
    }
}
