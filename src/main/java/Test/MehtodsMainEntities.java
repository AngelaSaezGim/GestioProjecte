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

import Service.Clientas01Service;
import Service.Facturaas01Service;
import Service.Operariresponsableas01Service;
import Service.Projecteas01Service;
import Service.Tascaas01Service;

import static Test.MainApp.log;
import static Test.MainApp.tcl;
import java.util.List;

/**
 *
 * @author angel
 */
public class MehtodsMainEntities {

    //*************** CONTADOR DE LAS 5 ENTIDADES *****************************
    protected static void showContador(Clientas01Service clientService, Facturaas01Service facturaService,
            Operariresponsableas01Service operariResponsableService, Projecteas01Service projecteService, Tascaas01Service tascaService) {
        System.out.println("Entidades Contador");
        System.out.println("*****************************");
        System.out.println("Nº Clientes " + clientService.getAllClients().size());
        System.out.println("Nº Facturas " + facturaService.getAllFacturas().size());
        System.out.println("Nº Operarios responsables " + operariResponsableService.getAllOperaris().size());
        System.out.println("Nº Proyectos " + projecteService.getAllProjects().size());
        System.out.println("Nº Tareas " + tascaService.getAllTasques().size());
        System.out.println("*****************************");
    }

    //*************** VACIAR TODAS LAS TABLAS  *****************************
    protected static void truncateAllTables(Clientas01Service clientService,
            Facturaas01Service facturaService,
            Operariresponsableas01Service operariResponsableService,
            Projecteas01Service projecteService,
            Tascaas01Service tascaService) {
        System.out.println("Vaciando todas las tablas...");
        try {
            //clientService.truncateTable();
            //facturaService.truncateTable();
            //operariResponsableService.truncateTable();
            //projecteService.truncateTable();
            //tascaService.truncateTable();
            System.out.println("Todas las tablas han sido vaciadas.");
        } catch (Exception e) {
            log.error("Error al vaciar las tablas: ", e);
        }
    }

    //*****************************************************************//
    //********************** CREATE ************************************//
    //*****************************************************************//
    //*************** AGREGAR CLIENTE  *****************************//
    public static void agregarCliente(Clientas01Service clientasService) {
        String continueAdding = "yes";

        while (continueAdding.equalsIgnoreCase("yes")) {
            // Pedir los datos al usuario para crear un newClient
            System.out.println("Introduce los datos del cliente:");

            //(puede ser vacío)
            System.out.print("Nombre: ");
            String nom = tcl.nextLine();

            // (puede ser vacío)
            System.out.print("Apellido: ");
            String cognom = tcl.nextLine();

            // NIF (No puede estar vacío)
            String nif = "";
            while (nif.isEmpty()) {
                System.out.print("NIF (No puede estar vacío): ");
                nif = tcl.nextLine();
            }

            // Crear la entidad de newClient
            Clientas01 newClient = new Clientas01();
            newClient.setNom(nom);
            newClient.setCognom(cognom);
            newClient.setNif(nif);

            // Agregar bd
            try {
                clientasService.createClient(newClient);
                System.out.println("Cliente agregado exitosamente.");
            } catch (Exception e) {
                System.out.println("Error al agregar el cliente: " + e.getMessage());
            }

            // Preguntar al usuario si quiere agregar otro cliente
            boolean validInput = false;
            while (!validInput) {
                System.out.print("¿Quieres agregar otro cliente? (yes/no): ");
                continueAdding = tcl.nextLine().trim().toLowerCase();

                if (continueAdding.equals("yes") || continueAdding.equals("no")) {
                    validInput = true;
                } else {
                    System.out.println("Por favor, ingresa 'yes' para continuar o 'no' para salir.");
                }
            }
        }
    }

    //*************** AGREGAR FACTURA  *****************************//
    public static void agregarFactura(Facturaas01Service facturaService) {

    }
    
