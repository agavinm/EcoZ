package es.unizar.sisinf.control;

import es.unizar.sisinf.data.vo.*;
import es.unizar.sisinf.data.dao.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import javax.ws.rs.Produces;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import de.micromata.opengis.kml.v_2_2_0.Kml;


@RestController
@Api(value="Route Management System", description="Operations pertaining to route in Route Managament System ")
public class RutaControl {
	
    RutaDAO rutaDAO = new RutaDAO();
    UsuarioDAO usuarioDAO = new UsuarioDAO();
	
	/**
	 * /registrarRuta?fichero=f&usuario=u
	 * @param fichero
	 * @param usuario
	 * @return JSONObject
	 */
	@ApiOperation(value = "Registrar una ruta, devuelve {O:Ok} o {E:error}", response = Object.class)
	@CrossOrigin
	@RequestMapping("/registrarRuta")
	@Produces("application/json")
    public Object registrarRuta(@ApiParam("fichero") @RequestParam("fichero") Kml fichero,
    		@ApiParam("usuario") @RequestParam("usuario") String email) {
		
		try {
			UsuarioVO usuarioaux = new UsuarioVO(email,null,null,null);
			UsuarioVO usuarioVO = usuarioDAO.findById(usuarioaux);
			
			RutaVO rutaVO = new RutaVO(fichero,usuarioVO);
			rutaDAO.create(rutaVO);
			return rutaVO;
		} 
		catch (Exception e) {
			return new ErrorVO(e.getMessage());
		}
	}
	
	/**
	 * /borrarRuta?id=i
	 * @param id
	 * @return JSONObject
	 */
	@ApiOperation(value = "Borrar una ruta, devuelve {O:Ok} o {E:error}", response = Object.class)
	@CrossOrigin
	@RequestMapping("/borrarRuta")
	@Produces("application/json")
    public Object borrarRuta(@ApiParam("id") @RequestParam("id") int id) {
		
		try {
			RutaVO rutaaux = new RutaVO(id);
			RutaVO rutaVO = rutaDAO.findById(rutaaux);
			rutaDAO.delete(rutaVO);
			return new String[] {"ok", "ok"};
		} 
		catch (Exception e) {
			return new ErrorVO(e.getMessage());
		}
	}
	
	/**
	 * /devolverRutas?email=e
	 * @param email
	 * @return JSONObject
	 */
	@ApiOperation(value = "Devolver rutas de usuario, devuelve {O:Ok} o {E:error}", response = Object.class)
	@CrossOrigin
	@RequestMapping("/devolverRuta")
	@Produces("application/json")
    public Object devolverRutas(@ApiParam("email") @RequestParam("email") String email) {
		
		try {
			// Falta función DAO
		} 
		catch (Exception e) {
			return new ErrorVO(e.getMessage());
		}
	}
	
}
