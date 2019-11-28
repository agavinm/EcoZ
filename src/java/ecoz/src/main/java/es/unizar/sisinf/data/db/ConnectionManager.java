/**
 * @file	ConnectionManager.java
 * @author	Andrés Gavín Murillo 716358
 * @author	Eduardo Gimeno Soriano 721615
 * @author	Sergio Álvarez Peiro 740241
 * @date	Octubre 2019
 * @coms	Sistemas de información - Práctica 2
 */

package es.unizar.sisinf.data.db;

import java.sql.*;

import es.unizar.sisinf.data.exception.DataException;

public class ConnectionManager {
	
	// JDBC
	private static final String JDBC_DRIVER = "org.postgresql.Driver";
	//private static final String DB_URL = "jdbc:postgresql://localhost:5432/sisinf";
	private static final String DB_URL = "jdbc:postgresql://sisinf-postgresql:5432/sisinf";
	
	// Credenciales
	private static final String USER = "sisinf";
	private static final String PASS = "sisinf";

	// Devuelve una nueva conexión
	public final static Connection getConnection() throws DataException {
		Connection conn = null;
		
		try {
			// Register
			Class.forName(JDBC_DRIVER);
			
			// Open a connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			
			return conn;
		}
		catch (Exception e) {
			throw new DataException(DataException.EX_CONEXION);
		}
	}
	
	public final static void releaseConnection(Connection conn) throws DataException {
		try {
			conn.close();
		}
		catch (Exception e) {
			throw new DataException(DataException.EX_FINALIZE);
		}
	}
}
