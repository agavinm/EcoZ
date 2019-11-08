package src.main.java.com.ecoz.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class discurreRepository extends DBConnect {
	// Excepciones
	private final String EX_DISCURRE_NO_CREADA = "Error: Ya existe esa ruta en la zona";
	private final String EX_DISCURRE_NO_EXISTENTE = "Error: No existe ninguna ruta que discurra por esa zona";
		
	// Consultas SQL
	private final String SQL_CREAR = "INSERT INTO ecoz.discurre(ruta_id, zona_nombre) VALUES(?,?)";
	private final String SQL_DELETE = "DELETE FROM ecoz.discure WHERE ruta_id=? AND zona_nombre=?";
	private final String SQL_EXISTS_PK = "SELECT COUNT(*) FROM ecoz.ruta WHERE ruta_id=? AND zona_nombre=?";

	protected discurreRepository() throws Exception {
		super();
	}
	
	/**
	 * 
	 * La función existeEntrada comprueba si existe una entrada en la tabla discurre de la
	 * base de datos con la clave primaria idicada
	 * 
	 * @param id
	 * @param nombre
	 * @return true si existe, false en caso contrario
	 * @throws Exception
	 */
	private Boolean existeEntrada(int id, String nombre) throws Exception {
		try (PreparedStatement pstmt = con.prepareStatement(SQL_EXISTS_PK, Statement.RETURN_GENERATED_KEYS)) {
			pstmt.setInt(1, id);
			pstmt.setString(2, nombre);
			
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
	 * La función crearDiscurre añade en la tabla discurre de la base de datos una nueva entrada
	 * con los datos introducidos como parámetros
	 * 
	 * @param id
	 * @param nombre
	 * @throws Exception
	 */
	public void crearRuta(int id, String nombre) throws Exception {
		// Comprobar si existe una entrada con la clave primaria indicada
		if (!existeEntrada(id,nombre)) {
			try (PreparedStatement pstmt = con.prepareStatement(SQL_CREAR, Statement.RETURN_GENERATED_KEYS)) {
				// Agregar el identificador de la ruta y el nombre de la zona
				pstmt.setInt(1, id);
				pstmt.setString(2, nombre);
				pstmt.executeUpdate();
			}
			catch (SQLException ex) {
				throw new Exception(EX_CONEXION); 
			}
		}
		else {
			throw new Exception(EX_DISCURRE_NO_CREADA);
		}
	}
	
	/**
	 * 
	 * La función borrarDiscurre borra la entrada de la tabla discurre de la base de datos
	 * cuya clave primaria es la id de la ruta y el nombre de la zona
	 * 
	 * @param id
	 * @param nombre
	 * @throws Exception
	 */
	public void borrarDiscurre(int id, String nombre) throws Exception {
		// Comprobar si existe una entrada con la clave primaria indicada
		if (existeEntrada(id,nombre)) {
			try (PreparedStatement pstmt = con.prepareStatement(SQL_DELETE, Statement.RETURN_GENERATED_KEYS)) {
				// Agregar el identificador de la ruta y el nombre de la zona a la consulta
				pstmt.setInt(1, id);
				pstmt.setString(2, nombre);
				
				pstmt.executeUpdate();
			} 
			catch (SQLException e) {
				throw new Exception(EX_CONEXION);
			}
		}
		else {
			throw new Exception(EX_DISCURRE_NO_EXISTENTE);
		}
	}
	
}
