/**
 * @file	RutaDAO.java
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

public class RutaDAO {
	
	// Excepciones
	private final String EX_CREAR = "Error: Ya existe una ruta con ese identificador";
	private final String EX_NOEXISTE = "Error: No existe una ruta con ese identificador";
	
	// Consultas SQL
	private final String SQL_CREATE = "INSERT INTO ecoz.ruta(id, fichero, usuario_email) VALUES(?,?,?)";
	private final String SQL_UPDATE = "UPDATE ecoz.ruta SET fichero=?, usuario_email=? WHERE id=?";
	private final String SQL_DELETE = "DELETE FROM ecoz.ruta WHERE id=?";
	private final String SQL_FIND_BY_ID = "SELECT * FROM ecoz.ruta WHERE id=?";
	private final String SQL_FIND_ALL = "SELECT * FROM ecoz.ruta";
	
	private UsuarioDAO usuarioDAO = new UsuarioDAO();
	
	/**
	 * La función findById selecciona la entrada de la tabla ruta
	 * de la base de datos cuya clave primaria es id
	 * 
	 * @param ruta
	 * @return RutaVO
	 * @throws DataException
	 */
	public RutaVO findById(RutaVO ruta) throws DataException {
		RutaVO result = null;
		
		try {
			// Abrir conexión e inicializar los parámetros
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_ID);
			ps.setInt(1, ruta.getId());
			
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
				
				result = new RutaVO(rs.getInt("id"), fileReturn, 
						usuarioDAO.findById(new UsuarioVO(rs.getString("usuario_email"), 
								null, null, null)));
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
	 * La función findById selecciona las entradas de la tabla ruta
	 * de la base de datos
	 * 
	 * @return ArrayList<RutaVO>
	 * @throws DataException
	 */
	public ArrayList<RutaVO> findAll() throws DataException {
		ArrayList<RutaVO> result = new ArrayList<>();
		
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
				
				result.add(new RutaVO(rs.getInt("id"), fileReturn, 
						usuarioDAO.findById(new UsuarioVO(rs.getString("usuario_email"), 
								null, null, null))));
			}
			ConnectionManager.releaseConnection(conn);
		}
		catch (SQLException e) {
			throw new DataException(DataException.EX_CONEXION);
		}
		
		return result;
	}

	/**
	 * La función create añade en la tabla ruta de la base de datos una nueva entrada
	 * con los datos introducidos como parámetros
	 * 
	 * @param ruta
	 * @throws DataException
	 */
	public void create(RutaVO ruta) throws DataException {
		try {
			// Abrir conexión e inicializar los parámetros
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement ps = conn.prepareStatement(SQL_CREATE);
			
			// Agregar el identificador de la ruta
			ps.setInt(1, ruta.getId());
		
			// Agregar el fichero kml de la ruta a la consulta
			// Primero hay que volcar el contenido del fichero kml a un txt
			// Debido a la implementación de la librería externa usada, se genera un txt en la carpeta del proyecto
			int x = (int) ((Math.random()*((100-999)+1))+100);
			String fileName = "tmp" + x + ".txt";
			try {
				ruta.getFichero().marshal(new File(fileName));
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
			
			// Agregar a la consulta el email del usuario de la ruta
			ps.setString(3, ruta.getUsuario().getEmail());
			
			// Ejecutar consulta
			ps.executeUpdate();
			
			ConnectionManager.releaseConnection(conn);
		}
		catch (SQLException e) {
			throw new DataException(EX_CREAR);
		}
	}
	
	/**
	 * La función update actualiza la entrada de la tabla ruta de la base
	 * de datos cuya clave primaria es id
	 * 
	 * @param ruta
	 * @throws DataException
	 */
	public void update(RutaVO ruta) throws DataException {
		try {
			// Abrir conexión e inicializar los parámetros
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement ps = conn.prepareStatement(SQL_UPDATE);
		
			// Agregar el fichero kml de la ruta a la consulta
			// Primero hay que volcar el contenido del fichero kml a un txt
			// Debido a la implementación de la librería externa usada, se genera un txt en la carpeta del proyecto
			int x = (int) ((Math.random()*((100-999)+1))+100);
			String fileName = "tmp" + x + ".txt";
			try {
				ruta.getFichero().marshal(new File(fileName));
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
			
			// Agregar a la consulta el email del usuario de la ruta
			ps.setString(2, ruta.getUsuario().getEmail());
			
			// Agregar el identificador de la ruta
			ps.setInt(3, ruta.getId());
			
			// Ejecutar consulta
			ps.executeUpdate();
			
			ConnectionManager.releaseConnection(conn);
		}
		catch (SQLException e) {
			throw new DataException(EX_NOEXISTE);
		}
	}
	
	/**
	 * La función delete borra la entrada de la tabla ruta de la base de datos
	 * cuya clave primaria es id
	 * 
	 * @param ruta
	 * @throws DataException
	 */
	public void delete(RutaVO ruta) throws DataException {
		try {
			// Abrir conexión e inicializar los parámetros
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement ps = conn.prepareStatement(SQL_DELETE);
			ps.setInt(1, ruta.getId());
			
			// Ejecutar consulta
			ps.executeUpdate();
			
			ConnectionManager.releaseConnection(conn);
		}
		catch (SQLException e) {
			throw new DataException(EX_NOEXISTE);
		}
	}
}
