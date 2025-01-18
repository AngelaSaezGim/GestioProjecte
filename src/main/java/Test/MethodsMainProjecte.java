/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Test;

import Entity.Clientas01;
import Entity.Projecteas01;
import Entity.Tascaas01;
import Service.Clientas01Service;
import Service.Projecteas01Service;
import Service.Tascaas01Service;
import static Test.MainApp.log;
import static Test.MainApp.tcl;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

    public static void agregarProjecteBasic(Projecteas01Service projecteService, Clientas01Service clientService, Tascaas01Service tascaService) {

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
                        continue;
                }
                break;
            }

            Integer idClient = null;
            while (idClient == null) {
                System.out.print("ID del cliente (debe ser válido y existir): ");
                System.out.println("Lista de clientes disponibles; ");
                MethodsMainClient.listClientsBasic(clientService);
                System.out.println("Proyecto asociado a cliente...");
                String inputIdClient = tcl.nextLine();
                try {
                    idClient = Integer.parseInt(inputIdClient);
                    if (clientService.findClientById(idClient) == null) {
                        System.out.println("El cliente con ID " + idClient + " no existe. Inténtalo nuevamente.");
                        idClient = null;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("ID de cliente inválido. Inténtalo nuevamente.");
                }
            }

            System.out.print("IDs de las tareas (separados por comas): ");
            System.out.println("Lista de tareas disponibles; ");
            MethodsMainTasca.listTasquesBasic(tascaService);
            System.out.println("Proyecto asociado a tareas... (separalas por comas)");
            String inputTascas = tcl.nextLine();
            String[] tascaIds = inputTascas.split(",");
            List<Tascaas01> tasques = new ArrayList<>();

            for (String tascaId : tascaIds) {
                try {
                    int id = Integer.parseInt(tascaId.trim());
                    Tascaas01 tasca = tascaService.findTascaById(id);
                    if (tasca != null) {
                        tasques.add(tasca);
                    } else {
                        System.out.println("La tarea con ID " + id + " no existe.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("El ID " + tascaId + " no es válido.");
                }
            }

            Projecteas01 newProjecte = new Projecteas01();
            newProjecte.setDescripcio(descripcio);
            newProjecte.setEstat(estat);
            newProjecte.setTascaas01Collection(tasques);
            newProjecte.setIdClient(clientService.findClientById(idClient));

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

    public static void agregarProjecteComplete(Projecteas01Service projecteService, Clientas01Service clientService, Tascaas01Service tascaService) {

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
                        continue; // Volver al inicio del bucle - no puede ser nulo
                }
                break;
            }

            Integer idClient = null;
            while (idClient == null) {
                System.out.print("ID del cliente (debe ser válido y existir): ");
                System.out.println("Lista de clientes disponibles; ");
                MethodsMainClient.listClientsBasic(clientService);
                System.out.println("Proyecto asociado a cliente...");
                String inputIdClient = tcl.nextLine();
                try {
                    idClient = Integer.parseInt(inputIdClient);
                    if (clientService.findClientById(idClient) == null) {
                        System.out.println("El cliente con ID " + idClient + " no existe. Inténtalo nuevamente.");
                        idClient = null;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("ID de cliente inválido.");
                    System.out.println("Deseas crear un cliente nuevo y asociarlo al proyecto");
                }
            }

            System.out.print("IDs de las tareas (separados por comas): ");
            System.out.println("Lista de tareas disponibles; ");
            MethodsMainTasca.listTasquesBasic(tascaService);
            System.out.println("Proyecto asociado a tareas... (separalas por comas)");
            String inputTascas = tcl.nextLine();
            String[] tascaIds = inputTascas.split(",");
            List<Tascaas01> tasques = new ArrayList<>();

            for (String tascaId : tascaIds) {
                try {
                    int id = Integer.parseInt(tascaId.trim());
                    Tascaas01 tasca = tascaService.findTascaById(id);
                    if (tasca != null) {
                        tasques.add(tasca);
                    } else {
                        System.out.println("La tarea con ID " + id + " no existe.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("El ID " + tascaId + " no es válido.");
                }
            }

            Projecteas01 newProjecte = new Projecteas01();
            newProjecte.setDescripcio(descripcio);
            newProjecte.setEstat(estat);
            newProjecte.setTascaas01Collection(tasques);
            newProjecte.setIdClient(clientService.findClientById(idClient));

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
                listProjecteBasic(projecteService);
                break;
            case 2:
                log.info("=== MOSTRANDO PROYECTOS ===");
                log.info("*=== [MODO COMPLETO] ===*");
                listProjecteComplete(projecteService);
                break;
            default:
                System.out.println("Opción no válida.");
                break;
        }
    }

    public static void listProjecteComplete(Projecteas01Service projecteService) {
        List<Projecteas01> proyectos = projecteService.findAllWithDetails();
        proyectos.forEach(proyecto -> {

            System.out.println("- > PROYECTO Nº [" + proyecto.getIdProjecte() + "]" + " " + proyecto.getDescripcio() + " | Estado: " + proyecto.getEstat());

            System.out.println("\t" + "Proyecto nº " + proyecto.getIdProjecte() + " pertenece al cliente: ");
            Clientas01 cliente = proyecto.getIdClient();
            if (cliente != null) {
                System.out.println("\t -" + "[" + cliente.getIdClient() + "]" + cliente.getNom() + " " + cliente.getCognom() + " - NIF: " + cliente.getNif());
            } else {
                System.out.println("\t" + "Cliente asociado no encontrado.");
            }

            System.out.println("\t" + "Proyecto nº " + proyecto.getIdProjecte() + " tiene las siguientes tareas: ");
            Collection<Tascaas01> tareas = proyecto.getTascaas01Collection();
            if (tareas != null && !tareas.isEmpty()) {
                tareas.forEach(tarea -> {
                    System.out.println("\t -" + "[" + tarea.getIdTasca() + "]" + tarea.getDescripcio() + " | Estado: " + tarea.getEstat());
                });
            } else {
                System.out.println("\t" + "No tiene tareas asociadas.");
            }
        });
    }

    public static void listProjecteBasic(Projecteas01Service projecteService) {
        System.out.println("|--------------------------------------|");
        for (Projecteas01 proyecto : projecteService.findAllProjects()) {
            System.out.println("---> " + "[" + proyecto.getIdProjecte() + "]" + " " + proyecto.getDescripcio() + " | Estado: " + proyecto.getEstat());
        }
        System.out.println("|--------------------------------------|");
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
                System.out.print("Eliminando todos los proyectos... ");
                if (projecteService.findAllProjects().isEmpty()) {
                    System.out.println("No hay proyectos para eliminar.");
                } else {
                    System.out.println("Eliminando...");
                    projecteService.deleteTable();
                    System.out.println("Todos los proyectos han sido eliminados.");
                }
                break;
            case 2:
                System.out.print("Introduce el ID del proyecto a eliminar: ");
                System.out.println("Proyectos disponibles:");
                listProjecteComplete(projecteService);
                System.out.print("ID del proyecto a eliminar: ");
                int idProyecto = tcl.nextInt();
                tcl.nextLine();

                Projecteas01 proyecto = projecteService.findProjectById(idProyecto);
                if (proyecto != null) {
                    projecteService.deleteProject(proyecto);
                    System.out.println("Proyecto con ID " + idProyecto + " ha sido eliminado.");
                } else {
                    System.out.println("No se encontró un proyecto con ese ID.");
                }
                break;
            default:
                System.out.println("Opción no válida.");
                break;
        }
    }
}
