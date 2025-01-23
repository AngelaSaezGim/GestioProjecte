-- Eliminar tablas si existen
DROP TABLE IF EXISTS FacturaAS01;
DROP TABLE IF EXISTS TascaAS01;
DROP TABLE IF EXISTS OperariResponsableAS01;
DROP TABLE IF EXISTS ProjecteAS01;
DROP TABLE IF EXISTS ClientAS01;

-- Crear tabla ClientAS01
CREATE TABLE IF NOT EXISTS ClientAS01 (
    idClient INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(45) NOT NULL,
    cognom VARCHAR(45),
    nif VARCHAR(9) NOT NULL
);

-- Crear tabla ProjecteAS01
CREATE TABLE IF NOT EXISTS ProjecteAS01 (
    idProjecte INT PRIMARY KEY AUTO_INCREMENT,
    descripcio VARCHAR(45),
    estat ENUM('No iniciat', 'En procés', 'Finalitzat'),
    fechaFinalitzacio DATE DEFAULT NULL,
    idClient INT,
    FOREIGN KEY (idClient) REFERENCES ClientAS01(idClient) ON DELETE SET NULL
);

-- Crear tabla OperariResponsableAS01
CREATE TABLE IF NOT EXISTS OperariResponsableAS01 (
    idOperariTasca INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(45),
    cognom VARCHAR(45),
    nifOperari VARCHAR(9) NOT NULL,
    observacions VARCHAR(255)
);

-- Crear tabla TascaAS01
CREATE TABLE IF NOT EXISTS TascaAS01 (
    idTasca INT PRIMARY KEY AUTO_INCREMENT,
    descripcio VARCHAR(45),
    estat ENUM('No iniciat', 'En procés', 'Finalitzat'),
    idProjecte INT,
    idOperari INT,
    FOREIGN KEY (idProjecte) REFERENCES ProjecteAS01(idProjecte),
    FOREIGN KEY (idOperari) REFERENCES OperariResponsableAS01(idOperariTasca)
);

-- Crear tabla FacturaAS01
CREATE TABLE IF NOT EXISTS FacturaAS01 (
    idFactura INT PRIMARY KEY AUTO_INCREMENT,
    idTasca INT,
    idClient INT,
    data DATE NOT NULL,
    importTotal DOUBLE NOT NULL,
    observacions VARCHAR(255),
    FOREIGN KEY (idTasca) REFERENCES TascaAS01(idTasca),
    FOREIGN KEY (idClient) REFERENCES ClientAS01(idClient)
);

-- Insertar datos predeterminados en ClientAS01
INSERT INTO ClientAS01 (nom, cognom, nif) VALUES
('Ángela', 'Sáez', '73665652X');

-- Insertar datos predeterminados en ProjecteAS01
INSERT INTO ProjecteAS01 (descripcio, estat, fechaFinalitzacio, idClient) VALUES
('Construcció de la casa de Raquel', 'No iniciat', NULL, 1),
('Construcció de la casa de Marcos', 'En procés', NULL, 1),
('Construcció de la casa de Ruben', 'Finalitzat', '2020-12-31', 1),
('Construcció de la casa de David', 'No iniciat', NULL, 1);

-- Insertar datos predeterminados en OperariResponsableAS01
INSERT INTO OperariResponsableAS01 (nifOperari, nom, cognom, observacions) VALUES
('12345678A', 'Pepe', 'Goteras', 'Tasques inicials completades per Pepe Goteras');

-- Insertar datos predeterminados en TascaAS01
INSERT INTO TascaAS01 (descripcio, estat, idProjecte, idOperari) VALUES
('Preparar el terreny', 'Finalitzat', 2, 1),
('Construir els fonaments', 'En procés', 2, 1),
('Dissenyar plànols', 'Finalitzat', 3, 1),
('Organitzar subministraments', 'En procés', 4, 1);

-- Insertar datos predeterminados en FacturaAS01
INSERT INTO FacturaAS01 (idTasca, idClient, data, importTotal, observacions) VALUES
(1, 1, '2017-12-31', 200, 'Pago de antemano'),
(3, 1, '2024-11-30', 256, 'Pago por plazos');