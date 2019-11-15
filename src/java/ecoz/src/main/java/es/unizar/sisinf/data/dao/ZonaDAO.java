/**
 * @file	ZonaDAO.java
 * @author	Andrés Gavín Murillo 716358
 * @author	Eduardo Gimeno Soriano 721615
 * @author	Sergio Álvarez Peiro 740241
 * @date	Octubre 2019
 * @coms	Sistemas de información - Práctica 2
 */

package es.unizar.sisinf.data.dao;

import es.unizar.sisinf.data.vo.*;
import es.unizar.sisinf.data.db.ConnectionManager;
import es.unizar.sisinf.data.exception.DataException;
import java.sql.*;
import java.util.ArrayList;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.FileUtils;
import de.micromata.opengis.kml.v_2_2_0.Kml;

public class ZonaDAO {
	
	// Excepciones 
	private final String EX_CREAR = "Error: Ya existe una zona con ese nombre";
	private final String EX_NOEXISTE = "Error: No existe una zona con ese nombre";
	
	// Consultas SQL
	private final String SQL_CREATE = "INSERT INTO ecoz.zona(nombre, fichero, co2, o3, no2, pm10) VALUES(?,?,?,?,?,?)";
	private final String SQL_UPDATE = "UPDATE ecoz.zona SET fichero=?, co2=?, o3=?, no2=?, pm10=? WHERE nombre=?";
	private final String SQL_DELETE = "DELETE FROM ecoz.zona WHERE nombre=?";
	private final String SQL_FIND_BY_ID = "SELECT * FROM ecoz.zona WHERE nombre=?";
	private final String SQL_FIND_ALL = "SELECT * FROM ecoz.zona";

	/**
	 * La función findById selecciona la entrada de la tabla zona
	 * de la base de datos cuya clave primaria es nombre
	 * 
	 * @param zona
	 * @return UsuarioVO
	 * @throws DataException
	 */
	public ZonaVO findById(ZonaVO zona) throws DataException {
		ZonaVO result = null;
		
		try {
			// Abrir conexión e inicializar los parámetros
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_ID);
			ps.setString(1, zona.getNombre());
			
			// Ejecutar consulta
			ResultSet rs = ps.executeQuery();
			
			// Leer resultados			
			if (rs.next()) {
				InputStream is = rs.getBinaryStream("fichero");
				int x = (int) ((Math.random()*((100-999)+1))+100);
				String fileName = "tmp" + x + ".txt";
				try {
					FileUtils.copyInputStreamToFile(is, new File(fileName));
				} 
				catch (IOException e) {
					throw new DataException(DataException.EX_ZONARUTA);
				}
				File file = new File(fileName);
				Kml fileReturn = Kml.unmarshal(file);
				file.delete();
				
				result = new ZonaVO(rs.getString("nombre"), fileReturn, rs.getFloat("co2"),
						rs.getFloat("o3"), rs.getFloat("no2"), rs.getFloat("pm10"));
			}
			else {
				result = null;
			}
			ConnectionManager.releaseConnection(conn);
		}
		catch (SQLException e) {
			throw new DataException(EX_NOEXISTE);
		}
		
		if (result == null) 
			throw new DataException(EX_NOEXISTE);
		
