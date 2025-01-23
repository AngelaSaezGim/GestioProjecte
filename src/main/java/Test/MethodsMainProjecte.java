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
                agregarProjecteComplete(projecteService, clientasService, facturaService, operariService, tascaService);
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

            Date fechaFinalitzacio = null;
            if ("Finalitzat".equalsIgnoreCase(estat)) {
                System.out.println("Si esta finalizado, dime la fecha en la que se ha finalizado ;");
                while (fechaFinalitzacio == null) {
                    System.out.print("Fecha finalizació (Formato: yyyy-MM-dd, No puede estar vacía): ");
                    String input = tcl.nextLine();
                    try {
                        fechaFinalitzacio = new SimpleDateFormat("yyyy-MM-dd").parse(input);
                    } catch (ParseException e) {
                        System.out.println("Fecha inválida. Asegúrate de usar el formato yyyy-MM-dd.");
                    }
                }
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

            // Verifica si el proyecto está finalizado antes de permitir agregar tareas
            if ("Finalitzat".equalsIgnoreCase(estat)) {
                System.out.println("Dado que el proyecto está finalizado, solo puedes asociar tareas que también estén finalizadas.");
                System.out.println("Lista de tareas FINALIZADAS disponibles; ");
                listProjecteByState(projecteService, "Finalitzat");
            } else {
                MethodsMainTasca.listTasquesBasic(tascaService);
            }
            System.out.println("Proyecto asociado a tareas... (separarlas por comas)");
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
                            System.out.println("No se puede asociar la tarea con ID " + id + " porque no está finalizada.");
                        } else {
                            tasques.add(tasca);
                        }
                    } else {
                        System.out.println("La tarea con ID " + id + " no existe.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("El ID " + tascaId + " no es válido.");
                }
            }

            if (!allTasksFinalized) {
                System.out.println("No se pueden asociar tareas que no están finalizadas con un proyecto finalizado.");
                System.out.println("Porfavor, vuelva a empezar");
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
                System.out.println("No se pudo asociar el proyecto al cliente.");
                continue;
            }

            try {
                projecteService.createProject(newProjecte);
                System.out.println("Proyecto agregado exitosamente.");
            } catch (Exception e) {
                System.out.println("Error al agregar el proyecto: " + e.getMessage());
            }

            boolean validInput = false;
            while (!validInput) {
                System.out.print("¿Quieres agregar otro projecto ? (si/no): ");
                continueAdding = tcl.nextLine().trim().toLowerCase();

                if (continueAdding.equals("si") || continueAdding.equals("no")) {
                    validInput = true;
                } else {
                    System.out.println("Por favor, ingresa 'si' para continuar o 'no' para salir.");
                }
            }
        }
    }

    public static void agregarProjecteComplete(Projecteas01Service projecteService, Clientas01Service clientService, Facturaas01Service facturaService, Operariresponsableas01Service operariService, Tascaas01Service tascaService) {

        String continueAdding = "si";

        while (continueAdding.equalsIgnoreCase("si")) {

            System.out.println("Introduce los datos del proyecto:");

            // Descripción del proyecto
            System.out.print("Descripción del proyecto: ");
            String descripcio = tcl.nextLine();

            // Selección del estado del proyecto
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

            // Fecha de finalización si está finalizado
            Date fechaFinalitzacio = null;
            if ("Finalitzat".equalsIgnoreCase(estat)) {
                System.out.println("Si este proyecto está finalizado, ingresa la fecha de finalización:");
                while (fechaFinalitzacio == null) {
                    System.out.print("Fecha finalización (Formato: yyyy-MM-dd, No puede estar vacía): ");
                    String input = tcl.nextLine();
                    try {
                        fechaFinalitzacio = new SimpleDateFormat("yyyy-MM-dd").parse(input);
                    } catch (ParseException e) {
                        System.out.println("Fecha inválida. Asegúrate de usar el formato yyyy-MM-dd.");
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
                System.out.println("Proyecto agregado exitosamente.");
            } catch (Exception e) {
                System.out.println("Error al agregar el proyecto: " + e.getMessage());
            }

            // Preguntar si se desea agregar otro proyecto
            boolean validInput = false;
            while (!validInput) {
                System.out.print("¿Quieres agregar otro proyecto en modo completo? (si/no): ");
                continueAdding = tcl.nextLine().trim().toLowerCase();

                if (continueAdding.equals("si") || continueAdding.equals("no")) {
                    validInput = true;
                } else {
                    System.out.println("Por favor, ingresa 'si' para continuar o 'no' para salir.");
                }
            }
        }
    }

    public static List<Tascaas01> asociarTascasProjecte(Tascaas01Service tascaService, Projecteas01Service projecteService,
            Facturaas01Service facturaService, Operariresponsableas01Service operariService, Clientas01Service clientService, Integer idProjecte, String estat) {

        // Opciones para agregar tareas
        System.out.println("Opciones:");
        System.out.println("1. Buscar tareas existentes");
        System.out.println("2. Crear nuevas tareas asociadas a este proyecto");
        System.out.print("Elige una opción para las tareas (1/2): ");
        String optionTasca = tcl.nextLine();
        List<Tascaas01> tasques = new ArrayList<>();
        boolean allTasksFinalized = true;
        switch (optionTasca) {
            case "1":
                // Buscar tareas existentes
                System.out.println("Lista de tareas existentes: ");
                MethodsMainTasca.listTasquesBasic(tascaService);

                // Solicitar los IDs de las tareas (esto tiene más sentido dentro de "1" si estamos buscando tareas)
                System.out.print("IDs de las tareas (separados por comas): ");
                String inputTascas = tcl.nextLine();
                String[] tascaIds = inputTascas.split(",");

                for (String tascaId : tascaIds) {
                    try {
                        int id = Integer.parseInt(tascaId.trim());
                        Tascaas01 tasca = tascaService.findTascaById(id);
                        if (tasca != null) {
                            if ("Finalitzat".equalsIgnoreCase(estat) && !"Finalitzat".equalsIgnoreCase(tasca.getEstat())) {
                                allTasksFinalized = false;
                                System.out.println("No se puede asociar la tarea con ID " + id + " porque no está finalizada.");
                            } else {
                                tasques.add(tasca);
                            }
                        } else {
                            System.out.println("La tarea con ID " + id + " no existe.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("El ID " + tascaId + " no es válido.");
                    }
                }
                // Verificación de si las tareas pueden ser asociadas con el proyecto
                if ("Finalitzat".equalsIgnoreCase(estat) && !allTasksFinalized) {
                    System.out.println("No se pueden asociar tareas que no están finalizadas con un proyecto finalizado.");
                    System.out.println("Por favor, vuelva a empezar.");
                    break;
                }
                break;
            case "2":
                // Crear nuevas tareas
                // SI EL PROYECTO ESTA FINALIZADO SOLO SE PUEDEN CREAR TAREAS FINALIZADAS
                System.out.println("Creando nuevas tareas para el proyecto...");
                tasques = MethodsMainTasca.agregarTascaComplete(tascaService, projecteService, facturaService, operariService, clientService, idProjecte, estat);
                break;
            default:
                System.out.println("Opción no válida. Intenta nuevamente.");
                break;
        }
        return tasques;
    }

    public static Integer asociarClientProjecte(Clientas01Service clientService, Integer idProjecte) {

        Integer idClient = null;

        while (idClient == null) {
            System.out.println("Opciones:");
            System.out.println("1. Buscar un cliente existente");
            System.out.println("2. Crear un nuevo cliente asociado a este proyecto");
            System.out.print("Elige una opción para el cliente (1/2): ");
            String optionClient = tcl.nextLine();

            if (optionClient.equals("1")) {
                System.out.println("Lista de clientes disponibles:");
                MethodsMainClient.listClientsBasic(clientService);
                System.out.print("ID del Cliente a asociar: ");
                String input = tcl.nextLine();
                try {
                    idClient = Integer.parseInt(input);
                    if (clientService.findClientById(idClient) == null) {
                        System.out.println("El cliente con ID " + idClient + " no existe.");
                        idClient = null;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("ID de cliente inválido. Inténtalo nuevamente.");
                }
            } else if (optionClient.equals("2")) {
                // Crear un nuevo cliente (reemplazar con lógica de creación)
                System.out.println("Creando un nuevo cliente...");
                Integer idFactura = 1; //esto nada
                idClient = MethodsMainClient.agregarClienteBasic(clientService, idFactura);
            } else {
                System.out.println("Opción no válida. Inténtalo nuevamente.");
            }
        }
        return idClient;
    }

    //PROYECTO ASOCIADO A UNA TAREA SOBRECARGADO
    //SOBRECARGADO CON ID TASCA
    public static Integer agregarProjecteComplete(Projecteas01Service projecteService, Clientas01Service clientService, Facturaas01Service facturaService, Operariresponsableas01Service operariService, Tascaas01Service tascaService,
             Integer idTasca) {

        System.out.println("Introduce los datos del proyecto:");

        // Descripción del proyecto
        System.out.print("Descripción del proyecto: ");
        String descripcio = tcl.nextLine();

        // Selección del estado del proyecto
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

        // Fecha de finalización si está finalizado
        Date fechaFinalitzacio = null;
        if ("Finalitzat".equalsIgnoreCase(estat)) {
            System.out.println("Si este proyecto está finalizado, ingresa la fecha de finalización:");
            while (fechaFinalitzacio == null) {
                System.out.print("Fecha finalización (Formato: yyyy-MM-dd, No puede estar vacía): ");
                String input = tcl.nextLine();
                try {
                    fechaFinalitzacio = new SimpleDateFormat("yyyy-MM-dd").parse(input);
                } catch (ParseException e) {
                    System.out.println("Fecha inválida. Asegúrate de usar el formato yyyy-MM-dd.");
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
            System.out.println("No se encontró la tarea con el ID especificado.");
        }
        newProjecte.setIdClient(clientService.findClientById(idClient));

        try {
            projecteService.createProject(newProjecte);
            System.out.println("Proyecto agregado exitosamente.");
            return newProjecte.getIdProjecte();
        } catch (Exception e) {
            System.out.println("Error al agregar el proyecto: " + e.getMessage());
        }
        return null; // null si falla
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

            System.out.println("- > PROYECTO Nº [" + proyecto.getIdProjecte() + "]" + " " + proyecto.getDescripcio() + " | Estado: " + proyecto.getEstat()
                    + "| fecha finalización = " + (proyecto.getFechaFinalitzacio() != null ? proyecto.getFechaFinalitzacio() : "No finalizado"));

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
            System.out.println("---> " + "[" + proyecto.getIdProjecte() + "]" + " " + proyecto.getDescripcio() + " | Estado: " + proyecto.getEstat()
                    + "| fecha finalización = " + (proyecto.getFechaFinalitzacio() != null ? proyecto.getFechaFinalitzacio() : "No finalizado"));
        }
        System.out.println("|--------------------------------------|");
    }

    public static void listProjecteByState(Projecteas01Service projecteService, String state) {
        System.out.println("|--------------------------------------|");
        // Mostrar proyectos por estado
        for (Projecteas01 proyectoFinalizado : projecteService.findAllByState(state)) {
            System.out.println("---> " + "[" + proyectoFinalizado.getIdProjecte() + "]" + " " + proyectoFinalizado.getDescripcio() + " | Estado: " + proyectoFinalizado.getEstat()
                    + "| fecha finalización = " + (proyectoFinalizado.getFechaFinalitzacio() != null ? proyectoFinalizado.getFechaFinalitzacio() : "No finalizado"));
        }
        System.out.println("|--------------------------------------|");
    }

    //*****************************************************************//
    //********************** DELETE ************************************//
    //*****************************************************************//
    //*************** DELETE PROJECTE  *****************************//
    // PROCESAR FECHA DE FINALIZACION - CONDICIONAL
    protected static void eliminarProjectes(Projecteas01Service projecteService) {
        System.out.println("**** Aviso ****");
        System.out.println("No podremos eliminar ningún PROYECTO que tenga alguna TAREA que ya haya generado la FACTURA asociada.");
        System.out.println("**Se podrá eliminar un proyecto; \n"
                + "-> SOLO cuando esté en estado FINALIZADO y ADEMÁS todas sus tareas (FINALIZADAS) hayan sido facturadas HACE MÁS DE 5 AÑOS");

        MainApp.esperarIntro();

        System.out.println("\n¿Cómo deseas eliminar los proyectos?");
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
                    // Se itera cada proyecto, se revisa si puede ser eliminado y se elimina
                    List<Projecteas01> proyectosAEliminar = new ArrayList<>();
                    for (Projecteas01 proyecto : projecteService.findAllProjects()) {
                        String info = projecteService.deleteProjectVerification(proyecto);
                        if (info == null) {
                            proyectosAEliminar.add(proyecto);
                        } else {
                            System.out.println("No se puede eliminar el proyecto con ID " + proyecto.getIdProjecte() + ": " + info);
                        }
                    }

                    if (!proyectosAEliminar.isEmpty()) {
                        // Mostrar el listado de proyectos a eliminar
                        System.out.println("Los siguientes proyectos serán eliminados:");
                        for (Projecteas01 proyecto : proyectosAEliminar) {
                            System.out.println(proyecto.getDescripcio() + " (ID: " + proyecto.getIdProjecte() + ")");
                        }

                        System.out.print("¿Estás seguro de que deseas eliminar estos proyectos? (S/N): ");
                        String confirmacion = tcl.nextLine();
                        if (confirmacion.equalsIgnoreCase("S")) {
                            // Eliminar los proyectos
                            for (Projecteas01 proyecto : proyectosAEliminar) {
                                try {
                                    projecteService.deleteProject(proyecto);
                                    System.out.println("Proyecto con ID " + proyecto.getIdProjecte() + " ha sido eliminado.");
                                } catch (Exception e) {
                                    System.out.println("Error al eliminar el proyecto con ID " + proyecto.getIdProjecte() + ": " + e.getMessage());
                                }
                            }
                        } else {
                            System.out.println("La eliminación ha sido cancelada.");
                        }
                    } else {
                        System.out.println("No hay proyectos para eliminar.");
                    }
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
                    String info = projecteService.deleteProjectVerification(proyecto);
                    if (info == null) {
                        try {
                            projecteService.deleteProject(proyecto);
                            System.out.println("Proyecto con ID " + idProyecto + " ha sido eliminado.");
                        } catch (Exception e) {
                            System.out.println("Error al eliminar el proyecto con ID " + idProyecto + ": " + e.getMessage());
                        }
                    } else {
                        System.out.println("No se puede eliminar el proyecto con ID " + idProyecto + ": " + info);
                    }
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
