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

public class DiscurreDAO {
	
	// Excepciones
	private final String EX_CREAR = "Error: Ya existe esa ruta en la zona";
	private final String EX_NOEXISTE = "Error: No existe ninguna ruta que discurra por esa zona";
		
	// Consultas SQL
	private final String SQL_CREATE = "INSERT INTO ecoz.discurre(ruta_id, zona_nombre) VALUES(?,?)";
	private final String SQL_DELETE = "DELETE FROM ecoz.discurre WHERE ruta_id=? AND zona_nombre=?";
	private final String SQL_FIND_BY_ID = "SELECT * FROM ecoz.discurre WHERE ruta_id=? AND zona_nombre=?";
	private final String SQL_FIND_ALL = "SELECT * FROM ecoz.discurre";
	
	private RutaDAO rutaDAO = new RutaDAO();
	private ZonaDAO zonaDAO = new ZonaDAO();

	/**
	 * La función findById selecciona la entrada de la tabla discurre
	 * de la base de datos cuya clave primaria es ruta y zona
	 * 
	 * @param discurre
	 * @return DiscurreVO
	 * @throws DataException
	 */
	public DiscurreVO findById(DiscurreVO discurre) throws DataException {
		DiscurreVO result = null;
		
		try {
			// Abrir conexión e inicializar los parámetros
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_ID);
			ps.setInt(1, discurre.getRuta().getId());
			ps.setString(2, discurre.getZona().getNombre());
			
			// Ejecutar consulta
			ResultSet rs = ps.executeQuery();
			
			// Leer resultados
			if (rs.next()) {
				result = new DiscurreVO(rutaDAO.findById(new RutaVO(rs.getInt("ruta_id"), null, null)), 
						zonaDAO.findById(new ZonaVO(rs.getString("zona_nombre"), null, null, null, 
								null, null)));
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
	 * La función findById selecciona las entradas de la tabla discurre
	 * de la base de datos
	 * 
	 * @return ArrayList<DiscurreVO>
	 * @throws DataException
	 */
	public ArrayList<DiscurreVO> findAll() throws DataException {
		ArrayList<DiscurreVO> result = new ArrayList<>();
		
		try {
			// Abrir conexión e inicializar los parámetros
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement ps = conn.prepareStatement(SQL_FIND_ALL);
			
			// Ejecutar consulta
			ResultSet rs = ps.executeQuery();
			
			// Leer resultados
			while(rs.next()) {
				result.add(new DiscurreVO(rutaDAO.findById(new RutaVO(rs.getInt("ruta_id"), null, null)), 
						zonaDAO.findById(new ZonaVO(rs.getString("zona_nombre"), null, null, null, 
								null, null))));
			}
			ConnectionManager.releaseConnection(conn);
		}
		catch (SQLException e) {
			throw new DataException(DataException.EX_CONEXION);
		}
		
		return result;
	}
	
	/**
	 * La función create añade en la tabla discurre de la base de datos una nueva entrada
	 * con los datos introducidos como parámetros
	 * 
	 * @param discurre
	 * @throws DataException
	 */
	public void create(DiscurreVO discurre) throws DataException {
		try {
			// Abrir conexión e inicializar los parámetros
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement ps = conn.prepareStatement(SQL_CREATE);
			ps.setInt(1, discurre.getRuta().getId());
			ps.setString(2, discurre.getZona().getNombre());
			
			// Ejecutar consulta
			ps.executeUpdate();
			
			ConnectionManager.releaseConnection(conn);
		}
		catch (SQLException e) {
			throw new DataException(EX_CREAR);
		}
	}

	/**
	 * La función delete borra la entrada de la tabla discurre de la base de datos
	 * cuya clave primaria es email
	 * 
	 * @param discurre
	 * @throws DataException
	 */
	public void delete(DiscurreVO discurre) throws DataException {
		try {
			// Abrir conexión e inicializar los parámetros
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement ps = conn.prepareStatement(SQL_DELETE);
			ps.setInt(1, discurre.getRuta().getId());
			ps.setString(2, discurre.getZona().getNombre());
			
			// Ejecutar consulta
			ps.executeUpdate();
			
			ConnectionManager.releaseConnection(conn);
		}
		catch (SQLException e) {
			throw new DataException(EX_NOEXISTE);
		}
	}
}
