/**
 * @file	UsuarioVO.java
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

public class UsuarioDAO {

	// Excepciones
	private final String EX_CREAR = "Error: Ya existe un usuario con ese email.";
	private final String EX_NOEXISTE = "Error: No existe un usuario con ese email.";

	// Consultas SQL
	private final String SQL_CREATE= "INSERT INTO ecoz.usuario(email, nombre, apellidos, contrasena) VALUES(?,?,?,?)";
	private final String SQL_UPDATE = "UPDATE ecoz.usuario SET nombre=?, apellidos=?, contrasena=? WHERE email=?";
	private final String SQL_DELETE = "DELETE FROM ecoz.usuario WHERE email=?";
	private final String SQL_FIND_BY_ID = "SELECT * FROM ecoz.usuario WHERE email=?";
	private final String SQL_FIND_ALL = "SELECT * FROM ecoz.usuario";

	/**
	 * La función findById selecciona la entrada de la tabla usuario
	 * de la base de datos cuya clave primaria es email
	 * 
	 * @param usuario
	 * @return UsuarioVO
	 * @throws DataException
	 */
	public UsuarioVO findById(UsuarioVO usuario) throws DataException {
		UsuarioVO result = null;
		
		try {
			// Abrir conexión e inicializar los parámetros
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_ID);
			ps.setString(1, usuario.getEmail());
			
			// Ejecutar consulta
			ResultSet rs = ps.executeQuery();
			
			// Leer resultados
			if (rs.next()) {
				result = new UsuarioVO(rs.getString("email"), rs.getString("nombre"), 
						rs.getString("apellidos"), rs.getString("contrasena"));
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
	 * La función findById selecciona las entradas de la tabla usuario
	 * de la base de datos
	 * 
	 * @return ArrayList<UsuarioVO>
	 * @throws DataException
	 */
	public ArrayList<UsuarioVO> findAll() throws DataException {
		ArrayList<UsuarioVO> result = new ArrayList<>();
		
		try {
			// Abrir conexión e inicializar los parámetros
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement ps = conn.prepareStatement(SQL_FIND_ALL);
			
			// Ejecutar consulta
			ResultSet rs = ps.executeQuery();
			
			// Leer resultados
			while (rs.next()) {
				result.add(new UsuarioVO(rs.getString("email"), rs.getString("nombre"), 
						rs.getString("apellidos"), rs.getString("contrasena")));
			}
			ConnectionManager.releaseConnection(conn);
		}
		catch (SQLException e) {
			throw new DataException(DataException.EX_CONEXION);
		}
		
		return result;
	}
	
	/**
	 * La función create añade en la tabla usuario de la base de datos una nueva entrada
	 * con los datos introducidos como parámetros
	 * 
	 * @param usuario
	 * @throws DataException
	 */
	public void create(UsuarioVO usuario) throws DataException {
		try {
			// Abrir conexión e inicializar los parámetros
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement ps = conn.prepareStatement(SQL_CREATE);
			ps.setString(1, usuario.getEmail());
			ps.setString(2, usuario.getNombre());
			ps.setString(3, usuario.getApellidos());
			ps.setString(4, usuario.getContrasena());
			
			// Ejecutar consulta
			ps.executeUpdate();
			
			ConnectionManager.releaseConnection(conn);
		}
		catch (SQLException e) {
			throw new DataException(EX_CREAR);
		}
	}

	/**
	 * La función update actualiza la entrada de la tabla usuario de la base
	 * de datos cuya clave primaria es email
	 * 
	 * @param usuario
	 * @throws DataException
	 */
	public void update(UsuarioVO usuario) throws DataException {
		try {
			// Abrir conexión e inicializar los parámetros
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement ps = conn.prepareStatement(SQL_UPDATE);
			ps.setString(1, usuario.getNombre());
			ps.setString(2, usuario.getApellidos());
			ps.setString(3, usuario.getContrasena());
			ps.setString(4, usuario.getEmail());
			
			// Ejecutar consulta
			ps.executeUpdate();
			
			ConnectionManager.releaseConnection(conn);
		}
		catch (SQLException e) {
			throw new DataException(EX_NOEXISTE);
		}
	}

	/**
	 * La función delete borra la entrada de la tabla usuario de la base de datos
	 * cuya clave primaria es email
	 * 
	 * @param usuario
	 * @throws DataException
	 */
	public void delete(UsuarioVO usuario) throws DataException {
		try {
			// Abrir conexión e inicializar los parámetros
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement ps = conn.prepareStatement(SQL_DELETE);
			ps.setString(1, usuario.getEmail());
			
			// Ejecutar consulta
			ps.executeUpdate();
			
			ConnectionManager.releaseConnection(conn);
		}
		catch (SQLException e) {
			throw new DataException(EX_NOEXISTE);
		}
	}
}
