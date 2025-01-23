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
        String title = "          Comptador d'Entitats        ";

        System.out.println(border);
        System.out.println(title);
        System.out.println(border);
        System.out.printf("* %-35s *\n", "Nº Clients: " + clientService.findAllClients().size());
        System.out.printf("* %-35s *\n", "Nº Factures: " + facturaService.findAllFacturas().size());
        System.out.printf("* %-35s *\n", "Nº Operaris responsables: " + operariResponsableService.findAllOperaris().size());
        System.out.printf("* %-35s *\n", "Nº Projectes: " + projecteService.findAllProjects().size());
        System.out.printf("* %-35s *\n", "Nº Tasques: " + tascaService.findAllTasques().size());
        System.out.println(border);
    }

    //*************** VACIAR TODAS LAS TABLAS  *****************************
    protected static void truncateAllTables(Clientas01Service clientService,
            Facturaas01Service facturaService,
            Operariresponsableas01Service operariResponsableService,
            Projecteas01Service projecteService,
            Tascaas01Service tascaService) {
        System.out.println("Buidant totes les taules...");
        try {
            facturaService.deleteTable();
            tascaService.deleteTable();
            operariResponsableService.deleteTable();
            projecteService.deleteTable();
            clientService.deleteTable();
            System.out.println("Totes les taules han estat buidades.");
        } catch (Exception e) {
            log.error("Error en buidar les taules: ", e);
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
