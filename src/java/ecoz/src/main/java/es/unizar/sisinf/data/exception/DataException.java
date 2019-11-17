/**
 * @file	DataException.java
 * @author	Andrés Gavín Murillo 716358
 * @author	Eduardo Gimeno Soriano 721615
 * @author	Sergio Álvarez Peiro 740241
 * @date	Octubre 2019
 * @coms	Sistemas de información - Práctica 2
 */

package es.unizar.sisinf.data.exception;

public class DataException extends Exception {
	
	private static final long serialVersionUID = 1L;
	public static final String EX_CONEXION = "Error: No se ha podido conectar con la base de datos.";
	public static final String EX_FINALIZE = "Error: No se ha podido cerrar la conexión con la base de datos.";
	public static final String EX_RUTA = "Error: No se ha podido guardar la ruta.";
	public static final String EX_ZONARUTA = "Error: No se ha podido cargar la ruta o zona.";
	
	public DataException(String message) {
		super(message);
	}
}
