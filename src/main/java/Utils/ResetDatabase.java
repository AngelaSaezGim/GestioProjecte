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

    public static void dropAllTablesSQL(EntityManager em) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.createNativeQuery("DROP TABLE IF EXISTS FacturaAS01").executeUpdate();
            em.createNativeQuery("DROP TABLE IF EXISTS TascaAS01").executeUpdate();
            em.createNativeQuery("DROP TABLE IF EXISTS OperariResponsableAS01").executeUpdate();
            em.createNativeQuery("DROP TABLE IF EXISTS ProjecteAS01").executeUpdate();
            em.createNativeQuery("DROP TABLE IF EXISTS ClientAS01").executeUpdate();
            tx.commit();
            System.out.println("Todas las tablas han sido eliminadas con DROP");
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }

    public static void crearTablasSQLde0(EntityManager em) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            em.createNativeQuery(
                    "CREATE TABLE IF NOT EXISTS ClientAS01 ("
                    + "idClient INT PRIMARY KEY AUTO_INCREMENT, "
                    + "nom VARCHAR(45) NOT NULL, "
                    + "cognom VARCHAR(45), "
                    + "nif VARCHAR(9) NOT NULL"
                    + ")"
            ).executeUpdate();

            em.createNativeQuery(
                    "CREATE TABLE IF NOT EXISTS ProjecteAS01 ("
                    + "idProjecte INT PRIMARY KEY AUTO_INCREMENT, "
                    + "descripcio VARCHAR(45), "
                    + "estat ENUM('No iniciat', 'En procés', 'Finalitzat'), "
                    + "idClient INT, "
                    + "FOREIGN KEY (idClient) REFERENCES ClientAS01(idClient)"
                    + ")"
            ).executeUpdate();

            em.createNativeQuery(
                    "CREATE TABLE IF NOT EXISTS OperariResponsableAS01 ("
                    + "idOperariTasca INT PRIMARY KEY AUTO_INCREMENT, "
                    + "nom VARCHAR(45), "
                    + "cognom VARCHAR(45), "
                    + "nifOperari VARCHAR(9) NOT NULL, "
                    + "observacions VARCHAR(255)"
                    + ")"
            ).executeUpdate();

            em.createNativeQuery(
                    "CREATE TABLE IF NOT EXISTS TascaAS01 ("
                    + "idTasca INT PRIMARY KEY AUTO_INCREMENT, "
                    + "descripcio VARCHAR(45), "
                    + "estat ENUM('No iniciat', 'En procés', 'Finalitzat'), "
                    + "idProjecte INT, "
                    + "idOperari INT NOT NULL, "
                    + "FOREIGN KEY (idProjecte) REFERENCES ProjecteAS01(idProjecte), "
                    + "FOREIGN KEY (idOperari) REFERENCES OperariResponsableAS01(idOperariTasca)"
                    + ")"
            ).executeUpdate();

            em.createNativeQuery(
                    "CREATE TABLE IF NOT EXISTS FacturaAS01 ("
                    + "idFactura INT PRIMARY KEY AUTO_INCREMENT, "
                    + "idTasca INT NOT NULL, "
                    + "idClient INT NOT NULL, "
                    + "data DATE NOT NULL, "
                    + "importTotal DOUBLE NOT NULL, "
                    + "observacions VARCHAR(255), "
                    + "FOREIGN KEY (idTasca) REFERENCES TascaAS01(idTasca), "
                    + "FOREIGN KEY (idClient) REFERENCES ClientAS01(idClient)"
                    + ")"
            ).executeUpdate();

            tx.commit();
            System.out.println("Todas las tablas han sido creadas.");
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }

    public static void insertarDadesPredeterminats(EntityManager em) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            // Insertar datos en ClientAS01
            em.createNativeQuery(
                    "INSERT INTO ClientAS01 (nom, cognom, nif) "
                    + "VALUES ('Ángela', 'Sáez', '73665652X')"
            ).executeUpdate();

            // Insertar datos en ProjecteAS01
            em.createNativeQuery(
                    "INSERT INTO ProjecteAS01 (descripcio, estat, idClient) "
                    + "VALUES "
                    + "('Construcció de la casa de Raquel', 'No iniciat', 1), "
                    + "('Construcció de la casa de Marcos', 'En procés', 1), "
                    + "('Construcció de la casa de Ruben', 'Finalitzat', 1), "
                    + "('Construcció de la casa de David', 'No iniciat', 1)"
            ).executeUpdate();

            // Insertar datos en OperariResponsableAS01
            em.createNativeQuery(
                    "INSERT INTO OperariResponsableAS01 (nifOperari, nom, cognom, observacions) "
                    + "VALUES "
                    + "('12345678A', 'Pepe', 'Goteras', 'Tasques inicials completades per Pepe Goteras')"
            ).executeUpdate();

            // Insertar datos en TascaAS01
            em.createNativeQuery(
                    "INSERT INTO TascaAS01 (descripcio, estat, idProjecte, idOperari) "
                    + "VALUES "
                    + "('Preparar el terreny', 'Finalitzat', 2, 1), "
                    + "('Construir els fonaments', 'En procés', 2, 1), "
                    + "('Dissenyar plànols', 'Finalitzat', 3, 1), "
                    + "('Organitzar subministraments', 'En procés', 3, 1)"
            ).executeUpdate();

            // Insertar datos en FacturaAS01
            em.createNativeQuery(
                    "INSERT INTO FacturaAS01 (idTasca, idClient, data, importTotal, observacions) "
                    + "VALUES "
                    + "(1, 1, '2017-12-31', 200, 'Pago de antemano'), "
                    + "(3, 1, '2024-11-30', 256, 'Pago por plazos')"
            ).executeUpdate();

            tx.commit();
            System.out.println("Datos predeterminados han sido insertados.");
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }
}
