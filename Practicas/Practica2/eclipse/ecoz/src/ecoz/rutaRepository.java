package ecoz;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.io.FileUtils;

import de.micromata.opengis.kml.v_2_2_0.Kml;

public class rutaRepository extends DBConnect {
	// Excepciones
	private final String EX_RUTA_NO_CREADA = "Error: Ya existe una ruta con ese identificador";
	private final String EX_RUTA_NO_EXISTENTE = "Error: No existe una ruta con ese identificador";
	
	// Consultas SQL
	private final String SQL_CREAR = "INSERT INTO ecoz.ruta(id, fichero, usuario_email) VALUES(?,?,?)";
	private final String SQL_DELETE = "DELETE FROM ecoz.ruta WHERE id=?";
	private final String SQL_EXISTS_PK = "SELECT COUNT(*) FROM ecoz.ruta WHERE id=?";
	private final String SQL_UPDATE_FILE = "UPDATE ecoz.ruta SET fichero=? WHERE id=?";
	private final String SQL_SELECT_FILE = "SELECT fichero FROM ecoz.ruta WHERE id=?";
	private final String SQL_UPDATE_EMAIL = "UPDATE ecoz.ruta SET usuario_email=? WHERE id=?";
	private final String SQL_SELECT_EMAIL = "SELECT usuario_email FROM ecoz.ruta WHERE id=?";
	
	protected rutaRepository() throws Exception {
		super();
	}
	
