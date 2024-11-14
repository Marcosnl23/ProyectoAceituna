DROP DATABASE IF EXISTS aceituna_db;

-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS aceituna_db;
USE aceituna_db;

-- Tabla Trabajador
CREATE TABLE Trabajador (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    edad INT NOT NULL,
    puesto VARCHAR(50),
    salario DECIMAL(10, 2)
);

-- Tabla Cuadrilla
CREATE TABLE Cuadrilla (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    supervisor_id INT,
    FOREIGN KEY (supervisor_id) REFERENCES Trabajador(id) ON DELETE SET NULL
);

-- Tabla Olivar
CREATE TABLE Olivar (
    id INT AUTO_INCREMENT PRIMARY KEY,
    ubicacion VARCHAR(100) NOT NULL,
    hectareas DECIMAL(5, 2) NOT NULL,
    produccionAnual DECIMAL(10, 2) NOT NULL
);

-- Tabla Almazara
CREATE TABLE Almazara (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    ubicacion VARCHAR(100) NOT NULL,
    capacidad DECIMAL(10, 2) NOT NULL
);

-- Tabla intermedia Cuadrilla_Olivar (relación muchos a muchos entre Cuadrilla y Olivar)
CREATE TABLE Cuadrilla_Olivar (
    cuadrilla_id INT,
    olivar_id INT,
    PRIMARY KEY (cuadrilla_id, olivar_id),
    FOREIGN KEY (cuadrilla_id) REFERENCES Cuadrilla(id) ON DELETE CASCADE,
    FOREIGN KEY (olivar_id) REFERENCES Olivar(id) ON DELETE CASCADE
);

-- Tabla intermedia Cuadrilla_Trabajador (relación muchos a muchos entre Cuadrilla y Trabajador)
CREATE TABLE Cuadrilla_Trabajador (
    cuadrilla_id INT,
    trabajador_id INT,
    PRIMARY KEY (cuadrilla_id, trabajador_id),
    FOREIGN KEY (cuadrilla_id) REFERENCES Cuadrilla(id) ON DELETE CASCADE,
    FOREIGN KEY (trabajador_id) REFERENCES Trabajador(id) ON DELETE CASCADE
);

-- Tabla Producción (registra la cantidad recolectada de una cuadrilla en un olivar en una fecha determinada)
CREATE TABLE Produccion (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cuadrilla_id INT NOT NULL,
    olivar_id INT NOT NULL,
    almazara_id INT NOT NULL,
    fecha DATE NOT NULL,
    cantidadRecolectada DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (cuadrilla_id) REFERENCES Cuadrilla(id) ON DELETE CASCADE,
    FOREIGN KEY (olivar_id) REFERENCES Olivar(id) ON DELETE CASCADE,
    FOREIGN KEY (almazara_id) REFERENCES Almazara(id) ON DELETE CASCADE
);

-- select
select * from almazara;
select * from cuadrilla;
select * from cuadrilla_olivar;
select * from cuadrilla_trabajador;

select * from olivar;
select * from produccion;
select * from trabajador;

-- insert
-- Inserción de datos en la tabla Trabajador
-- Inserción de datos en la tabla Trabajador
INSERT INTO Trabajador (nombre, edad, puesto, salario) VALUES 
('Juan Pérez', 34, 'Recolector', 1500.00),
('María García', 28, 'Supervisor', 2000.00),
('Luis Martínez', 45, 'Conductor', 1800.00),
('Ana Sánchez', 40, 'Supervisor', 2200.00),
('Pedro Gómez', 50, 'Supervisor', 2400.00);  -- Cambiado de 'Recolector' a 'Supervisor' para ser el supervisor de Cuadrilla C

-- Inserción de datos en la tabla Cuadrilla
INSERT INTO Cuadrilla (nombre, supervisor_id) VALUES
('Cuadrilla A', 2),  -- Supervisada por María García
('Cuadrilla B', 4),  -- Supervisada por Ana Sánchez
('Cuadrilla C', 5);  -- Supervisada por Pedro Gómez

-- Inserción de datos en la tabla Olivar
INSERT INTO Olivar (ubicacion, hectareas, produccionAnual) VALUES
('Olivar Norte', 15.5, 5000.00),
('Olivar Sur', 20.0, 7000.00),
('Olivar Este', 12.75, 4300.00),
('Olivar Oeste', 18.2, 6200.00);

-- Inserción de datos en la tabla Almazara
INSERT INTO Almazara (nombre, ubicacion, capacidad) VALUES
('Almazara La Esperanza', 'Calle Olivares, 45', 15000.00),
('Almazara El Molino', 'Avenida de la Aceituna, 10', 12000.00),
('Almazara Verde', 'Camino de los Olivos, 5', 18000.00);

-- Inserción de datos en la tabla intermedia Cuadrilla_Olivar
INSERT INTO Cuadrilla_Olivar (cuadrilla_id, olivar_id) VALUES
(1, 1),
(1, 2),
(2, 3),
(2, 4),
(3, 1),
(3, 4);

-- Inserción de datos en la tabla intermedia Cuadrilla_Trabajador
INSERT INTO Cuadrilla_Trabajador (cuadrilla_id, trabajador_id) VALUES
(1, 1),  
(1, 3),  
(2, 2),  
(2, 4),  
(3, 1),  
(3, 5);  

-- Inserción de datos en la tabla Producción
INSERT INTO Produccion (cuadrilla_id, olivar_id, almazara_id, fecha, cantidadRecolectada) VALUES
(1, 1, 1, '2023-10-01', 150.00),
(1, 2, 1, '2023-10-02', 200.00),
(2, 3, 2, '2023-10-03', 180.00),
(2, 4, 3, '2023-10-04', 250.00),
(3, 1, 2, '2023-10-05', 300.00),
(3, 4, 3, '2023-10-06', 220.00);


