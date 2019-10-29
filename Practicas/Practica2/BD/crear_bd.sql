CREATE SCHEMA IF NOT EXISTS ecoz;

CREATE TABLE IF NOT EXISTS ecoz.Usuario
(
   Email             CHAR(20)     PRIMARY KEY,
   Nombre            CHAR(20),
   Apellidos         CHAR(20),
   Contrasena        CHAR(10)     NOT NULL
);

CREATE TABLE IF NOT EXISTS ecoz.Ruta
(
   Id              INTEGER     PRIMARY KEY,
   Fichero         BYTEA          NOT NULL,
   Usuario_Email   CHAR(20)       NOT NULL,
   FOREIGN KEY (Usuario_Email) REFERENCES ecoz.Usuario(Email) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS ecoz.Zona
(
   Nombre    CHAR(20)     PRIMARY KEY,
   Fichero   BYTEA           NOT NULL,
   CO2       FLOAT           NOT NULL,
   O3        FLOAT           NOT NULL,
   NO2       FLOAT           NOT NULL,
   PM10      FLOAT           NOT NULL
);

CREATE TABLE IF NOT EXISTS ecoz.Discurre
(
   Ruta_Id        INTEGER    NOT NULL,
   Zona_Nombre   CHAR(20)    NOT NULL,
   PRIMARY KEY (Ruta_Id, Zona_Nombre),
   FOREIGN KEY (Ruta_Id) REFERENCES ecoz.Ruta(Id) ON DELETE CASCADE,
   FOREIGN KEY (Zona_Nombre) REFERENCES ecoz.Zona(Nombre) ON DELETE CASCADE
);

