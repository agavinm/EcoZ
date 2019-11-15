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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value="User Management System", description="Operations pertaining to user in User Managament System ")
public class UsuarioControl {

    UsuarioDAO usuarioDAO = new UsuarioDAO();
	
	//Registra a un usuario recibiendo como parámetros obligatorios el nombre de usuario, el correo
	//la contraseña, el nombre y los apellidos, y siendo opcionales el teléfono, el código postal
	//la ciudad, la provincia, latitud y longitud, y la imagen de perfil.
	//localhost:8080/registrar?un=karny2&pass=caca&cor=cececw@gmail.com&na=saul&lna=alarcon
	@ApiOperation(value = "Register a user, returns {O:Ok} if ok or error message if not ok", response = String.class)
	@CrossOrigin
	@RequestMapping("/registrar")
    public String registrar(@ApiParam(value = "email", required = false, defaultValue = "") @RequestParam("email") String email,
    		@ApiParam(value = "contrasena", required = false, defaultValue = "") @RequestParam("contrasena") String contrasena) {
		
		try {
			usuarioDAO.create(new UsuarioVO(email, null, null, contrasena)); // TODO: Guardar solo contraseña
		} 
		catch (Exception e) {
			return "{E:" + e.getMessage() + "}";
		}
		
        return "{O:Ok}";
	}
	
	
}
