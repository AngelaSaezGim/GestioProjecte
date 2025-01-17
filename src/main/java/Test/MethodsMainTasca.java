/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Test;

import Entity.Tascaas01;
import Service.Projecteas01Service;
import Service.Tascaas01Service;
import static Test.MainApp.log;
import static Test.MainApp.tcl;

/**
 *
 * @author angsaegim
 */
public class MethodsMainTasca {

    //*****************************************************************//
    //********************** CREATE ************************************//
    //*****************************************************************//
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

            System.out.println("Introduce los datos de la tasca:");

            System.out.print("Descripción de la tasca: ");
            String descripcio = tcl.nextLine();

            String estat = "";
            while (true) {
                System.out.println("Selecciona el estado de la tasca:");
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

            Integer idProjecte = null;
            while (idProjecte == null) {
                System.out.print("ID del proyecto asociado (debe ser válido y existir): ");
                System.out.println("¿Quieres ver la lista de proyectos disponibles? (si/no): ");
                String verLista;
                boolean validInput = false;

                while (!validInput) {
                    verLista = tcl.nextLine().trim().toLowerCase();

                    if (verLista.equals("si")) {
                        projecteService.getAllProjects().forEach(System.out::println);
                        validInput = true; 
                    } else if (verLista.equals("no")) {
                        System.out.println("No se mostrará la lista.");
                        validInput = true;
                    } else {
                        System.out.println("Respuesta no válida. Por favor, responde 'si' o 'no': \n ");
                        System.out.print("ID del proyecto asociado (debe ser válido y existir): ");
                    }
                }
                String inputIdProjecte = tcl.nextLine();
                try {
                    idProjecte = Integer.parseInt(inputIdProjecte);
                    if (projecteService.findProjectById(idProjecte) == null) {
                        System.out.println("El proyecto con ID " + idProjecte + " no existe. Inténtalo nuevamente.");
                        idProjecte = null;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("ID de proyecto inválido. Inténtalo nuevamente.");
                }
            }

            Tascaas01 newTasca = new Tascaas01();
            newTasca.setDescripcio(descripcio);
            newTasca.setEstat(estat);
            newTasca.setIdProjecte(projecteService.findProjectById(idProjecte));
            //Falta idOperari

            try {
                tascaService.createTasca(newTasca);
                System.out.println("Tasca agregada exitosamente.");
            } catch (Exception e) {
                System.out.println("Error al agregar la tasca: " + e.getMessage());
            }

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

    //*****************************************************************//
    //********************** FIND ************************************//
    //*****************************************************************//
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

    //*****************************************************************//
    //********************** DELETE ************************************//
    //*****************************************************************//
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

}
