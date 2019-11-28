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

INSERT INTO ecoz.zona (nombre, fichero, co2, o3, no2, pm10) VALUES ('Zona1', '<?xml version="1.0" encoding="UTF-8"?>
<kml xmlns="http://www.opengis.net/kml/2.2">
  <Document>
    <name>Zona 1</name>
    <Style id="poly-E65100-2000-77-nodesc-normal">
      <LineStyle>
        <color>ff0051e6</color>
        <width>2</width>
      </LineStyle>
      <PolyStyle>
        <color>4d0051e6</color>
        <fill>1</fill>
        <outline>1</outline>
      </PolyStyle>
      <BalloonStyle>
        <text><![CDATA[<h3>$[name]</h3>]]></text>
      </BalloonStyle>
    </Style>
    <Style id="poly-E65100-2000-77-nodesc-highlight">
      <LineStyle>
        <color>ff0051e6</color>
        <width>3</width>
      </LineStyle>
      <PolyStyle>
        <color>4d0051e6</color>
        <fill>1</fill>
        <outline>1</outline>
      </PolyStyle>
      <BalloonStyle>
        <text><![CDATA[<h3>$[name]</h3>]]></text>
      </BalloonStyle>
    </Style>
    <StyleMap id="poly-E65100-2000-77-nodesc">
      <Pair>
        <key>normal</key>
        <styleUrl>#poly-E65100-2000-77-nodesc-normal</styleUrl>
      </Pair>
      <Pair>
        <key>highlight</key>
        <styleUrl>#poly-E65100-2000-77-nodesc-highlight</styleUrl>
      </Pair>
    </StyleMap>
    <Placemark>
      <name>Zona 1</name>
      <styleUrl>#poly-E65100-2000-77-nodesc</styleUrl>
      <Polygon>
        <outerBoundaryIs>
          <LinearRing>
            <tessellate>1</tessellate>
            <coordinates>
              -0.906842,41.697803,0
              -0.907356,41.676138,0
              -0.875256,41.676138,0
              -0.875256,41.697803,0
              -0.906842,41.697803,0
            </coordinates>
          </LinearRing>
        </outerBoundaryIs>
      </Polygon>
    </Placemark>
  </Document>
</kml>
', 0.27, 10.69, 43.75, 19.12);
INSERT INTO ecoz.zona (nombre, fichero, co2, o3, no2, pm10) VALUES ('Zona2', '<?xml version="1.0" encoding="UTF-8"?>
<kml xmlns="http://www.opengis.net/kml/2.2">
  <Document>
    <name>Zona 2</name>
    <Style id="poly-558B2F-2000-77-nodesc-normal">
      <LineStyle>
        <color>ff2f8b55</color>
        <width>2</width>
      </LineStyle>
      <PolyStyle>
        <color>4d2f8b55</color>
        <fill>1</fill>
        <outline>1</outline>
      </PolyStyle>
      <BalloonStyle>
        <text><![CDATA[<h3>$[name]</h3>]]></text>
      </BalloonStyle>
    </Style>
    <Style id="poly-558B2F-2000-77-nodesc-highlight">
      <LineStyle>
        <color>ff2f8b55</color>
        <width>3</width>
      </LineStyle>
      <PolyStyle>
        <color>4d2f8b55</color>
        <fill>1</fill>
        <outline>1</outline>
      </PolyStyle>
      <BalloonStyle>
        <text><![CDATA[<h3>$[name]</h3>]]></text>
      </BalloonStyle>
    </Style>
    <StyleMap id="poly-558B2F-2000-77-nodesc">
      <Pair>
        <key>normal</key>
        <styleUrl>#poly-558B2F-2000-77-nodesc-normal</styleUrl>
      </Pair>
      <Pair>
        <key>highlight</key>
        <styleUrl>#poly-558B2F-2000-77-nodesc-highlight</styleUrl>
      </Pair>
    </StyleMap>
    <Placemark>
      <name>Zona 2</name>
      <styleUrl>#poly-558B2F-2000-77-nodesc</styleUrl>
      <Polygon>
        <outerBoundaryIs>
          <LinearRing>
            <tessellate>1</tessellate>
            <coordinates>
              -0.875256,41.697803,0
              -0.875256,41.676138,0
              -0.847962,41.676329,0
              -0.84779,41.697609,0
              -0.875256,41.697803,0
            </coordinates>
          </LinearRing>
        </outerBoundaryIs>
      </Polygon>
    </Placemark>
  </Document>
</kml>
', 0.37, 11.48, 23.5, 12.07);
INSERT INTO ecoz.zona (nombre, fichero, co2, o3, no2, pm10) VALUES ('Zona3', '<?xml version="1.0" encoding="UTF-8"?>
<kml xmlns="http://www.opengis.net/kml/2.2">
  <Document>
    <name>Zona 3</name>
    <Style id="poly-FFD600-2000-77-nodesc-normal">
      <LineStyle>
        <color>ff00d6ff</color>
        <width>2</width>
      </LineStyle>
      <PolyStyle>
        <color>4d00d6ff</color>
        <fill>1</fill>
        <outline>1</outline>
      </PolyStyle>
      <BalloonStyle>
        <text><![CDATA[<h3>$[name]</h3>]]></text>
      </BalloonStyle>
    </Style>
    <Style id="poly-FFD600-2000-77-nodesc-highlight">
      <LineStyle>
        <color>ff00d6ff</color>
        <width>3</width>
      </LineStyle>
      <PolyStyle>
        <color>4d00d6ff</color>
        <fill>1</fill>
        <outline>1</outline>
      </PolyStyle>
      <BalloonStyle>
        <text><![CDATA[<h3>$[name]</h3>]]></text>
      </BalloonStyle>
    </Style>
    <StyleMap id="poly-FFD600-2000-77-nodesc">
      <Pair>
        <key>normal</key>
        <styleUrl>#poly-FFD600-2000-77-nodesc-normal</styleUrl>
      </Pair>
      <Pair>
        <key>highlight</key>
        <styleUrl>#poly-FFD600-2000-77-nodesc-highlight</styleUrl>
      </Pair>
    </StyleMap>
    <Placemark>
      <name>Zona 3</name>
      <styleUrl>#poly-FFD600-2000-77-nodesc</styleUrl>
      <Polygon>
        <outerBoundaryIs>
          <LinearRing>
            <tessellate>1</tessellate>
            <coordinates>
              -0.907356,41.676138,0
              -0.907356,41.652861,0
              -0.876114,41.652861,0
              -0.875256,41.676138,0
              -0.907356,41.676138,0
            </coordinates>
          </LinearRing>
        </outerBoundaryIs>
      </Polygon>
    </Placemark>
  </Document>
</kml>
', 0.28, 2.15, 30.43, 12.92);
INSERT INTO ecoz.zona (nombre, fichero, co2, o3, no2, pm10) VALUES ('Zona4', '<?xml version="1.0" encoding="UTF-8"?>
<kml xmlns="http://www.opengis.net/kml/2.2">
  <Document>
    <name>Zona 4</name>
    <Style id="poly-C2185B-2000-77-nodesc-normal">
      <LineStyle>
        <color>ff5b18c2</color>
        <width>2</width>
      </LineStyle>
      <PolyStyle>
        <color>4d5b18c2</color>
        <fill>1</fill>
        <outline>1</outline>
      </PolyStyle>
      <BalloonStyle>
        <text><![CDATA[<h3>$[name]</h3>]]></text>
      </BalloonStyle>
    </Style>
    <Style id="poly-C2185B-2000-77-nodesc-highlight">
      <LineStyle>
        <color>ff5b18c2</color>
        <width>3</width>
      </LineStyle>
      <PolyStyle>
        <color>4d5b18c2</color>
        <fill>1</fill>
        <outline>1</outline>
      </PolyStyle>
      <BalloonStyle>
        <text><![CDATA[<h3>$[name]</h3>]]></text>
      </BalloonStyle>
    </Style>
    <StyleMap id="poly-C2185B-2000-77-nodesc">
      <Pair>
        <key>normal</key>
        <styleUrl>#poly-C2185B-2000-77-nodesc-normal</styleUrl>
      </Pair>
      <Pair>
        <key>highlight</key>
        <styleUrl>#poly-C2185B-2000-77-nodesc-highlight</styleUrl>
      </Pair>
    </StyleMap>
    <Placemark>
      <name>Zona 4</name>
      <styleUrl>#poly-C2185B-2000-77-nodesc</styleUrl>
      <Polygon>
        <outerBoundaryIs>
          <LinearRing>
            <tessellate>1</tessellate>
            <coordinates>
              -0.907356,41.676138,0
              -0.941345,41.676073,0
              -0.9416883,41.653246,0
              -0.907356,41.652861,0
              -0.907356,41.676138,0
            </coordinates>
          </LinearRing>
        </outerBoundaryIs>
      </Polygon>
    </Placemark>
  </Document>
</kml>
', 0.29, 3.28, 35.55, 10.96);
INSERT INTO ecoz.zona (nombre, fichero, co2, o3, no2, pm10) VALUES ('Zona5', '<?xml version="1.0" encoding="UTF-8"?>
<kml xmlns="http://www.opengis.net/kml/2.2">
  <Document>
    <name>Zona 5</name>
    <Style id="poly-795548-2000-77-nodesc-normal">
      <LineStyle>
        <color>ff485579</color>
        <width>2</width>
      </LineStyle>
      <PolyStyle>
        <color>4d485579</color>
        <fill>1</fill>
        <outline>1</outline>
      </PolyStyle>
      <BalloonStyle>
        <text><![CDATA[<h3>$[name]</h3>]]></text>
      </BalloonStyle>
    </Style>
    <Style id="poly-795548-2000-77-nodesc-highlight">
      <LineStyle>
        <color>ff485579</color>
        <width>3</width>
      </LineStyle>
      <PolyStyle>
        <color>4d485579</color>
        <fill>1</fill>
        <outline>1</outline>
      </PolyStyle>
      <BalloonStyle>
        <text><![CDATA[<h3>$[name]</h3>]]></text>
      </BalloonStyle>
    </Style>
    <StyleMap id="poly-795548-2000-77-nodesc">
      <Pair>
        <key>normal</key>
        <styleUrl>#poly-795548-2000-77-nodesc-normal</styleUrl>
      </Pair>
      <Pair>
        <key>highlight</key>
        <styleUrl>#poly-795548-2000-77-nodesc-highlight</styleUrl>
      </Pair>
    </StyleMap>
    <Placemark>
      <name>Zona 5</name>
      <styleUrl>#poly-795548-2000-77-nodesc</styleUrl>
      <Polygon>
        <outerBoundaryIs>
          <LinearRing>
            <tessellate>1</tessellate>
            <coordinates>
              -0.941431,41.66652,0
              -0.96821,41.666392,0
              -0.96821,41.644459,0
              -0.941775,41.644459,0
              -0.941431,41.66652,0
            </coordinates>
          </LinearRing>
        </outerBoundaryIs>
      </Polygon>
    </Placemark>
  </Document>
</kml>
', 0.24, 4.38, 31.5, 11.1);
INSERT INTO ecoz.zona (nombre, fichero, co2, o3, no2, pm10) VALUES ('Zona6', '<?xml version="1.0" encoding="UTF-8"?>
<kml xmlns="http://www.opengis.net/kml/2.2">
  <Document>
    <name>Zona 6</name>
    <Style id="poly-9C27B0-2000-77-nodesc-normal">
      <LineStyle>
        <color>ffb0279c</color>
        <width>2</width>
      </LineStyle>
      <PolyStyle>
        <color>4db0279c</color>
        <fill>1</fill>
        <outline>1</outline>
      </PolyStyle>
      <BalloonStyle>
        <text><![CDATA[<h3>$[name]</h3>]]></text>
      </BalloonStyle>
    </Style>
    <Style id="poly-9C27B0-2000-77-nodesc-highlight">
      <LineStyle>
        <color>ffb0279c</color>
        <width>3</width>
      </LineStyle>
      <PolyStyle>
        <color>4db0279c</color>
        <fill>1</fill>
        <outline>1</outline>
      </PolyStyle>
      <BalloonStyle>
        <text><![CDATA[<h3>$[name]</h3>]]></text>
      </BalloonStyle>
    </Style>
    <StyleMap id="poly-9C27B0-2000-77-nodesc">
      <Pair>
        <key>normal</key>
        <styleUrl>#poly-9C27B0-2000-77-nodesc-normal</styleUrl>
      </Pair>
      <Pair>
        <key>highlight</key>
        <styleUrl>#poly-9C27B0-2000-77-nodesc-highlight</styleUrl>
      </Pair>
    </StyleMap>
    <Placemark>
      <name>Zona 6</name>
      <styleUrl>#poly-9C27B0-2000-77-nodesc</styleUrl>
      <Polygon>
        <outerBoundaryIs>
          <LinearRing>
            <tessellate>1</tessellate>
            <coordinates>
              -0.9416883,41.653246,0
              -0.94186,41.620467,0
              -0.9085154,41.6202738,0
              -0.9079896,41.6284687,0
              -0.9078929,41.6365353,0
              -0.907356,41.652861,0
              -0.9416883,41.653246,0
            </coordinates>
          </LinearRing>
        </outerBoundaryIs>
      </Polygon>
    </Placemark>
  </Document>
</kml>
', 0.11, 17.45, 41.77, 10);
INSERT INTO ecoz.zona (nombre, fichero, co2, o3, no2, pm10) VALUES ('Zona7', '<?xml version="1.0" encoding="UTF-8"?>
<kml xmlns="http://www.opengis.net/kml/2.2">
  <Document>
    <name>Zona 7</name>
    <Style id="poly-0288D1-2000-77-nodesc-normal">
      <LineStyle>
        <color>ffd18802</color>
        <width>2</width>
      </LineStyle>
      <PolyStyle>
        <color>4dd18802</color>
        <fill>1</fill>
        <outline>1</outline>
      </PolyStyle>
      <BalloonStyle>
        <text><![CDATA[<h3>$[name]</h3>]]></text>
      </BalloonStyle>
    </Style>
    <Style id="poly-0288D1-2000-77-nodesc-highlight">
      <LineStyle>
        <color>ffd18802</color>
        <width>3</width>
      </LineStyle>
      <PolyStyle>
        <color>4dd18802</color>
        <fill>1</fill>
        <outline>1</outline>
      </PolyStyle>
      <BalloonStyle>
        <text><![CDATA[<h3>$[name]</h3>]]></text>
      </BalloonStyle>
    </Style>
    <StyleMap id="poly-0288D1-2000-77-nodesc">
      <Pair>
        <key>normal</key>
        <styleUrl>#poly-0288D1-2000-77-nodesc-normal</styleUrl>
      </Pair>
      <Pair>
        <key>highlight</key>
        <styleUrl>#poly-0288D1-2000-77-nodesc-highlight</styleUrl>
      </Pair>
    </StyleMap>
    <Placemark>
      <name>Zona 7</name>
      <styleUrl>#poly-0288D1-2000-77-nodesc</styleUrl>
      <Polygon>
        <outerBoundaryIs>
          <LinearRing>
            <tessellate>1</tessellate>
            <coordinates>
              -0.875256,41.676138,0
              -0.876114,41.652861,0
              -0.814687,41.652543,0
              -0.814344,41.676396,0
              -0.875256,41.676138,0
            </coordinates>
          </LinearRing>
        </outerBoundaryIs>
      </Polygon>
    </Placemark>
  </Document>
</kml>
', 0.3, 3.9, 28.82, 14.73);
INSERT INTO ecoz.zona (nombre, fichero, co2, o3, no2, pm10) VALUES ('Zona8', '<?xml version="1.0" encoding="UTF-8"?>
<kml xmlns="http://www.opengis.net/kml/2.2">
  <Document>
    <name>Zona 8</name>
    <Style id="poly-A52714-2000-77-nodesc-normal">
      <LineStyle>
        <color>ff1427a5</color>
        <width>2</width>
      </LineStyle>
      <PolyStyle>
        <color>4d1427a5</color>
        <fill>1</fill>
        <outline>1</outline>
      </PolyStyle>
      <BalloonStyle>
        <text><![CDATA[<h3>$[name]</h3>]]></text>
      </BalloonStyle>
    </Style>
    <Style id="poly-A52714-2000-77-nodesc-highlight">
      <LineStyle>
        <color>ff1427a5</color>
        <width>3</width>
      </LineStyle>
      <PolyStyle>
        <color>4d1427a5</color>
        <fill>1</fill>
        <outline>1</outline>
      </PolyStyle>
      <BalloonStyle>
        <text><![CDATA[<h3>$[name]</h3>]]></text>
      </BalloonStyle>
    </Style>
    <StyleMap id="poly-A52714-2000-77-nodesc">
      <Pair>
        <key>normal</key>
        <styleUrl>#poly-A52714-2000-77-nodesc-normal</styleUrl>
      </Pair>
      <Pair>
        <key>highlight</key>
        <styleUrl>#poly-A52714-2000-77-nodesc-highlight</styleUrl>
      </Pair>
    </StyleMap>
    <Placemark>
      <name>Zona 8</name>
      <styleUrl>#poly-A52714-2000-77-nodesc</styleUrl>
      <Polygon>
        <outerBoundaryIs>
          <LinearRing>
            <tessellate>1</tessellate>
            <coordinates>
              -0.876114,41.652861,0
              -0.907356,41.652861,0
              -0.9077132,41.6427445,0
              -0.9078704,41.6376862,0
              -0.9079274,41.6350127,0
              -0.9078558,41.6326279,0
              -0.8764632,41.632387,0
              -0.876114,41.652861,0
            </coordinates>
          </LinearRing>
        </outerBoundaryIs>
      </Polygon>
    </Placemark>
  </Document>
</kml>
', 0.47, 4.55, 51.15, 25.56);
INSERT INTO ecoz.zona (nombre, fichero, co2, o3, no2, pm10) VALUES ('Zona9', '<?xml version="1.0" encoding="UTF-8"?>
<kml xmlns="http://www.opengis.net/kml/2.2">
  <Document>
    <name>Zona 9</name>
    <Style id="poly-0F9D58-2000-77-nodesc-normal">
      <LineStyle>
        <color>ff589d0f</color>
        <width>2</width>
      </LineStyle>
      <PolyStyle>
        <color>4d589d0f</color>
        <fill>1</fill>
        <outline>1</outline>
      </PolyStyle>
      <BalloonStyle>
        <text><![CDATA[<h3>$[name]</h3>]]></text>
      </BalloonStyle>
    </Style>
    <Style id="poly-0F9D58-2000-77-nodesc-highlight">
      <LineStyle>
        <color>ff589d0f</color>
        <width>3</width>
      </LineStyle>
      <PolyStyle>
        <color>4d589d0f</color>
        <fill>1</fill>
        <outline>1</outline>
      </PolyStyle>
      <BalloonStyle>
        <text><![CDATA[<h3>$[name]</h3>]]></text>
      </BalloonStyle>
    </Style>
    <StyleMap id="poly-0F9D58-2000-77-nodesc">
      <Pair>
        <key>normal</key>
        <styleUrl>#poly-0F9D58-2000-77-nodesc-normal</styleUrl>
      </Pair>
      <Pair>
        <key>highlight</key>
        <styleUrl>#poly-0F9D58-2000-77-nodesc-highlight</styleUrl>
      </Pair>
    </StyleMap>
    <Placemark>
      <name>Zona 9</name>
      <styleUrl>#poly-0F9D58-2000-77-nodesc</styleUrl>
      <Polygon>
        <outerBoundaryIs>
          <LinearRing>
            <tessellate>1</tessellate>
            <coordinates>
              -0.876313,41.632403,0
              -0.9078558,41.6326279,0
              -0.9081778,41.625682,0
              -0.9084568,41.6187039,0
              -0.908929,41.604812,0
              -0.877343,41.604427,0
              -0.8766135,41.618415,0
              -0.876313,41.632403,0
            </coordinates>
          </LinearRing>
        </outerBoundaryIs>
      </Polygon>
    </Placemark>
  </Document>
</kml>
', 0.42, 6.11, 18.18, 9.55);
INSERT INTO ecoz.zona (nombre, fichero, co2, o3, no2, pm10) VALUES ('Zona10', '<?xml version="1.0" encoding="UTF-8"?>
<kml xmlns="http://www.opengis.net/kml/2.2">
  <Document>
    <name>Zona 10</name>
    <Style id="poly-000000-2000-77-nodesc-normal">
      <LineStyle>
        <color>ff000000</color>
        <width>2</width>
      </LineStyle>
      <PolyStyle>
        <color>4d000000</color>
        <fill>1</fill>
        <outline>1</outline>
      </PolyStyle>
      <BalloonStyle>
        <text><![CDATA[<h3>$[name]</h3>]]></text>
      </BalloonStyle>
    </Style>
    <Style id="poly-000000-2000-77-nodesc-highlight">
      <LineStyle>
        <color>ff000000</color>
        <width>3</width>
      </LineStyle>
      <PolyStyle>
        <color>4d000000</color>
        <fill>1</fill>
        <outline>1</outline>
      </PolyStyle>
      <BalloonStyle>
        <text><![CDATA[<h3>$[name]</h3>]]></text>
      </BalloonStyle>
    </Style>
    <StyleMap id="poly-000000-2000-77-nodesc">
      <Pair>
        <key>normal</key>
        <styleUrl>#poly-000000-2000-77-nodesc-normal</styleUrl>
      </Pair>
      <Pair>
        <key>highlight</key>
        <styleUrl>#poly-000000-2000-77-nodesc-highlight</styleUrl>
      </Pair>
    </StyleMap>
    <Placemark>
      <name>Zona 10</name>
      <styleUrl>#poly-000000-2000-77-nodesc</styleUrl>
      <Polygon>
        <outerBoundaryIs>
          <LinearRing>
            <tessellate>1</tessellate>
            <coordinates>
              -0.8767636,41.6152883,0
              -0.8467246,41.6150313,0
              -0.8456517,41.652719,0
              -0.876114,41.652861,0
              -0.8764925,41.6329759,0
              -0.8767636,41.6152883,0
            </coordinates>
          </LinearRing>
        </outerBoundaryIs>
      </Polygon>
    </Placemark>
  </Document>
</kml>
', 0.18, 13.13, 39.68, 8.1);
