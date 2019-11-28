/**
 * @file	RutaVO.java
 * @author	Andrés Gavín Murillo 716358
 * @author	Eduardo Gimeno Soriano 721615
 * @author	Sergio Álvarez Peiro 740241
 * @date	Octubre 2019
 * @coms	Sistemas de información - Práctica 2
 */

package es.unizar.sisinf.data.vo;

import java.util.Map;

import org.json.JSONObject;

public class RutaVO {
	
	/**
	 * @property id: identificador único de la entidad
	 */
	private Integer id;
	
	/**
	 * @property fichero: Fichero JSON en forma de mapa donde se guarda la ruta
	 */
	private Map<String,Object> fichero;
	
	/**
	 * @property usuario: Usuario al que pertenece la ruta
	 */
	private UsuarioVO usuario;

	/**
	 * constructor que permite crear objetos RUTA a partir de un fichero y un usuario
	 * @param fichero
	 * @param usuario
	 */
	public RutaVO(Map<String,Object> fichero, UsuarioVO usuario) {
		super();
		this.id = null;
		this.fichero = fichero;
		this.usuario = usuario;
	}

	/**
	 * constructor que permite crear objetos RUTA a partir de un fichero y un usuario
	 * @param fichero
	 * @param usuario
	 */
	public RutaVO(JSONObject fichero, UsuarioVO usuario) {
		super();
		this.id = null;
		this.fichero = fichero.toMap();
		this.usuario = usuario;
	}
	
	/**
	 * constructor que permite crear objetos RUTA a partir de la id
	 * @param id
	 */
	public RutaVO(int id) {
		super();
		this.id = id;
		this.fichero = null;
		this.usuario = null;
	}
	
	/*
	 * Métodos de la clase
	 */

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Map<String,Object> getFichero() {
		return fichero;
	}

	public void setFichero(Map<String,Object> fichero) {
		this.fichero = fichero;
	}

	public UsuarioVO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioVO usuario) {
		this.usuario = usuario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		RutaVO other = (RutaVO) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RutaVO [id=" + id + ", fichero=" + fichero + ", usuario=" + usuario + "]";
	}
}
