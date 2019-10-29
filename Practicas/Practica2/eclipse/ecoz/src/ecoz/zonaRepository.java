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
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.AbstractMap.SimpleEntry;

import org.apache.commons.io.FileUtils;

import de.micromata.opengis.kml.v_2_2_0.Kml;

public class zonaRepository extends DBConnect {
	
	private final String EX_ZONA_NO_CREADA = "Error: No se ha podido crear la zona";
	
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
	
	private final String SQL_EXISTS_PK = "SELECT COUNT(*) FROM ecoz.zona WHERE nombre=?";
	
	/**
	 * 
	 * La función existeEntrada comprueba si existe una entrada en la tabla zona de la
	 * base de datos con la clave primaria indicada
	 * 
	 * @param nombre
	 * @return true si existe, false en caso contrario
	 */
	private Boolean existeEntrada(String nombre) throws Exception {
		try (PreparedStatement pstmt = con.prepareStatement(SQL_EXISTS_PK, Statement.RETURN_GENERATED_KEYS)) {
			pstmt.setString(1, nombre);
			
			ResultSet rs = pstmt.executeQuery();
			// Extraer el resultado
			rs.next();
			if (rs.getInt(1) == 1) {
				return true;
			}
			else {
				return false;
			}
		} catch (SQLException e) {
			throw new Exception(EX_CONEXION); 
		}
	}
	
	/**
	 * 
	 * La función crearZona añade en la tabla zona de la base de datos una nueva entrada
	 * con los datos introducidos como parámetros
	 * 
	 * @param nombre
	 * @param fichero
	 * @param co2
	 * @param o3
	 * @param no2
	 * @param pm10
	 */
	public void crearZona(String nombre, Kml fichero, Float co2, Float o3, Float no2, Float pm10) throws Exception {
		// Comprobar si existe una entrada con la clave primaria indicada
		if (!existeEntrada(nombre)) {
			try (PreparedStatement pstmt = con.prepareStatement(SQL_CREAR, Statement.RETURN_GENERATED_KEYS)) {
				// Agregar el nombre de la zona a la consulta
				pstmt.setString(1, nombre);
			
				// Agregar el fichero kml de la zona a la consulta
				// Primero hay que volcar el contenido del fichero kml a un txt
				// Debido a la implementación de la librería externa usada, se genera un txt en la carpeta del proyecto
				int x = (int) ((Math.random()*((100-999)+1))+100);
				String fileName = "tmp-" + x + ".txt";
				try {
					fichero.marshal(new File(fileName));
				} 
				catch (FileNotFoundException e) {
					throw new Exception(EX_RUTA);
				}
				// Se vuelve a abrir el txt generado
				File file = new File(fileName);
				InputStream is = null;
				try {
					is = (InputStream) new FileInputStream(file);
				} 
				catch (FileNotFoundException e2) {
					throw new Exception(EX_RUTA);
				}
				pstmt.setBinaryStream(2, is, (int) file.length());
				// Se borra el txt generado
				file.delete();
				
				// Agregar a la consulta los distintos niveles de gases
				pstmt.setFloat(3, co2);
				pstmt.setFloat(4, o3);
				pstmt.setFloat(5, no2);
				pstmt.setFloat(6, pm10);
				
				pstmt.executeUpdate();
			}
			catch (SQLException ex) {
				throw new Exception(EX_CONEXION); 
			}
		}
		else {
			throw new Exception(EX_ZONA_NO_CREADA);
		}
	}
	
	/**
	 * 
	 * La función actualizarCO2 actualiza el valor de CO2 de la entrada de la tabla zona de la base
	 * de datos cuya clave primaria es el nombre de la zona indicado
	 * 
	 * @param nombre
	 * @param co2
	 * @return 0 si se ha podido actualizar, 1 si falla la conexión con la base de datos,
	 * 2 si se produce una excepción, 3 si no existe una entrada en la base de datos con
	 * la clave indicada
	 */
	public void actualizarCO2(String nombre, Float co2) {
		
		
		try (PreparedStatement pstmt = con.prepareStatement(SQL_UPDATE_CO2, Statement.RETURN_GENERATED_KEYS)) {
			// Agregar el nombre de la zona a la consulta
			pstmt.setString(1, nombre);
			
			// Agregar a la consulta nivel de CO2
			pstmt.setFloat(2, co2);
			
			pstmt.executeUpdate();
			return 0;
		}
		catch (SQLException ex) {
			return 2;
		}
	}
	
	/**
	 * 
	 * La función actualizarO3 actualiza el valor de O3 de la entrada de la tabla zona de la base
	 * de datos cuya clave primaria es el nombre de la zona indicado
	 * 
	 * @param nombre
	 * @param o3
	 * @return 0 si se ha podido actualizar, 1 si falla la conexión con la base de datos,
	 * 2 si se produce una excepción
	 */
	public Integer actualizarO3(String nombre, Float o3) {
		// Comprobar si existe una entrada con la clave primaria indicada
		int cd = existeEntrada(nombre);
		if (cd != 0) {
			return cd;
		}
		
		try (PreparedStatement pstmt = con.prepareStatement(SQL_UPDATE_O3, Statement.RETURN_GENERATED_KEYS)) {
			// Agregar el nombre de la zona a la consulta
			pstmt.setString(1, nombre);
			
			// Agregar a la consulta nivel de O3
			pstmt.setFloat(2, o3);
			
			pstmt.executeUpdate();
			return 0;
		}
		catch (SQLException ex) {
			return 2;
		}
	}
	
