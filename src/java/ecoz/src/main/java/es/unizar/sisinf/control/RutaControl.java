/**
 * @file	RutaControl.java
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

import org.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value="User Management System", description="Operations pertaining to user in User Managament System ")
public class RutaControl {

    RutaDAO rutaDAO = new RutaDAO();
    UsuarioDAO usuarioDAO = new UsuarioDAO();
	
	/**
	 * /registrarRuta?fichero=f&usuarioEmail=e&usuarioContrasena=c
	 * @param fichero
	 * @param email
	 * @param contrasena
	 * @return JSONObject
	 */
	@ApiOperation(value = "Registrar una ruta, devuelve Json(RutaVO) o {E:error}", response = Object.class)
	@CrossOrigin
	@RequestMapping("/registrarRuta")
	@Produces("application/json")
    public Object registrarRuta(@ApiParam("fichero") @RequestParam("fichero") JSONObject fichero,
    		@ApiParam("email") @RequestParam("email") String email,
    		@ApiParam("contrasena") @RequestParam("contrasena") String contrasena) {
		
		try {
			UsuarioVO usuarioVO = usuarioDAO.findById(new UsuarioVO(email, null, null, contrasena));
			if (usuarioVO.getContrasena().equals(contrasena)) {
				RutaVO rutaVO = new RutaVO(fichero, usuarioVO);
				rutaDAO.create(rutaVO);
				return rutaVO;
			}
			else
				throw new Exception("Error: Contraseña incorrecta.");
		} 
		catch (Exception e) {
			return new ErrorVO(e.getMessage());
		}
	}
	
	/**
	 * /devolverRutas?usuarioEmail=e&usuarioContrasena=c
	 * @param email
	 * @param contrasena
	 * @return JSONObject
	 */
	@ApiOperation(value = "Devuelve las rutas de un usuario, devuelve Json(ArrayList<RutaVO>) o {error:error}", response = Object.class)
	@CrossOrigin
	@RequestMapping("/devolverRutas")
	@Produces("application/json")
    public Object devolverRutas(@ApiParam("email") @RequestParam("email") String email,
    		@ApiParam("contrasena") @RequestParam("contrasena") String contrasena) {
		
		try {
			UsuarioVO usuarioVO = usuarioDAO.findById(new UsuarioVO(email, null, null, contrasena));
			if (usuarioVO.getContrasena().equals(contrasena)) {
				ArrayList<RutaVO> rutaVOList = rutaDAO.findByUser(usuarioVO);
				return rutaVOList;
			}
			else
				throw new Exception("Error: Contraseña incorrecta.");
		} 
		catch (Exception e) {
			return new ErrorVO(e.getMessage());
		}
	}
	
	/**
	 * /borrarRuta?rutaId=r&usuarioEmail=e&usuarioContrasena=c
	 * @param rutaId
	 * @param email
	 * @param contrasena
	 * @return JSONObject
	 */
	@ApiOperation(value = "Borra una ruta, devuelve {ok:ok} o {error:error}", response = Object.class)
	@CrossOrigin
	@RequestMapping("/borrarRuta")
	@Produces("application/json")
    public Object borrarRuta(@ApiParam("rutaId") @RequestParam("rutaId") Integer rutaId,
    		@ApiParam("email") @RequestParam("email") String email,
    		@ApiParam("contrasena") @RequestParam("contrasena") String contrasena) {
		
		try {
			UsuarioVO usuarioVO = usuarioDAO.findById(new UsuarioVO(email, null, null, contrasena));
			if (usuarioVO.getContrasena().equals(contrasena)) {
				RutaVO rutaVO = rutaDAO.findById(new RutaVO(rutaId));
				if (rutaVO.getUsuario().equals(usuarioVO)) {
					rutaDAO.delete(rutaVO);
					return new String[] {"ok", "ok"};
				}
				else
					throw new Exception("Error: La ruta no pertenece al usuario.");
			}
			else
				throw new Exception("Error: Contraseña incorrecta.");
		} 
		catch (Exception e) {
			return new ErrorVO(e.getMessage());
		}
	}
}
