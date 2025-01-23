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
import Service.Operariresponsableas01Service;
import Service.Projecteas01Service;
import Service.Tascaas01Service;
import static Test.MainApp.log;
import static Test.MainApp.tcl;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    public static void agregarFacturaMenu(Facturaas01Service facturaService, Tascaas01Service tascaService, Clientas01Service clientasService,
            Projecteas01Service projecteService, Operariresponsableas01Service operariService) {

        System.out.println("Com vols agregar les factures?");
        System.out.println("1. Bàsic (Només creem la factura)");
        System.out.println("2. Complet (Creem factura i, si no existeix alguna dada associada, la creem)");

        int opcion = tcl.nextInt();
        tcl.nextLine();

        switch (opcion) {
            case 1:
                System.out.println("=== CREACIÓ FACTURES ===");
                System.out.println("*=== [MODE BÀSIC] ===*");
                agregarFacturaBasic(facturaService, tascaService, clientasService);
                break;
            case 2:
                System.out.println("=== CREACIÓ FACTURES ===");
                System.out.println("*=== [MODE COMPLET] ===*");
                agregarFacturaComplete(facturaService, tascaService, clientasService, projecteService, operariService);
                break;

            default:
                System.out.println("Opció no vàlida.");
                break;
        }

    }

    public static void agregarFacturaBasic(Facturaas01Service facturaService, Tascaas01Service tascaService, Clientas01Service clientService) {
        String continueAdding = "si";

        while (continueAdding.equalsIgnoreCase("si")) {

            System.out.println("Introdueix les dades de la factura:");

            Integer idTasca = null;
            while (idTasca == null) {
                System.out.print("ID de la Tasca (No pot estar buit i ha d'existir): ");
                System.out.println("Llista de tasques disponibles:");
                MethodsMainTasca.listTasquesBasic(tascaService);
                System.out.println("Factura associada a tasca...");
                String inputIdTasca = tcl.nextLine();
                try {
                    idTasca = Integer.parseInt(inputIdTasca);
                    if (tascaService.findTascaById(idTasca) == null) {
                        System.out.println("La tasca amb ID " + idTasca + " no existeix. Torna-ho a intentar.");
                        idTasca = null;
                    }
                } catch (NumberFormatException e) {
                    log.info("ID de tasca invàlid. Torna-ho a intentar.");
                }
            }

            Integer idClient = null;
            while (idClient == null) {
                System.out.print("ID del Client (No pot estar buit i ha d'existir): ");
                System.out.println("Llista de clients disponibles:");
                MethodsMainClient.listClientsBasic(clientService);
                System.out.println("Factura associada a client...");
                String inputIdCliente = tcl.nextLine();
                try {
                    idClient = Integer.parseInt(inputIdCliente);
                    if (clientService.findClientById(idClient) == null) {
                        System.out.println("El client amb ID " + idClient + " no existeix. Torna-ho a intentar.");
                        idClient = null;
                    }
                } catch (NumberFormatException e) {
                    log.info("ID de client invàlid. Torna-ho a intentar.");
                }
            }

            // Validar data (no puede estar vacía)
            Date data = null;
            while (data == null) {
                System.out.print("Data (Format: yyyy-MM-dd, No pot estar buida): ");
                String input = tcl.nextLine();
                try {
                    data = new SimpleDateFormat("yyyy-MM-dd").parse(input);
                } catch (ParseException e) {
                    System.out.println("Data invàlida. Assegura't d'usar el format yyyy-MM-dd.");
                }
            }

            Double importTotal = null;
            while (importTotal == null) {
                System.out.print("Import Total (numero positiu): ");
                String input = tcl.nextLine();
                try {
                    importTotal = Double.parseDouble(input);
                    if (importTotal <= 0) {
                        System.out.println("L'import total ha de ser positiu.");
                        importTotal = null;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Import total invàlid. Torna-ho a intentar.");
                }
            }

            // (puede estar vacío)
            System.out.print("Observacions: ");
            String observacions = tcl.nextLine();

            // Crear la entidad de nueva factura
            Facturaas01 newFactura = new Facturaas01();
            newFactura.setIdTasca(tascaService.findTascaById(idTasca));
            newFactura.setIdClient(clientService.findClientById(idClient));
            newFactura.setData(data);
            newFactura.setImportTotal(importTotal);
            newFactura.setObservacions(observacions);

            // bd
            try {
                facturaService.createFactura(newFactura);
                System.out.println("Factura agregada amb èxit.");
            } catch (Exception e) {
                System.out.println("Error en agregar la factura: " + e.getMessage());
            }

            boolean validInput = false;
            while (!validInput) {
                System.out.print("Vols agregar una altra factura? (si/no): ");
                continueAdding = tcl.nextLine().trim().toLowerCase();

                if (continueAdding.equals("si") || continueAdding.equals("no")) {
                    validInput = true;
                } else {
                    System.out.println("Per favor, introdueix 'si' per continuar o 'no' per eixir.");
                }
            }
        }
    }

    public static void agregarFacturaComplete(Facturaas01Service facturaService, Tascaas01Service tascaService, Clientas01Service clientService,
            Projecteas01Service projecteService, Operariresponsableas01Service operariService) {

        String continueAdding = "si";

        while (continueAdding.equalsIgnoreCase("si")) {

            System.out.println("Introdueix les dades de la factura:");

            // Validar fecha (no puede estar vacía)
            Date data = null;
            while (data == null) {
                System.out.print("Data (Format: yyyy-MM-dd, No pot estar buida): ");
                String input = tcl.nextLine();
                try {
                    data = new SimpleDateFormat("yyyy-MM-dd").parse(input);
                } catch (ParseException e) {
                    System.out.println("Data invàlida. Assegura't d'usar el format yyyy-MM-dd.");
                }
            }

            Double importTotal = null;
            while (importTotal == null) {
                System.out.print("Import Total (numero positiu): ");
                String input = tcl.nextLine();
                try {
                    importTotal = Double.parseDouble(input);
                    if (importTotal <= 0) {
                        System.out.println("L'import total ha de ser positiu.");
                        importTotal = null;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Import total invàlid. Torna-ho a intentar.");
                }
            }

            // (puede estar vacío)
            System.out.print("Observacions: ");
            String observacions = tcl.nextLine();

            // Crear la entidad de nueva factura
            Facturaas01 newFactura = new Facturaas01();
            newFactura.setData(data);
            newFactura.setImportTotal(importTotal);
            newFactura.setObservacions(observacions);

            facturaService.createFactura(newFactura);
            // CON ESTO PODREMOS ACCEDER A SU ID AUTOGENERADO
            Integer idFactura = newFactura.getIdFactura();

            // Asociar tarea a la factura
            Integer idTasca = asociarTareaFactura(tascaService, projecteService, facturaService, operariService, clientService, idFactura);

            // Asociar cliente a la factura
            Integer idClient = asociarClienteFactura(clientService, idFactura);

            newFactura.setIdTasca(tascaService.findTascaById(idTasca));
            newFactura.setIdClient(clientService.findClientById(idClient));

            try {
                facturaService.createFactura(newFactura); // Se inserta la factura a la BASE DE DATOS
            } catch (Exception e) {
                System.out.println("Error en agregar la factura: " + e.getMessage());
            }

            boolean validInput = false;
            while (!validInput) {
                System.out.print("¿Vols agregar una altra factura en modo completo? (si/no): ");
                continueAdding = tcl.nextLine().trim().toLowerCase();

                if (continueAdding.equals("si") || continueAdding.equals("no")) {
                    validInput = true;
                } else {
                    System.out.println("Per favor, introdueix 'si' per continuar o 'no' per eixir.");
                }
            }
        }
    }

    // Método para asociar tarea
    private static Integer asociarTareaFactura(
            Tascaas01Service tascaService,
            Projecteas01Service projecteService,
            Facturaas01Service facturaService,
            Operariresponsableas01Service operariService,
            Clientas01Service clientService,
            Integer idFactura) {

        System.out.println("[TASCA ASSOCIADA A FACTURA]");

        Integer idTasca = null;
        while (idTasca == null) {
            System.out.println("Opcions:");
            System.out.println("1. Buscar tasca existent");
            System.out.println("2. Crear una nova tasca");
            System.out.print("Tria una opció per a la tasca (1/2): ");
            String option = tcl.nextLine();

            if (option.equals("1")) {
                System.out.println("Llista de tasques disponibles:");
                MethodsMainTasca.listTasquesBasic(tascaService);
                System.out.print("ID de la Tasca a associar: ");
                String inputIdTasca = tcl.nextLine();
                try {
                    idTasca = Integer.parseInt(inputIdTasca);
                    if (tascaService.findTascaById(idTasca) == null) {
                        System.out.println("La tasca amb ID " + idTasca + " no existeix.");
                        idTasca = null;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("ID de tasca invàlid. Torna-ho a intentar.");
                }
            } else if (option.equals("2")) {
                System.out.println("Creant una nova tasca...");
                idTasca = MethodsMainTasca.agregarTascaComplete(tascaService, projecteService, clientService, facturaService, operariService, idFactura);
                if (idTasca != null) {
                    System.out.println("Tasca creada amb ID: " + idTasca);
                } else {
                    System.out.println("No s'ha pogut crear la tasca.");
                }
            } else {
                System.out.println("Opció no vàlida. Torna-ho a intentar.");
            }
        }
        return idTasca;
    }

    // Método para asociar cliente
    private static Integer asociarClienteFactura(Clientas01Service clientService, Integer idFactura) {

        System.out.println("[CLIENT ASSOCIAT A FACTURA]");

        Integer idClient = null;
        while (idClient == null) {
            System.out.println("Opcions:");
            System.out.println("1. Buscar client existent");
            System.out.println("2. Crear un nou client");
            System.out.print("Tria una opció per al client (1/2): ");
            String optionClient = tcl.nextLine();

            if (optionClient.equals("1")) {
                System.out.println("Llista de clients disponibles:");
                MethodsMainClient.listClientsBasic(clientService);
                System.out.print("ID del Client a associar: ");
                String input = tcl.nextLine();
                try {
                    idClient = Integer.parseInt(input);
                    if (clientService.findClientById(idClient) == null) {
                        System.out.println("El client amb ID " + idClient + " no existeix.");
                        idClient = null;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("ID de client invàlid. Torna-ho a intentar.");
                }
            } else if (optionClient.equals("2")) {
                System.out.println("Creant un nou client...");
                idClient = MethodsMainClient.agregarClienteBasic(clientService, idFactura);
                if (idClient != null) {
                    System.out.println("Client creat amb ID: " + idClient);
                } else {
                    System.out.println("No s'ha pogut crear el client.");
                }
            } else {
                System.out.println("Opció no vàlida. Torna-ho a intentar.");
            }
        }
        return idClient;
    }

    // METODO SOBRECARGADO
    //CREAR FACTURA CON ID TASCA
    public static Integer agregarFacturaComplete(Facturaas01Service facturaService, Tascaas01Service tascaService, Clientas01Service clientService,
            Projecteas01Service projecteService, Operariresponsableas01Service operariService, Integer idTasca) {

        System.out.println("[FACTURA ASSOCIADA A TASCA]");

        System.out.println("Introdueix les dades de la factura:");

        // Validar fecha (no puede estar vacía)
        Date data = null;
        while (data == null) {
            System.out.print("Data (Format: yyyy-MM-dd, No pot estar buida): ");
            String input = tcl.nextLine();
            try {
                data = new SimpleDateFormat("yyyy-MM-dd").parse(input);
            } catch (ParseException e) {
                System.out.println("Data invàlida. Assegura't d'usar el format yyyy-MM-dd.");
            }
        }

        Double importTotal = null;
        while (importTotal == null) {
            System.out.print("Import total (número positiu): ");
            String input = tcl.nextLine();
            try {
                importTotal = Double.parseDouble(input);
                if (importTotal <= 0) {
                    System.out.println("L'import total ha de ser positiu.");
                    importTotal = null;
                }
            } catch (NumberFormatException e) {
                System.out.println("Import total invàlid. Torna-ho a intentar.");
            }
        }

        // (puede estar vacío)
        System.out.print("Observacions: ");
        String observacions = tcl.nextLine();

        // Crear la entidad de nueva factura
        Facturaas01 newFactura = new Facturaas01();
        newFactura.setData(data);
        newFactura.setImportTotal(importTotal);
        newFactura.setObservacions(observacions);

        facturaService.createFactura(newFactura);
        // CON ESTO PODREMOS ACCEDER A SU ID AUTOGENERADO
        Integer idFactura = newFactura.getIdFactura();

        // Asociar tarea a la factura
        // YA LO TENEMOS DE ANTES - PARÁMETRO
        // Asociar cliente a la factura
        Integer idClient = asociarClienteFactura(clientService, idFactura);

        newFactura.setIdTasca(tascaService.findTascaById(idTasca));
        newFactura.setIdClient(clientService.findClientById(idClient));

        try {
            facturaService.createFactura(newFactura); // Se inserta la factura a la BASE DE DATOS
            System.out.println("Factura agregada correctament");
            return newFactura.getIdFactura();
        } catch (Exception e) {
            System.out.println("Error en agregar la factura: " + e.getMessage());
        }

        return null;
    }

    //*****************************************************************//
    //********************** FIND ************************************//
    //*****************************************************************//
    //*************** LISTAR FACTURAS  *****************************//
    protected static void listFacturas(Facturaas01Service facturaService) {

        System.out.println("Com vols veure les factures?");
        System.out.println("1. Bàsic (Només dades de la factura)");
        System.out.println("2. Complet (Dades de la factura + taules relacionades)");

        int opcion = tcl.nextInt();
        tcl.nextLine();

        switch (opcion) {
            case 1:
                System.out.println("=== MOSTRANT FACTURES ===");
                System.out.println("*=== [MODE BÀSIC] ===*");
                listFacturaBasic(facturaService);
                break;

            case 2:
                System.out.println("=== MOSTRANT FACTURES ===");
                System.out.println("*=== [MODE COMPLET] ===*");
                listFacturaComplete(facturaService);
                break;

            default:
                System.out.println("Opció no vàlida.");
                break;
        }
    }

    public static void listFacturaComplete(Facturaas01Service facturaService) {
        List<Facturaas01> facturas = facturaService.findAllWithDetails();
        facturas.forEach(factura -> {

            System.out.println("- > FACTURA Nº [" + factura.getIdFactura() + "] "
                    + factura.getObservacions() + " | "
                    + factura.getData() + " | "
                    + factura.getImportTotal());

            System.out.println("\tFactura nº " + factura.getIdFactura() + " es del client:");
            Clientas01 cliente = factura.getIdClient();
            if (cliente != null) {
                System.out.println("\t  - [" + cliente.getIdClient() + "] "
                        + cliente.getNom() + " " + cliente.getCognom()
                        + " - NIF: " + cliente.getNif());
            } else {
                System.out.println("\t  Client associat no trobat.");
            }

            System.out.println("\tFactura nº " + factura.getIdFactura() + " es de la tasca :");
            Tascaas01 tarea = factura.getIdTasca();
            if (tarea != null) {
                System.out.println("\t  - [" + tarea.getIdTasca() + "] "
                        + tarea.getDescripcio() + " | "
                        + tarea.getEstat());
            } else {
                System.out.println("\t Tasca associada no trobada.");
            }
        });
    }

    public static void listFacturaBasic(Facturaas01Service facturaService) {
        System.out.println("|--------------------------------------|");
        for (Facturaas01 factura : facturaService.findAllFacturas()) {
            System.out.println("---> [" + factura.getIdFactura() + "] "
                    + factura.getObservacions() + " | "
                    + factura.getData() + " | "
                    + factura.getImportTotal());
        }
        System.out.println("|--------------------------------------|");
    }

    //*****************************************************************//
    //********************** DELETE ************************************//
    //*****************************************************************//
    //*************** DELETE FACTURAS  *****************************//
    protected static void eliminarFacturas(Facturaas01Service facturaService) {
        System.out.println("**** Avís ****");
        System.out.println("Només es pot ESBORRAR una factura SI han passat més de 5 anys des de la seua creació");
        MainApp.esperarIntro();

        System.out.println("\nCom vols eliminar les factures?");
        System.out.println("1. Eliminar totes les factures");
        System.out.println("2. Eliminar una factura per ID");

        int opcion = tcl.nextInt();
        tcl.nextLine();

        switch (opcion) {
            case 1:
                System.out.println("Eliminant totes les factures...");
                if (facturaService.findAllFacturas().isEmpty()) {
                    System.out.println("No hi ha factures per eliminar.");
                } else {
                    // Se itera cada factura, se revisa si se puede eliminar
                    List<Facturaas01> facturasAEliminar = new ArrayList<>();
                    for (Facturaas01 factura : facturaService.findAllFacturas()) {
                        String info = facturaService.deleteFacturaVerification(factura);
                        if (info == null) {
                            facturasAEliminar.add(factura);
                        } else {
                            System.out.println("No es pot eliminar la factura amb ID " + factura.getIdFactura() + ": " + info);
                        }
                    }

                    if (!facturasAEliminar.isEmpty()) {
                        System.out.println("Les següents factures seran eliminades:");
                        for (Facturaas01 factura : facturasAEliminar) {
                            System.out.println("Factura ID: " + factura.getIdFactura());
                        }

                        // Confirmación
                        System.out.print("Estàs segur que vols eliminar aquestes factures? (S/N): ");
                        String confirmacion = tcl.nextLine();
                        if (confirmacion.equalsIgnoreCase("S")) {
                            for (Facturaas01 factura : facturasAEliminar) {
                                try {
                                    facturaService.deleteFactura(factura);
                                    System.out.println("Factura amb ID " + factura.getIdFactura() + " ha sigut eliminada correctament.");
                                } catch (Exception e) {
                                    System.out.println("Error en eliminar la factura amb ID " + factura.getIdFactura() + ": " + e.getMessage());
                                }
                            }
                        } else {
                            System.out.println("L'eliminació ha sigut cancel·lada.");
                        }
                    } else {
                        System.out.println("No hi ha factures per eliminar.");
                    }
                }
                break;
            case 2:
                System.out.println("Factures disponibles:");
                listFacturaComplete(facturaService);

                System.out.print("Introdueix l'ID de la factura a eliminar: ");
                int idFactura = tcl.nextInt();
                tcl.nextLine();

                Facturaas01 factura = facturaService.findFacturaById(idFactura);
                if (factura != null) {
                    String info = facturaService.deleteFacturaVerification(factura);
                    if (info == null) {
                        try {
                            facturaService.deleteFactura(factura);
                            System.out.println("Factura amb ID " + idFactura + " ha sigut eliminada.");
                        } catch (Exception e) {
                            System.out.println("Error en eliminar la factura amb ID " + idFactura + ": " + e.getMessage());
                        }
                    } else {
                        System.out.println(info);
                    }
                } else {
                    System.out.println("No s'ha trobat cap factura amb aquest ID.");
                }
                break;
            default:
                System.out.println("Opció no vàlida.");
                break;
        }
    }
}
