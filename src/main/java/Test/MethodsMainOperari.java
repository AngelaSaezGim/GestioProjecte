/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Test;

import Entity.Operariresponsableas01;
import Entity.Tascaas01;
import Service.Operariresponsableas01Service;
import Service.Tascaas01Service;
import static Test.MainApp.log;
import static Test.MainApp.tcl;
import static Test.MehtodsMainEntities.esNifValido;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author angsaegim
 */
public class MethodsMainOperari {

    //*****************************************************************//
    //********************** CREATE ************************************//
    //*****************************************************************//
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

            List<Tascaas01> tasques = new ArrayList<>();
            boolean addingTasks = true;

            while (addingTasks) {
                System.out.print("ID de la Tarea (No puede estar vacío y debe existir): ");
                /// DUDAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAa
                System.out.print("Quieres ver la lista de tareas existentes sin operarios asignados ? ");
                System.out.println("Tarea a agregar al proyecto nuevo; ");
                String inputIdTasca = tcl.nextLine();

                try {
                    Integer idTasca = Integer.parseInt(inputIdTasca);
                    Tascaas01 tasca = tascaService.findTascaById(idTasca);
                    if (tasca == null) {
                        System.out.println("La tarea con ID " + idTasca + " no existe. Inténtalo nuevamente.");
                    } else {
                        tasques.add(tasca);
                        System.out.println("Tarea con ID " + idTasca + " asociada exitosamente.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("ID de tarea inválido. Inténtalo nuevamente.");
                }

                System.out.print("¿Deseas agregar otra tarea para este operario? (si/no): ");
                String addMoreTasks = tcl.nextLine().trim().toLowerCase();
                if (!addMoreTasks.equals("si")) {
                    addingTasks = false;
                }
            }

            System.out.print("Observaciones: ");
            String observacions = tcl.nextLine();

            Operariresponsableas01 newOperari = new Operariresponsableas01();
            newOperari.setNom(nom);
            newOperari.setCognom(cognom);
            newOperari.setNifOperari(nifOperari);
            newOperari.setTasques(tasques);
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

            List<Tascaas01> tasques = new ArrayList<>();
            boolean addingTasks = true;

            while (addingTasks) {
                System.out.print("ID de la Tarea (No puede estar vacío y debe existir): ");
                String inputIdTasca = tcl.nextLine();

                try {
                    Integer idTasca = Integer.parseInt(inputIdTasca);
                    Tascaas01 tasca = tascaService.findTascaById(idTasca);
                    if (tasca == null) {
                        System.out.println("La tarea con ID " + idTasca + " no existe. Inténtalo nuevamente.");
                    } else {
                        tasques.add(tasca);
                        System.out.println("Tarea con ID " + idTasca + " asociada exitosamente.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("ID de tarea inválido. Inténtalo nuevamente.");
                }

                System.out.print("¿Deseas agregar otra tarea para este operario? (si/no): ");
                String addMoreTasks = tcl.nextLine().trim().toLowerCase();
                if (!addMoreTasks.equals("si")) {
                    addingTasks = false;
                }
            }

            System.out.print("Observaciones: ");
            String observacions = tcl.nextLine();

            Operariresponsableas01 newOperari = new Operariresponsableas01();
            newOperari.setNom(nom);
            newOperari.setCognom(cognom);
            newOperari.setNifOperari(nifOperari);
            newOperari.setTasques(tasques);
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

    //*****************************************************************//
    //********************** FIND ************************************//
    //*****************************************************************//
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

    //*****************************************************************//
    //********************** DELETE ************************************//
    //*****************************************************************//
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

}