	/**
	 * 
	 * La función existeEntrada comprueba si existe una entrada en la tabla ruta de la
	 * base de datos con la clave primaria indicada
	 * 
	 * @param id
	 * @return true si existe, false en caso contrario
	 * @throws Exception
	 */
	private Boolean existeEntrada(int id) throws Exception {
		try (PreparedStatement pstmt = con.prepareStatement(SQL_EXISTS_PK, Statement.RETURN_GENERATED_KEYS)) {
			pstmt.setInt(1, id);
			
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
	 * La función crearRuta añade en la tabla ruta de la base de datos una nueva entrada
	 * con los datos introducidos como parámetros
	 * 
	 * @param id
	 * @param fichero
	 * @param email
	 * @throws Exception
	 */
	public void crearRuta(int id, Kml fichero, String email) throws Exception {
		// Comprobar si existe una entrada con la clave primaria indicada
		if (!existeEntrada(id)) {
			try (PreparedStatement pstmt = con.prepareStatement(SQL_CREAR, Statement.RETURN_GENERATED_KEYS)) {
				// Agregar el identificador de la ruta
				pstmt.setInt(1, id);
			
				// Agregar el fichero kml de la ruta a la consulta
				// Primero hay que volcar el contenido del fichero kml a un txt
				// Debido a la implementación de la librería externa usada, se genera un txt en la carpeta del proyecto
				int x = (int) ((Math.random()*((100-999)+1))+100);
				String fileName = "tmp" + x + ".txt";
				try {
					fichero.marshal(new File(fileName));
				} 
				catch (FileNotFoundException e) {
					throw new Exception(EX_RUTA);
				}
				// Se vuelve a abrir el txt generado
				File file = new File(fileName);
				InputStream is = null;
				try {
					is = (InputStream) new FileInputStream(file);
				} 
				catch (FileNotFoundException e2) {
					throw new Exception(EX_RUTA);
				}
				pstmt.setBinaryStream(2, is, (int) file.length());
				// Se borra el txt generado
				file.delete();
				
				// Agregar a la consulta el email del usuario de la ruta
				pstmt.setString(3, email);
				
				pstmt.executeUpdate();
			}
			catch (SQLException ex) {
				throw new Exception(EX_CONEXION); 
			}
		}
		else {
			throw new Exception(EX_RUTA_NO_CREADA);
		}
	}
	
	/**
	 * 
	 * La función borrarRuta borra la entrada de la tabla ruta de la base de datos
	 * cuya clave primaria es id
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void borrarRuta(int id) throws Exception {
		// Comprobar si existe una entrada con la clave primaria indicada
		if (existeEntrada(id)) {
			try (PreparedStatement pstmt = con.prepareStatement(SQL_DELETE, Statement.RETURN_GENERATED_KEYS)) {
				// Agregar el identificador de la ruta a la consulta
				pstmt.setInt(1, id);
				
				pstmt.executeUpdate();
			} 
			catch (SQLException e) {
				throw new Exception(EX_CONEXION);
			}
		}
		else {
			throw new Exception(EX_RUTA_NO_EXISTENTE);
		}
	}
	
	/**
	 * 
	 * La función actualizarFichero actualiza el fichero kml de la entrada de la tabla ruta de la base
	 * de datos cuya clave primaria es id
	 * 
	 * @param id
	 * @param fichero
	 * @throws Exception
	 */
	public void actualizarFichero(int id, Kml fichero) throws Exception {
		// Comprobar si existe una entrada con la clave primaria indicada
		if (existeEntrada(id)) {
			try (PreparedStatement pstmt = con.prepareStatement(SQL_UPDATE_FILE, Statement.RETURN_GENERATED_KEYS)) {
				// Agregar el fichero kml de la ruta a la consulta
				// Primero hay que volcar el contenido del fichero kml a un txt
				// Debido a la implementación de la librería externa usada, se genera un txt en la carpeta del proyecto
				int x = (int) ((Math.random()*((100-999)+1))+100);
				String fileName = "tmp" + x + ".txt";
				try {
					fichero.marshal(new File(fileName));
				} 
				catch (FileNotFoundException e) {
					throw new Exception(EX_RUTA);
				}
				
				// Se vuelve a abrir el txt generado
				File file = new File(fileName);
				InputStream is = null;
				try {
					is = (InputStream) new FileInputStream(file);
				} 
				catch (FileNotFoundException e2) {
					throw new Exception(EX_RUTA);
				}
				
				pstmt.setBinaryStream(1, is, (int) file.length());
				// Se borra el txt generado
				file.delete();
				
				// Agregar el identificador de la ruta a la consulta
				pstmt.setInt(2, id);
				
				pstmt.executeUpdate();
				
			}
			catch (SQLException ex) {
				throw new Exception(EX_CONEXION);
			}
		}
		else {
			throw new Exception(EX_RUTA_NO_EXISTENTE);
		}
	}
	
	/**
	 * 
	 * La función seleccionarFichero selecciona el fichero kml de la entrada de la tabla ruta
	 * de la base de datos cuya clave primaria es id
	 * 
	 * @param id
	 * @throws Exception
	 */
	public Kml seleccionarFichero(int id) throws Exception {
		// Comprobar si existe una entrada con la clave primaria indicada
		if (existeEntrada(id)) {
			try (PreparedStatement pstmt = con.prepareStatement(SQL_SELECT_FILE, Statement.RETURN_GENERATED_KEYS)) {
				// Agregar el identificador de la ruta a la consulta
				pstmt.setInt(1, id);
							
				ResultSet rs = pstmt.executeQuery();
				// Extraer el resultado
				rs.next();
				InputStream is = rs.getBinaryStream(1);
				int x = (int) ((Math.random()*((100-999)+1))+100);
				String fileName = "tmp" + x + ".txt";
				try {
					FileUtils.copyInputStreamToFile(is, new File(fileName));
				} 
				catch (IOException e) {
					throw new Exception(EX_ZONARUTA);
				}
				File file = new File(fileName);
				Kml fileReturn = Kml.unmarshal(file);
				file.delete();
				
				return fileReturn;
			} 
			catch (SQLException e) {
				throw new Exception(EX_CONEXION);
			}
		}
		else {
			throw new Exception(EX_RUTA_NO_EXISTENTE);
		}
	}
	
	/**
	 * 
	 * La función actualizarEmail actualiza el valor del email de la entrada de la tabla ruta de la base
	 * de datos cuya clave primaria es id
	 * 
	 * @param id
	 * @param email
	 * @throws Exception
	 */
	public void actualizarEmail(int id, String email) throws Exception {
		// Comprobar si existe una entrada con la clave primaria indicada
		if (existeEntrada(id)) {
			try (PreparedStatement pstmt = con.prepareStatement(SQL_UPDATE_EMAIL, Statement.RETURN_GENERATED_KEYS)) {
				// Agregar a la consulta el email del usuario
				pstmt.setString(1, email);
				
				// Agregar el identificador de la ruta a la consulta
				pstmt.setInt(2, id);
				
				pstmt.executeUpdate();
			}
			catch (SQLException ex) {
				throw new Exception(EX_CONEXION);
			}
		}
		else {
			throw new Exception(EX_RUTA_NO_EXISTENTE);
		}
	}
	
	/**
	 * 
	 * La función seleccionarEmail selecciona el valor del email de la entrada de la tabla ruta
	 * de la base de datos cuya clave primaria es id
	 * 
	 * @param id
	 * @throws Exception
	 */
	public Float seleccionarEmail(int id) throws Exception {
		// Comprobar si existe una entrada con la clave primaria indicada
		if (existeEntrada(id)) {
			try (PreparedStatement pstmt = con.prepareStatement(SQL_SELECT_EMAIL, Statement.RETURN_GENERATED_KEYS)) {
				// Agregar el identificador de la ruta a la consulta
				pstmt.setInt(1, id);
				
				ResultSet rs = pstmt.executeQuery();
				// Extraer el resultado
				rs.next();
				return Float.parseFloat(rs.getString(1));
			}
			catch (SQLException ex) {
				throw new Exception(EX_CONEXION);
			}
		}
		else {
			throw new Exception(EX_RUTA_NO_EXISTENTE);
		}
	}
}
