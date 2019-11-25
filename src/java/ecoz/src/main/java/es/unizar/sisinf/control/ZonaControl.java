/**
 * @file	ZonaControl.java
 * @author	Andrés Gavín Murillo 716358
 * @author	Eduardo Gimeno Soriano 721615
 * @author	Sergio Álvarez Peiro 740241
 * @date	Noviembre 2019
 * @coms	Sistemas de información - Práctica 3
 */

package es.unizar.sisinf.control;

import es.unizar.sisinf.data.vo.*;
import es.unizar.sisinf.data.dao.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;

import javax.ws.rs.Produces;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value="User Management System", description="Operations pertaining to user in User Managament System ")
public class ZonaControl {

    ZonaDAO zonaDAO = new ZonaDAO();
	
	/**
	 * /devolverZonas
	 * @return JSONObject
	 */
	@ApiOperation(value = "Devuelve las zonas, devuelve Json(ArrayList<ZonaVO>) o {error:error}", response = Object.class)
	@CrossOrigin
	@RequestMapping("/devolverZonas")
	@Produces("application/json")
    public Object devolverZonas() {
		
		try {
			ArrayList<ZonaVO> zonaVOList = zonaDAO.findAll();
			return zonaVOList;
		} 
		catch (Exception e) {
			return new ErrorVO(e.getMessage());
		}
	}
}
