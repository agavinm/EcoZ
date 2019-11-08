//******************************************************************************
// File:   zonaRepository.java
// Author: Andrés Gavín Murillo 716358
// Author: Eduardo Gimeno Soriano 721615
// Author: Sergio Álvarez Peiro 740241
// Date:   Octubre 2019
// Coms:   Sistemas de información - Práctica 2
//******************************************************************************

package src.main.java.com.ecoz.repository;

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
	// Excepciones 
	private final String EX_ZONA_NO_CREADA = "Error: Ya existe una zona con ese nombre";
	private final String EX_ZONA_NO_EXISTENTE = "Error: No existe una zona con ese nombre";
	
	// Consultas SQL
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
	 * Constructor por defecto
	 * 
	 * @throws Exception
	 */
	protected zonaRepository() throws Exception {
		super();
	}
	
	/**
	 * 
	 * La función existeEntrada comprueba si existe una entrada en la tabla zona de la
	 * base de datos con la clave primaria indicada
	 * 
	 * @param nombre
	 * @return true si existe, false en caso contrario
	 * @throws Exception
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
	 * @throws Exception
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
				String fileName = "tmp" + x + ".txt";
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
	 * @throws Exception
	 */
	public void actualizarCO2(String nombre, Float co2) throws Exception {
		// Comprobar si existe una entrada con la clave primaria indicada
		if (existeEntrada(nombre)) {
			try (PreparedStatement pstmt = con.prepareStatement(SQL_UPDATE_CO2, Statement.RETURN_GENERATED_KEYS)) {
				// Agregar a la consulta nivel de CO2
				pstmt.setFloat(1, co2);
				
				// Agregar el nombre de la zona a la consulta
				pstmt.setString(2, nombre);
				
				pstmt.executeUpdate();
			}
			catch (SQLException ex) {
				throw new Exception(EX_CONEXION);
			}
		}
		else {
			throw new Exception(EX_ZONA_NO_EXISTENTE);
		}
	}
	
	/**
	 * 
	 * La función actualizarO3 actualiza el valor de O3 de la entrada de la tabla zona de la base
	 * de datos cuya clave primaria es el nombre de la zona indicado
	 * 
	 * @param nombre
	 * @param o3
	 * @throws Exception
	 */
	public void actualizarO3(String nombre, Float o3) throws Exception {
		// Comprobar si existe una entrada con la clave primaria indicada
		if (existeEntrada(nombre)) {
			try (PreparedStatement pstmt = con.prepareStatement(SQL_UPDATE_O3, Statement.RETURN_GENERATED_KEYS)) {
				// Agregar a la consulta nivel de O3
				pstmt.setFloat(1, o3);
				
				// Agregar el nombre de la zona a la consulta
				pstmt.setString(2, nombre);
				
				pstmt.executeUpdate();
			}
			catch (SQLException ex) {
				throw new Exception(EX_CONEXION);
			}
		}
		else {
			throw new Exception(EX_ZONA_NO_EXISTENTE);
		}
	}
	
	/**
	 * 
	 * La función actualizarNO2 actualiza el valor de NO2 de la entrada de la tabla zona de la base
	 * de datos cuya clave primaria es el nombre de la zona indicado
	 * 
	 * @param nombre
	 * @param no2
	 * @throws Exception
	 */
	public void actualizarNO2(String nombre, Float no2) throws Exception {
		// Comprobar si existe una entrada con la clave primaria indicada
		if (existeEntrada(nombre)) {
			try (PreparedStatement pstmt = con.prepareStatement(SQL_UPDATE_NO2, Statement.RETURN_GENERATED_KEYS)) {
				// Agregar a la consulta nivel de NO2
				pstmt.setFloat(1, no2);
				
				// Agregar el nombre de la zona a la consulta
				pstmt.setString(2, nombre);
				
				pstmt.executeUpdate();
			}
			catch (SQLException ex) {
				throw new Exception(EX_CONEXION);
			}
		}
		else {
			throw new Exception(EX_ZONA_NO_EXISTENTE);
		}
	}
	
	/**
	 * 
	 * La función actualizarPM10 actualiza el valor de PM10 de la entrada de la tabla zona de la base
	 * de datos cuya clave primaria es el nombre de la zona indicado
	 * 
	 * @param nombre
	 * @param pm10
	 * @throws Exception
	 */
	public void actualizarPM10(String nombre, Float pm10) throws Exception {
		// Comprobar si existe una entrada con la clave primaria indicada
		if (existeEntrada(nombre)) {
			try (PreparedStatement pstmt = con.prepareStatement(SQL_UPDATE_PM10, Statement.RETURN_GENERATED_KEYS)) {
				// Agregar a la consulta nivel de PM10
				pstmt.setFloat(1, pm10);
				
				// Agregar el nombre de la zona a la consulta
				pstmt.setString(2, nombre);
				
				pstmt.executeUpdate();
			}
			catch (SQLException ex) {
				throw new Exception(EX_CONEXION);
			}
		}
		else {
			throw new Exception(EX_ZONA_NO_EXISTENTE);
		}
		
	}
	
	/**
	 * 
	 * La función actualizarFichero actualiza el fichero kml de la entrada de la tabla zona de la base
	 * de datos cuya clave primaria es el nombre de la zona indicado
	 * 
	 * @param nombre
	 * @param fichero
	 * @throws Exception
	 */
	public void actualizarFichero(String nombre, Kml fichero) throws Exception {
		// Comprobar si existe una entrada con la clave primaria indicada
		if (existeEntrada(nombre)) {
			try (PreparedStatement pstmt = con.prepareStatement(SQL_UPDATE_FILE, Statement.RETURN_GENERATED_KEYS)) {
				// Agregar el fichero kml de la zona a la consulta
				// Primero hay que volcar el contenido del fichero kml a un txt
				// Debido a la implementación de la librería externa usada, se genera un txt en la carpeta del proyecto
				int x = (int) ((Math.random()*((100-999)+1))+100);
				String fileName = "tmp" + x + ".txt";
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
				
				pstmt.setBinaryStream(1, is, (int) file.length());
				// Se borra el txt generado
				file.delete();
				
				// Agregar el nombre de la zona a la consulta
				pstmt.setString(2, nombre);
				
				pstmt.executeUpdate();
				
			}
			catch (SQLException ex) {
				throw new Exception(EX_CONEXION);
			}
		}
		else {
			throw new Exception(EX_ZONA_NO_EXISTENTE);
		}
	}
	
	/**
	 * 
	 * La función seleccionarCO2 selecciona el valor de CO2 de la entrada de la tabla zona
	 * de la base de datos cuya clave primaria es el nombre de la zona indicado
	 * 
	 * @param nombre
	 * @throws Exception
	 */
	public Float seleccionarCO2(String nombre) throws Exception {
		// Comprobar si existe una entrada con la clave primaria indicada
		if (existeEntrada(nombre)) {
			try (PreparedStatement pstmt = con.prepareStatement(SQL_SELECT_CO2, Statement.RETURN_GENERATED_KEYS)) {
				// Agregar el nombre de la zona a la consulta
				pstmt.setString(1, nombre);
				
				ResultSet rs = pstmt.executeQuery();
				// Extraer el resultado
				rs.next();
				return Float.parseFloat(rs.getString(1));
			}
			catch (SQLException ex) {
				throw new Exception(EX_CONEXION);
			}
		}
		else {
			throw new Exception(EX_ZONA_NO_EXISTENTE);
		}
	}
	
	/**
	 * 
	 * La función seleccionarO3 selecciona el valor de O3 de la entrada de la tabla zona
	 * de la base de datos cuya clave primaria es el nombre de la zona indicado
	 * 
	 * @param nombre
	 * @throws Exception
	 */
	public Float seleccionarO3(String nombre) throws Exception {
		// Comprobar si existe una entrada con la clave primaria indicada
		if (existeEntrada(nombre)) {
			try (PreparedStatement pstmt = con.prepareStatement(SQL_SELECT_O3, Statement.RETURN_GENERATED_KEYS)) {
				// Agregar el nombre de la zona a la consulta
				pstmt.setString(1, nombre);
				
				ResultSet rs = pstmt.executeQuery();
				// Extraer el resultado
				rs.next();
				return Float.parseFloat(rs.getString(1));
			}
			catch (SQLException ex) {
				throw new Exception(EX_CONEXION);
			}
		}
		else {
			throw new Exception(EX_ZONA_NO_EXISTENTE);
		}
	}
	
	/**
	 * 
	 * La función seleccionarNO2 selecciona el valor de NO2 de la entrada de la tabla zona
	 * de la base de datos cuya clave primaria es el nombre de la zona indicado
	 * 
	 * @param nombre
	 * @throws Exception
	 */
	public Float seleccionarNO2(String nombre) throws Exception {
		// Comprobar si existe una entrada con la clave primaria indicada
		if (existeEntrada(nombre)) {
			try (PreparedStatement pstmt = con.prepareStatement(SQL_SELECT_NO2, Statement.RETURN_GENERATED_KEYS)) {
				// Agregar el nombre de la zona a la consulta
				pstmt.setString(1, nombre);
				
				ResultSet rs = pstmt.executeQuery();
				// Extraer el resultado
				rs.next();
				return Float.parseFloat(rs.getString(1));
			}
			catch (SQLException ex) {
				throw new Exception(EX_CONEXION);
			}
		}
		else {
			throw new Exception(EX_ZONA_NO_EXISTENTE);
		}
	}
	
	/**
	 * 
	 * La función seleccionarPM10 selecciona el valor de PM10 de la entrada de la tabla zona
	 * de la base de datos cuya clave primaria es el nombre de la zona indicado
	 * 
	 * @param nombre
	 * @throws Exception
	 */
	public Float seleccionarPM10(String nombre) throws Exception {
		// Comprobar si existe una entrada con la clave primaria indicada
		if (existeEntrada(nombre)) {
			try (PreparedStatement pstmt = con.prepareStatement(SQL_SELECT_PM10, Statement.RETURN_GENERATED_KEYS)) {
				// Agregar el nombre de la zona a la consulta
				pstmt.setString(1, nombre);
				
				ResultSet rs = pstmt.executeQuery();
				// Extraer el resultado
				rs.next();
				return Float.parseFloat(rs.getString(1));
			}
			catch (SQLException ex) {
				throw new Exception(EX_CONEXION);
			}
		}
		else {
			throw new Exception(EX_ZONA_NO_EXISTENTE);
		}
	}
	
	/**
	 * 
	 * La función seleccionarFichero selecciona el fichero kml de la entrada de la tabla zona
	 * de la base de datos cuya clave primaria es el nombre de la zona indicado
	 * 
	 * @param nombre
	 * @throws Exception
	 */
	public Kml seleccionarFichero(String nombre) throws Exception {
		// Comprobar si existe una entrada con la clave primaria indicada
		if (existeEntrada(nombre)) {
			try (PreparedStatement pstmt = con.prepareStatement(SQL_SELECT_FILE, Statement.RETURN_GENERATED_KEYS)) {
				// Agregar el nombre de la zona a la consulta
				pstmt.setString(1, nombre);
							
				ResultSet rs = pstmt.executeQuery();
				// Extraer el resultado
				rs.next();
				InputStream is = rs.getBinaryStream(1);
				int x = (int) ((Math.random()*((100-999)+1))+100);
				String fileName = "tmp" + x + ".txt";
				try {
					FileUtils.copyInputStreamToFile(is, new File(fileName));
				} 
				catch (IOException e) {
					throw new Exception(EX_ZONARUTA);
				}
				File file = new File(fileName);
				Kml fileReturn = Kml.unmarshal(file);
				file.delete();
				
				return fileReturn;
			} 
			catch (SQLException e) {
				throw new Exception(EX_CONEXION);
			}
		}
		else {
			throw new Exception(EX_ZONA_NO_EXISTENTE);
		}
	}
	
	/**
	 * 
	 * La función borrarZona borra la entrada de la tabla zona de la base de datos
	 * cuya clave primaria es el nombre de la zona indicado
	 * 
	 * @param nombre
	 * @throws Exception
	 */
	public void borrarZona(String nombre) throws Exception {
		// Comprobar si existe una entrada con la clave primaria indicada
		if (existeEntrada(nombre)) {
			try (PreparedStatement pstmt = con.prepareStatement(SQL_DELETE, Statement.RETURN_GENERATED_KEYS)) {
				// Agregar el nombre de la zona a la consulta
				pstmt.setString(1, nombre);
				
				pstmt.executeUpdate();
			} 
			catch (SQLException e) {
				throw new Exception(EX_CONEXION);
			}
		}
		else {
			throw new Exception(EX_ZONA_NO_EXISTENTE);
		}
	}
}