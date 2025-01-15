/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Test;

import Entity.Clientas01;
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
import java.util.List;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

/**
 *
 * @author angel
 */
public class MehtodsMainEntities {

    //*************** CONTADOR DE LAS 5 ENTIDADES *****************************
    protected static void showContador(Clientas01Service clientService, Facturaas01Service facturaService,
            Operariresponsableas01Service operariResponsableService, Projecteas01Service projecteService, Tascaas01Service tascaService) {
        String border = "**************************************";
        String title = "          Entidades Contador          ";

        System.out.println(border);
        System.out.println(title);
        System.out.println(border);
        System.out.printf("* %-35s *\n", "Nº Clientes: " + clientService.getAllClients().size());
        System.out.printf("* %-35s *\n", "Nº Facturas: " + facturaService.getAllFacturas().size());
        System.out.printf("* %-35s *\n", "Nº Operarios responsables: " + operariResponsableService.getAllOperaris().size());
        System.out.printf("* %-35s *\n", "Nº Proyectos: " + projecteService.getAllProjects().size());
        System.out.printf("* %-35s *\n", "Nº Tareas: " + tascaService.getAllTasques().size());
        System.out.println(border);
    }

    //*************** VACIAR TODAS LAS TABLAS  *****************************
    protected static void truncateAllTables(Clientas01Service clientService,
            Facturaas01Service facturaService,
            Operariresponsableas01Service operariResponsableService,
            Projecteas01Service projecteService,
            Tascaas01Service tascaService) {
        System.out.println("Vaciando todas las tablas...");
        try {
            //clientService.truncateTable();
            //facturaService.truncateTable();
            //operariResponsableService.truncateTable();
            //projecteService.truncateTable();
            //tascaService.truncateTable();
            System.out.println("Todas las tablas han sido vaciadas.");
        } catch (Exception e) {
            log.error("Error al vaciar las tablas: ", e);
        }
    }
    
    // Método auxiliar para validar el formato del NIF
    protected static boolean esNifValido(String nif) {
        if (nif == null || nif.length() != 9) {
            return false;
        }

        String numeros = nif.substring(0, 8);
        char letra = nif.charAt(8);

        if (!numeros.matches("\\d+")) {
            return false;
        }

        String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
        int resto = Integer.parseInt(numeros) % 23;
        char letraCorrecta = letras.charAt(resto);

        return letra == letraCorrecta;
    }

}
