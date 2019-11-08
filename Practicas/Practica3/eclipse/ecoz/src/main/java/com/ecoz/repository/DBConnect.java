//******************************************************************************
// File:   DBConnect.java
// Author: Andrés Gavín Murillo 716358
// Author: Eduardo Gimeno Soriano 721615
// Author: Sergio Álvarez Peiro 740241
// Date:   Octubre 2019
// Coms:   Sistemas de información - Práctica 2
//******************************************************************************

package com.ecoz.repository;

import java.sql.*;

abstract class DBConnect { // Capa DAO
	protected final String EX_CONEXION = "Error: No se ha podido conectar con la base de datos.";
	protected final String EX_FINALIZE = "Error: No se ha podido cerrar la conexión con la base de datos.";
	protected final String EX_RUTA = "Error: No se ha podido guardar la ruta.";
	protected final String EX_ZONARUTA = "Error: No se ha podido cargar la ruta o zona.";
	
	protected Connection con;

	protected DBConnect() throws Exception {
		try {
			this.con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/sisinf", "sisinf", "sisinf");
		}
		catch(SQLException e) {
			throw new Exception(EX_CONEXION);
		}
	}
	
	protected void finalize() throws Exception {
		if (this.con != null) {
			try {
				this.con.close();
			}
			catch(SQLException e) {
				throw new Exception(EX_FINALIZE);
			}
		}
	}
}
