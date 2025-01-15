/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Test;

import Entity.Clientas01;
import Service.Clientas01Service;
import static Test.MainApp.log;
import static Test.MainApp.tcl;
import static Test.MehtodsMainEntities.esNifValido;
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
}
