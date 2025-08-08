CREATE DATABASE bdusuarios;
USE bdusuarios;

CREATE TABLE sexo (
 id INT AUTO_INCREMENT PRIMARY KEY,
 sexo VARCHAR(50));

INSERT INTO sexo (sexo) VALUES ('Masculino'), ('Femenino');

CREATE TABLE usuarios (
 id INT AUTO_INCREMENT PRIMARY KEY,
 nombre VARCHAR(100),
 apellidos VARCHAR(100),
 fksexo INT,
FOREIGN KEY (fksexo) REFERENCES sexo(id) ON DELETE CASCADE ON UPDATE CASCADE);
