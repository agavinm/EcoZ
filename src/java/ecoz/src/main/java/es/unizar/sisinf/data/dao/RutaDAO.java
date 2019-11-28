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
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

public class RutaDAO {
	
	// Excepciones
	private final String EX_CREAR = "Error: Ya existe una ruta con ese identificador";
	private final String EX_NOEXISTE = "Error: No existe una ruta con ese identificador";
	
	// Consultas SQL
	private final String SQL_CREATE = "INSERT INTO ecoz.ruta(id, fichero, usuario_email) VALUES(?,?,?)";
	private final String SQL_UPDATE = "UPDATE ecoz.ruta SET fichero=?, usuario_email=? WHERE id=?";
	private final String SQL_DELETE = "DELETE FROM ecoz.ruta WHERE id=?";
	private final String SQL_FIND_BY_ID = "SELECT * FROM ecoz.ruta WHERE id=?";
	private final String SQL_FIND_BY_USER = "SELECT * FROM ecoz.ruta WHERE usuario_email=?";
	private final String SQL_FIND_ALL = "SELECT * FROM ecoz.ruta";
	private final String SQL_FIND_HIGHEST_ID = "SELECT id FROM ecoz.ruta ORDER BY id DESC LIMIT 1";
	
	private UsuarioDAO usuarioDAO = new UsuarioDAO();
	
	/**
	 * La función generateID devuelve el siguiente id a asignar
	 * 
	 * @return id
	 * @throws DataException
	 */
	public int generateID() {
		int newID = 0;
		try {
			// Abrir conexión e inicializar los parámetros
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement ps = conn.prepareStatement(SQL_FIND_HIGHEST_ID);
			
			// Ejecutar consulta
			ResultSet rs = ps.executeQuery();

			// Leer resultados			
			if (rs.next()) {
				newID = rs.getInt("id") + 1;
			}
		}
		catch (SQLException | DataException e) {
			newID = 0;
		}
		return newID;
	}
	
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
				JSONObject fileReturn = new JSONObject(IOUtils.toString(is, StandardCharsets.UTF_8));
				
				result = new RutaVO(fileReturn, 
						usuarioDAO.findById(new UsuarioVO(rs.getString("usuario_email"), 
								null, null, null)));
				result.setId(ruta.getId());
			}
			else {
				result = null;
			}
			ConnectionManager.releaseConnection(conn);
		}
		catch (Exception e) {
			throw new DataException(EX_NOEXISTE);
		}
		
		if (result == null) 
			throw new DataException(EX_NOEXISTE);
		
		return result;
	}
	
	/**
	 * La función findByUser selecciona las entradas de la tabla ruta
	 * de la base de datos
	 * 
	 * @return ArrayList<RutaVO>
	 * @throws DataException
	 */
	public ArrayList<RutaVO> findByUser(UsuarioVO usuarioVO) throws DataException {
		ArrayList<RutaVO> result = new ArrayList<>();
		
		try {
			// Abrir conexión e inicializar los parámetros
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_USER);
			ps.setString(1, usuarioVO.getEmail());
			
			// Ejecutar consulta
			ResultSet rs = ps.executeQuery();
			
			// Leer resultados			
			while(rs.next()) {
				InputStream is = rs.getBinaryStream("fichero");
				JSONObject fileReturn = new JSONObject(IOUtils.toString(is, StandardCharsets.UTF_8));
				
				RutaVO rutaVO = new RutaVO(fileReturn, 
						usuarioDAO.findById(new UsuarioVO(rs.getString("usuario_email"), 
								null, null, null)));
				rutaVO.setId(rs.getInt("id"));
				result.add(rutaVO);
			}
			ConnectionManager.releaseConnection(conn);
		}
		catch (Exception e) {
			throw new DataException(DataException.EX_CONEXION);
		}
		
		return result;
	}
	
	/**
	 * La función findAll selecciona las entradas de la tabla ruta
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
				JSONObject fileReturn = new JSONObject(IOUtils.toString(is, StandardCharsets.UTF_8));
				
				RutaVO rutaVO = new RutaVO(fileReturn, 
						usuarioDAO.findById(new UsuarioVO(rs.getString("usuario_email"), 
								null, null, null)));
				rutaVO.setId(rs.getInt("id"));
				result.add(rutaVO);
			}
			ConnectionManager.releaseConnection(conn);
		}
		catch (Exception e) {
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
			
			// Generar y agregar el identificador de la ruta
			int id = generateID();
			
			if (ruta.getId() == null) {
				ruta.setId(id);
			}

			ps.setInt(1, ruta.getId());
			
			ps.setBinaryStream(2, new ByteArrayInputStream(ruta.getFichero().toString().getBytes()));
			
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
			
			ps.setBinaryStream(1, new ByteArrayInputStream(ruta.getFichero().toString().getBytes()));
			
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
