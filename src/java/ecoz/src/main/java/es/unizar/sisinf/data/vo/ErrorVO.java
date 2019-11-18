/**
 * @file	ErrorVO.java
 * @author	Andrés Gavín Murillo 716358
 * @author	Eduardo Gimeno Soriano 721615
 * @author	Sergio Álvarez Peiro 740241
 * @date	Noviembre 2019
 * @coms	Sistemas de información - Práctica 3
 */

package es.unizar.sisinf.data.vo;

public class ErrorVO {
	
	/**
	 * @property error: tipo de error
	 */
	private String error;

	/**
	 * constructor que permite crear objetos ERROR a partir de sus componentes
	 * @param error
	 */
	public ErrorVO(String error) {
		super();
		this.error = error;
	}
	
	/*
	 * Métodos de la clase
	 */

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((error == null) ? 0 : error.hashCode());
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
		ErrorVO other = (ErrorVO) obj;
		if (error == null) {
			if (other.error != null)
				return false;
		} else if (!error.equals(other.error))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ErrorVO [error=" + error + "]";
	}
}
