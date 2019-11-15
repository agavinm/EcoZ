/**
 * @file	UsuarioVO.java
 * @author	Andrés Gavín Murillo 716358
 * @author	Eduardo Gimeno Soriano 721615
 * @author	Sergio Álvarez Peiro 740241
 * @date	Octubre 2019
 * @coms	Sistemas de información - Práctica 2
 */

package es.unizar.sisinf.data.vo;

public class UsuarioVO {
	
	/**
	 * @property email: Email, identificador único de la entidad
	 */
	private String email = null;
	
	/**
	 * @property nombre: Nombre
	 */
	private String nombre = null;
	
	/**
	 * @property apellidos: Apellidos
	 */
	private String apellidos = null;
	
	/**
	 * @property contrasena: Contraseña
	 */
	private String contrasena = null;

	/**
	 * constructor que permite crear objetos USUARIO a partir de sus componentes
	 * @param email
	 * @param nombre
	 * @param apellidos
	 * @param fechaNacimiento
	 * @param contrasena
	 */
	public UsuarioVO(String email, String nombre, String apellidos, String contrasena) {
		super();
		if (email != null) this.email = email.trim();
		if (nombre != null) this.nombre = nombre.trim();
		if (apellidos != null) this.apellidos = apellidos.trim();
		if (contrasena != null) this.contrasena = contrasena.trim();
	}
	
	/*
	 * Métodos de la clase
	 */

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		if (email != null) this.email = email.trim();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = null;
		if (nombre != null) this.nombre = nombre.trim();
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = null;
		if (apellidos != null) this.apellidos = apellidos.trim();
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		if (contrasena != null) this.contrasena = contrasena.trim();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsuarioVO other = (UsuarioVO) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UsuarioVO [email=" + email + ", nombre=" + nombre + ", apellidos=" + apellidos + 
				", contrasena=" + contrasena + "]";
	}
}
