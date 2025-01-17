/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Test;

import Entity.Clientas01;
import Entity.Facturaas01;
import Entity.Tascaas01;
import Service.Clientas01Service;
import Service.Facturaas01Service;
import Service.Tascaas01Service;
import static Test.MainApp.log;
import static Test.MainApp.tcl;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 *
 * @author angsaegim
 */
public class MethodsMainFactura {

    //*****************************************************************//
    //********************** CREATE ************************************//
    //*****************************************************************//
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
                System.out.println("Quieres ver la lista de tareas existentes ? ");
                System.out.println("Factura asociada a tarea...");
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
                System.out.println("Quieres ver la lista de clientes ? ");
                System.out.println("Factura asociada a cliente...");
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
                        System.out.println("Deseas crear una nueva tarea asociada a esta la factura?");
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
                        System.out.println("Deseas crear un nuevo cliente asociado a esa factura?");
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

    //*****************************************************************//
    //********************** FIND ************************************//
    //*****************************************************************//
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
                for (Facturaas01 factura : facturaService.findAllFacturas()) {
                    System.out.println("---> " + "[" + factura.getIdFactura() + "]" + " " + factura.getObservacions() + " | " + factura.getData() + " | " + factura.getImportTotal());
                }
                break;

            case 2:
                log.info("=== MOSTRANDO FACTURAS ===");
                log.info("*=== [MODO COMPLETO] ===*");
                listFacturaComplete(facturaService);
                break;

            default:
                System.out.println("Opción no válida.");
                break;
        }
    }

    public static void listFacturaComplete(Facturaas01Service facturaService) {
        List<Facturaas01> facturas = facturaService.findAllWithDetails();
        facturas.forEach(factura -> {

            System.out.println("- > FACTURA Nº [" + factura.getIdFactura() + "]" + " " + factura.getObservacions() + " | " + factura.getData() + " | " + factura.getImportTotal());

            System.out.println("\t" + "Factura nº " + factura.getIdFactura() + " es del cliente: ");
            Clientas01 cliente = factura.getIdClient();
            if (cliente != null) {
                System.out.println("\t -" + "[" + cliente.getIdClient() + "]" + cliente.getNom() + " " + cliente.getCognom() + " - NIF: " + cliente.getNif());
            } else {
                System.out.println("\t" + "Cliente asociado no encontrado.");
            }

            System.out.println("\t" + "Factura nº " + factura.getIdFactura() + " es de la tarea : ");
            Tascaas01 tarea = factura.getIdTasca();
            if (tarea != null) {
                System.out.println("\t -" + "[" + tarea.getIdTasca() + "]" + tarea.getDescripcio() + "|" + tarea.getEstat());
            } else {
                System.out.println("\t" + "Tarea asociada no encontrada.");
            }
        });
    }

    //*****************************************************************//
    //********************** DELETE ************************************//
    //*****************************************************************//
    
    //*************** DELETE FACTURAS  *****************************//
    protected static void eliminarFacturas(Facturaas01Service facturaService) {
        
        System.out.println("¿Cómo deseas eliminar las facturas?");
        System.out.println("1. Eliminar todas las facturas");
        System.out.println("2. Eliminar una factura por ID");

        int opcion = tcl.nextInt();
        tcl.nextLine();

        switch (opcion) {
            case 1:
                System.out.print("Eliminando todas las facturas... ");
                if (facturaService.findAllFacturas().isEmpty()) {
                    System.out.println("No hay facturas para eliminar.");
                } else {
                    System.out.println("Eliminando...");
                    facturaService.deleteTable();
                    System.out.println("Todos los clientes han sido eliminados.");
                }
                break;
            case 2:
                System.out.print("Introduce el ID de la factura a eliminar: ");
                System.out.println("Facturas disponibles: ");
                for (Facturaas01 factura : facturaService.findAllFacturas()) {
                    System.out.println("[" + factura.getIdFactura() + "] Fecha: " + factura.getData() + " - Total: " + factura.getImportTotal() + "€" + "Observacions: " + factura.getObservacions());
                }
                System.out.print("ID de la factura a eliminar: ");
                int idFactura = tcl.nextInt();
                tcl.nextLine(); 

                Facturaas01 factura = facturaService.findFacturaById(idFactura);
                if (factura != null) {
                    facturaService.deleteFactura(factura);
                    System.out.println("Factura con ID " + idFactura + " ha sido eliminada.");
                } else {
                    System.out.println("No se encontró una factura con ese ID.");
                }
                break;
            default:
                System.out.println("Opción no válida.");
                break;
        }
    }

}
