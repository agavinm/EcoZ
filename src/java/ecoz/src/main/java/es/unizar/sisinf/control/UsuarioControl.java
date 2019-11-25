/**
 * @file	UsuarioControl.java
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

import javax.ws.rs.Produces;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value="User Management System", description="Operations pertaining to user in User Managament System ")
public class UsuarioControl {

    UsuarioDAO usuarioDAO = new UsuarioDAO();
	
	/**
	 * /registrarUsuario?email=e&contrasena=c
	 * @param email
	 * @param contrasena
	 * @return JSONObject
	 */
	@ApiOperation(value = "Registrar un usuario, devuelve Json(UsuarioVO) o {E:error}", response = Object.class)
	@CrossOrigin
	@RequestMapping("/registrarUsuario")
	@Produces("application/json")
    public Object registrarUsuario(@ApiParam("email") @RequestParam("email") String email,
    		@ApiParam("contrasena") @RequestParam("contrasena") String contrasena) {
		
		try {
			UsuarioVO usuarioVO = new UsuarioVO(email, null, null, contrasena);
			usuarioDAO.create(usuarioVO);
			return usuarioVO;
		} 
		catch (Exception e) {
			return new ErrorVO(e.getMessage());
		}
	}
	
	/**
	 * /iniciarUsuario?email=e&contrasena=c
	 * @param email
	 * @param contrasena
	 * @return JSONObject
	 */
	@ApiOperation(value = "Iniciar un usuario, devuelve Json(UsuarioVO) o {error:error}", response = Object.class)
	@CrossOrigin
	@RequestMapping("/iniciarUsuario")
	@Produces("application/json")
    public Object iniciarUsuario(@ApiParam("email") @RequestParam("email") String email,
    		@ApiParam("contrasena") @RequestParam("contrasena") String contrasena) {
		
		try {
			UsuarioVO usuarioVO = usuarioDAO.findById(new UsuarioVO(email, null, null, contrasena));
			if (usuarioVO.getContrasena().equals(contrasena)) 
				return usuarioVO;
			else
				throw new Exception("Error: Contraseña incorrecta.");
		} 
		catch (Exception e) {
			return new ErrorVO(e.getMessage());
		}
	}
	
	/**
	 * /actualizarUsuario?email=e&contrasena=c&nombre=n&apellidos=a
	 * @param email
	 * @param contrasena
	 * @param nombre
	 * @param apellidos
	 * @return JSONObject
	 */
	@ApiOperation(value = "Actualizar un usuario, devuelve Json(UsuarioVO) o {error:error}", response = Object.class)
	@CrossOrigin
	@RequestMapping("/actualizarUsuario")
	@Produces("application/json")
    public Object actualizarUsuario(@ApiParam("email") @RequestParam("email") String email,
    		@ApiParam("contrasena") @RequestParam("contrasena") String contrasena,
			@ApiParam(value = "nombre", required = false) @RequestParam(value = "nombre", required = false) String nombre,
			@ApiParam(value = "apellidos", required = false) @RequestParam(value = "apellidos", required = false) String apellidos) {
		
		try {
			UsuarioVO usuarioVO = usuarioDAO.findById(new UsuarioVO(email, null, null, contrasena));
			if (usuarioVO.getContrasena().equals(contrasena)) {
				usuarioVO.setNombre(nombre);
				usuarioVO.setApellidos(apellidos);
				usuarioDAO.update(usuarioVO);
				return usuarioVO;
			}
			else
				throw new Exception("Error: Contraseña incorrecta.");
		} 
		catch (Exception e) {
			return new ErrorVO(e.getMessage());
		}
	}
	
	/**
	 * /actualizarContrasena?email=e&contrasenaActual=cA&contrasenaNueva=cN
	 * @param email
	 * @param contrasenaActual
	 * @param contrasenaActual
	 * @return JSONObject
	 */
	@ApiOperation(value = "Actualiza contraseña de un usuario, devuelve Json(UsuarioVO) o {error:error}", response = Object.class)
	@CrossOrigin
	@RequestMapping("/actualizarContrasena")
	@Produces("application/json")
    public Object actualizarContrasena(@ApiParam("email") @RequestParam("email") String email,
    		@ApiParam("contrasenaActual") @RequestParam("contrasenaActual") String contrasenaActual,
    		@ApiParam("contrasenaNueva") @RequestParam("contrasenaNueva") String contrasenaNueva) {
		
		try {
			UsuarioVO usuarioVO = usuarioDAO.findById(new UsuarioVO(email, null, null, contrasenaActual));
			if (usuarioVO.getContrasena().equals(contrasenaActual)) {
				usuarioVO.setContrasena(contrasenaNueva);
				usuarioDAO.update(usuarioVO);
				return usuarioVO;
			}
			else
				throw new Exception("Error: Contraseña actual incorrecta.");
		} 
		catch (Exception e) {
			return new ErrorVO(e.getMessage());
		}
	}
	
	/**
	 * /borrarUsuario?email=e&contrasena=c
	 * @param email
	 * @param contrasena
	 * @return JSONObject
	 */
	@ApiOperation(value = "Borra un usuario, devuelve {ok:ok} o {error:error}", response = Object.class)
	@CrossOrigin
	@RequestMapping("/borrarUsuario")
	@Produces("application/json")
    public Object borrarUsuario(@ApiParam("email") @RequestParam("email") String email,
    		@ApiParam("contrasena") @RequestParam("contrasena") String contrasena) {
		
		try {
			UsuarioVO usuarioVO = usuarioDAO.findById(new UsuarioVO(email, null, null, contrasena));
			if (usuarioVO.getContrasena().equals(contrasena)) {
				usuarioDAO.delete(usuarioVO);
				return new String[] {"ok", "ok"};
			}
			else
				throw new Exception("Error: Contraseña incorrecta.");
		} 
		catch (Exception e) {
			return new ErrorVO(e.getMessage());
		}
	}
}
