/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Test;

import Entity.Clientas01;
import Entity.Facturaas01;
import Entity.Projecteas01;
import Service.Clientas01Service;
import static Test.MainApp.log;
import static Test.MainApp.tcl;
import static Test.MehtodsMainEntities.esNifValido;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author angsaegim
 */
public class MethodsMainClient {

    //*****************************************************************//
    //********************** CREATE ************************************//
    //*****************************************************************//
    //*************** AGREGAR CLIENTE  *****************************//
    // BASICO Y COMPLETO (Compleyto = upsert)
    public static void agregarClienteBasic(Clientas01Service clientasService) {
        String continueAdding = "si";

        while (continueAdding.equalsIgnoreCase("si")) {
            System.out.println("Introdueix les dades del client:");

            //NO PUEDE SER NULO - no puede estar vacío
            String nom = "";
            while (nom.isEmpty()) {
                System.out.print("Nom (No pot estar buit): ");
                nom = tcl.nextLine();
            }

            // (puede ser vacío)
            System.out.print("Cognom: ");
            String cognom = tcl.nextLine();

            // NIF (No puede estar vacío) - DEBE SER VALIDO
            String nif = "";
            while (nif.isEmpty() || !esNifValido(nif)) {
                System.out.print("NIF (No pot estar buit i ha de ser vàlid): ");
                nif = tcl.nextLine().trim();
                if (!esNifValido(nif)) {
                    System.out.println("El NIF introduït no és vàlid. Torna-ho a intentar.");
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
                System.out.println("Client afegit amb èxit.");
            } catch (Exception e) {
                System.out.println("Error en afegir el client: " + e.getMessage());
            }

            // Preguntar al usuario si quiere agregar otro client
            boolean validInput = false;
            while (!validInput) {
                System.out.print("Vols afegir un altre client? (si/no): ");
                continueAdding = tcl.nextLine().trim().toLowerCase();

                if (continueAdding.equals("si") || continueAdding.equals("no")) {
                    validInput = true;
                } else {
                    System.out.println("Introdueix 'si' per continuar o 'no' per eixir.\"");
                }
            }
        }
    }

    // METODO SOBRECARGADO CON IDFACTURA
    // CREA Y DEVUELVE EL ID DEL CLIENTE
    public static Integer agregarClienteBasic(Clientas01Service clientasService, Integer idFactura) {

        System.out.println("[ CLIENT ASSOCIAT A FACTURA ]");

        System.out.println("Introdueix les dades del client:");

        //NO PUEDE SER NULO - no puede estar vacío
        String nom = "";
        while (nom.isEmpty()) {
            System.out.print("Nom (No pot estar buit): ");
            nom = tcl.nextLine();
        }

        // (puede ser vacío)
        System.out.print("Cognom: ");
        String cognom = tcl.nextLine();

        // NIF (No puede estar vacío) - DEBE SER VALIDO
        String nif = "";
        while (nif.isEmpty() || !esNifValido(nif)) {
            System.out.print("NIF (No pot estar buit i ha de ser vàlid): ");
            nif = tcl.nextLine().trim();
            if (!esNifValido(nif)) {
                System.out.println("El NIF introduït no és vàlid. Torna-ho a intentar.");
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
            System.out.println("Client afegit amb èxit.");
            return newClient.getIdClient();
        } catch (Exception e) {
            System.out.println("Error en afegir el client: " + e.getMessage());
        }
        //ONE CLIENTE FACTURA (no creamos más)
        return null;  //Si falla pasamos null
    }

    //*****************************************************************//
    //********************** FIND ************************************//
    //*****************************************************************//
    //*************** LISTAR CLIENTE  *****************************//
    protected static void listClients(Clientas01Service clientService) {

        System.out.println("Com vols veure els clients?");
        System.out.println("1. Bàsic (Només dades del client)");
        System.out.println("2. Complet (Dades del client + taules relacionades)");

        int opcion = tcl.nextInt();
        tcl.nextLine();

        switch (opcion) {
            case 1:
                System.out.println("=== MOSTRANT CLIENTS ===");
                System.out.println("*=== [MODE BÀSIC] ===*");
                listClientsBasic(clientService);
                break;
            case 2:
                System.out.println("=== MOSTRANT CLIENTS ===");
                System.out.println("*=== [MODE COMPLET] ===*");
                listClientsComplete(clientService);
                break;
            default:
                System.out.println("Opció no vàlida.");
                break;
        }
    }

    public static void listClientsComplete(Clientas01Service clientService) {
        List<Clientas01> clients = clientService.findAllWithDetails();
        clients.forEach(client -> {

            System.out.println("-> [" + client.getIdClient() + " ] Client: " + client.getNom() + " "
                    + client.getCognom() + " - NIF: " + client.getNif());

            // Proyectos
            Collection<Projecteas01> projectes = client.getProjecteas01Collection();
            if (projectes == null || projectes.isEmpty()) {
                System.out.println("\t- Projectes: No hi ha projectes.");
            } else {
                System.out.println("\t- Projectes de " + client.getNom() + ":");
                projectes.forEach(projecte -> {
                    String descripcio = projecte.getDescripcio();
                    String estat = projecte.getEstat();
                    String dataFinalitzacio = projecte.getFechaFinalitzacio() != null
                            ? projecte.getFechaFinalitzacio().toString()
                            : "No finalitzat";

                    System.out.println("---> [" + projecte.getIdProjecte() + "] Projecte: " + descripcio
                            + " | Estat: " + estat
                            + " | Data de finalització: " + dataFinalitzacio);
                });
            }

            // Facturas
            Collection<Facturaas01> facturas = client.getFacturaas01Collection();
            if (facturas == null || facturas.isEmpty()) {
                System.out.println("\t- Factures: No hi ha factures.");
            } else {
                System.out.println("\t- Factures de " + client.getNom() + ":");
                facturas.forEach(factura -> {
                    Integer idFactura = factura.getIdFactura();
                    String observacions = factura.getObservacions();
                    String dataFactura = factura.getData() != null ? factura.getData().toString() : "Fecha no disponible";
                    Double importTotal = factura.getImportTotal();

                    System.out.println("---> Factura: " + "[" + idFactura + "] "
                            + "Observacions: " + observacions
                            + " | Data: " + dataFactura
                            + " | Import Total: " + importTotal);
                });
            }
        });
    }

    public static void listClientsBasic(Clientas01Service clientService) {
        System.out.println("|--------------------------------------|");
        for (Clientas01 client : clientService.findAllClients()) {
            System.out.println("---> [" + client.getIdClient() + "] "
                    + client.getNom() + " " + client.getCognom()
                    + " - NIF: " + client.getNif());
        }
        System.out.println("|--------------------------------------|");
    }

    //*****************************************************************//
    //********************** DELETE ************************************//
    //*****************************************************************//
    //*************** DELETE CLIENT  *****************************//
    protected static void eliminarClientes(Clientas01Service clientService) {
        System.out.println("**** Avís ****");
        System.out.println("Es podrà eliminar el client que ja no tinga projectes actius O tots els projectes estiguen finalitzats fa més de 5 anys.");
        MainApp.esperarIntro();

        // 
        //
        System.out.println("\nCom vols eliminar els clients?");
        System.out.println("1. Eliminar tots els clients");
        System.out.println("2. Eliminar un client per ID");

        int opcion = tcl.nextInt();
        tcl.nextLine();

        switch (opcion) {
            case 1: // 
                System.out.print("Eliminant tots els clients... \n");
                if (clientService.findAllClients().isEmpty()) {
                     System.out.println("No hi ha clients per a eliminar.");
                } else {
                    // Se itera cada client, se revisa si se puede eliminar
                    List<Clientas01> clientesAEliminar = new ArrayList<>();
                    for (Clientas01 client : clientService.findAllClients()) {
                        String info = clientService.deleteClientVerification(client);
                        if (info == null) {
                            clientesAEliminar.add(client);
                        } else {
                             System.out.println("No es pot eliminar el client amb ID " + client.getIdClient() + ": " + info);
                        }
                    }

                    if (!clientesAEliminar.isEmpty()) {
                        // Mostrar el listado de clients a eliminar
                        System.out.println("Els següents clients seran eliminats:");
                        for (Clientas01 client : clientesAEliminar) {
                        System.out.println(client.getNom() + " (ID: " + client.getIdClient() + ")");
                        }

                        // Confirmación antes de eliminar
                        System.out.print("Estàs segur que vols eliminar aquests clients? (S/N): ");
                        String confirmacion = tcl.nextLine();
                        if (confirmacion.equalsIgnoreCase("S")) {
                            // Eliminar los clients
                            for (Clientas01 cliente : clientesAEliminar) {
                                try {
                                    clientService.deleteClient(cliente);
                                    System.out.println(cliente.getNom() + " ha sigut eliminat/da correctament.");
                                } catch (Exception e) {
                                    System.out.println("Error en eliminar el client amb ID " + cliente.getIdClient() + ": " + e.getMessage());
                                }
                            }
                        } else {
                            System.out.println("L'eliminació ha sigut cancel·lada.");
                        }
                    } else {
                        System.out.println("No hi ha clients per a eliminar.");
                    }
                }
                break;
                // INFORMAR 
            case 2:
                 System.out.println("Clients disponibles:");
                listClientsComplete(clientService); // Lista todos los clients disponibles

                System.out.print("Introdueix l'ID del client a eliminar: ");
                int idCliente = tcl.nextInt();
                tcl.nextLine(); // Consumir la nueva línea

                // Buscar al client por ID
                Clientas01 cliente = clientService.findClientById(idCliente);
                if (cliente != null) {
                    try {
                        clientService.deleteClient(cliente);
                        if (cliente != null) {
                        } else {
                            System.out.println(cliente.getNom() + " ha sigut eliminat/da correctament.");
                        }
                    } catch (RuntimeException e) {
                        System.out.println("Error en eliminar el client: " + e.getMessage()); // Manejo de errores
                    }
                } else {
                    System.out.println("No s'ha trobat un client amb aquest ID.");
                }
                break;
            default:
                System.out.println("Opció no vàlida.");
                break;
        }
    }
}
