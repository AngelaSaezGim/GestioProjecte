/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Test;

import Entity.Clientas01;
import Entity.Projecteas01;
import Entity.Tascaas01;
import Service.Clientas01Service;
import Service.Facturaas01Service;
import Service.Operariresponsableas01Service;
import Service.Projecteas01Service;
import Service.Tascaas01Service;
import static Test.MainApp.log;
import static Test.MainApp.tcl;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
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
    public static void agregarProjecteMenu(Projecteas01Service projecteService, Clientas01Service clientasService, Facturaas01Service facturaService, Operariresponsableas01Service operariService, Tascaas01Service tascaService) {

        System.out.println("Com vols afegir els projectes?");
        System.out.println("1. Bàsic (Només creem el projecte)");
        System.out.println("2. Complet (Creem el projecte i, si no existeix alguna dada associada, la creem)");

        int opcion = tcl.nextInt();
        tcl.nextLine();

        switch (opcion) {
            case 1:
                System.out.println("=== CREACIÓ DE PROJECTES ===");
                System.out.println("*=== [MODO BÀSIC] ===*");
                agregarProjecteBasic(projecteService, clientasService, tascaService);
                break;
            case 2:
                System.out.println("=== CREACIÓ DE PROJECTES ===");
                System.out.println("*=== [MODO COMPLET] ===*");
                agregarProjecteComplete(projecteService, clientasService, facturaService, operariService, tascaService);
                break;
            default:
                System.out.println("Opció no vàlida.");
                break;
        }
    }

    public static void agregarProjecteBasic(Projecteas01Service projecteService, Clientas01Service clientService, Tascaas01Service tascaService) {

        String continueAdding = "si";

        while (continueAdding.equalsIgnoreCase("si")) {

            System.out.println("Introdueix les dades del projecte:");

            System.out.print("Descripció del projecte: ");
            String descripcio = tcl.nextLine();

            String estat = "";
            while (true) {
                System.out.println("Selecciona l'estat del projecte:");
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
                        System.out.println("Opció no vàlida. Torna a intentar-ho.");
                        continue;
                }
                break;
            }

            Date fechaFinalitzacio = null;
            if ("Finalitzat".equalsIgnoreCase(estat)) {
                System.out.println("Si està finalitzat, indica la data en què s'ha finalitzat:");
                while (fechaFinalitzacio == null) {
                    System.out.print("Data de finalització (Format: yyyy-MM-dd, No pot estar buida): ");
                    String input = tcl.nextLine();
                    try {
                        fechaFinalitzacio = new SimpleDateFormat("yyyy-MM-dd").parse(input);
                    } catch (ParseException e) {
                        System.out.println("Data no vàlida. Assegura't d'utilitzar el format yyyy-MM-dd.");
                    }
                }
            }

            Integer idClient = null;
            while (idClient == null) {
                System.out.print("ID del client (ha de ser vàlid i existir): ");
                System.out.println("Llista de clients disponibles:");
                MethodsMainClient.listClientsBasic(clientService);
                System.out.println("Projecte associat a client...");
                String inputIdClient = tcl.nextLine();
                try {
                    idClient = Integer.parseInt(inputIdClient);
                    if (clientService.findClientById(idClient) == null) {
                        System.out.println("El client amb ID " + idClient + " no existeix. Torna a intentar-ho.");
                        idClient = null;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("ID de client no vàlid. Torna a intentar-ho.");
                }
            }

            System.out.print("IDs de les tasques (separats per comes): ");
            System.out.println("Llista de tasques disponibles:");

            // Verifica si el proyecto está finalizado antes de permitir agregar tareas
            if ("Finalitzat".equalsIgnoreCase(estat)) {
                System.out.println("Com que el projecte està finalitzat, només pots associar tasques que també estiguin finalitzades.");
                System.out.println("Llista de tasques FINALITZADES disponibles:");
                listProjecteByState(projecteService, "Finalitzat");
            } else {
                MethodsMainTasca.listTasquesBasic(tascaService);
            }
            System.out.println("Projecte associat a tasques... (separa-les per comes)");
            String inputTascas = tcl.nextLine();
            String[] tascaIds = inputTascas.split(",");
            List<Tascaas01> tasques = new ArrayList<>();
            boolean allTasksFinalized = true;

            for (String tascaId : tascaIds) {
                try {
                    int id = Integer.parseInt(tascaId.trim());
                    Tascaas01 tasca = tascaService.findTascaById(id);
                    if (tasca != null) {
                        if ("Finalitzat".equalsIgnoreCase(estat) && !"Finalitzat".equalsIgnoreCase(tasca.getEstat())) {
                            allTasksFinalized = false; // Marca que no todas las tareas están finalizadas
                            System.out.println("No es pot associar la tasca amb ID " + id + " perquè no està finalitzada.");
                        } else {
                            tasques.add(tasca);
                        }
                    } else {
                        System.out.println("La tasca amb ID " + id + " no existeix.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("L'ID " + tascaId + " no és vàlid.");
                }
            }

            if (!allTasksFinalized) {
                System.out.println("No es poden associar tasques que no estan finalitzades amb un projecte finalitzat.");
                System.out.println("Per favor, torna a començar.");
                continue;
            }

            Projecteas01 newProjecte = new Projecteas01();
            newProjecte.setDescripcio(descripcio);
            newProjecte.setEstat(estat);
            newProjecte.setFechaFinalitzacio(fechaFinalitzacio);
            newProjecte.setTascaas01Collection(tasques);

            // Obtener el cliente y asociarlo al proyecto
            Clientas01 client = clientService.findClientById(idClient);
            if (client != null) {
                newProjecte.setIdClient(client); // Asociar cliente al proyecto
            } else {
                System.out.println("No s'ha pogut associar el projecte al client.");
                continue;
            }

            try {
                projecteService.createProject(newProjecte);
                System.out.println("Projecte afegit amb èxit.");
            } catch (Exception e) {
                System.out.println("Error en afegir el projecte: " + e.getMessage());
            }

            boolean validInput = false;
            while (!validInput) {
                System.out.print("Vols afegir un altre projecte? (si/no): ");
                continueAdding = tcl.nextLine().trim().toLowerCase();

                if (continueAdding.equals("si") || continueAdding.equals("no")) {
                    validInput = true;
                } else {
                    System.out.println("Introdueix 'si' per continuar o 'no' per sortir.");
                }
            }
        }
    }

    public static void agregarProjecteComplete(Projecteas01Service projecteService, Clientas01Service clientService, Facturaas01Service facturaService, Operariresponsableas01Service operariService, Tascaas01Service tascaService) {

        String continueAdding = "si";

        while (continueAdding.equalsIgnoreCase("si")) {

            System.out.println("Introdueix les dades del projecte:");

            // Descripción del proyecto
            System.out.print("Descripció del projecte: ");
            String descripcio = tcl.nextLine();

            // Selección del estado del proyecto
            String estat = "";
            while (true) {
                System.out.println("Selecciona l'estat del projecte:");
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
                        System.out.println("Opció no vàlida. Torna a intentar-ho.");
                        continue;
                }
                break;
            }

            // Fecha de finalización si está finalizado
            Date fechaFinalitzacio = null;
            if ("Finalitzat".equalsIgnoreCase(estat)) {
                System.out.println("Si està finalitzat, indica la data en què s'ha finalitzat:");
                while (fechaFinalitzacio == null) {
                    System.out.print("Data de finalització (Format: yyyy-MM-dd, No pot estar buida): ");
                    String input = tcl.nextLine();
                    try {
                        fechaFinalitzacio = new SimpleDateFormat("yyyy-MM-dd").parse(input);
                    } catch (ParseException e) {
                        System.out.println("Data no vàlida. Assegura't d'utilitzar el format yyyy-MM-dd.");
                    }
                }
            }

            Projecteas01 newProjecte = new Projecteas01();
            newProjecte.setDescripcio(descripcio);
            newProjecte.setEstat(estat);
            newProjecte.setFechaFinalitzacio(fechaFinalitzacio);

            projecteService.createProject(newProjecte);
            // CON ESTO PODREMOS ACCEDER A SU ID AUTOGENERADO
            Integer idProjecte = newProjecte.getIdProjecte();

            List<Tascaas01> tasques = asociarTascasProjecte(tascaService, projecteService, facturaService, operariService, clientService, idProjecte, estat);
            Integer idClient = asociarClientProjecte(clientService, idProjecte);

            newProjecte.setTascaas01Collection(tasques);
            newProjecte.setIdClient(clientService.findClientById(idClient));

            try {
                projecteService.createProject(newProjecte);
                System.out.println("Projecte afegit amb èxit.");
            } catch (Exception e) {
                System.out.println("Error en afegir el projecte: " + e.getMessage());
            }

            // Preguntar si se desea agregar otro proyecto
            boolean validInput = false;
            while (!validInput) {
                System.out.print("¿Vols afegir un altre projecte en modo completo? (si/no): ");
                continueAdding = tcl.nextLine().trim().toLowerCase();

                if (continueAdding.equals("si") || continueAdding.equals("no")) {
                    validInput = true;
                } else {
                    System.out.println("Introdueix 'si' per continuar o 'no' per sortir.");
                }
            }
        }
    }

    public static List<Tascaas01> asociarTascasProjecte(Tascaas01Service tascaService, Projecteas01Service projecteService,
            Facturaas01Service facturaService, Operariresponsableas01Service operariService, Clientas01Service clientService, Integer idProjecte, String estat) {

        System.out.println("[TASCA ASSOCIA A PROJECTE]");

        // Opciones para agregar tareas
        System.out.println("Opcions:");
        System.out.println("1. Cercar tasques existents");
        System.out.println("2. Crear noves tasques associades a aquest projecte");
        System.out.print("Escull una opció per a les tasques (1/2): ");
        String optionTasca = tcl.nextLine();
        List<Tascaas01> tasques = new ArrayList<>();
        boolean allTasksFinalized = true;
        switch (optionTasca) {
            case "1":
                // Buscar tareas existentes
                System.out.println("Llista de tasques existents: ");
                MethodsMainTasca.listTasquesBasic(tascaService);

                // Solicitar los IDs de las tareas (esto tiene más sentido dentro de "1" si estamos buscando tareas)
                System.out.print("IDs de les tasques (separats per comes): ");
                String inputTascas = tcl.nextLine();
                String[] tascaIds = inputTascas.split(",");

                for (String tascaId : tascaIds) {
                    try {
                        int id = Integer.parseInt(tascaId.trim());
                        Tascaas01 tasca = tascaService.findTascaById(id);
                        if (tasca != null) {
                            if ("Finalitzat".equalsIgnoreCase(estat) && !"Finalitzat".equalsIgnoreCase(tasca.getEstat())) {
                                allTasksFinalized = false;
                                System.out.println("No es pot associar la tasca amb ID " + id + " perquè no està finalitzada.");
                            } else {
                                tasques.add(tasca);
                            }
                        } else {
                            System.out.println("La tasca amb ID " + id + " no existeix.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("L'ID " + tascaId + " no és vàlid.");
                    }
                }
                // Verificación de si las tareas pueden ser asociadas con el proyecto
                if ("Finalitzat".equalsIgnoreCase(estat) && !allTasksFinalized) {
                    System.out.println("No es poden associar tasques que no estan finalitzades amb un projecte finalitzat.");
                    System.out.println("Si us plau, torna a començar.");
                    break;
                }
                break;
            case "2":
                // Crear nuevas tareas
                System.out.println("Creant noves tasques per al projecte...");
                tasques = MethodsMainTasca.agregarTascaComplete(tascaService, projecteService, facturaService, operariService, clientService, idProjecte, estat);
                break;
            default:
                System.out.println("Opció no vàlida. Torna-ho a intentar.");
                break;
        }
        return tasques;
    }

    public static Integer asociarClientProjecte(Clientas01Service clientService, Integer idProjecte) {

        System.out.println("[CLIENTE ASOCIADO A PROJECTE]");

        Integer idClient = null;

        while (idClient == null) {
            System.out.println("Opcions:");
            System.out.println("1. Cercar un client existent");
            System.out.println("2. Crear un nou client associat a aquest projecte");
            System.out.print("Escull una opció per al client (1/2): ");
            String optionClient = tcl.nextLine();

            if (optionClient.equals("1")) {
                System.out.println("Llista de clients disponibles:");
                MethodsMainClient.listClientsBasic(clientService);
                System.out.print("ID del Client a associar: ");
                String input = tcl.nextLine();
                try {
                    idClient = Integer.parseInt(input);
                    if (clientService.findClientById(idClient) == null) {
                        System.out.println("El client amb ID " + idClient + " no existeix.");
                        idClient = null;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("ID de client invàlid. Torna-ho a intentar.");
                }
            } else if (optionClient.equals("2")) {
                // Crear un nuevo cliente (reemplazar con lógica de creación)
                System.out.println("Creant un nou client...");
                Integer idFactura = 1; //esto nada
                idClient = MethodsMainClient.agregarClienteBasic(clientService, idFactura);
            } else {
                System.out.println("Opció no vàlida. Torna-ho a intentar.");
            }
        }
        return idClient;
    }

    //PROYECTO ASOCIADO A UNA TAREA SOBRECARGADO
    //SOBRECARGADO CON ID TASCA
    //Si el estat tasca no es Finalizat NO SE PUEDE AGREGAR A UN PROJECTO FINALIZADO
    public static Integer agregarProjecteComplete(Projecteas01Service projecteService, Clientas01Service clientService, Facturaas01Service facturaService, Operariresponsableas01Service operariService, Tascaas01Service tascaService,
            Integer idTasca, String estatTasca) {

        System.out.println("[PROJECTE ASSOCIAT A TASCA]");

        System.out.println("Introdueix les dades del projecte:");

        // Descripción del proyecto
        System.out.print("Descripció del projecte: ");
        String descripcio = tcl.nextLine();

        // Selección del estado del proyecto
        String estat = "";
        while (true) {
            System.out.println("Selecciona l'estat del projecte:");
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
                    System.out.println("Opción no válida. Intenta nuevamente.");
                    continue;
            }
            if ("Finalitzat".equalsIgnoreCase(estat) && !"Finalitzat".equalsIgnoreCase(estatTasca)) {
                System.out.println("Error: No pots afegir una tasca que no estiga finalitzada a un projecte finalitzat.");
                continue;
            }

            break;
        }

        // Fecha de finalización si está finalizado
        Date fechaFinalitzacio = null;
        if ("Finalitzat".equalsIgnoreCase(estat)) {
            System.out.println("Si aquest projecte està finalitzat, introdueix la data de finalització:");
            while (fechaFinalitzacio == null) {
                System.out.print("Data finalització (Format: yyyy-MM-dd, No pot estar buida): ");
                String input = tcl.nextLine();
                try {
                    fechaFinalitzacio = new SimpleDateFormat("yyyy-MM-dd").parse(input);
                } catch (ParseException e) {
                    System.out.println("Data invàlida. Assegura't d'utilitzar el format yyyy-MM-dd.");
                }
            }
        }

        Projecteas01 newProjecte = new Projecteas01();
        newProjecte.setDescripcio(descripcio);
        newProjecte.setEstat(estat);
        newProjecte.setFechaFinalitzacio(fechaFinalitzacio);

        projecteService.createProject(newProjecte);
        // CON ESTO PODREMOS ACCEDER A SU ID AUTOGENERADO
        Integer idProjecte = newProjecte.getIdProjecte();

        Integer idClient = asociarClientProjecte(clientService, idProjecte);

        Tascaas01 tasca = tascaService.findTascaById(idTasca);
        if (tasca != null) {
            newProjecte.setTascaas01Collection(Collections.singletonList(tasca));
        } else {
            System.out.println("No es va trobar la tasca amb l'ID especificat.");
        }
        newProjecte.setIdClient(clientService.findClientById(idClient));

        try {
            projecteService.createProject(newProjecte);
            System.out.println("Projecte afegit correctament.");
            return newProjecte.getIdProjecte();
        } catch (Exception e) {
            System.out.println("Error en afegir el projecte: " + e.getMessage());
        }
        return null; // null si falla
    }

    //*****************************************************************//
    //********************** FIND ************************************//
    //*****************************************************************//
    //*************** LISTAR PROYECTOS  *****************************//
    protected static void listProyectos(Projecteas01Service projecteService) {

        System.out.println("¿Com vols veure els projectes?");
        System.out.println("1. Bàsic (Només dades del projecte)");
        System.out.println("2. Completo (Dades del projecte + taules relacionades)");

        int opcion = tcl.nextInt();
        tcl.nextLine();

        switch (opcion) {
            case 1:
                System.out.println("=== MOSTRANT PROJECTES ===");
                System.out.println("*=== [MODE BÀSIC] ===*");
                listProjecteBasic(projecteService);
                break;
            case 2:
                System.out.println("=== MOSTRANT PROJECTES ===");
                System.out.println("*=== [MODE COMPLET] ===*");
                listProjecteComplete(projecteService);
                break;
            default:
                System.out.println("Opció no vàlida.");
                break;
        }
    }

    public static void listProjecteComplete(Projecteas01Service projecteService) {
        List<Projecteas01> proyectos = projecteService.findAllWithDetails();
        proyectos.forEach(proyecto -> {

            System.out.println("- > PROYECTO Nº [" + proyecto.getIdProjecte() + "]" + " " + proyecto.getDescripcio() + " | Estado: " + proyecto.getEstat()
                    + "| data finalización = " + (proyecto.getFechaFinalitzacio() != null ? proyecto.getFechaFinalitzacio() : "No finalizado"));

            System.out.println("\t" + "Proyecto nº " + proyecto.getIdProjecte() + " pertenece al cliente: ");
            Clientas01 cliente = proyecto.getIdClient();
            if (cliente != null) {
                System.out.println("\t -" + "[" + cliente.getIdClient() + "]" + cliente.getNom() + " " + cliente.getCognom() + " - NIF: " + cliente.getNif());
            } else {
                System.out.println("\t" + "Client associat no trobat.");
            }

            System.out.println("\t" + "Proyecto nº " + proyecto.getIdProjecte() + " té les següents tasques: ");
            Collection<Tascaas01> tareas = proyecto.getTascaas01Collection();
            if (tareas != null && !tareas.isEmpty()) {
                tareas.forEach(tarea -> {
                    System.out.println("\t -" + "[" + tarea.getIdTasca() + "]" + tarea.getDescripcio() + " | Estado: " + tarea.getEstat());
                });
            } else {
                System.out.println("\t" + "No té tasques associades.");
            }
        });
    }

    public static void listProjecteBasic(Projecteas01Service projecteService) {
        System.out.println("|--------------------------------------|");
        for (Projecteas01 proyecto : projecteService.findAllProjects()) {
            System.out.println("---> " + "[" + proyecto.getIdProjecte() + "]" + " " + proyecto.getDescripcio() + " | Estat: " + proyecto.getEstat()
                    + "| data finalització = " + (proyecto.getFechaFinalitzacio() != null ? proyecto.getFechaFinalitzacio() : "No finalitzat"));
        }
        System.out.println("|--------------------------------------|");
    }

    public static void listProjecteByState(Projecteas01Service projecteService, String state) {
        System.out.println("|--------------------------------------|");
        // Mostrar projectes per estat
        for (Projecteas01 proyectoFinalizado : projecteService.findAllByState(state)) {
            System.out.println("---> " + "[" + proyectoFinalizado.getIdProjecte() + "]" + " " + proyectoFinalizado.getDescripcio() + " | Estat: " + proyectoFinalizado.getEstat()
                    + "| data finalització = " + (proyectoFinalizado.getFechaFinalitzacio() != null ? proyectoFinalizado.getFechaFinalitzacio() : "No finalitzat"));
        }
        System.out.println("|--------------------------------------|");
    }

    //*****************************************************************//
    //********************** DELETE ************************************//
    //*****************************************************************//
    //*************** DELETE PROJECTE  *****************************//
    // PROCESAR FECHA DE FINALIZACION - CONDICIONAL
    protected static void eliminarProjectes(Projecteas01Service projecteService) {
        System.out.println("**** Avís ****");
        System.out.println("No podrem eliminar cap PROJECTE que tingui alguna TASCA que ja hagi generat la FACTURA associada.");
        System.out.println("**Es podrà eliminar un projecte; \n"
                + "-> NOMÉS quan estigui en estat FINALITZAT i ADEMÉS totes les seves tasques (FINALITZADES) hagin sigut facturades FA MÉS DE 5 ANYS");

        MainApp.esperarIntro();

        System.out.println("\nCom vols eliminar els projectes?");
        System.out.println("1. Eliminar tots els projectes");
        System.out.println("2. Eliminar un projecte per ID [BASIC y COMPLETE]");

        int opcion = tcl.nextInt();
        tcl.nextLine();

        switch (opcion) {
            case 1:
                System.out.print("Eliminant tots els projectes... ");
                if (projecteService.findAllProjects().isEmpty()) {
                    System.out.println("No hi ha projectes per eliminar.");
                } else {
                    // Se itera cada proyecto, se revisa si puede ser eliminado y se elimina
                    List<Projecteas01> proyectosAEliminar = new ArrayList<>();
                    for (Projecteas01 proyecto : projecteService.findAllProjects()) {
                        String info = projecteService.deleteProjectVerification(proyecto);
                        if (info == null) {
                            proyectosAEliminar.add(proyecto);
                        } else {
                            System.out.println("No es pot eliminar el projecte amb ID " + proyecto.getIdProjecte() + ": " + info);
                        }
                    }

                    if (!proyectosAEliminar.isEmpty()) {
                        // Mostrar el listado de proyectos a eliminar
                        System.out.println("Els següents projectes seran eliminats:");
                        for (Projecteas01 proyecto : proyectosAEliminar) {
                            System.out.println(proyecto.getDescripcio() + " (ID: " + proyecto.getIdProjecte() + ")");
                        }

                        System.out.print("Estàs segur que vols eliminar aquests projectes? (S/N): ");
                        String confirmacion = tcl.nextLine();
                        if (confirmacion.equalsIgnoreCase("S")) {
                            // Eliminar los proyectos
                            for (Projecteas01 proyecto : proyectosAEliminar) {
                                try {
                                    projecteService.deleteProject(proyecto);
                                    System.out.println("Projecte amb ID " + proyecto.getIdProjecte() + " ha estat eliminat.");
                                } catch (Exception e) {
                                    System.out.println("Error en eliminar el projecte amb ID " + proyecto.getIdProjecte() + ": " + e.getMessage());
                                }
                            }
                        } else {
                            System.out.println("L'eliminació ha estat cancel·lada.");
                        }
                    } else {
                        System.out.println("No hi ha projectes per eliminar.");
                    }
                }
                break;
            case 2:
                System.out.println("Projectes disponibles:");
                listProjecteComplete(projecteService);

                System.out.print("ID del projecte a eliminar: ");
                int idProyecto = tcl.nextInt();
                tcl.nextLine();

                Projecteas01 proyecto = projecteService.findProjectById(idProyecto);
                if (proyecto != null) {
                    System.out.println("Elige un MODO DE ELIMINACIÓN");
                    System.out.println("1. BASIC (Eliminem, si té tasques asociades no es pot eliminar)");
                    System.out.println("2. COMPLETE (Eliminem projecte (cuando cumpla restricció) i tasques associades");
                    int opcionMode = tcl.nextInt();
                    tcl.nextLine();

                    switch (opcionMode) {
                        case 1:
                            System.out.println("S'eliminarà el projecte " + proyecto.getIdProjecte());
                            if (projecteService.hasTasquesVerification(proyecto)) {
                                System.out.println("Aquest projecte té tasques associades. No es pot eliminar en mode BASIC.");
                                return;
                            }
                            try {
                                projecteService.deleteProject(proyecto);
                                System.out.println("Projecto amb ID " + idProyecto + " ha sigut eliminat.");
                            } catch (Exception e) {
                                System.out.println("Error en eliminar projecte amb ID " + idProyecto + ": " + e.getMessage());
                            }
                            break;

                        case 2:
                            System.out.println("S'eliminarà el projecte " + proyecto.getIdProjecte());

                            //1º RESTRICCIONES CONCRETAS
                            String verificationResult = projecteService.deleteProjectVerification(proyecto);
                            if (verificationResult != null) {
                                System.out.println(verificationResult); // Si hay restricciones CONCRETAS, mostrar mensaje.
                                return;
                            }

                            // Verificación de tareas asociadas para eliminación completa
                            if (projecteService.hasTasquesVerification(proyecto)) {
                                System.out.println("Aquest projecte té tasques associades, es borraran també.");
                                System.out.print("Estàs segur de voler eliminar aquest projecte i les tasques associades? (S/N): ");
                                String respuesta = tcl.nextLine();
                                if ("S".equalsIgnoreCase(respuesta)) {
                                    projecteService.deleteProject(proyecto);
                                    System.out.println("Projecte i tasques associades eliminades correctament.");
                                } else {
                                    System.out.println("Eliminació cancel·lada.");
                                }
                            } else {
                                try {
                                    projecteService.deleteProject(proyecto);
                                    System.out.println("Projecto amb ID " + proyecto.getIdProjecte() + " ha sigut eliminat.");
                                } catch (Exception e) {
                                    System.out.println("Error en eliminar projecte amb ID " + proyecto.getIdProjecte() + ": " + e.getMessage());
                                }
                            }
                            break;

                        default:
                            System.out.println("Opció no vàlida.");
                            break;
                    }
                } else {
                    System.out.println("No s'ha trobat cap projecte amb aquest ID.");
                }
                break;
            default:
                System.out.println("Opció no vàlida.");
                break;
        }
    }
}