	/**
	 * 
	 * La función actualizarNO2 actualiza el valor de NO2 de la entrada de la tabla zona de la base
	 * de datos cuya clave primaria es el nombre de la zona indicado
	 * 
	 * @param nombre
	 * @param no2
	 * @return 0 si se ha podido actualizar, 1 si falla la conexión con la base de datos,
	 * 2 si se produce una excepción
	 */
	public Integer actualizarNO2(String nombre, Float no2) {
		if (con == null) {
			return 1;
		}
		
		try (PreparedStatement pstmt = con.prepareStatement(SQL_UPDATE_NO2, Statement.RETURN_GENERATED_KEYS)) {
			// Agregar el nombre de la zona a la consulta
			pstmt.setString(1, nombre);
			
			// Agregar a la consulta nivel de NO2
			pstmt.setFloat(2, no2);
			
			pstmt.executeUpdate();
			return 0;
		}
		catch (SQLException ex) {
			return 2;
		}
	}
	
	/**
	 * 
	 * La función actualizarPM10 actualiza el valor de PM10 de la entrada de la tabla zona de la base
	 * de datos cuya clave primaria es el nombre de la zona indicado
	 * 
	 * @param nombre
	 * @param pm10
	 * @return 0 si se ha podido actualizar, 1 si falla la conexión con la base de datos,
	 * 2 si se produce una excepción
	 */
	public Integer actualizarPM10(String nombre, Float pm10) {
		if (con == null) {
			return 1;
		}
		
		try (PreparedStatement pstmt = con.prepareStatement(SQL_UPDATE_PM10, Statement.RETURN_GENERATED_KEYS)) {
			// Agregar el nombre de la zona a la consulta
			pstmt.setString(1, nombre);
			
			// Agregar a la consulta nivel de PM10
			pstmt.setFloat(2, pm10);
			
			pstmt.executeUpdate();
			return 0;
		}
		catch (SQLException ex) {
			return 2;
		}
	}
	
	/**
	 * 
	 * La función actualizarFichero actualiza el fichero kml de la entrada de la tabla zona de la base
	 * de datos cuya clave primaria es el nombre de la zona indicado
	 * 
	 * @param nombre
	 * @param fichero
	 * @return 0 si se ha podido actualizar, 1 si falla la conexión con la base de datos,
	 * 2 si se produce una excepción
	 */
	public Integer actualizarFichero(String nombre, Kml fichero) {
		if (con == null) {
			return 1;
		}
		
		try (PreparedStatement pstmt = con.prepareStatement(SQL_UPDATE_FILE, Statement.RETURN_GENERATED_KEYS)) {
			// Agregar el nombre de la zona a la consulta
			pstmt.setString(1, nombre);
			
			// Agregar el fichero kml de la zona a la consulta
			// Primero hay que volcar el contenido del fichero kml a un txt
			// Debido a la implementación de la librería externa usada, se genera un txt en la carpeta del proyecto
			int x = (int) ((Math.random()*((100-999)+1))+100);
			String fileName = "tmp-" + x + ".txt";
			try {
				fichero.marshal(new File(fileName));
			} catch (FileNotFoundException e) {
				return 2;
			}
			// Se vuelve a abrir el txt generado
			File file = new File(fileName);
			InputStream is = null;
			try {
				is = (InputStream) new FileInputStream(file);
			} catch (FileNotFoundException e2) {
				return 2;
			}
			pstmt.setBinaryStream(2, is, (int) file.length());
			// Se borra el txt generado
			file.delete();
			
			pstmt.executeUpdate();
			return 0;
		}
		catch (SQLException ex) {
			return 2;
		}
	}
	
	/**
	 * 
	 * La función seleccionarCO2 selecciona el valor de CO2 de la entrada de la tabla zona
	 * de la base de datos cuya clave primaria es el nombre de la zona indicado
	 * 
	 * @param nombre
	 * @return El nivel de CO2 para la entrada de la tabla si existe, 1 si falla la conexión
	 * con la base de datos, 2 si se produce una excepción, 3 si no existe una entrada con
	 * la clave primaria indicada en la base de datos
	 */
	public String seleccionarCO2(String nombre) {
		if (con == null) {
			return "1";
		}
		
		try (PreparedStatement pstmt = con.prepareStatement(SQL_SELECT_CO2, Statement.RETURN_GENERATED_KEYS)) {
			// Agregar el nombre de la zona a la consulta
			pstmt.setString(1, nombre);
			
			ResultSet rs = pstmt.executeQuery();
			// Extraer el resultado
			if (rs.next()) {
				return rs.getString(1);
			}
			else {
				return "3";
			}
		}
		catch (SQLException ex) {
			return "2";
		}
	}
	
