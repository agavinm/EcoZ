CREATE TABLE Usuario
(
   Email             VARCHAR(20)     PRIMARY KEY,
   Nombre            VARCHAR(20),
   Apellidos         VARCHAR(20),
   FechaNacimiento   DATE,
   Contraseña        VARCHAR(10)     NOT NULL
);

CREATE TABLE Ruta
(
   Id              NUMBER 	   PRIMARY KEY,
   Fichero         BYTEA           NOT NULL,
   Usuario_Email   VARCHAR(20)     NOT NULL,
   FOREIGN KEY (Usuario_Email) REFERENCES Usuario(Email)
);

CREATE TABLE Zona
(
   Nombre    VARCHAR(20)     PRIMARY KEY,
   Fichero   BYTEA           NOT NULL,
   CO2       FLOAT           NOT NULL,
   O3        FLOAT           NOT NULL,
   NO2       FLOAT           NOT NULL,
   PM10      FLOAT           NOT NULL
);

CREATE TABLE Discurre
(
   Ruta_Id       NUMBER,
   Zona_Nombre   VARCHAR(20),
   PRIMARY KEY (Ruta_Id,Zona_Nombre),
   FOREIGN KEY (Ruta_Id) REFERENCES Ruta(Id),
   FOREIGN KEY (Zona_Nombre) REFERENCES Zona(Nombre)
);

