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
    idClient INT,
	FOREIGN KEY (idClient) REFERENCES ClientAS01(idClient)
);

-- 3. Tasca: IdTasca (PK, AI), descripció, EstatTasca (No Iniciada, en procés, finalitzada), IdProjecte
CREATE TABLE IF NOT EXISTS TascaAS01 (
    idTasca INT PRIMARY KEY AUTO_INCREMENT, 
    descripcio VARCHAR(45),
    estat ENUM('No iniciat', 'En procés', 'Finalitzat'),
    idProjecte INT,
	FOREIGN KEY (idProjecte) REFERENCES ProjecteAS01(idProjecte)
);

/*4. OperariResponsable: IdOperariTasca (PK, AI), NIFOperari (NN), IdTasca (NN), observacions*/
CREATE TABLE IF NOT EXISTS OperariResponsableAS01 (
    idOperariTasca INT PRIMARY KEY AUTO_INCREMENT,
    nifOperari VARCHAR(9) NOT NULL,
    idTasca INT NOT NULL,
    observacions VARCHAR(255),
    FOREIGN KEY (idTasca) REFERENCES TascaAS01(idTasca)
);

/*5. Factura: IdFactura (PK AI) , IdTasca (NN), IdClient (NN), data (NN)*/
CREATE TABLE IF NOT EXISTS FacturaAS01 (
    idFactura INT PRIMARY KEY AUTO_INCREMENT,
    idTasca INT NOT NULL,
    idClient INT NOT NULL,
    data DATE NOT NULL,
    FOREIGN KEY (idTasca) REFERENCES TascaAS01(idTasca),
    FOREIGN KEY (idClient) REFERENCES ClientAS01(idClient)
);