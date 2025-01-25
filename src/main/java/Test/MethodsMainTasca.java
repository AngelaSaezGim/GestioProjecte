/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Test;

import Entity.Facturaas01;
import Entity.Operariresponsableas01;
import Entity.Projecteas01;
import Entity.Tascaas01;
import Service.Clientas01Service;
import Service.Facturaas01Service;
import Service.Operariresponsableas01Service;
import Service.Projecteas01Service;
import Service.Tascaas01Service;
import static Test.MainApp.log;
import static Test.MainApp.tcl;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author angsaegim
 */
public class MethodsMainTasca {

    //*****************************************************************//
    //********************** CREATE ************************************//
    //*****************************************************************//
    //*************** AGREGAR TASCA  *****************************//
    public static void agregarTascaMenu(Tascaas01Service tascaService, Projecteas01Service projecteService, Facturaas01Service facturaService, Operariresponsableas01Service operariService,
            Clientas01Service clientService) {

        System.out.println("Com vols afegir les tasques?");
        System.out.println("1. Bàsic (Només creem la tasca)");
        System.out.println("2. Complet (Creem la tasca i, si no existeix algun dada associada, la creem)");

        int opcion = tcl.nextInt();
        tcl.nextLine();

        switch (opcion) {
            case 1:
                System.out.println("=== CREACIÓ TASQUES ===");
                System.out.println("*=== [MODE BÀSIC] ===*");
                agregarTascaBasic(tascaService, projecteService, facturaService, operariService);
                break;
            case 2:
                System.out.println("=== CREACIÓ TASQUES ===");
                System.out.println("*=== [MODE COMPLET] ===*");
                agregarTascaComplete(tascaService, projecteService, facturaService, operariService,
                        clientService);
                break;

            default:
                System.out.println("Opció no vàlida.");
                break;
        }
    }

