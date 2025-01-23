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

            System.out.println("Introdueix les dades de l'operari responsable:");

            System.out.print("Nom de l'operari: ");
            String nom = tcl.nextLine();

           System.out.print("Cognom de l'operari: ");
            String cognom = tcl.nextLine();

            // DEBE SER VALIDO
            String nifOperari = "";
            while (nifOperari.isEmpty() || !esNifValido(nifOperari)) {
                System.out.print("NIF de l'operari (ha de ser únic i no pot estar buit):  ");
                nifOperari = tcl.nextLine().trim();
                if (!esNifValido(nifOperari)) {
                    System.out.println("El NIF introduït no és vàlid. Torna-ho a intentar.");
                }
            }

            System.out.print("Observacions: ");
            String observacions = tcl.nextLine();

            Operariresponsableas01 newOperari = new Operariresponsableas01();
            newOperari.setNom(nom);
            newOperari.setCognom(cognom);
            newOperari.setNifOperari(nifOperari);
            newOperari.setObservacions(observacions);

            try {
                operariResponsableService.createOperari(newOperari);
                 System.out.println("Operari responsable afegit amb èxit.");
            } catch (Exception e) {
                 System.out.println("Error en afegir l'operari responsable: " + e.getMessage());
            }

            boolean validInput = false;
            while (!validInput) {
                System.out.print("Vols afegir un altre operari? (si/no): ");
                continueAdding = tcl.nextLine().trim().toLowerCase();

                if (continueAdding.equals("si") || continueAdding.equals("no")) {
                    validInput = true;
                } else {
                    System.out.println("Per favor, introdueix 'si' per continuar o 'no' per eixir.");
                }
            }
        }

    }
    
    // METODO SOBRECARGADO - CREA OPERARI (con tarea) Y LO DEVUELVE
     public static Integer agregarOperariBasic(Operariresponsableas01Service operariResponsableService, Integer idTasca) {
         
            System.out.println("[OPERARI ASSOCIAT A TASCA]");

            System.out.println("Introdueix les dades de l'operari responsable:");

             System.out.print("Nom de l'operari: ");
            String nom = tcl.nextLine();

            System.out.print("Cognom de l'operari: ");
            String cognom = tcl.nextLine();

            // DEBE SER VALIDO
            String nifOperari = "";
            while (nifOperari.isEmpty() || !esNifValido(nifOperari)) {
                System.out.print("NIF de l'operari (ha de ser únic i no pot estar buit):  ");
                nifOperari = tcl.nextLine().trim();
                if (!esNifValido(nifOperari)) {
                    System.out.println("El NIF introduït no és vàlid. Torna-ho a intentar.");
                }
            }

            System.out.print("Observacions: ");
            String observacions = tcl.nextLine();

            Operariresponsableas01 newOperari = new Operariresponsableas01();
            newOperari.setNom(nom);
            newOperari.setCognom(cognom);
            newOperari.setNifOperari(nifOperari);
            newOperari.setObservacions(observacions);

            try {
                operariResponsableService.createOperari(newOperari);
                System.out.println("Operari responsable afegit amb èxit.");
                return newOperari.getIdOperariTasca();
            } catch (Exception e) {
                System.out.println("Error en afegir l'operari responsable: " + e.getMessage());
            }
            return null;  //Si falla pasamos null
        }

    //*****************************************************************//
    //********************** FIND ************************************//
    //*****************************************************************//
    //*************** LISTAR OPERARIOS RESPONSABLES  *****************************//
    protected static void listOperariosResponsables(Operariresponsableas01Service operariResponsableService) {

        
    System.out.println("Com vols veure els operaris responsables?");
    System.out.println("1. Bàsic (Només dades de l'operari responsable)");
    System.out.println("2. Complet (Dades de l'operari responsable + taules relacionades)");

        int opcion = tcl.nextInt();
        tcl.nextLine();

        switch (opcion) {
            case 1:
                System.out.println("=== MOSTRANT OPERARIS RESPONSABLES ===");
                System.out.println("*=== [MODE BÀSIC] ===*");
                listOperariBasic(operariResponsableService);
                break;
            case 2:
                System.out.println("=== MOSTRANT OPERARIS RESPONSABLES ===");
                System.out.println("*=== [MODE COMPLET] ===*");
                listOperariComplete(operariResponsableService);
                break;
            default:
                System.out.println("Opció no vàlida.");
                break;
        }
    }

    public static void listOperariComplete(Operariresponsableas01Service operariResponsableService) {

        List<Operariresponsableas01> operarios = operariResponsableService.findAllWithDetails();
        operarios.forEach(operari -> {
            System.out.println("- > OPERARI RESPONSABLE [" + operari.getIdOperariTasca() + "] " + operari.getNom() + " "
                    + operari.getCognom() + " | NIF: " + operari.getNifOperari()
                    + " | Observacions: " + operari.getObservacions());

            System.out.println("\tTareas  assignades a l'operari " + operari.getNom() + ":");
            Collection<Tascaas01> tasques = operari.getTasques();
            if (tasques != null && !tasques.isEmpty()) {
                tasques.forEach(tasca -> {
                    System.out.println("\t - [" + tasca.getIdTasca() + "] " + tasca.getDescripcio()
                            + " | Estat: " + tasca.getEstat());
                });
            } else {
                System.out.println("\tNo hi ha tasques associades a aquest operari.");
            }
        });
    }

    public static void listOperariBasic(Operariresponsableas01Service operariResponsableService) {
        System.out.println("|--------------------------------------|");
        for (Operariresponsableas01 operari : operariResponsableService.findAllOperaris()) {
            System.out.println("---> [" + operari.getIdOperariTasca() + "] " + operari.getNom() + " " + operari.getCognom()
                    + " | NIF: " + operari.getNifOperari()
                    + " | Observacions: " + operari.getObservacions());
        }
        System.out.println("|--------------------------------------|");

    }

    //*****************************************************************//
    //********************** DELETE ************************************//
    //*****************************************************************//
    //*************** DELETE OPERARI  *****************************//
    protected static void eliminarOperariosResponsables(Operariresponsableas01Service operariService) {
        
        System.out.println("**** Avís ****");
        System.out.println("No es pot eliminar cap operari responsable que estiga treballant en una tasca en estat 'tasca en procés'.");
        MainApp.esperarIntro();
    
        System.out.println("Com vols eliminar els operaris responsables?");
        System.out.println("1. Eliminar tots els operaris responsables");
        System.out.println("2. Eliminar un operari responsable per ID");

        int opcion = tcl.nextInt();
        tcl.nextLine();

        switch (opcion) {
            case 1:
                System.out.print("Eliminant tots els operaris responsables... \n");
                if (operariService.findAllOperaris().isEmpty()) {
                    System.out.println("No hi ha operaris responsables per eliminar.");
                } else {
                    //Se itera cada operario, se revisa, se elimina
                    List<Operariresponsableas01> operariosAEliminar = new ArrayList<>();
                    for (Operariresponsableas01 operari : operariService.findAllOperaris()) {
                        String info = operariService.deleteOperariVerification(operari);
                        if (info == null) {
                            operariosAEliminar.add(operari);
                        } else {
                            System.out.println("No es pot eliminar l'operari " + operari.getIdOperariTasca() + ": " + info);
                        }
                    }

                    if (!operariosAEliminar.isEmpty()) {
                        // Mostrar el listado de operarios a eliminar
                         System.out.println("Els següents operaris responsables seran eliminats:");
                        for (Operariresponsableas01 operari : operariosAEliminar) {
                            System.out.println(operari.getNom() + " (ID: " + operari.getIdOperariTasca() + ")");
                        }

                        System.out.print("Estàs segur que vols eliminar aquests operaris responsables? (S/N): ");
                        String confirmacion = tcl.nextLine();
                        if (confirmacion.equalsIgnoreCase("S")) {
                            // Eliminar los operarios
                            for (Operariresponsableas01 operari : operariosAEliminar) {
                                try {
                                    operariService.deleteOperari(operari);
                                    System.out.println(operari.getNom() + " ha sigut eliminat/da correctament.");
                                } catch (Exception e) {
                                     System.out.println("Error en eliminar l'operari " + operari.getIdOperariTasca() + ": " + e.getMessage());
                                }
                            }
                        } else {
                            System.out.println("L'eliminació ha sigut cancel·lada.");
                        }
                    } else {
                        System.out.println("No hi ha operaris responsables per eliminar.");
                    }
                }
                break;
            case 2:
                 System.out.print("Introdueix l'ID de l'operari responsable a eliminar: ");
                 System.out.println("Operaris responsables disponibles:");
                listOperariComplete(operariService);

                System.out.print("ID de l'operari responsable a eliminar: ");
                int idOperario = tcl.nextInt();
                tcl.nextLine();

                Operariresponsableas01 operario = operariService.findOperariById(idOperario);
                if (operario != null) {
                    String info = operariService.deleteOperariVerification(operario);
                    if (info == null) {
                        try {
                            operariService.deleteOperari(operario);
                            System.out.println("Operari responsable amb ID " + idOperario + " ha sigut eliminat.");
                        } catch (Exception e) {
                            System.out.println("Error en eliminar l'operari amb ID " + idOperario + ": " + e.getMessage());
                        }
                    } else {
                         System.out.println("No es pot eliminar l'operari " + idOperario + ": " + info);
                    }
                } else {
                    System.out.println("No s'ha trobat cap operari responsable amb eixe ID.");
                }
                break;
            default:
                 System.out.println("Opció no vàlida.");
                break;
        }
    }

}