	/**
	 * 
	 * La función seleccionarO3 selecciona el valor de O3 de la entrada de la tabla zona
	 * de la base de datos cuya clave primaria es el nombre de la zona indicado
	 * 
	 * @param nombre
	 * @return El nivel de O3 para la entrada de la tabla si existe, 1 si falla la conexión
	 * con la base de datos, 2 si se produce una excepción, 3 si no existe una entrada con
	 * la clave primaria indicada en la base de datos
	 */
	public String seleccionarO3(String nombre) {
		if (con == null) {
			return "1";
		}
		
		try (PreparedStatement pstmt = con.prepareStatement(SQL_SELECT_O3, Statement.RETURN_GENERATED_KEYS)) {
			// Agregar el nombre de la zona a la consulta
			pstmt.setString(1, nombre);
			
			ResultSet rs = pstmt.executeQuery();
			// Extraer el resultado
			if (rs.next()) {
				return rs.getString(1);
			}
			else {
				return "3";
			}
		}
		catch (SQLException ex) {
			return "2";
		}
	}
	
	/**
	 * 
	 * La función seleccionarNO2 selecciona el valor de NO2 de la entrada de la tabla zona
	 * de la base de datos cuya clave primaria es el nombre de la zona indicado
	 * 
	 * @param nombre
	 * @return El nivel de NO2 para la entrada de la tabla si existe, 1 si falla la conexión
	 * con la base de datos, 2 si se produce una excepción, 3 si no existe una entrada con
	 * la clave primaria indicada en la base de datos
	 */
	public String seleccionarNO2(String nombre) {
		if (con == null) {
			return "1";
		}
		
		try (PreparedStatement pstmt = con.prepareStatement(SQL_SELECT_NO2, Statement.RETURN_GENERATED_KEYS)) {
			// Agregar el nombre de la zona a la consulta
			pstmt.setString(1, nombre);
			
			ResultSet rs = pstmt.executeQuery();
			// Extraer el resultado
			if (rs.next()) {
				return rs.getString(1);
			}
			else {
				return "3";
			}
		}
		catch (SQLException ex) {
			return "2";
		}
	}
	
	/**
	 * 
	 * La función seleccionarPM10 selecciona el valor de PM10 de la entrada de la tabla zona
	 * de la base de datos cuya clave primaria es el nombre de la zona indicado
	 * 
	 * @param nombre
	 * @return El nivel de PM10 para la entrada de la tabla si existe, 1 si falla la conexión
	 * con la base de datos, 2 si se produce una excepción, 3 si no existe una entrada con
	 * la clave primaria indicada en la base de datos
	 */
	public String seleccionarPM10(String nombre) {
		if (con == null) {
			return "1";
		}
		
		try (PreparedStatement pstmt = con.prepareStatement(SQL_SELECT_PM10, Statement.RETURN_GENERATED_KEYS)) {
			// Agregar el nombre de la zona a la consulta
			pstmt.setString(1, nombre);
			
			ResultSet rs = pstmt.executeQuery();
			// Extraer el resultado
			if (rs.next()) {
				return rs.getString(1);
			}
			else {
				return "3";
			}
		}
		catch (SQLException ex) {
			return "2";
		}
	}
	
	/**
	 * 
	 * La función seleccionarFichero selecciona el fichero kml de la entrada de la tabla zona
	 * de la base de datos cuya clave primaria es el nombre de la zona indicado
	 * 
	 * @param nombre
	 * @return SimpleEntry con clave 1 indica que falla la conexión con la base de datos,
	 * SimpleEntry con clave 2 indica que se produce una excepción, SimpleEntry con calve 3
	 * indica que no existe una entrada con la clave primaria indicada en la base de datos,
	 * SimpleEntry con calve 0 indica que se puede obtener el fichero kml y como valor el
	 * el propio fichero, en los casos anterior valor vale null
	 */
	public SimpleEntry seleccionarFichero(String nombre) {
		if (con == null) {
			return new SimpleEntry(1, null);
		}
		
		try (PreparedStatement pstmt = con.prepareStatement(SQL_SELECT_FILE, Statement.RETURN_GENERATED_KEYS)) {
			// Agregar el nombre de la zona a la consulta
			pstmt.setString(1, nombre);
						
			ResultSet rs = pstmt.executeQuery();
			// Extraer el resultado
			if (rs.next()) {
				String content = rs.getString(1);
				int x = (int) ((Math.random()*((100-999)+1))+100);
				String fileName = "tmp-" + x + ".txt";
				try {
					FileUtils.writeStringToFile(new File(fileName), content);
				} catch (IOException e) {
					return new SimpleEntry(2, null);
				}
				Kml fileReturn = Kml.unmarshal(new File(fileName));
				return new SimpleEntry(0, fileReturn);
			}
			else {
				return new SimpleEntry(3, null);
			}			
		} 
		catch (SQLException e) {
			return new SimpleEntry(2, null);
		}
	}
}