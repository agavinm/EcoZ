/**
 * @file	DiscurreVO.java
 * @author	Andrés Gavín Murillo 716358
 * @author	Eduardo Gimeno Soriano 721615
 * @author	Sergio Álvarez Peiro 740241
 * @date	Octubre 2019
 * @coms	Sistemas de información - Práctica 2
 */

package es.unizar.sisinf.data.vo;

public class DiscurreVO {
	
	/**
	 * @property ruta: Ruta, identificador de la entidad
	 */
	private RutaVO ruta;
	
	/**
	 * @property zona: Zona, identificador de la entidad
	 */
	private ZonaVO zona;

	/**
	 * constructor que permite crear objetos DISCURRE a partir de sus componentes
	 * @param ruta
	 * @param zona
	 */
	public DiscurreVO(RutaVO ruta, ZonaVO zona) {
		super();
		this.ruta = ruta;
		this.zona = zona;
	}
	
	/*
	 * Métodos de la clase
	 */

	public RutaVO getRuta() {
		return ruta;
	}

	public void setRuta(RutaVO ruta) {
		this.ruta = ruta;
	}

	public ZonaVO getZona() {
		return zona;
	}

	public void setZona(ZonaVO zona) {
		this.zona = zona;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ruta == null) ? 0 : ruta.hashCode());
		result = prime * result + ((zona == null) ? 0 : zona.hashCode());
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
		DiscurreVO other = (DiscurreVO) obj;
		if (ruta == null) {
			if (other.ruta != null)
				return false;
		} else if (!ruta.equals(other.ruta))
			return false;
		if (zona == null) {
			if (other.zona != null)
				return false;
		} else if (!zona.equals(other.zona))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DiscurreVO [ruta=" + ruta + ", zona=" + zona + "]";
	}
}
