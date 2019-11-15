/**
 * @file	RutaVO.java
 * @author	Andrés Gavín Murillo 716358
 * @author	Eduardo Gimeno Soriano 721615
 * @author	Sergio Álvarez Peiro 740241
 * @date	Octubre 2019
 * @coms	Sistemas de información - Práctica 2
 */

package es.unizar.sisinf.data.vo;

import de.micromata.opengis.kml.v_2_2_0.Kml;

public class RutaVO {
	
	/**
	 * @property id: identificador único de la entidad
	 */
	private Integer id;
	
	/**
	 * @property fichero: Fichero KML donde se guarda la ruta
	 */
	private Kml fichero;
	
	/**
	 * @property usuario: Usuario al que pertenece la ruta
	 */
	private UsuarioVO usuario;

	/**
	 * constructor que permite crear objetos RUTA a partir de sus componentes
	 * @param id
	 * @param fichero
	 * @param usuario
	 */
	public RutaVO(Integer id, Kml fichero, UsuarioVO usuario) {
		super();
		this.id = id;
		this.fichero = fichero;
		this.usuario = usuario;
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

	public Kml getFichero() {
		return fichero;
	}

	public void setFichero(Kml fichero) {
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