    //*************** AGREGAR OPERARI  *****************************//
    public static void agregarOperari(Operariresponsableas01Service operariResponsableService) {

    }
    //*************** AGREGAR PROJECTE  *****************************//
     public static void agregarProjecte(Projecteas01Service projecteService) {

    }
    //*************** AGREGAR TASCA  *****************************//
      public static void agregarTasca(Tascaas01Service tascaService) {

    }
      
    //*************** fin create *****************************************
    //*****************************************************************//
    //********************** FIND ************************************//
    //*****************************************************************//
    //*************** LISTAR CLIENTE  *****************************//
    protected static void listClients(Clientas01Service clientService) {

        System.out.println("¿Cómo deseas ver los clientes?");
        System.out.println("1. Básico (Solo datos del cliente)");
        System.out.println("2. Completo (Datos del cliente + tablas relacionadas)");

        int opcion = tcl.nextInt();
        tcl.nextLine();

        switch (opcion) {
            case 1:
                log.info("=== MOSTRANDO CLIENTES ===");
                log.info("*=== [MODO BÁSICO] ===*");
                for (Clientas01 cliente : clientService.getAllClients()) {
                    System.out.println(cliente.getNom() + " " + cliente.getCognom() + " - NIF: " + cliente.getNif());
                }
                break;
            case 2:
                log.info("=== MOSTRANDO CLIENTES ===");
                log.info("*=== [MODO COMPLETO] ===*");
                List<Clientas01> clientes = clientService.getAllClients();
                clientes.forEach(cliente -> log.info(cliente));
                log.info("\n");
                break;
            default:
                System.out.println("Opción no válida.");
                break;
        }
    }

    //*************** LISTAR FACTURAS  *****************************//
    protected static void listFacturas(Facturaas01Service facturaService) {

        System.out.println("¿Cómo deseas ver las facturas?");
        System.out.println("1. Básico (Solo datos de la factura)");
        System.out.println("2. Completo (Datos de la factura + tablas relacionadas)");

        int opcion = tcl.nextInt();
        tcl.nextLine();

        switch (opcion) {
            case 1:
                log.info("=== MOSTRANDO FACTURAS ===");
                log.info("*=== [MODO BÁSICO] ===*");
                break;

            case 2:
                log.info("=== MOSTRANDO FACTURAS ===");
                log.info("*=== [MODO COMPLETO] ===*");
                break;

            default:
                System.out.println("Opción no válida.");
                break;
        }
    }

    //*************** LISTAR OPERARIOS RESPONSABLES  *****************************//
    protected static void listOperariosResponsables(Operariresponsableas01Service operariResponsableService) {

        System.out.println("¿Cómo deseas ver los operarios responsables?");
        System.out.println("1. Básico (Solo datos del operario responsable)");
        System.out.println("2. Completo (Datos del operario responsable + tablas relacionadas)");

        int opcion = tcl.nextInt();
        tcl.nextLine();

        switch (opcion) {
            case 1:
                log.info("=== MOSTRANDO OPERARIOS RESPONSABLES ===");
                log.info("*=== [MODO BÁSICO] ===*");
                break;
            case 2:
                log.info("=== MOSTRANDO OPERARIOS RESPONSABLES ===");
                log.info("*=== [MODO COMPLETO] ===*");
                break;
            default:
                System.out.println("Opción no válida.");
                break;
        }
    }

    //*************** LISTAR PROYECTOS  *****************************//
    protected static void listProyectos(Projecteas01Service projecteService) {

        System.out.println("¿Cómo deseas ver los proyectos?");
        System.out.println("1. Básico (Solo datos del proyecto)");
        System.out.println("2. Completo (Datos del proyecto + tablas relacionadas)");

        int opcion = tcl.nextInt();
        tcl.nextLine();

        switch (opcion) {
            case 1:
                log.info("=== MOSTRANDO PROYECTOS ===");
                log.info("*=== [MODO BÁSICO] ===*");
                break;
            case 2:
                log.info("=== MOSTRANDO PROYECTOS ===");
                log.info("*=== [MODO COMPLETO] ===*");
                break;
            default:
                System.out.println("Opción no válida.");
                break;
        }
    }