		return result;
	}

	/**
	 * La función findById selecciona las entradas de la tabla zona
	 * de la base de datos
	 * 
	 * @return ArrayList<ZonaVO>
	 * @throws DataException
	 */
	public ArrayList<ZonaVO> findAll() throws DataException {
		ArrayList<ZonaVO> result = new ArrayList<>();
		
		try {
			// Abrir conexión e inicializar los parámetros
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement ps = conn.prepareStatement(SQL_FIND_ALL);
			
			// Ejecutar consulta
			ResultSet rs = ps.executeQuery();
			
			// Leer resultados
			while(rs.next()) {
				InputStream is = rs.getBinaryStream("fichero");
				int x = (int) ((Math.random()*((100-999)+1))+100);
				String fileName = "tmp" + x + ".txt";
				try {
					FileUtils.copyInputStreamToFile(is, new File(fileName));
				} 
				catch (IOException e) {
					throw new DataException(DataException.EX_ZONARUTA);
				}
				File file = new File(fileName);
				Kml fileReturn = Kml.unmarshal(file);
				file.delete();
				
				result.add(new ZonaVO(rs.getString("nombre"), fileReturn, rs.getFloat("co2"),
						rs.getFloat("o3"), rs.getFloat("no2"), rs.getFloat("pm10")));
			}
			ConnectionManager.releaseConnection(conn);
		}
		catch (SQLException e) {
			throw new DataException(DataException.EX_CONEXION);
		}
		
		return result;
	}
	
	/**
	 * La función create añade en la tabla zona de la base de datos una nueva entrada
	 * con los datos introducidos como parámetros
	 * 
	 * @param zona
	 * @throws DataException
	 */
	public void create(ZonaVO zona) throws DataException {
		try {
			// Abrir conexión e inicializar los parámetros
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement ps = conn.prepareStatement(SQL_CREATE);
			
			// Agregar el nombre de la zona a la consulta
			ps.setString(1, zona.getNombre());
		
			// Agregar el fichero kml de la zona a la consulta
			// Primero hay que volcar el contenido del fichero kml a un txt
			// Debido a la implementación de la librería externa usada, se genera un txt en la carpeta del proyecto
			int x = (int) ((Math.random()*((100-999)+1))+100);
			String fileName = "tmp" + x + ".txt";
			try {
				zona.getFichero().marshal(new File(fileName));
			} 
			catch (FileNotFoundException e) {
				throw new DataException(DataException.EX_RUTA);
			}
			// Se vuelve a abrir el txt generado
			File file = new File(fileName);
			InputStream is = null;
			try {
				is = (InputStream) new FileInputStream(file);
			} 
			catch (FileNotFoundException e2) {
				throw new DataException(DataException.EX_RUTA);
			}
			ps.setBinaryStream(2, is, (int) file.length());
			// Se borra el txt generado
			file.delete();
			
			// Agregar a la consulta los distintos niveles de gases
			ps.setFloat(3, zona.getCo2());
			ps.setFloat(4, zona.getO3());
			ps.setFloat(5, zona.getNo2());
			ps.setFloat(6, zona.getPm10());
			
			// Ejecutar consulta
			ps.executeUpdate();
			
			ConnectionManager.releaseConnection(conn);
		}
		catch (SQLException e) {
			throw new DataException(EX_CREAR);
		}
	}

	/**
	 * La función update actualiza la entrada de la tabla zona de la base
	 * de datos cuya clave primaria es nombre
	 * 
	 * @param zona
	 * @throws DataException
	 */
	public void update(ZonaVO zona) throws DataException {
		try {
			// Abrir conexión e inicializar los parámetros
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement ps = conn.prepareStatement(SQL_UPDATE);
		
			// Agregar el fichero kml de la zona a la consulta
			// Primero hay que volcar el contenido del fichero kml a un txt
			// Debido a la implementación de la librería externa usada, se genera un txt en la carpeta del proyecto
			int x = (int) ((Math.random()*((100-999)+1))+100);
			String fileName = "tmp" + x + ".txt";
			try {
				zona.getFichero().marshal(new File(fileName));
			} 
			catch (FileNotFoundException e) {
				throw new DataException(DataException.EX_RUTA);
			}
			// Se vuelve a abrir el txt generado
			File file = new File(fileName);
			InputStream is = null;
			try {
				is = (InputStream) new FileInputStream(file);
			} 
			catch (FileNotFoundException e2) {
				throw new DataException(DataException.EX_RUTA);
			}
			ps.setBinaryStream(1, is, (int) file.length());
			// Se borra el txt generado
			file.delete();
			
			// Agregar a la consulta los distintos niveles de gases
			ps.setFloat(2, zona.getCo2());
			ps.setFloat(3, zona.getO3());
			ps.setFloat(4, zona.getNo2());
			ps.setFloat(5, zona.getPm10());
			
			// Agregar el nombre de la zona a la consulta
			ps.setString(6, zona.getNombre());
			
			// Ejecutar consulta
			ps.executeUpdate();
			
			ConnectionManager.releaseConnection(conn);
		}
		catch (SQLException e) {
			throw new DataException(EX_NOEXISTE);
		}
	}

	/**
	 * La función delete borra la entrada de la tabla zona de la base de datos
	 * cuya clave primaria es nombre
	 * 
	 * @param zona
	 * @throws DataException
	 */
	public void delete(ZonaVO zona) throws DataException {
		try {
			// Abrir conexión e inicializar los parámetros
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement ps = conn.prepareStatement(SQL_DELETE);
			ps.setString(1, zona.getNombre());
			
			// Ejecutar consulta
			ps.executeUpdate();
			
			ConnectionManager.releaseConnection(conn);
		}
		catch (SQLException e) {
			throw new DataException(EX_NOEXISTE);
		}
	}
}