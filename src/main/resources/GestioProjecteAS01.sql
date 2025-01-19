CREATE DATABASE IF NOT EXISTS GestioProjectesAS01;
USE GestioProjectesAS01;

-- 1. Client: IdClient (PK, AI), Nom (NN), Cognoms, NIF (NN)
CREATE TABLE IF NOT EXISTS ClientAS01 (
    idClient INT PRIMARY KEY AUTO_INCREMENT, 
    nom VARCHAR(45) NOT NULL,
    cognom VARCHAR(45),
    nif VARCHAR(9) NOT NULL 
);

-- 2. Projecte: IdProjecte (PK , AI), descripció, Estat (No iniciat, en procés, finalitzat), IdClient
CREATE TABLE IF NOT EXISTS ProjecteAS01 (
    idProjecte INT PRIMARY KEY AUTO_INCREMENT, 
    descripcio VARCHAR(45),
    estat ENUM('No iniciat', 'En procés', 'Finalitzat'),
    fechaFinalitzacio DATE DEFAULT NULL,
    idClient INT,
	FOREIGN KEY (idClient) REFERENCES ClientAS01(idClient)
);

/*4. OperariResponsable: IdOperariTasca (PK, AI), nom, cognom, NIFOperari (NN), IdTasca (NN), observacions*/
CREATE TABLE IF NOT EXISTS OperariResponsableAS01 (
    idOperariTasca INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(45),
    cognom VARCHAR(45),
    nifOperari VARCHAR(9) NOT NULL,
    observacions VARCHAR(255)
);

-- 3. Tasca: IdTasca (PK, AI), descripció, EstatTasca (No Iniciada, en procés, finalitzada), IdProjecte
CREATE TABLE IF NOT EXISTS TascaAS01 (
    idTasca INT PRIMARY KEY AUTO_INCREMENT, 
    descripcio VARCHAR(45),
    estat ENUM('No iniciat', 'En procés', 'Finalitzat'),
    idProjecte INT,
    idOperari INT NOT NULL,
	FOREIGN KEY (idProjecte) REFERENCES ProjecteAS01(idProjecte),
    FOREIGN KEY (idOperari) REFERENCES OperariResponsableAS01(idOperariTasca)
);

/*5. Factura: IdFactura (PK AI) , IdTasca (NN), IdClient (NN), data (NN), importTotal, observacions */
CREATE TABLE IF NOT EXISTS FacturaAS01 (
    idFactura INT PRIMARY KEY AUTO_INCREMENT,
    idTasca INT NOT NULL,
    idClient INT NOT NULL,
    data DATE NOT NULL,
    importTotal DOUBLE NOT NULL,
    observacions VARCHAR(255),
    FOREIGN KEY (idTasca) REFERENCES TascaAS01(idTasca),
    FOREIGN KEY (idClient) REFERENCES ClientAS01(idClient)
);

-- Com a mínim caldrà afegir les següents dades (completar en cas de camps claus o no nuls)

-- CLIENT: Registrat a tu mateixa com a client

INSERT INTO ClientAS01 (nom, cognom, nif)
VALUES ('Ángela', 'Sáez', '73665652X');

-- PROJECTE: Registra 4 projectes que portaran com a nom “Construcció de la casa de XXXXXXXX “ on XXXXXXX seran 4 companys de classe.
INSERT INTO ProjecteAS01 (descripcio, estat, fechaFinalitzacio, idClient)
VALUES 
    ('Construcció de la casa de Raquel', 'No iniciat', NULL, 1),
    ('Construcció de la casa de Marcos', 'En procés', NULL, 1),
    ('Construcció de la casa de Ruben', 'Finalitzat', '2020-12-31', 1),
    ('Construcció de la casa de David', 'No iniciat', NULL, 1);

-- OPERARI RESPONSABLE: Registra a Pepe Goteras com a responsable de les dues tasques. NIF 12345678A.
INSERT INTO OperariResponsableAS01 (nifOperari, nom, cognom, observacions)
VALUES 
    ('12345678A', 'Pepe', 'Goteras', 'Tasques inicials completades per Pepe Goteras');
    
-- TASCA: Inventa 2 tasques per 2 dels projectes anteriors i els altres 2 els deixes sense tasques. Finalitza 1 tasca per cadascun del 2 projectes amb tasques.
INSERT INTO TascaAS01 (descripcio, estat, idProjecte, idOperari)
VALUES 
    ('Preparar el terreny', 'Finalitzat', 2, 1),
    ('Construir els fonaments', 'En procés', 2, 1),
    ('Dissenyar plànols', 'Finalitzat', 3, 1),
    ('Organitzar subministraments', 'En procés', 4, 1);

-- FACTURA:Emet una factura amb data 31/12/2017 i una altra amb data 30/11/2024.
INSERT INTO FacturaAS01 (idTasca, idClient, data, importTotal, observacions)
VALUES 
    (1, 1, '2017-12-31', 200, 'Pago de antemano' ),
    (3, 1, '2024-11-30', 256, 'Pago por plazos');
    

DROP TABLE IF EXISTS FacturaAS01;
DROP TABLE IF EXISTS TascaAS01;
DROP TABLE IF EXISTS OperariResponsableAS01;
DROP TABLE IF EXISTS ProjecteAS01;
DROP TABLE IF EXISTS ClientAS01;