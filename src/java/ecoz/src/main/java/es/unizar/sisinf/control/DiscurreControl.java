/**
 * @file	DiscurreControl.java
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
import io.swagger.annotations.ApiParam;

import java.util.ArrayList;

import javax.ws.rs.Produces;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value="User Management System", description="Operations pertaining to user in User Managament System ")
public class DiscurreControl {

    DiscurreDAO discurreDAO = new DiscurreDAO();
    RutaDAO rutaDAO = new RutaDAO();
    ZonaDAO zonaDAO = new ZonaDAO();
	
	/**
	 * /registrarDiscurre?rutaId=r&zonaNombre=z
	 * @param rutaId
	 * @param zonaNombre
	 * @return JSONObject
	 */
	@ApiOperation(value = "Registrar una ruta, devuelve Json(DiscurreVO) o {E:error}", response = Object.class)
	@CrossOrigin
	@RequestMapping("/registrarDiscurre")
	@Produces("application/json")
    public Object registrarDiscurre(@ApiParam("rutaId") @RequestParam("rutaId") Integer rutaId,
    		@ApiParam("zonaNombre") @RequestParam("zonaNombre") String zonaNombre) {
		
		try {
			RutaVO rutaVO = rutaDAO.findById(new RutaVO(rutaId));
			ZonaVO zonaVO = zonaDAO.findById(new ZonaVO(zonaNombre, null, null, null, null, null));
			DiscurreVO discurreVO = new DiscurreVO(rutaVO, zonaVO);
			discurreDAO.create(discurreVO);
			return discurreVO;
		} 
		catch (Exception e) {
			return new ErrorVO(e.getMessage());
		}
	}
	
	/**
	 * /discurrePor?rutaId=r
	 * @param rutaId
	 * @return JSONArray
	 */
	@ApiOperation(value = "Discurre por zonas, devuelve Json(ArrayList<DiscurreVO>) o {E:error}", response = Object.class)
	@CrossOrigin
	@RequestMapping("/discurrePor")
	@Produces("application/json")
    public Object discurrePor(@ApiParam("rutaId") @RequestParam("rutaId") Integer rutaId) {
		
		try {
			ArrayList<DiscurreVO> discurreVOList = discurreDAO.findByRuta(new RutaVO(rutaId));
			return discurreVOList;
		} 
		catch (Exception e) {
			return new ErrorVO(e.getMessage());
		}
	}
	
	/**
	 * /borrarDiscurre?rutaId=r&zonaNombre=z
	 * @param rutaId
	 * @param zonaNombre
	 * @return JSONObject
	 */
	@ApiOperation(value = "Registrar una ruta, devuelve {ok:ok} o {E:error}", response = Object.class)
	@CrossOrigin
	@RequestMapping("/borrarDiscurre")
	@Produces("application/json")
    public Object borrarDiscurre(@ApiParam("rutaId") @RequestParam("rutaId") Integer rutaId,
    		@ApiParam("zonaNombre") @RequestParam("zonaNombre") String zonaNombre) {
		
		try {
			RutaVO rutaVO = rutaDAO.findById(new RutaVO(rutaId));
			ZonaVO zonaVO = zonaDAO.findById(new ZonaVO(zonaNombre, null, null, null, null, null));
			DiscurreVO discurreVO = discurreDAO.findById(new DiscurreVO(rutaVO, zonaVO));
			discurreDAO.delete(discurreVO);
			return new String[] {"ok", "ok"};
		} 
		catch (Exception e) {
			return new ErrorVO(e.getMessage());
		}
	}
}