    public static void agregarTascaBasic(Tascaas01Service tascaService, Projecteas01Service projecteService, Facturaas01Service facturaService, Operariresponsableas01Service operariService) {

        String continueAdding = "si";

        while (continueAdding.equalsIgnoreCase("si")) {

            System.out.println("Introdueix les dades de la tasca:");

            System.out.print("Descripció de la tasca: ");
            String descripcio = tcl.nextLine();

            String estat = "";
            while (true) {
                System.out.println("Selecciona l'estat de la tasca:");
                System.out.println("1. No iniciat");
                System.out.println("2. En procés");
                System.out.println("3. Finalitzat");
                System.out.print("Introdueix el número corresponent a l'estat: ");
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
                        System.out.println("Opció no vàlida. Torna-ho a intentar.");
                        continue;
                }
                break;
            }

            Integer idProjecte = null;
            while (idProjecte == null) {
                System.out.print("ID del projecte associat (ha de ser vàlid i existir): ");
                System.out.println("Llista de projectes disponibles; ");
                MethodsMainProjecte.listProjecteBasic(projecteService);
                System.out.println("Tasques associades al projecte...");
                String inputIdProjecte = tcl.nextLine();
                try {
                    idProjecte = Integer.parseInt(inputIdProjecte);
                    Projecteas01 project = projecteService.findProjectById(idProjecte);
                    if (project == null) {
                        System.out.println("El projecte amb ID " + idProjecte + " no existeix. Torna-ho a intentar.");
                        idProjecte = null;
                        MainApp.esperarIntro();
                        // REVISAR SI ASIGNAMOS UNA TAREA NO FINALIZADA A PROYECTO FINALIZADO
                    } else if ("Finalitzat".equals(project.getEstat()) && !"Finalitzat".equals(estat)) {
                        System.out.println("No es pot afegir una tasca no finalitzada a un projecte finalitzat.");
                        idProjecte = null;
                        MainApp.esperarIntro();
                    }
                } catch (NumberFormatException e) {
                    System.out.println("ID de projecte invàlid. Torna-ho a intentar.");
                }
            }

            Facturaas01 factura = null;
            while (factura == null) {
                System.out.print("ID de la factura associada: ");
                System.out.println("Llista de factures disponibles:");
                MethodsMainFactura.listFacturaBasic(facturaService);
                System.out.println("Projecte associat a la factura...");
                String inputFacturaId = tcl.nextLine();
                try {
                    int facturaId = Integer.parseInt(inputFacturaId.trim());
                    factura = facturaService.findFacturaById(facturaId);
                    if (factura == null) {
                        System.out.println("La factura amb ID " + facturaId + " no existeix. Torna-ho a intentar.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("L'ID de factura introduït no és vàlid. Torna-ho a intentar.");
                }
            }

            Integer idOperari = null;
            while (idOperari == null) {
                System.out.print("ID de l'operari associat (ha de ser vàlid i existir): ");
                System.out.println("Llista d'operaris disponibles; ");
                MethodsMainOperari.listOperariBasic(operariService);
                System.out.println("Tasques associades a l'operari...");
                String inputIdOperari = tcl.nextLine();
                try {
                    idOperari = Integer.parseInt(inputIdOperari);
                    if (operariService.findOperariById(idOperari) == null) {
                        System.out.println("L'operari amb ID " + idOperari + " no existeix. Torna-ho a intentar.");
                        idOperari = null;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("ID d'operari invàlid. Torna-ho a intentar.");
                }
            }

            Tascaas01 newTasca = new Tascaas01();
            newTasca.setDescripcio(descripcio);
            newTasca.setEstat(estat);
            newTasca.setIdProjecte(projecteService.findProjectById(idProjecte));
            newTasca.setFactura(factura);
            newTasca.setOperariResponsable(operariService.findOperariById(idOperari));

            try {
                tascaService.createTasca(newTasca);
                System.out.println("Tasca afegida amb èxit.");
            } catch (Exception e) {
                System.out.println("Error en afegir la tasca: " + e.getMessage());
            }

            boolean validInput = false;
            while (!validInput) {
                System.out.print("Vols afegir una altra tasca? (si/no): ");
                continueAdding = tcl.nextLine().trim().toLowerCase();

                if (continueAdding.equals("si") || continueAdding.equals("no")) {
                    validInput = true;
                } else {
                    System.out.println("Si us plau, introdueix 'si' per continuar o 'no' per sortir.");
                }
            }
        }

    }

    public static void agregarTascaComplete(Tascaas01Service tascaService, Projecteas01Service projecteService, Facturaas01Service facturaService, Operariresponsableas01Service operariService,
            Clientas01Service clientService) {
        String continueAdding = "si";

        while (continueAdding.equalsIgnoreCase("si")) {

            System.out.println("Introdueix les dades de la tasca:");

            System.out.print("Descripció de la tasca: ");
            String descripcio = tcl.nextLine();

            String estat = "";
            while (true) {
                System.out.println("Selecciona l'estat de la tasca:");
                System.out.println("1. No iniciat");
                System.out.println("2. En procés");
                System.out.println("3. Finalitzat");
                System.out.print("Introdueix el número corresponent a l'estat: ");
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
                        System.out.println("Opció no vàlida. Torna-ho a intentar.");
                        continue;
                }
                break;
            }

            Tascaas01 newTasca = new Tascaas01();
            newTasca.setDescripcio(descripcio);
            newTasca.setEstat(estat);

            tascaService.createTasca(newTasca);
            // CON ESTO PODREMOS ACCEDER A SU ID AUTOGENERADO
            Integer idTasca = newTasca.getIdTasca();

            // Asociar proyecto a tarea
            //COGEMOS ESTAT PARA VALIDAR
            Integer idProjecte = asociarProjecteTasca(projecteService, clientService, idTasca, facturaService,
                    operariService, tascaService, estat);

            //Asociar factura a tarea
            Integer idFactura = asociarFacturaTasca(facturaService, tascaService, clientService, projecteService, operariService, idTasca);

            //Asociar operario a tarea
            Integer idOperari = asociarOperariTasca(operariService, idTasca);

            newTasca.setIdProjecte(projecteService.findProjectById(idProjecte));
            newTasca.setFactura(facturaService.findFacturaById(idFactura));
            newTasca.setOperariResponsable(operariService.findOperariById(idOperari));

            try {
                tascaService.createTasca(newTasca);
                System.out.println("Tasca afegida amb èxit.");
            } catch (Exception e) {
                System.out.println("Error en afegir la tasca: " + e.getMessage());
            }

            // Preguntar si desea agregar otra tasca
            boolean validInput = false;
            while (!validInput) {
                System.out.print("¿Vols afegir una altra tasca en modo completo ? (si/no): ");
                continueAdding = tcl.nextLine().trim().toLowerCase();

                if (continueAdding.equals("si") || continueAdding.equals("no")) {
                    validInput = true;
                } else {
                    System.out.println("Si us plau, introdueix 'si' per continuar o 'no' per sortir.");
                }
            }
        }
    }

    private static Integer asociarProjecteTasca(Projecteas01Service projecteService, Clientas01Service clientService, Integer idTasca, Facturaas01Service facturaService,
            Operariresponsableas01Service operariService, Tascaas01Service tascaService, String estat) {

        System.out.println("[PROJECTE ASSOCIAT A TASCA]");

        Integer idProjecte = null;
        while (idProjecte == null) {
            System.out.println("Opcions:");
            System.out.println("1. Buscar projecte existent");
            System.out.println("2. Crear un nou projecte associat a eixa tasca");
            System.out.print("Tria una opció per al projecte (1/2): ");
            String option = tcl.nextLine();

            if (option.equals("1")) {
                // Buscar proyecto
                System.out.print("ID del projecte associat (ha de ser vàlid i existir): ");
                System.out.println("Llista de projectes disponibles: ");
                MethodsMainProjecte.listProjecteBasic(projecteService);
                System.out.println("Tasca associada al projecte...");
                String inputIdProjecte = tcl.nextLine();
                try {
                    idProjecte = Integer.parseInt(inputIdProjecte);
                    Projecteas01 project = projecteService.findProjectById(idProjecte);
                    if (project == null) {
                        System.out.println("El projecte amb ID " + idProjecte + " no existeix.");
                        idProjecte = null;
                    } else if ("Finalitzat".equals(project.getEstat()) && !"Finalitzat".equals(estat)) {
                        System.out.println("No es pot agregar una tasca no finalitzada a un projecte finalitzat.");
                        idProjecte = null; // Reinicia el bucle si el proyecto y la tarea no son compatibles
                        MainApp.esperarIntro();
                    }
                } catch (NumberFormatException e) {
                    System.out.println("ID de projecte invàlid. Intenta-ho de nou.");
                }
            } else if (option.equals("2")) {
                System.out.println("Creant un nou projecte...");
                idProjecte = MethodsMainProjecte.agregarProjecteComplete(projecteService, clientService, facturaService, operariService, tascaService, idTasca, estat);
            } else {
                System.out.println("Opció no vàlida. Intenta-ho de nou.");
            }
        }
        return idProjecte;
    }

    private static Integer asociarFacturaTasca(Facturaas01Service facturaService, Tascaas01Service tascaService, Clientas01Service clientService,
            Projecteas01Service projecteService, Operariresponsableas01Service operariService, Integer idTasca) {

        System.out.println("[FACTURA ASOCIADA A TAREA]");

        Integer idFactura = null;

        while (idFactura == null) {
            System.out.println("Opcions:");
            System.out.println("1. Buscar una factura existent");
            System.out.println("2. Crear una nova factura associada a eixa tasca");
            System.out.print("Tria una opció per a la factura (1/2): ");
            String option = tcl.nextLine();

            if (option.equals("1")) {
                System.out.println("Llista de factures disponibles: ");
                MethodsMainFactura.listFacturaBasic(facturaService);
                System.out.print("ID de la factura associada: ");
                String inputFacturaId = tcl.nextLine();
                try {
                    idFactura = Integer.parseInt(inputFacturaId);
                    if (facturaService.findFacturaById(idFactura) == null) {
                        System.out.println("La factura amb ID " + idFactura + " no existeix.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("ID de factura invàlid. Intenta-ho de nou.");
                }
            } else if (option.equals("2")) {
                System.out.println("Creant una nova factura...");
                idFactura = MethodsMainFactura.agregarFacturaComplete(facturaService, tascaService, clientService, projecteService, operariService, idTasca);
                if (idFactura != null) {
                    System.out.println("Factura creada amb ID: " + idFactura);
                } else {
                    System.out.println("No s'ha pogut crear la factura.");
                }
            } else {
                System.out.println("Opció no vàlida. Intenta-ho de nou.");
            }
        }
        return idFactura;
    }

    private static Integer asociarOperariTasca(Operariresponsableas01Service operariService, Integer idTasca) {

        System.out.println("[OPERARI ASSOCIAT A TASCA]");

        Integer idOperari = null;

        while (idOperari == null) {
            System.out.println("Opcions:");
            System.out.println("1. Buscar un operari existent");
            System.out.println("2. Crear un nou operari associat a eixa tasca");
            System.out.print("Tria una opció per a l'operari (1/2): ");
            String option = tcl.nextLine();

            if (option.equals("1")) {
                System.out.print("ID de l'operari associat (ha de ser vàlid i existir): ");
                System.out.println("Llista d'operaris disponibles: ");
                MethodsMainOperari.listOperariBasic(operariService);
                String inputIdOperari = tcl.nextLine();
                try {
                    idOperari = Integer.parseInt(inputIdOperari);
                    if (operariService.findOperariById(idOperari) == null) {
                        System.out.println("L'operari amb ID " + idOperari + " no existeix.");
                        idOperari = null;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("ID d'operari invàlid. Intenta-ho de nou.");
                }
            } else if (option.equals("2")) {
                // Crear nuevo operario
                System.out.println("Creant un nou operari...");
                idOperari = MethodsMainOperari.agregarOperariBasic(operariService, idTasca);
                if (idOperari != null) {
                    System.out.println("Operari creat amb ID: " + idOperari);
                } else {
                    System.out.println("No s'ha pogut crear l'operari.");
                }
            } else {
                System.out.println("Opció no vàlida. Intenta-ho de nou.");
            }
        }
        return idOperari;
    }

    // METODO SOBRECARGADO CON IDFACTURA
    // CREA Y DEVUELVE EL ID DE LA TAREA
    public static Integer agregarTascaComplete(Tascaas01Service tascaService, Projecteas01Service projecteService, Clientas01Service clientService,
            Facturaas01Service facturaService, Operariresponsableas01Service operariService, Integer idFactura) {

        System.out.println("TASCA ASSOCIADA A FACTURA");

        System.out.println("Introduïx les dades de la tasca:");

        System.out.print("Descripció de la tasca: ");
        String descripcio = tcl.nextLine();

        String estat = "";
        while (true) {
            System.out.println("Selecciona l'estat de la tasca:");
            System.out.println("1. No iniciat");
            System.out.println("2. En procés");
            System.out.println("3. Finalitzat");
            System.out.print("Introduïx el número corresponent a l'estat: ");
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
                    System.out.println("Opció no vàlida. Intenta novament.");
                    continue;
            }
            break;
        }

        Tascaas01 newTasca = new Tascaas01();
        newTasca.setDescripcio(descripcio);
        newTasca.setEstat(estat);

        tascaService.createTasca(newTasca);
        // CON ESTO PODREMOS ACCEDER A SU ID AUTOGENERADO
        Integer idTasca = newTasca.getIdTasca();

        Integer idProjecte = asociarProjecteTasca(projecteService, clientService, idTasca, facturaService, operariService, tascaService, estat);

        Integer idOperari = asociarOperariTasca(operariService, idTasca);

        newTasca.setIdProjecte(projecteService.findProjectById(idProjecte));
        newTasca.setFactura(facturaService.findFacturaById(idFactura)); // Asignar el objeto Facturaas01 a la tasca
        newTasca.setOperariResponsable(operariService.findOperariById(idOperari));

        try {
            tascaService.createTasca(newTasca);
            System.out.println("Tasca agregada amb èxit.");
            return newTasca.getIdTasca();
        } catch (Exception e) {
            System.out.println("Error en agregar la tasca: " + e.getMessage());
        }

        return null; // Devuelve null si hubo un error
    }

    //METODO SOBRECARGADO PARA PROJECTE
    //Creamos tasca para proyecte 
    //Crea y devuelve la List<Tascaas01> de las tasques
    //Si el estado que le paso es FINALIZADO no puedo asignarle una tasca con Estat "No iniciat" o "En progrés".
    public static List<Tascaas01> agregarTascaComplete(Tascaas01Service tascaService, Projecteas01Service projecteService, Facturaas01Service facturaService, Operariresponsableas01Service operariService,
            Clientas01Service clientService, Integer idProjecte, String EstatProjecte) {

        System.out.println("TASCA ASSOCIADA A PROJECTE");

        List<Tascaas01> tasques = new ArrayList<>();

        System.out.println("Introduïx les dades de la tasca:");

        System.out.print("Descripció de la tasca: ");
        String descripcio = tcl.nextLine();

        String estat = "";
        while (true) {
            System.out.println("Selecciona l'estat de la tasca:");
            System.out.println("1. No iniciat");
            System.out.println("2. En procés");
            System.out.println("3. Finalitzat");
            System.out.print("Introduïx el número corresponent a l'estat: ");
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
                    System.out.println("Opció no vàlida. Intenta novament.");
                    continue;
            }
            // Validar que el estado de la tarea no sea incompatible con el estado del proyecto
            if ("Finalitzat".equalsIgnoreCase(EstatProjecte) && !estat.equals("Finalitzat")) {
                System.out.println("No pots assignar una tasca amb estat 'No iniciat' o 'En procés' a un projecte finalitzat.");
                continue; // Volver a pedir
            }
            break;
        }

        Tascaas01 newTasca = new Tascaas01();
        newTasca.setDescripcio(descripcio);
        newTasca.setEstat(estat);

        tascaService.createTasca(newTasca);
        // CON ESTO PODREMOS ACCEDER A SU ID AUTOGENERADO
        Integer idTasca = newTasca.getIdTasca();

        Integer idFactura = asociarFacturaTasca(facturaService, tascaService, clientService, projecteService, operariService, idTasca);
        Integer idOperari = asociarOperariTasca(operariService, idTasca);

        newTasca.setIdProjecte(projecteService.findProjectById(idProjecte));
        newTasca.setFactura(facturaService.findFacturaById(idFactura));
        newTasca.setOperariResponsable(operariService.findOperariById(idOperari));

        try {
            tascaService.createTasca(newTasca);
            System.out.println("Tasca agregada amb èxit.");
            tasques.add(newTasca);
        } catch (Exception e) {
            System.out.println("Error en agregar la tasca: " + e.getMessage());
        }
        return tasques;
    }

    //*****************************************************************//
    //********************** FIND ************************************//
    //*****************************************************************//
    protected static void listTasques(Tascaas01Service tascaService) {

        System.out.println("Com desitges vore les tasques?");
        System.out.println("1. Bàsic (Només dades de la tasca)");
        System.out.println("2. Complet (Dades de la tasca + taules relacionades)");

        int opcion = tcl.nextInt();
        tcl.nextLine();

        switch (opcion) {
            case 1:
                System.out.println("=== MOSTRANT TASQUES ===");
                System.out.println("*=== [MODE BÀSIC] ===*");
                listTasquesBasic(tascaService);
                break;
            case 2:
                System.out.println("=== MOSTRANT TASQUES ===");
                System.out.println("*=== [MODE COMPLET] ===*");
                listTasquesComplete(tascaService);
                break;
            default:
                System.out.println("Opció no vàlida.");
                break;
        }
    }

    public static void listTasquesComplete(Tascaas01Service tascaService) {

        List<Tascaas01> tasques = tascaService.findAllWithDetails();
        tasques.forEach(tasca -> {
            System.out.println("- > TASCA Nº [" + tasca.getIdTasca() + "]" + " " + tasca.getDescripcio() + " | Estado: " + tasca.getEstat());

            System.out.println("\t" + "Tasca nº \" + tasca.getIdTasca() + \" pertany al projecte: ");
            Projecteas01 proyecto = tasca.getIdProjecte();
            if (proyecto != null) {
                System.out.println("\t -" + "[" + proyecto.getIdProjecte() + "]" + proyecto.getDescripcio() + " | Estado: " + proyecto.getEstat()
                        + "| fecha finalizació = " + (proyecto.getFechaFinalitzacio() != null ? proyecto.getFechaFinalitzacio() : "No finalizado"));
            } else {
                System.out.println("\t" + "Projecte associat no trobat.");
            }

            System.out.println("\t" + "Tasca nº " + tasca.getIdTasca() + " té la següent factura: ");
            Facturaas01 factura = tasca.getFactura();
            if (factura != null) {
                System.out.println("\t -" + "[" + factura.getIdFactura() + "]" + " " + " Fecha : " + factura.getData() + " | " + factura.getObservacions() + " | Importe: " + factura.getImportTotal());
            } else {
                System.out.println("\t" + "No té factura associada.");
            }

            System.out.println("\t" + "Tasca nº " + tasca.getIdTasca() + " té el següent operari responsable: ");
            Operariresponsableas01 operario = tasca.getOperariResponsable();
            if (operario != null) {
                System.out.println("\t -" + "[" + operario.getIdOperariTasca() + "]" + operario.getNom() + " " + operario.getCognom() + " | " + operario.getNifOperari() + "| Actividad : " + operario.getObservacions());
            } else {
                System.out.println("\t" + "Operari responsable no trobat.");
            }
        });
    }

    public static void listTasquesBasic(Tascaas01Service tascaService) {
        System.out.println("|--------------------------------------|");
        for (Tascaas01 tasca : tascaService.findAllTasques()) {
            System.out.println("---> " + "[" + tasca.getIdTasca() + "]" + " " + tasca.getDescripcio() + " | Estado: " + tasca.getEstat());
        }
        System.out.println("|--------------------------------------|");
    }

    //*****************************************************************//
    //********************** DELETE ************************************//
    //*****************************************************************//
    //*************** DELETE TASCA  *****************************//
    protected static void eliminarTasques(Tascaas01Service tascaService, Facturaas01Service facturaService) {

        System.out.println("**** Avís ****");
        System.out.println("Una tasca ES PODRÀ ESBORRAR SEMPRE QUE NO ESTIGA EN PROCÉS");
        MainApp.esperarIntro();

        System.out.println("Com vols eliminar les tasques?");
        System.out.println("1. Eliminar totes les tasques");
        System.out.println("2. Eliminar una tasca per ID [BASIC y COMPLETE]");

        int opcion = tcl.nextInt();
        tcl.nextLine();

        switch (opcion) {
            case 1:
                System.out.print("Eliminant totes les tasques... \n");
                if (tascaService.findAllTasques().isEmpty()) {
                    System.out.println("No hi ha tasques per a eliminar.");
                } else {
                    // Se itera cada tarea, se revisa si se puede eliminar
                    List<Tascaas01> tasquesAEliminar = new ArrayList<>();
                    for (Tascaas01 tasca : tascaService.findAllTasques()) {
                        String info = tascaService.deleteTascaVerification(tasca);
                        if (info == null) {
                            tasquesAEliminar.add(tasca);
                        } else {
                            System.out.println("No es pot eliminar la tasca amb ID " + tasca.getIdTasca() + ": " + info);
                        }
                    }

                    if (!tasquesAEliminar.isEmpty()) {
                        // Mostrar el listado de tareas a eliminar
                        System.out.println("Les següents tasques seran eliminades:");
                        for (Tascaas01 tasca : tasquesAEliminar) {
                            System.out.println("Tasca: " + tasca.getDescripcio() + " (ID: " + tasca.getIdTasca() + ")");
                        }

                        // Confirmación antes de eliminar
                        System.out.print("Estàs segur que vols eliminar aquestes tasques? (S/N): ");
                        String confirmacion = tcl.nextLine();
                        if (confirmacion.equalsIgnoreCase("S")) {
                            for (Tascaas01 tasca : tasquesAEliminar) {
                                try {
                                    tascaService.deleteTasca(tasca);
                                    System.out.println("La tasca amb ID " + tasca.getIdTasca() + " ha sigut eliminada correctament.");
                                } catch (Exception e) {
                                    System.out.println("Error en eliminar la tasca amb ID " + tasca.getIdTasca() + ": " + e.getMessage());
                                }
                            }
                        } else {
                            System.out.println("L'eliminació ha sigut cancel·lada.");
                        }
                    } else {
                        System.out.println("No hi ha tasques per a eliminar.");
                    }
                }
                break;

            case 2:
                System.out.println("Tasques disponibles:");
                listTasquesComplete(tascaService);

                System.out.print("Introdueix l'ID de la tasca a eliminar: ");
                int idTasca = tcl.nextInt();
                tcl.nextLine();

                System.out.println("Elige un MODO DE ELIMINACIÓN");
                System.out.println("\nCom vols eliminar les tasques ?");
                System.out.println("1. BASIC (Borramos, si hay datos relacionados sensibles no se podrá borrar)");
                System.out.println("2. COMPLETE (Borramos tascas y facturas asociadas) ");
                System.out.println("*Ambas cumplirán la restricció de projectes mencionada prèviament.");
                int opcionMode = tcl.nextInt();
                tcl.nextLine();

                switch (opcionMode) {
                    case 1:
                        Tascaas01 tascaBasic = tascaService.findTascaById(idTasca);
                        System.out.println("Se eliminará la tasca " + tascaBasic.getIdTasca());
                        if (tascaBasic != null) {
                            //1º - SE REVISAN RESTRICCIONES CONCRETAS
                            String verificationResult = tascaService.deleteTascaVerification(tascaBasic);
                            if (verificationResult != null) {
                                System.out.println(verificationResult); // Si hay restricciones, mostrar mensaje.
                                return;
                            }

                            // 2 º SE REVISA SI TIENE FACTURAS (BASIC - NO SE BORRARA)
                            if (tascaService.HasFacturesVerification(tascaBasic)) {
                                System.out.println("La tasca con id " + tascaBasic.getIdTasca() + " - TIENE FACTURAS ASOCIADAS");
                                System.out.println("NO SE BORRARÁ");
                            } else {
                                try {
                                    // HECHO TODO - SE BORRA LA TAREA
                                    //RESTRICCIONES CONCRETAS EN SERVICE
                                    tascaService.deleteTasca(tascaBasic);
                                    System.out.println("tasca " + tascaBasic.getIdTasca() + " ha sigut eliminat/da correctament.");
                                } catch (RuntimeException e) {
                                    System.out.println("Error en eliminar la tasca : " + e.getMessage());
                                }
                            }
                        } else {
                            System.out.println("No s'ha trobat una tasca amb aquest ID.");
                        }
                        break;

                    case 2:
                        Tascaas01 tascaComplete = tascaService.findTascaById(idTasca);
                        System.out.println("Se eliminará la tasca " + tascaComplete.getIdTasca());
                        if (tascaComplete != null) {

                            //1º RESTRICCIONES CONCRETAS
                            String verificationResult = tascaService.deleteTascaVerification(tascaComplete);
                            if (verificationResult != null) {
                                System.out.println(verificationResult); // Si hay restricciones, mostrar mensaje.
                                return;
                            }

                            //2 º FACTURAS - COMPLETO - SE ELIMINAN
                            if (tascaService.HasFacturesVerification(tascaComplete)) {

                                System.out.println("La tasca té factures associades, se ELIMINARÁN:");
                                System.out.println("¿Estás seguro de que quieres eliminar esta tasca y sus facturas? (Si/No):");
                                String respuesta = tcl.nextLine();

                                if ("Si".equalsIgnoreCase(respuesta)) {
                                    Facturaas01 factura = tascaComplete.getFactura();

                                    if (factura != null) {
                                        // Elimina primero la factura asociada
                                        facturaService.deleteFactura(factura);
                                        System.out.println("Factura eliminada.");
                                    }

                                    try {
                                        tascaService.deleteTasca(tascaComplete);
                                        System.out.println(tascaComplete.getIdTasca() + " ha sigut eliminat/da correctament.");
                                    } catch (RuntimeException e) {
                                        System.out.println("Error en eliminar el client: " + e.getMessage());
                                    }
                                } else {
                                    System.out.println("Eliminación cancelada.");
                                }
                            } else {
                                // NO TIENE FACTURAS - No borramos nada, seguimos mirando projectes;
                                System.out.println("La tasca no tiene facturas asociadas.");
                                try {
                                    tascaService.deleteTasca(tascaComplete);
                                    System.out.println(tascaComplete.getIdTasca() + " ha sigut eliminat/da correctament.");
                                } catch (RuntimeException e) {
                                    System.out.println("Error en eliminar la tasca: " + e.getMessage());
                                }
                            }
                        } else {
                            System.out.println("No s'ha trobat una tasca amb aquest ID.");
                        }
                        break;

                    default:
                        System.out.println("Opció no vàlida.");
                        break;
                }
                break;

            default:
                System.out.println("Opció no vàlida.");
                break;
        }
    }
}
