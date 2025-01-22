/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 *
 * @author angel
 */
public class ResetDatabase {

    // Constantes para los nombres de las tablas
    private static final String TABLE_CLIENT = "ClientAS01";
    private static final String TABLE_PROJECT = "ProjecteAS01";
    private static final String TABLE_OPERATOR = "OperariResponsableAS01";
    private static final String TABLE_TASK = "TascaAS01";
    private static final String TABLE_INVOICE = "FacturaAS01";

    public static void dropAllTablesSQL(EntityManager em) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.createNativeQuery("DROP TABLE IF EXISTS " + TABLE_INVOICE).executeUpdate();
            em.createNativeQuery("DROP TABLE IF EXISTS " + TABLE_TASK).executeUpdate();
            em.createNativeQuery("DROP TABLE IF EXISTS " + TABLE_OPERATOR).executeUpdate();
            em.createNativeQuery("DROP TABLE IF EXISTS " + TABLE_PROJECT).executeUpdate();
            em.createNativeQuery("DROP TABLE IF EXISTS " + TABLE_CLIENT).executeUpdate();
            tx.commit();
            System.out.println("Todas las tablas han sido eliminadas con DROP.");
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            System.err.println("Error al eliminar las tablas: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void crearTablasSQLde0(EntityManager em) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            // Crear tabla ClientAS01
            em.createNativeQuery(
                    "CREATE TABLE IF NOT EXISTS " + TABLE_CLIENT + " ("
                    + "idClient INT PRIMARY KEY AUTO_INCREMENT, "
                    + "nom VARCHAR(45) NOT NULL, "
                    + "cognom VARCHAR(45), "
                    + "nif VARCHAR(9) NOT NULL"
                    + ")"
            ).executeUpdate();

            // Crear tabla ProjecteAS01
            em.createNativeQuery(
                    "CREATE TABLE IF NOT EXISTS " + TABLE_PROJECT + " ("
                    + "idProjecte INT PRIMARY KEY AUTO_INCREMENT, "
                    + "descripcio VARCHAR(45), "
                    + "estat ENUM('No iniciat', 'En procés', 'Finalitzat'), "
                    + "fechaFinalitzacio DATE DEFAULT NULL, "
                    + "idClient INT, "
                    + "FOREIGN KEY (idClient) REFERENCES " + TABLE_CLIENT + "(idClient) "
                    + "ON DELETE SET NULL"
                    + ")"
            ).executeUpdate();

            // Crear tabla OperariResponsableAS01
            em.createNativeQuery(
                    "CREATE TABLE IF NOT EXISTS " + TABLE_OPERATOR + " ("
                    + "idOperariTasca INT PRIMARY KEY AUTO_INCREMENT, "
                    + "nom VARCHAR(45), "
                    + "cognom VARCHAR(45), "
                    + "nifOperari VARCHAR(9) NOT NULL, "
                    + "observacions VARCHAR(255)"
                    + ")"
            ).executeUpdate();

            // Crear tabla TascaAS01
            em.createNativeQuery(
                    "CREATE TABLE IF NOT EXISTS " + TABLE_TASK + " ("
                    + "idTasca INT PRIMARY KEY AUTO_INCREMENT, "
                    + "descripcio VARCHAR(45), "
                    + "estat ENUM('No iniciat', 'En procés', 'Finalitzat'), "
                    + "idProjecte INT, "
                    + "idOperari INT, "
                    + "FOREIGN KEY (idProjecte) REFERENCES " + TABLE_PROJECT + "(idProjecte), "
                    + "FOREIGN KEY (idOperari) REFERENCES " + TABLE_OPERATOR + "(idOperariTasca)"
                    + ")"
            ).executeUpdate();

            // Crear tabla FacturaAS01
            em.createNativeQuery(
                    "CREATE TABLE IF NOT EXISTS " + TABLE_INVOICE + " ("
                    + "idFactura INT PRIMARY KEY AUTO_INCREMENT, "
                    + "idTasca INT, "
                    + "idClient INT, "
                    + "data DATE NOT NULL, "
                    + "importTotal DOUBLE NOT NULL, "
                    + "observacions VARCHAR(255), "
                    + "FOREIGN KEY (idTasca) REFERENCES " + TABLE_TASK + "(idTasca), "
                    + "FOREIGN KEY (idClient) REFERENCES " + TABLE_CLIENT + "(idClient)"
                    + ")"
            ).executeUpdate();

            tx.commit();
            System.out.println("Todas las tablas han sido creadas.");
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            System.err.println("Error al crear las tablas: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void insertarDadesPredeterminats(EntityManager em) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            // Insertar datos en ClientAS01
            em.createNativeQuery(
                    "INSERT INTO ClientAS01 (nom, cognom, nif) VALUES "
                    + "('Ángela', 'Sáez', '73665652X')"
            ).executeUpdate();

            // Insertar datos en ProjecteAS01 con fechaFinalitzacio
            em.createNativeQuery(
                    "INSERT INTO ProjecteAS01 (descripcio, estat, fechaFinalitzacio, idClient) VALUES "
                    + "('Construcció de la casa de Raquel', 'No iniciat', NULL, 1), "
                    + "('Construcció de la casa de Marcos', 'En procés', NULL, 1), "
                    + "('Construcció de la casa de Ruben', 'Finalitzat', '2020-12-31', 1), "
                    + "('Construcció de la casa de David', 'No iniciat', NULL, 1)"
            ).executeUpdate();

            // Insertar datos en OperariResponsableAS01
            em.createNativeQuery(
                    "INSERT INTO OperariResponsableAS01 (nifOperari, nom, cognom, observacions) VALUES "
                    + "('12345678A', 'Pepe', 'Goteras', 'Tasques inicials completades per Pepe Goteras')"
            ).executeUpdate();

            // Insertar datos en TascaAS01
            em.createNativeQuery(
                    "INSERT INTO TascaAS01 (descripcio, estat, idProjecte, idOperari) VALUES "
                    + "('Preparar el terreny', 'Finalitzat', 2, 1), "
                    + "('Construir els fonaments', 'En procés', 2, 1), "
                    + "('Dissenyar plànols', 'Finalitzat', 3, 1), "
                    + "('Organitzar subministraments', 'En procés', 4, 1)"
            ).executeUpdate();

            // Insertar datos en FacturaAS01
            em.createNativeQuery(
                    "INSERT INTO FacturaAS01 (idTasca, idClient, data, importTotal, observacions) VALUES "
                    + "(1, 1, '2017-12-31', 200, 'Pago de antemano'), "
                    + "(3, 1, '2024-11-30', 256, 'Pago por plazos')"
            ).executeUpdate();

            tx.commit();
            System.out.println("Datos predeterminados han sido insertados.");
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            System.err.println("Error al insertar datos predeterminados: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
