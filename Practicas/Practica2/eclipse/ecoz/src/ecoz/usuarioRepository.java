//******************************************************************************
// File:   usuarioRepository.java
// Author: Andrés Gavín Murillo 716358
// Author: Eduardo Gimeno Soriano 721615
// Author: Sergio Álvarez Peiro 740241
// Date:   Octubre 2019
// Coms:   Sistemas de información - Práctica 2
//******************************************************************************

package ecoz;

import java.sql.*;

public class usuarioRepository extends DBConnect {
	
	private final String EX_CREAR = "Error: Ya existe un usuario con ese email.";
	private final String EX_NOEXISTE = "Error: No existe un usuario con ese email.";
	
	private final String SQL_CREAR = "INSERT INTO ecoz.usuario(email, contrasena) VALUES(?,?)";

	private final String SQL_ACTUALIZAR_NOMBRE = "UPDATE ecoz.usuario SET nombre=? WHERE email=?";
	private final String SQL_ACTUALIZAR_APELLIDOS = "UPDATE ecoz.usuario SET apellidos=? WHERE email=?";
	private final String SQL_ACTUALIZAR_CONTRASENA = "UPDATE ecoz.usuario SET contrasena=? WHERE email=?";

	private final String SQL_EXISTS_U = "SELECT COUNT(*) FROM ecoz.usuario WHERE email=?";
	private final String SQL_SELECT_NOMBRE = "SELECT nombre FROM ecoz.usuario WHERE email=?";
	private final String SQL_SELECT_APELLIDOS = "SELECT apellidos FROM ecoz.usuario WHERE email=?";
	private final String SQL_SELECT_CONTRASENA = "SELECT contrasena FROM ecoz.usuario WHERE email=?";
	
	private final String SQL_DELETE = "DELETE FROM ecoz.usuario WHERE email=?";
	
	protected usuarioRepository() throws Exception {
		super();
	}
	
	private boolean existeUsuario(String email) throws Exception {
		try (PreparedStatement pstmt = con.prepareStatement(SQL_EXISTS_U, Statement.RETURN_GENERATED_KEYS)) {
			pstmt.setString(1, email);
			
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			if (rs.getInt(1) == 1) {
				return true;
			}
			else {
				return false;
			}
		}
		catch (SQLException e) {
			throw new Exception(EX_CONEXION); 
		}
	}
	
	public void crearUsuario(String email, String contrasena) throws Exception {
		if (!existeUsuario(email)) {
			try (PreparedStatement pstmt = con.prepareStatement(SQL_CREAR, Statement.RETURN_GENERATED_KEYS)) {
				pstmt.setString(1, email);
				pstmt.setString(2, contrasena);
				pstmt.executeUpdate();
			}
			catch (SQLException ex) {
				throw new Exception(EX_CONEXION);
			}
		}
		else {
			throw new Exception(EX_CREAR);
		}
	}
	
	private void actualizar(String email, String valor, String sql) throws Exception {
		if (existeUsuario(email)) {
			try (PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
				pstmt.setString(1, valor);
				pstmt.setString(2, email);
				pstmt.executeUpdate();
			}
			catch (SQLException ex) {
				throw new Exception(EX_CONEXION);
			}
		}
		else {
			throw new Exception(EX_NOEXISTE);
		}
	}
	
	public void actualizarNombre(String email, String nombre) throws Exception {
		actualizar(email, nombre, SQL_ACTUALIZAR_NOMBRE);
	}
	
	public void actualizarApellidos(String email, String apellidos) throws Exception {
		actualizar(email, apellidos, SQL_ACTUALIZAR_APELLIDOS);
	}
	
	public void actualizarContrasena(String email, String contrasena) throws Exception {
		actualizar(email, contrasena, SQL_ACTUALIZAR_CONTRASENA);
	}
	
	private String seleccionar(String email, String sql) throws Exception {
		if (existeUsuario(email)) {
			try (PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
				pstmt.setString(1, email);
				
				ResultSet rs = pstmt.executeQuery();
				rs.next();
				return rs.getString(1);
			}
			catch (SQLException ex) {
				throw new Exception(EX_CONEXION);
			}
		}
		else {
			throw new Exception(EX_NOEXISTE);
		}
	}
	
	public String seleccionarNombre(String email) throws Exception {
		return seleccionar(email, SQL_SELECT_NOMBRE);
	}
	
	public String seleccionarApellidos(String email) throws Exception {
		return seleccionar(email, SQL_SELECT_APELLIDOS);
	}
	
	public String seleccionarContrasena(String email) throws Exception {
		return seleccionar(email, SQL_SELECT_CONTRASENA);
	}
	
	public void borrarUsuario(String email) throws Exception {
		if (existeUsuario(email)) {
			try (PreparedStatement pstmt = con.prepareStatement(SQL_DELETE, Statement.RETURN_GENERATED_KEYS)) {
				pstmt.setString(1, email);
				
				pstmt.executeUpdate();
			} 
			catch (SQLException e) {
				throw new Exception(EX_CONEXION);
			}
		}
		else {
			throw new Exception(EX_NOEXISTE);
		}
	}
}
