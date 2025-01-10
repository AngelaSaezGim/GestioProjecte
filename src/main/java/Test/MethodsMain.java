/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Test;

import static Test.MainApp.log;
import java.util.List;

import Service.Clientas01Service;
import Service.Facturaas01Service;
import Service.Operariresponsableas01Service;
import Service.Projecteas01Service;
import Service.Tascaas01Service;

import Entity.Clientas01;
import Entity.Facturaas01;
import Entity.Operariresponsableas01;
import Entity.Projecteas01;
import Entity.Tascaas01;

/**
 *
 * @author angsaegim
 */
public class MethodsMain {
    
    protected static void showAllClients(Clientas01Service clientService) {
        log.info("=== MOSTRANDO CLIENTES ===");
        List<Clientas01> clientes = clientService.getAllClients();
        clientes.forEach(cliente -> log.info(cliente));
        log.info("\n");
    }


    protected static void showAllFacturas(Facturaas01Service facturaService) {
        log.info("=== MOSTRANDO FACTURAS ===");
        List<Facturaas01> facturas = facturaService.getAllFacturas();
        facturas.forEach(factura -> log.info(factura));
        log.info("\n");
    }
    
    protected static void showAllOperariosResponsables(Operariresponsableas01Service operariResponsableService) {
        log.info("=== MOSTRANDO OPERARIOS RESPONSABLES ===");
        List<Operariresponsableas01> operarios = operariResponsableService.getAllOperaris();
        operarios.forEach(operario -> log.info(operario));
        log.info("\n");
    }

    protected static void showAllProyectos(Projecteas01Service projecteService) {
        log.info("=== MOSTRANDO PROYECTOS ===");
        List<Projecteas01> proyectos = projecteService.getAllProjects();
        proyectos.forEach(proyecto -> log.info(proyecto));
        log.info("\n");
    }

    protected static void showAllTareas(Tascaas01Service tascaService) {
        log.info("=== MOSTRANDO TAREAS ===");
        List<Tascaas01> tareas = tascaService.getAllTasques();
        tareas.forEach(tarea -> log.info(tarea));
        log.info("\n"); 
    }
    
}