/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Test;

import Entity.Facturaas01;
import Entity.Operariresponsableas01;
import Entity.Projecteas01;
import Entity.Tascaas01;
import Service.Projecteas01Service;
import Service.Tascaas01Service;
import static Test.MainApp.log;
import static Test.MainApp.tcl;
import java.util.List;
import java.util.Collection;

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
                        projecteService.findAllProjects().forEach(System.out::println);
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
                for (Tascaas01 tasca : tascaService.findAllTasques()) {
                    System.out.println("---> " + "[" + tasca.getIdTasca() + "]" + " " + tasca.getDescripcio() + " | Estado: " + tasca.getEstat());
                }
                break;
            case 2:
                log.info("=== MOSTRANDO TASQUES ===");
                log.info("*=== [MODO COMPLETO] ===*");
                listTasquesComplete(tascaService);
                break;
            default:
                System.out.println("Opción no válida.");
                break;
        }
    }

    public static void listTasquesComplete(Tascaas01Service tascaService) {

        List<Tascaas01> tasques = tascaService.findAllWithDetails();
        tasques.forEach(tasca -> {
            System.out.println("- > TAREA Nº [" + tasca.getIdTasca() + "]" + " " + tasca.getDescripcio() + " | Estado: " + tasca.getEstat());

            System.out.println("\t" + "Tarea nº " + tasca.getIdTasca() + " pertenece al proyecto: ");
            Projecteas01 proyecto = tasca.getIdProjecte();
            if (proyecto != null) {
                System.out.println("\t -" + "[" + proyecto.getIdProjecte() + "]" + proyecto.getDescripcio() + " | Estado: " + proyecto.getEstat());
            } else {
                System.out.println("\t" + "Proyecto asociado no encontrado.");
            }

            System.out.println("\t" + "Tarea nº " + tasca.getIdTasca() + " tiene las siguientes facturas: ");
            Collection<Facturaas01> facturas = tasca.getFacturaas01Collection();
            if (facturas != null && !facturas.isEmpty()) {
                facturas.forEach(factura -> {
                    System.out.println("\t -" + "[" + factura.getIdFactura() + "]" + " " + " Fecha : " + factura.getData() + " | " + factura.getObservacions() + " | Importe: " + factura.getImportTotal());
                });
            } else {
                System.out.println("\t" + "No tiene facturas asociadas.");
            }

            System.out.println("\t" + "Tarea nº " + tasca.getIdTasca() + " tiene el siguiente operario responsable: ");
            Operariresponsableas01 operario = tasca.getOperariResponsable();
            if (operario != null) {
                System.out.println("\t -" + "[" + operario.getIdOperariTasca() + "]" + operario.getNom() + " " + operario.getCognom() + " | " + operario.getNifOperari() + "| Actividad : " + operario.getObservacions());
            } else {
                System.out.println("\t" + "Operario responsable no encontrado.");
            }
        });

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
                System.out.print("Eliminando todas las tareas... ");
                if (tascaService.findAllTasques().isEmpty()) {
                    System.out.println("No hay tareas para eliminar.");
                } else {
                    tascaService.deleteTable();
                    System.out.println("Todas las tareas han sido eliminadas.");
                }
                break;
            case 2:
                System.out.println("Introduce el ID de la tasca a eliminar:");
                System.out.println("Tareas disponibles:");
                for (Tascaas01 tasca : tascaService.findAllTasques()) {
                    System.out.println("[" + tasca.getIdTasca() + "] " + tasca.getDescripcio() + " - Estado: " + tasca.getEstat());
                }
                System.out.print("ID de la tasca a eliminar: ");
                int idTasca = tcl.nextInt();
                tcl.nextLine();

                Tascaas01 tasca = tascaService.findTascaById(idTasca);
                if (tasca != null) {
                    tascaService.deleteTasca(tasca);
                    System.out.println("Tasca con ID " + idTasca + " ha sido eliminada.");
                } else {
                    System.out.println("No se encontró una tasca con ese ID.");
                }
                break;
            default:
                System.out.println("Opción no válida.");
                break;
        }
    }

}
