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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author angel
 */
public class MehtodsMainEntities {

    //*************** CONTADOR DE LAS 5 ENTIDADES *****************************
    protected static void showContador(Clientas01Service clientService, Facturaas01Service facturaService,
            Operariresponsableas01Service operariResponsableService, Projecteas01Service projecteService, Tascaas01Service tascaService) {
        String border = "**************************************";
        String title = "          Entidades Contador          ";

        System.out.println(border);
        System.out.println(title);
        System.out.println(border);
        System.out.printf("* %-35s *\n", "Nº Clientes: " + clientService.getAllClients().size());
        System.out.printf("* %-35s *\n", "Nº Facturas: " + facturaService.getAllFacturas().size());
        System.out.printf("* %-35s *\n", "Nº Operarios responsables: " + operariResponsableService.getAllOperaris().size());
        System.out.printf("* %-35s *\n", "Nº Proyectos: " + projecteService.getAllProjects().size());
        System.out.printf("* %-35s *\n", "Nº Tareas: " + tascaService.getAllTasques().size());
        System.out.println(border);
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
    // BASICO Y COMPLETO (Compleyto = upsert)
    public static void agregarClienteBasic(Clientas01Service clientasService) {
        String continueAdding = "si";

        while (continueAdding.equalsIgnoreCase("si")) {
            System.out.println("Introduce los datos del cliente:");

            //NO PUEDE SER NULO - no puede estar vaccío
            String nom = "";
            while (nom.isEmpty()) {
                System.out.print("Nombre (No puede estar vacío): ");
                nom = tcl.nextLine();
            }

            // (puede ser vacío)
            System.out.print("Apellido: ");
            String cognom = tcl.nextLine();

            // NIF (No puede estar vacío) - DEBE SER VALIDO
            String nif = "";
            while (nif.isEmpty() || !esNifValido(nif)) {
                System.out.print("NIF (No puede estar vacío y debe ser válido): ");
                nif = tcl.nextLine().trim();
                if (!esNifValido(nif)) {
                    System.out.println("El NIF ingresado no es válido. Inténtalo de nuevo.");
                }
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
                System.out.print("¿Quieres agregar otro cliente? (si/no): ");
                continueAdding = tcl.nextLine().trim().toLowerCase();

                if (continueAdding.equals("si") || continueAdding.equals("no")) {
                    validInput = true;
                } else {
                    System.out.println("Por favor, ingresa 'si' para continuar o 'no' para salir.");
                }
            }
        }
    }

    //*************** AGREGAR FACTURA  *****************************//
    public static void agregarFacturaMenu(Facturaas01Service facturaService, Tascaas01Service tascaService, Clientas01Service clientasService) {

        System.out.println("¿Cómo deseas agregar las facturas?");
        System.out.println("1. Básico (Solo creamos la factura)");
        System.out.println("2. Completo (Creamos factura y si no existe algun dato asociado lo creamos)");

        int opcion = tcl.nextInt();
        tcl.nextLine();

        switch (opcion) {
            case 1:
                log.info("=== CREACIÓN FACTURAS ===");
                log.info("*=== [MODO BÁSICO] ===*");
                agregarFacturaBasic(facturaService, tascaService, clientasService);
                break;
            case 2:
                log.info("=== CREACIÓN FACTURAS ===");
                log.info("*=== [MODO COMPLETO] ===*");
                agregarFacturaComplete(facturaService, tascaService, clientasService);
                break;

            default:
                System.out.println("Opción no válida.");
                break;
        }

    }

    public static void agregarFacturaBasic(Facturaas01Service facturaService, Tascaas01Service tascaService, Clientas01Service clientasService) {
        String continueAdding = "si";

        while (continueAdding.equalsIgnoreCase("si")) {

            System.out.println("Introduce los datos de la factura:");

            Integer idTasca = null;
            while (idTasca == null) {
                System.out.print("ID de la Tarea (No puede estar vacío y debe existir): ");
                String inputIdTasca = tcl.nextLine();
                try {
                    idTasca = Integer.parseInt(inputIdTasca);
                    if (tascaService.findTascaById(idTasca) == null) {
                        System.out.println("La tarea con ID " + idTasca + " no existe. Inténtalo nuevamente.");
                        idTasca = null;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("ID de tarea inválido. Inténtalo nuevamente.");
                }
            }

            Integer idClient = null;
            while (idClient == null) {
                System.out.print("ID del Cliente (No puede estar vacío y debe existir): ");
                String inputIdCliente = tcl.nextLine();
                try {
                    idClient = Integer.parseInt(inputIdCliente);
                    if (clientasService.findClientById(idClient) == null) {
                        System.out.println("El cliente con ID " + idClient + " no existe. Inténtalo nuevamente.");
                        idClient = null;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("ID de cliente inválido. Inténtalo nuevamente.");
                }
            }

            // Validar data (no puede estar vacía)
            Date data = null;
            while (data == null) {
                System.out.print("Fecha (Formato: yyyy-MM-dd, No puede estar vacía): ");
                String input = tcl.nextLine();
                try {
                    data = new SimpleDateFormat("yyyy-MM-dd").parse(input);
                } catch (ParseException e) {
                    System.out.println("Fecha inválida. Asegúrate de usar el formato yyyy-MM-dd.");
                }
            }

            Double importTotal = null;
            while (importTotal == null) {
                System.out.print("Importe Total (número positivo): ");
                String input = tcl.nextLine();
                try {
                    importTotal = Double.parseDouble(input);
                    if (importTotal <= 0) {
                        System.out.println("El importe total debe ser positivo.");
                        importTotal = null;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Importe total inválido. Inténtalo nuevamente.");
                }
            }

            // (puede estar vacío)
            System.out.print("Observaciones: ");
            String observacions = tcl.nextLine();

            // Crear la entidad de nueva factura
            Facturaas01 newFactura = new Facturaas01();
            newFactura.setIdTasca(tascaService.findTascaById(idTasca));
            newFactura.setIdClient(clientasService.findClientById(idClient));
            newFactura.setData(data);
            newFactura.setImportTotal(importTotal);
            newFactura.setObservacions(observacions);

            // bd
            try {
                facturaService.createFactura(newFactura);
                System.out.println("Factura agregada exitosamente.");
            } catch (Exception e) {
                System.out.println("Error al agregar la factura: " + e.getMessage());
            }

            boolean validInput = false;
            while (!validInput) {
                System.out.print("¿Quieres agregar otra factura? (si/no): ");
                continueAdding = tcl.nextLine().trim().toLowerCase();

                if (continueAdding.equals("si") || continueAdding.equals("no")) {
                    validInput = true;
                } else {
                    System.out.println("Por favor, ingresa 'si' para continuar o 'no' para salir.");
                }
            }
        }
    }

    public static void agregarFacturaComplete(Facturaas01Service facturaService, Tascaas01Service tascaService, Clientas01Service clientasService) {
        String continueAdding = "si";

        while (continueAdding.equalsIgnoreCase("si")) {

            System.out.println("Introduce los datos de la factura:");

            Integer idTasca = null;
            while (idTasca == null) {
                System.out.print("ID de la Tarea : ");
                String inputIdTasca = tcl.nextLine();
                try {
                    idTasca = Integer.parseInt(inputIdTasca);
                    if (tascaService.findTascaById(idTasca) == null) {
                        System.out.println("La tarea con ID " + idTasca + " no existe.");
                        System.out.println("Deseas crear una tasca con id " + idTasca + " ?");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("ID de tarea inválido. Inténtalo nuevamente.");
                }
            }

            Integer idClient = null;
            while (idClient == null) {
                System.out.print("ID del Cliente (No puede estar vacío y debe existir): ");
                String input = tcl.nextLine();
                try {
                    idClient = Integer.parseInt(input);
                    if (clientasService.findClientById(idClient) == null) {
                        System.out.println("El cliente con ID " + idClient + " no existe. Inténtalo nuevamente.");
                        System.out.println("Deseas crear un cliente con id " + idClient + " ?");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("ID de cliente inválido. Inténtalo nuevamente.");
                }
            }

            // Validar data (no puede estar vacía)
            Date data = null;
            while (data == null) {
                System.out.print("Fecha (Formato: yyyy-MM-dd, No puede estar vacía): ");
                String input = tcl.nextLine();
                try {
                    data = new SimpleDateFormat("yyyy-MM-dd").parse(input);
                } catch (ParseException e) {
                    System.out.println("Fecha inválida. Asegúrate de usar el formato yyyy-MM-dd.");
                }
            }

            Double importTotal = null;
            while (importTotal == null) {
                System.out.print("Importe Total (número positivo): ");
                String input = tcl.nextLine();
                try {
                    importTotal = Double.parseDouble(input);
                    if (importTotal <= 0) {
                        System.out.println("El importe total debe ser positivo.");
                        importTotal = null;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Importe total inválido. Inténtalo nuevamente.");
                }
            }

            // (puede estar vacío)
            System.out.print("Observaciones: ");
            String observacions = tcl.nextLine();

            // Crear la entidad de nueva factura
            Facturaas01 newFactura = new Facturaas01();
            newFactura.setIdTasca(tascaService.findTascaById(idTasca));
            newFactura.setIdClient(clientasService.findClientById(idClient));
            newFactura.setData(data);
            newFactura.setImportTotal(importTotal);
            newFactura.setObservacions(observacions);

            // bd
            try {
                facturaService.createFactura(newFactura);
                System.out.println("Factura agregada exitosamente.");
            } catch (Exception e) {
                System.out.println("Error al agregar la factura: " + e.getMessage());
            }

            boolean validInput = false;
            while (!validInput) {
                System.out.print("¿Quieres agregar otra factura? (si/no): ");
                continueAdding = tcl.nextLine().trim().toLowerCase();

                if (continueAdding.equals("si") || continueAdding.equals("no")) {
                    validInput = true;
                } else {
                    System.out.println("Por favor, ingresa 'si' para continuar o 'no' para salir.");
                }
            }
        }
    }

    //*************** AGREGAR OPERARI  *****************************//
    public static void agregarOperariResponsableMenu(Operariresponsableas01Service operariService, Tascaas01Service tascaService) {

        System.out.println("¿Cómo deseas agregar a los operarios responsables?");
        System.out.println("1. Básico (Solo creamos el operario responsable)");
        System.out.println("2. Completo (Creamos el operario responsable y si no existe algún dato asociado, lo creamos)");

        int opcion = tcl.nextInt();
        tcl.nextLine();

        switch (opcion) {
            case 1:
                System.out.println("=== CREACIÓN OPERARIOS RESPONSABLES ===");
                System.out.println("*=== [MODO BÁSICO] ===*");
                agregarOperariBasic(operariService, tascaService);
                break;
            case 2:
                System.out.println("=== CREACIÓN OPERARIOS RESPONSABLES ===");
                System.out.println("*=== [MODO COMPLETO] ===*");
                agregarOperariComplete(operariService, tascaService);
                break;

            default:
                System.out.println("Opción no válida.");
                break;
        }
    }

    public static void agregarOperariBasic(Operariresponsableas01Service operariResponsableService, Tascaas01Service tascaService) {

        String continueAdding = "si";

        while (continueAdding.equalsIgnoreCase("si")) {

            System.out.println("Introduce los datos del operario responsable:");

            System.out.print("Nombre del operario: ");
            String nom = tcl.nextLine();

            System.out.print("Apellido del operario: ");
            String cognom = tcl.nextLine();

            // DEBE SER VALIDO
            String nifOperari = "";
            while (nifOperari.isEmpty() || !esNifValido(nifOperari)) {
                System.out.print("NIF del operario (debe ser único y no puede estar vacío):  ");
                nifOperari = tcl.nextLine().trim();
                if (!esNifValido(nifOperari)) {
                    System.out.println("El NIF ingresado no es válido. Inténtalo de nuevo.");
                }
            }

            Integer idTasca = null;
            while (idTasca == null) {
                System.out.print("ID de la Tarea (No puede estar vacío y debe existir): ");
                String inputIdTasca = tcl.nextLine();
                try {
                    idTasca = Integer.parseInt(inputIdTasca);
                    if (tascaService.findTascaById(idTasca) == null) {
                        System.out.println("La tarea con ID " + idTasca + " no existe. Inténtalo nuevamente.");
                        idTasca = null;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("ID de tarea inválido. Inténtalo nuevamente.");
                }
            }

            System.out.print("Observaciones: ");
            String observacions = tcl.nextLine();

            Operariresponsableas01 newOperari = new Operariresponsableas01();
            newOperari.setNom(nom);
            newOperari.setCognom(cognom);
            newOperari.setNifOperari(nifOperari);
            newOperari.setIdTasca(tascaService.findTascaById(idTasca));
            newOperari.setObservacions(observacions);

            try {
                operariResponsableService.createOperari(newOperari);
                System.out.println("Operario responsable agregado exitosamente.");
            } catch (Exception e) {
                System.out.println("Error al agregar el operario responsable: " + e.getMessage());
            }

            boolean validInput = false;
            while (!validInput) {
                System.out.print("¿Quieres agregar otro operari? (si/no): ");
                continueAdding = tcl.nextLine().trim().toLowerCase();

                if (continueAdding.equals("si") || continueAdding.equals("no")) {
                    validInput = true;
                } else {
                    System.out.println("Por favor, ingresa 'si' para continuar o 'no' para salir.");
                }
            }
        }

    }

    public static void agregarOperariComplete(Operariresponsableas01Service operariResponsableService, Tascaas01Service tascaService) {

        String continueAdding = "si";

        while (continueAdding.equalsIgnoreCase("si")) {

            System.out.println("Introduce los datos del operario responsable:");

            System.out.print("Nombre del operario: ");
            String nom = tcl.nextLine();

            System.out.print("Apellido del operario: ");
            String cognom = tcl.nextLine();

            // DEBE SER VALIDO
            String nifOperari = "";
            while (nifOperari.isEmpty() || !esNifValido(nifOperari)) {
                System.out.print("NIF del operario (debe ser único y no puede estar vacío):  ");
                nifOperari = tcl.nextLine().trim();
                if (!esNifValido(nifOperari)) {
                    System.out.println("El NIF ingresado no es válido. Inténtalo de nuevo.");
                }
            }

            Integer idTasca = null;
            while (idTasca == null) {
                System.out.print("ID de la Tarea (No puede estar vacío y debe existir): ");
                String inputIdTasca = tcl.nextLine();
                try {
                    idTasca = Integer.parseInt(inputIdTasca);
                    if (tascaService.findTascaById(idTasca) == null) {
                        System.out.println("La tarea con ID " + idTasca + " no existe. Inténtalo nuevamente.");
                        System.out.println("Deseas crear una tasca con id " + idTasca + " ?");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("ID de tarea inválido. Inténtalo nuevamente.");
                }
            }

            System.out.print("Observaciones: ");
            String observacions = tcl.nextLine();

            Operariresponsableas01 newOperari = new Operariresponsableas01();
            newOperari.setNom(nom);
            newOperari.setCognom(cognom);
            newOperari.setNifOperari(nifOperari);
            newOperari.setIdTasca(tascaService.findTascaById(idTasca));
            newOperari.setObservacions(observacions);

            boolean validInput = false;
            while (!validInput) {
                System.out.print("¿Quieres agregar otro operari? (si/no): ");
                continueAdding = tcl.nextLine().trim().toLowerCase();

                if (continueAdding.equals("si") || continueAdding.equals("no")) {
                    validInput = true;
                } else {
                    System.out.println("Por favor, ingresa 'si' para continuar o 'no' para salir.");
                }
            }
        }

    }

    //*************** AGREGAR PROJECTE  *****************************//
    public static void agregarProjecteMenu(Projecteas01Service projecteService, Clientas01Service clientasService) {

        System.out.println("¿Cómo deseas agregar a los proyectos?");
        System.out.println("1. Básico (Solo creamos el proyecto)");
        System.out.println("2. Completo (Creamos el proyecto y si no existe algún dato asociado, lo creamos)");

        int opcion = tcl.nextInt();
        tcl.nextLine();

        switch (opcion) {
            case 1:
                System.out.println("=== CREACIÓN PROYECTOS ===");
                System.out.println("*=== [MODO BÁSICO] ===*");
                agregarProjecteBasic(projecteService, clientasService);
                break;
            case 2:
                System.out.println("=== CREACIÓN PROYECTOS ===");
                System.out.println("*=== [MODO COMPLETO] ===*");
                agregarProjecteComplete(projecteService, clientasService);
                break;

            default:
                System.out.println("Opción no válida.");
                break;
        }
    }

    public static void agregarProjecteBasic(Projecteas01Service projecteService, Clientas01Service clientasService) {

        String continueAdding = "si";

        while (continueAdding.equalsIgnoreCase("si")) {

            System.out.println("Introduce los datos del proyecto:");

            System.out.print("Descripción del proyecto: ");
            String descripcio = tcl.nextLine();

            String estat = "";
            while (!estat.equals("No iniciat") && !estat.equals("En procés") && !estat.equals("Finalitzat")) {
                System.out.print("Estado del proyecto (No iniciat, En procés, Finalitzat): ");
                estat = tcl.nextLine().trim();
                if (!estat.equals("No iniciat") && !estat.equals("En procés") && !estat.equals("Finalitzat")) {
                    System.out.println("Estado no válido. Intenta nuevamente.");
                }
            }

            Integer idClient = null;
            while (idClient == null) {
                System.out.print("ID del cliente (debe ser válido y existir): ");
                String inputIdClient = tcl.nextLine();
                try {
                    idClient = Integer.parseInt(inputIdClient);
                    if (clientasService.findClientById(idClient) == null) {
                        System.out.println("El cliente con ID " + idClient + " no existe. Inténtalo nuevamente.");
                        idClient = null;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("ID de cliente inválido. Inténtalo nuevamente.");
                }
            }

            Projecteas01 newProjecte = new Projecteas01();
            newProjecte.setDescripcio(descripcio);
            newProjecte.setEstat(estat);
            newProjecte.setIdClient(clientasService.findClientById(idClient));

            try {
                projecteService.createProject(newProjecte);
                System.out.println("Proyecto agregado exitosamente.");
            } catch (Exception e) {
                System.out.println("Error al agregar el proyecto: " + e.getMessage());
            }

            boolean validInput = false;
            while (!validInput) {
                System.out.print("¿Quieres agregar otro projecte? (si/no): ");
                continueAdding = tcl.nextLine().trim().toLowerCase();

                if (continueAdding.equals("si") || continueAdding.equals("no")) {
                    validInput = true;
                } else {
                    System.out.println("Por favor, ingresa 'si' para continuar o 'no' para salir.");
                }
            }
        }
    }

    public static void agregarProjecteComplete(Projecteas01Service projecteService, Clientas01Service clientasService) {

        String continueAdding = "si";

        while (continueAdding.equalsIgnoreCase("si")) {

            boolean validInput = false;
            while (!validInput) {
                System.out.print("¿Quieres agregar otro projecte? (si/no): ");
                continueAdding = tcl.nextLine().trim().toLowerCase();

                if (continueAdding.equals("si") || continueAdding.equals("no")) {
                    validInput = true;
                } else {
                    System.out.println("Por favor, ingresa 'si' para continuar o 'no' para salir.");
                }
            }
        }
    }

    //*************** AGREGAR TASCA  *****************************//
    public static void agregarTascaMenu(Tascaas01Service tascaService, Projecteas01Service projecteService) {

        System.out.println("¿Cómo deseas agregar las tascas?");
        System.out.println("1. Básico (Solo creamos la tasca)");
        System.out.println("2. Completo (Creamos la tasca y si no existe algún dato asociado, lo creamos)");

        int opcion = tcl.nextInt();
        tcl.nextLine();

        switch (opcion) {
            case 1:
                System.out.println("=== CREACIÓN TASCAS ===");
                System.out.println("*=== [MODO BÁSICO] ===*");
                agregarTascaBasic(tascaService, projecteService);
                break;
            case 2:
                System.out.println("=== CREACIÓN TASCAS ===");
                System.out.println("*=== [MODO COMPLETO] ===*");
                agregarTascaComplete(tascaService, projecteService);
                break;

            default:
                System.out.println("Opción no válida.");
                break;
        }
    }

    public static void agregarTascaBasic(Tascaas01Service tascaService, Projecteas01Service projecteService) {

        String continueAdding = "si";

        while (continueAdding.equalsIgnoreCase("si")) {

            boolean validInput = false;
            while (!validInput) {
                System.out.print("¿Quieres agregar otra tasca? (si/no): ");
                continueAdding = tcl.nextLine().trim().toLowerCase();

                if (continueAdding.equals("si") || continueAdding.equals("no")) {
                    validInput = true;
                } else {
                    System.out.println("Por favor, ingresa 'si' para continuar o 'no' para salir.");
                }
            }
        }

    }

    public static void agregarTascaComplete(Tascaas01Service tascaService, Projecteas01Service projecteService) {

        String continueAdding = "si";

        while (continueAdding.equalsIgnoreCase("si")) {

            boolean validInput = false;
            while (!validInput) {
                System.out.print("¿Quieres agregar otra tasca? (si/no): ");
                continueAdding = tcl.nextLine().trim().toLowerCase();

                if (continueAdding.equals("si") || continueAdding.equals("no")) {
                    validInput = true;
                } else {
                    System.out.println("Por favor, ingresa 'si' para continuar o 'no' para salir.");
                }
            }
        }

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

    // Método auxiliar para validar el formato del NIF
    private static boolean esNifValido(String nif) {
        if (nif == null || nif.length() != 9) {
            return false;
        }

        String numeros = nif.substring(0, 8);
        char letra = nif.charAt(8);

        if (!numeros.matches("\\d+")) {
            return false;
        }

        String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
        int resto = Integer.parseInt(numeros) % 23;
        char letraCorrecta = letras.charAt(resto);

        return letra == letraCorrecta;
    }

}
