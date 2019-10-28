//******************************************************************************
// File:   zonaRepository.java
// Author: Andrés Gavín Murillo 716358
// Author: Eduardo Gimeno Soriano 721615
// Author: Sergio Álvarez Peiro 740241
// Date:   Octubre 2019
// Coms:   Sistemas de información - Práctica 2
//******************************************************************************

package ecoz;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.*;

import de.micromata.opengis.kml.v_2_2_0.Kml;

public class zonaRepository extends DBConnect {
	private final String SQL_CREAR = "INSERT INTO ecoz.zona(nombre, fichero, co2, o3, no2, pm10) VALUES(?,?,?,?,?,?)";
	
	private final String SQL_UPDATE_CO2 = "UPDATE ecoz.zona SET co2=? WHERE nombre=?";
	private final String SQL_UPDATE_O3 = "UPDATE ecoz.zona SET o3=? WHERE nombre=?";
	private final String SQL_UPDATE_NO2 = "UPDATE ecoz.zona SET no2=? WHERE nombre=?";
	private final String SQL_UPDATE_PM10 = "UPDATE ecoz.zona SET pm10=? WHERE nombre=?";
	private final String SQL_UPDATE_FILE = "UPDATE ecoz.zona SET fichero=? WHERE nombre=?";
	
	private final String SQL_SELECT_CO2 = "SELECT co2 FROM ecoz.zona WHERE nombre=?";
	private final String SQL_SELECT_O3 = "SELECT o3 FROM ecoz.zona WHERE nombre=?";
	private final String SQL_SELECT_NO2 = "SELECT no2 FROM ecoz.zona WHERE nombre=?";
	private final String SQL_SELECT_PM10 = "SELECT pm10 FROM ecoz.zona WHERE nombre=?";
	private final String SQL_SELECT_FILE = "SELECT fichero FROM ecoz.zona WHERE nombre=?";
	
	private final String SQL_DELETE = "DELETE FROM ecoz.zona WHERE nombre=?";
	
	public String crearZona(String nombre, Kml fichero, Float co2, Float o3, Float no2, Float pm10) {
		if (con == null) {
			return "ERROR";
		}
		
		try (PreparedStatement pstmt = con.prepareStatement(SQL_CREAR, Statement.RETURN_GENERATED_KEYS)) {
			pstmt.setString(1, nombre);
		
			try {
				fichero.marshal(new File("tmp.txt"));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			File file = new File("tmp.txt");
			InputStream is = null;
			try {
				is = (InputStream) new FileInputStream(file);
			} catch (FileNotFoundException e2) {
				e2.printStackTrace();
			}
			pstmt.setBinaryStream(2, is, (int) file.length());
			file.delete();
			
			pstmt.setFloat(3, co2);
			pstmt.setFloat(4, o3);
			pstmt.setFloat(5, no2);
			pstmt.setFloat(6, pm10);
			
			pstmt.executeUpdate();
			return "Zona creada con nombre " + nombre;
		}
		catch (SQLException ex) {
			return ex.getMessage();
		}
	}
}