    //*************** LISTAR TAREAS  *****************************//
    protected static void listTasques(Tascaas01Service tascaService) {

        System.out.println("¿Cómo deseas ver las tasques?");
        System.out.println("1. Básico (Solo datos de la tasca)");
        System.out.println("2. Completo (Datos de la tasca + tablas relacionadas)");

        int opcion = tcl.nextInt();
        tcl.nextLine();

        switch (opcion) {
            case 1:
                log.info("=== MOSTRANDO TASQUES ===");
                log.info("*=== [MODO BÁSICO] ===*");
                break;
            case 2:
                log.info("=== MOSTRANDO TASQUES ===");
                log.info("*=== [MODO COMPLETO] ===*");
                break;
            default:
                System.out.println("Opción no válida.");
                break;
        }
    }

    /**
     * ************** fin find *****************************************
     */
    //*****************************************************************//
    //********************** DELETE ************************************//
    //*****************************************************************//
    
    //*************** DELETE CLIENT  *****************************//
    protected static void eliminarClientes(Clientas01Service clientasService) {
        System.out.println("¿Cómo deseas eliminar los clientes?");
        System.out.println("1. Eliminar todos los clientes");
        System.out.println("2. Eliminar un cliente por ID");

        int opcion = tcl.nextInt();
        tcl.nextLine();

        switch (opcion) {
            case 1:
                break;
            case 2:
                break;
            default:
                System.out.println("Opción no válida.");
                break;
        }
    }

    //*************** DELETE FACTURAS  *****************************//
    protected static void eliminarFacturas(Facturaas01Service facturaService) {
        System.out.println("¿Cómo deseas eliminar las facturas?");
        System.out.println("1. Eliminar todas las facturas");
        System.out.println("2. Eliminar una factura por ID");

        int opcion = tcl.nextInt();
        tcl.nextLine();

        switch (opcion) {
            case 1:
                break;
            case 2:
                break;
            default:
                System.out.println("Opción no válida.");
                break;
        }
    }

    //*************** DELETE OPERARI  *****************************//
    protected static void eliminarOperariosResponsables(Operariresponsableas01Service operariResponsableService) {
        System.out.println("¿Cómo deseas eliminar los operarios responsables?");
        System.out.println("1. Eliminar todos los operarios responsables");
        System.out.println("2. Eliminar un operario responsable por ID");

        int opcion = tcl.nextInt();
        tcl.nextLine();

        switch (opcion) {
            case 1:
                break;
            case 2:
                break;
            default:
                System.out.println("Opción no válida.");
                break;
        }
    }

    //*************** DELETE PROJECTE  *****************************//
    protected static void eliminarProjectes(Projecteas01Service projecteService) {
        System.out.println("¿Cómo deseas eliminar los proyectos?");
        System.out.println("1. Eliminar todos los proyectos");
        System.out.println("2. Eliminar un proyecto por ID");

        int opcion = tcl.nextInt();
        tcl.nextLine();

        switch (opcion) {
            case 1:
                break;
            case 2:
                break;
            default:
                System.out.println("Opción no válida.");
                break;
        }
    }

    //*************** DELETE TASCA  *****************************//
    protected static void eliminarTasques(Tascaas01Service tascaService) {
        System.out.println("¿Cómo deseas eliminar las tasques?");
        System.out.println("1. Eliminar todas las tasques");
        System.out.println("2. Eliminar una tasca por ID");

        int opcion = tcl.nextInt();
        tcl.nextLine();

        switch (opcion) {
            case 1:
                break;
            case 2:
                break;
            default:
                System.out.println("Opción no válida.");
                break;
        }
    }
    // *************** fin delete *****************************************
}
