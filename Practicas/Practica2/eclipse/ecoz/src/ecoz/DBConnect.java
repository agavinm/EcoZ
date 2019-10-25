//******************************************************************************
// File:   DBConnect.java
// Author: Andrés Gavín Murillo 716358
// Author: Eduardo Gimeno Soriano 721615
// Author: Sergio Álvarez Peiro 740241
// Date:   Octubre 2019
// Coms:   Sistemas de información - Práctica 2
//******************************************************************************

package ecoz;

import java.sql.*;

abstract class DBConnect { // Capa DAO
	protected Connection con;

	protected DBConnect() {
		try {
			this.con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/sisinf", "sisinf", "sisinf");
		}
		catch(SQLException e) {
			System.out.println("ERROR: " + e.getMessage());
		}
	}
	
	protected void finalize() {
		if (this.con != null) {
			try {
				this.con.close();
			}
			catch(SQLException e) {
				System.out.println("ERROR: " + e.getMessage());
			}
		}
	}
}
