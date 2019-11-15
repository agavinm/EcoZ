/**
 * @file	ZonaVO.java
 * @author	Andrés Gavín Murillo 716358
 * @author	Eduardo Gimeno Soriano 721615
 * @author	Sergio Álvarez Peiro 740241
 * @date	Octubre 2019
 * @coms	Sistemas de información - Práctica 2
 */

package es.unizar.sisinf.data.vo;

import de.micromata.opengis.kml.v_2_2_0.Kml;

public class ZonaVO {
	
	/**
	 * @property nombre: Nombre, identificador único de la entidad
	 */
	private String nombre = null;
	
	/**
	 * @property fichero: Fichero KML donde se guarda la zona
	 */
	private Kml fichero;
	
	/**
	 * @property co2: CO2
	 */
	private Float co2;
	
	/**
	 * @property o3: O3
	 */
	private Float o3;
	
	/**
	 * @property no2: NO2
	 */
	private Float no2;
	
	/**
	 * @property pm10: PM10
	 */
	private Float pm10;

	/**
	 * constructor que permite crear objetos ZONA a partir de sus componentes
	 * @param nombre
	 * @param fichero
	 * @param co2
	 * @param o3
	 * @param no2
	 * @param pm10
	 */
	public ZonaVO(String nombre, Kml fichero, Float co2, Float o3, Float no2, Float pm10) {
		super();
		if (nombre != null) this.nombre = nombre.trim();
		this.fichero = fichero;
		this.co2 = co2;
		this.o3 = o3;
		this.no2 = no2;
		this.pm10 = pm10;
	}
	
	/*
	 * Métodos de la clase
	 */

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		if (nombre != null) this.nombre = nombre.trim();
	}

	public Kml getFichero() {
		return fichero;
	}

	public void setFichero(Kml fichero) {
		this.fichero = fichero;
	}

	public Float getCo2() {
		return co2;
	}

	public void setCo2(Float co2) {
		this.co2 = co2;
	}

	public Float getO3() {
		return o3;
	}

	public void setO3(Float o3) {
		this.o3 = o3;
	}

	public Float getNo2() {
		return no2;
	}

	public void setNo2(Float no2) {
		this.no2 = no2;
	}

	public Float getPm10() {
		return pm10;
	}

	public void setPm10(Float pm10) {
		this.pm10 = pm10;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
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
		ZonaVO other = (ZonaVO) obj;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ZonaVO [nombre=" + nombre + ", fichero=" + fichero + ", co2=" + co2 + ", o3=" + o3 + ", no2=" + no2
				+ ", pm10=" + pm10 + "]";
	}
}
