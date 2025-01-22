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
            System.out.println("Introduce los datos del cliente:");

            //NO PUEDE SER NULO - no puede estar vacío
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
                listClientsBasic(clientService);
                break;
            case 2:
                log.info("=== MOSTRANDO CLIENTES ===");
                log.info("*=== [MODO COMPLETO] ===*");
                listClientsComplete(clientService);
                break;
            default:
                System.out.println("Opción no válida.");
                break;
        }
    }

    public static void listClientsComplete(Clientas01Service clientService) {
        List<Clientas01> clientes = clientService.findAllWithDetails();
        clientes.forEach(cliente -> {

            log.info("-> [" + cliente.getIdClient() + " ] Cliente: " + cliente.getNom() + " "
                    + cliente.getCognom() + " - NIF: " + cliente.getNif());

            // Proyectos
            Collection<Projecteas01> proyectos = cliente.getProjecteas01Collection();
            if (proyectos == null || proyectos.isEmpty()) {
                log.info("\t- Proyectos: No hay proyectos.");
            } else {
                log.info("\t- Proyectos de " + cliente.getNom() + ":");
                proyectos.forEach(proyecto -> {
                    String descripcion = proyecto.getDescripcio();
                    String estat = proyecto.getEstat();
                    String fechaFinalizacion = proyecto.getFechaFinalitzacio() != null
                            ? proyecto.getFechaFinalitzacio().toString()
                            : "No finalizado";

                    System.out.println("---> [" + proyecto.getIdProjecte() + "] Proyecto: " + descripcion
                            + " | Estado: " + estat
                            + " | Fecha finalización: " + fechaFinalizacion);
                });
            }

            // Facturas
            Collection<Facturaas01> facturas = cliente.getFacturaas01Collection();
            if (facturas == null || facturas.isEmpty()) {
                log.info("\t- Facturas: No hay facturas.");
            } else {
                log.info("\t- Facturas de " + cliente.getNom() + ":");
                facturas.forEach(factura -> {
                    Integer idFactura = factura.getIdFactura();
                    String observaciones = factura.getObservacions();
                    String fechaFactura = factura.getData() != null ? factura.getData().toString() : "Fecha no disponible";
                    Double importeTotal = factura.getImportTotal();

                    System.out.println("---> Factura: " + "[" + idFactura + "] "
                            + "Observaciones: " + observaciones
                            + " | Fecha: " + fechaFactura
                            + " | Importe Total: " + importeTotal);
                    
                });
            }
        });
    }

    public static void listClientsBasic(Clientas01Service clientService) {
        System.out.println("|--------------------------------------|");
        for (Clientas01 cliente : clientService.findAllClients()) {
            System.out.println("---> [" + cliente.getIdClient() + "] "
                    + cliente.getNom() + " " + cliente.getCognom()
                    + " - NIF: " + cliente.getNif());
        }
        System.out.println("|--------------------------------------|");
    }

    //*****************************************************************//
    //********************** DELETE ************************************//
    //*****************************************************************//
    //*************** DELETE CLIENT  *****************************//
    protected static void eliminarClientes(Clientas01Service clientService) {
        System.out.println("**** Aviso ****");
        System.out.println("Se podrá eliminar el cliente que ya no tiene proyectos activos O todos los proyectos están finalizados hace más de 5 años.");
        MainApp.esperarIntro();
        
        System.out.println("\n¿Cómo deseas eliminar los clientes?");
        System.out.println("1. Eliminar todos los clientes");
        System.out.println("2. Eliminar un cliente por ID");

        int opcion = tcl.nextInt();
        tcl.nextLine();

        switch (opcion) {
            case 1:
                System.out.print("Eliminando todos los clientes... \n");
                System.out.print("Eliminando todos los clientes... \n");
                if (clientService.findAllClients().isEmpty()) {
                    System.out.println("No hay clientes para eliminar.");
                } else {
                    // Se itera cada cliente, se revisa si se puede eliminar
                    List<Clientas01> clientesAEliminar = new ArrayList<>();
                    for (Clientas01 cliente : clientService.findAllClients()) {
                        String info = clientService.deleteClientVerification(cliente);
                        if (info == null) {
                            clientesAEliminar.add(cliente);
                        } else {
                            System.out.println("No se puede eliminar el cliente con ID " + cliente.getIdClient() + ": " + info);
                        }
                    }

                    if (!clientesAEliminar.isEmpty()) {
                        // Mostrar el listado de clientes a eliminar
                        System.out.println("Los siguientes clientes serán eliminados:");
                        for (Clientas01 cliente : clientesAEliminar) {
                            System.out.println(cliente.getNom() + " (ID: " + cliente.getIdClient() + ")");
                        }

                        // Confirmación antes de eliminar
                        System.out.print("¿Estás seguro de que deseas eliminar estos clientes? (S/N): ");
                        String confirmacion = tcl.nextLine();
                        if (confirmacion.equalsIgnoreCase("S")) {
                            // Eliminar los clientes
                            for (Clientas01 cliente : clientesAEliminar) {
                                try {
                                    clientService.deleteClient(cliente);
                                    System.out.println(cliente.getNom() + " ha sido eliminado/a correctamente.");
                                } catch (Exception e) {
                                    System.out.println("Error al eliminar el cliente con ID " + cliente.getIdClient() + ": " + e.getMessage());
                                }
                            }
                        } else {
                            System.out.println("La eliminación ha sido cancelada.");
                        }
                    } else {
                        System.out.println("No hay clientes para eliminar.");
                    }
                }
                break;
            case 2:
                System.out.println("Clientes disponibles:");
                listClientsComplete(clientService); // Lista todos los clientes disponibles

                System.out.print("Introduce el ID del cliente a eliminar: ");
                int idCliente = tcl.nextInt();
                tcl.nextLine(); // Consumir la nueva línea

                // Buscar al cliente por ID
                Clientas01 cliente = clientService.findClientById(idCliente);
                if (cliente != null) {
                    try {
                        clientService.deleteClient(cliente);
                        if (cliente != null) {
                        } else {
                            System.out.println(cliente.getNom() + " ha sido eliminado/a correctamente.");
                        }
                    } catch (RuntimeException e) {
                        System.out.println("Error al eliminar el cliente: " + e.getMessage()); // Manejo de errores
                    }
                } else {
                    System.out.println("No se encontró un cliente con ese ID.");
                }
                break;
            default:
                System.out.println("Opción no válida.");
                break;
        }
    }
}
