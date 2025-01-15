/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Test;

import Entity.Projecteas01;
import Entity.Tascaas01;
import Service.Clientas01Service;
import Service.Projecteas01Service;
import Service.Tascaas01Service;
import static Test.MainApp.log;
import static Test.MainApp.tcl;
import java.util.Collection;

/**
 *
 * @author angsaegim
 */
public class MethodsMainProjecte {
    
    //*****************************************************************//
    //********************** CREATE ************************************//
    //*****************************************************************//
    
    //*************** AGREGAR PROJECTE  *****************************//
    
    public static void agregarProjecteMenu(Projecteas01Service projecteService, Clientas01Service clientasService, Tascaas01Service tascaService) {

        System.out.println("¿Cómo deseas agregar a los proyectos?");
        System.out.println("1. Básico (Solo creamos el proyecto)");
        System.out.println("2. Completo (Creamos el proyecto y si no existe algún dato asociado, lo creamos)");

        int opcion = tcl.nextInt();
        tcl.nextLine();

        switch (opcion) {
            case 1:
                System.out.println("=== CREACIÓN PROYECTOS ===");
                System.out.println("*=== [MODO BÁSICO] ===*");
                agregarProjecteBasic(projecteService, clientasService, tascaService);
                break;
            case 2:
                System.out.println("=== CREACIÓN PROYECTOS ===");
                System.out.println("*=== [MODO COMPLETO] ===*");
                agregarProjecteComplete(projecteService, clientasService, tascaService);
                break;

            default:
                System.out.println("Opción no válida.");
                break;
        }
    }

    public static void agregarProjecteBasic(Projecteas01Service projecteService, Clientas01Service clientasService, Tascaas01Service tascaService) {

        String continueAdding = "si";

        while (continueAdding.equalsIgnoreCase("si")) {

            System.out.println("Introduce los datos del proyecto:");

            System.out.print("Descripción del proyecto: ");
            String descripcio = tcl.nextLine();

            String estat = "";
            while (true) {
                System.out.println("Selecciona el estado del proyecto:");
                System.out.println("1. No iniciat");
                System.out.println("2. En procés");
                System.out.println("3. Finalitzat");
                System.out.print("Introduce el número correspondiente al estado: ");
                int opcion = tcl.nextInt();
                tcl.nextLine();

                switch (opcion) {
                    case 1:
                        estat = "No iniciat";
                        break;
                    case 2:
                        estat = "En procés";
                        break;
                    case 3:
                        estat = "Finalitzat";
                        break;
                    default:
                        System.out.println("Opción no válida. Intenta nuevamente.");
                        continue; // Volver al inicio del bucle
                }
                break;
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

            Integer idTasca = null;
            while (idTasca == null) {
                System.out.print("ID de la tasca (debe ser un número válido): ");
                String inputIdTasca = tcl.nextLine();
                try {
                    idTasca = Integer.parseInt(inputIdTasca);
                    // Aquí puedes verificar si `idTasca` es válido según tu lógica de negocio.
                    if (tascaService.findTascaById(idTasca) == null) {
                        System.out.println("La tarea con ID " + idTasca + " no existe. Inténtalo nuevamente.");
                        idTasca = null;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("ID de tasca inválido. Inténtalo nuevamente.");
                }
            }

            Projecteas01 newProjecte = new Projecteas01();
            newProjecte.setDescripcio(descripcio);
            newProjecte.setEstat(estat);
            newProjecte.setTascaas01Collection((Collection<Tascaas01>) tascaService.findTascaById(idTasca));
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

    public static void agregarProjecteComplete(Projecteas01Service projecteService, Clientas01Service clientasService, Tascaas01Service tascaService) {

        String continueAdding = "si";

        while (continueAdding.equalsIgnoreCase("si")) {
            
            System.out.println("Introduce los datos del proyecto:");

            System.out.print("Descripción del proyecto: ");
            String descripcio = tcl.nextLine();

            String estat = "";
            while (true) {
                System.out.println("Selecciona el estado del proyecto:");
                System.out.println("1. No iniciat");
                System.out.println("2. En procés");
                System.out.println("3. Finalitzat");
                System.out.print("Introduce el número correspondiente al estado: ");
                int opcion = tcl.nextInt();
                tcl.nextLine();

                switch (opcion) {
                    case 1:
                        estat = "No iniciat";
                        break;
                    case 2:
                        estat = "En procés";
                        break;
                    case 3:
                        estat = "Finalitzat";
                        break;
                    default:
                        System.out.println("Opción no válida. Intenta nuevamente.");
                        continue; // Volver al inicio del bucle
                }
                break;
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
                    System.out.println("ID de cliente inválido.");
                    System.out.println("Deseas crear un cliente nuevo y asociarlo al proyecto");
                }
            }

            Integer idTasca = null;
            while (idTasca == null) {
                System.out.print("ID de la tasca (debe ser un número válido): ");
                String inputIdTasca = tcl.nextLine();
                try {
                    idTasca = Integer.parseInt(inputIdTasca);
                    // Aquí puedes verificar si `idTasca` es válido según tu lógica de negocio.
                    if (tascaService.findTascaById(idTasca) == null) {
                        System.out.println("La tarea con ID " + idTasca + " no existe. Inténtalo nuevamente.");
                        idTasca = null;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("ID de tasca inválido. Inténtalo nuevamente.");
                }
            }

            Projecteas01 newProjecte = new Projecteas01();
            newProjecte.setDescripcio(descripcio);
            newProjecte.setEstat(estat);
            newProjecte.setTascaas01Collection((Collection<Tascaas01>) tascaService.findTascaById(idTasca));
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
    
     //*****************************************************************//
    //********************** FIND ************************************//
    //*****************************************************************//
    
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
    
    //*****************************************************************//
    //********************** DELETE ************************************//
    //*****************************************************************//
    
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
}
