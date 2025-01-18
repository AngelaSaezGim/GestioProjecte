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
import java.util.Collection;

/**
 *
 * @author angsaegim
 */
public class MethodsMainOperari {

    //*****************************************************************//
    //********************** CREATE ************************************//
    //*****************************************************************//
    //*************** AGREGAR OPERARI  *****************************//
    // ES SOLO BASIC 
    public static void agregarOperariBasic(Operariresponsableas01Service operariResponsableService) {

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

            System.out.print("Observaciones: ");
            String observacions = tcl.nextLine();

            Operariresponsableas01 newOperari = new Operariresponsableas01();
            newOperari.setNom(nom);
            newOperari.setCognom(cognom);
            newOperari.setNifOperari(nifOperari);
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
                listOperariBasic(operariResponsableService);
                break;
            case 2:
                log.info("=== MOSTRANDO OPERARIOS RESPONSABLES ===");
                log.info("*=== [MODO COMPLETO] ===*");
                listOperariComplete(operariResponsableService);
                break;
            default:
                System.out.println("Opción no válida.");
                break;
        }
    }

    public static void listOperariComplete(Operariresponsableas01Service operariResponsableService) {

        List<Operariresponsableas01> operarios = operariResponsableService.findAllWithDetails();
        operarios.forEach(operari -> {
            System.out.println("- > OPERARIO RESPONSABLE [" + operari.getIdOperariTasca() + "] " + operari.getNom() + " "
                    + operari.getCognom() + " | NIF: " + operari.getNifOperari()
                    + " | Observaciones: " + operari.getObservacions());

            System.out.println("\tTareas asignadas al operario " + operari.getNom() + ":");
            Collection<Tascaas01> tasques = operari.getTasques();
            if (tasques != null && !tasques.isEmpty()) {
                tasques.forEach(tasca -> {
                    System.out.println("\t - [" + tasca.getIdTasca() + "] " + tasca.getDescripcio()
                            + " | Estado: " + tasca.getEstat());
                });
            } else {
                System.out.println("\tNo hay tareas asociadas a este operario.");
            }
        });
    }

    public static void listOperariBasic(Operariresponsableas01Service operariResponsableService) {
        System.out.println("|--------------------------------------|");
        for (Operariresponsableas01 operari : operariResponsableService.findAllOperaris()) {
            System.out.println("---> [" + operari.getIdOperariTasca() + "] " + operari.getNom() + " " + operari.getCognom()
                    + " | NIF: " + operari.getNifOperari()
                    + " | Observaciones: " + operari.getObservacions());
        }
        System.out.println("|--------------------------------------|");

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
                System.out.print("Eliminando todos los operarios responsables... ");
                if (operariResponsableService.findAllOperaris().isEmpty()) {
                    System.out.println("No hay operarios responsables para eliminar.");
                } else {
                    System.out.println("Eliminando...");
                    operariResponsableService.deleteTable();
                    System.out.println("Todos los operarios responsables han sido eliminados.");
                }
                break;
            case 2:
                System.out.print("Introduce el ID del operario responsable a eliminar: ");
                System.out.println("Operarios responsables disponibles:");
                listOperariComplete(operariResponsableService);
                System.out.print("ID del operario responsable a eliminar: ");
                int idOperario = tcl.nextInt();
                tcl.nextLine();

                Operariresponsableas01 operario = operariResponsableService.findOperariById(idOperario);
                if (operario != null) {
                    operariResponsableService.deleteOperari(operario);
                    System.out.println("Operario responsable con ID " + idOperario + " ha sido eliminado.");
                } else {
                    System.out.println("No se encontró un operario responsable con ese ID.");
                }
                break;
            default:
                System.out.println("Opción no válida.");
                break;
        }
    }

}
