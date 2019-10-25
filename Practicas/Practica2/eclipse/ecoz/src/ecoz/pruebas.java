package ecoz;

import java.sql.*;

public class pruebas {
	
	public static void main(String[] args) throws SQLException {
		Connection con = DBConnect.createConnection();
		
		if (con!=null) {
			System.out.println(con.getClientInfo().toString());
			//con.setSchema("ecoz");

			try (PreparedStatement pstmt = con.prepareStatement("INSERT INTO ecoz.usuario(email, nombre, apellidos, contrasena) VALUES(?,?,?,?)", 
					Statement.RETURN_GENERATED_KEYS)) {
				pstmt.setString(1, "edu@unizar.es");
				pstmt.setString(2, "edu");
				pstmt.setString(3, "gimeno");
				pstmt.setString(4, "abcd");
				
				if (pstmt.executeUpdate() > 0) {
					System.out.println("OK");
				}
			}
			catch (SQLException ex) {
				System.out.println(ex.getMessage());
			}
			con.close();
		}
	}
}
