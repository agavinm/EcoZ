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
	
	private final String SQL_CREAR = "INSERT INTO ecoz.usuario(email, contrasena) VALUES(?,?)";
	private final String SQL_EXISTS_U = "SELECT COUNT(*) FROM ecoz.usuario WHERE email=?";
	
	protected usuarioRepository() throws Exception {
		super();
	}
	
	private boolean existeUsuario(String email) throws Exception {
		try (PreparedStatement pstmt = con.prepareStatement(SQL_EXISTS_U, Statement.RETURN_GENERATED_KEYS)) {
			pstmt.setString(1, email);
			
			ResultSet rs = pstmt.executeQuery();
			// Extraer el resultado
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
}
