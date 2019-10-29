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
	private final String SQL_CREAR = "INSERT INTO ecoz.usuario(email, contrasena) VALUES(?,?)";
	
	protected usuarioRepository() throws Exception {
		super();
	}
	
	// Es un ejemplo que no sigue los DAOS NI LOS EVA NI ESA MIERDA pero FUNCIONA y tiene SENTIDO
	public String crearUsuario(String email, String contrasena) {
		if (con == null) {
			return "ERROR";
		}
		
		try (PreparedStatement pstmt = con.prepareStatement(SQL_CREAR, Statement.RETURN_GENERATED_KEYS)) {
			pstmt.setString(1, email);
			pstmt.setString(2, contrasena);
			pstmt.executeUpdate();
			return "Usuario creado con email " + email;
		}
		catch (SQLException ex) {
			return ex.getMessage();
		}
	}
}
