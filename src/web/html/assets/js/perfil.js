/**
 * @file	perfil.js
 * @author	Andrés Gavín Murillo 716358
 * @author	Eduardo Gimeno Soriano 721615
 * @author	Sergio Álvarez Peiro 740241
 * @date	Noviembre 2019
 * @coms	Sistemas de información - Práctica 3
 */

// Carga usuario de las cookies y si no hay, vuelve a la pantalla de inicio
$(document).ready(function() {
  if (_usuarioVO) {
    if (_usuarioVO.nombre) {
      document.getElementById("modificar-nombre").value = _usuarioVO.nombre;
    }

    if (_usuarioVO.apellidos) {
      document.getElementById("modificar-apellidos").value = _usuarioVO.apellidos;
    }
  }
});

// Función de modificar la información del usuario
function modificarUsuario() {
  var email = _usuarioVO.email;
  var password = document.getElementById("modificar-password").value;
  var nombre = document.getElementById("modificar-nombre").value;
  var apellidos = document.getElementById("modificar-apellidos").value;
  
  if (password.length >= 5) {
    // Si cumple el formato de contraseña, realiza la petición
    $.ajax({
      type: "GET",  
      url: "/ecoz/actualizarUsuario",
      data: {email: email, contrasena: md5(password).substr(0, 10), 
             nombre: nombre, apellidos: apellidos},
      success: function(usuarioVO) {
        if (usuarioVO.hasOwnProperty('error')) {
          alert(usuarioVO.error);
        }
        else {
          // Guardar usuario como JSON serializado
          _usuarioVO = usuarioVO;
          localStorage['usuario'] = _usuarioVO;
          alert('Usuario actualizado.');
          window.location.reload();
        }
      },
      
      error: function(){  
        alert('Error: No se ha podido establecer la conexión.');
      }
    });
  }
}

// Función de modificar la contraseña del usuario
function modificarPassword() {
  var email = _usuarioVO.email;
  var passwordActual = document.getElementById("modificar-password-actual").value;
  var passwordNueva1 = document.getElementById("modificar-password-nueva1").value;
  var passwordNueva2 = document.getElementById("modificar-password-nueva2").value;
  
  if (passwordActual.length >= 5 && passwordNueva1.length >= 5 &&
      passwordNueva1 == passwordNueva2) {
    // Si cumple el formato de contraseña, realiza la petición
    $.ajax({
      type: "GET",  
      url: "/ecoz/actualizarContrasena",
      data: {email: email, contrasenaActual: md5(passwordActual).substr(0, 10), 
             contrasenaNueva: md5(passwordNueva1).substr(0, 10)},
      success: function(usuarioVO) {
        if (usuarioVO.hasOwnProperty('error')) {
          alert(usuarioVO.error);
        }
        else {
          // Guardar usuario como JSON serializado
          _usuarioVO = usuarioVO;
          localStorage['usuario'] = _usuarioVO;
          alert('Contraseña actualizada.');
          window.location.reload();
        }
      },
      
      error: function(){  
        alert('Error: No se ha podido establecer la conexión.');
      }
    });
  }
  else if (passwordNueva1 != passwordNueva2) {
    alert('Error: Las contraseñas no coinciden.');
  }
}

// Función de eliminar el usuario
function eliminarUsuario() {
  var _email = _usuarioVO.email;
  var email = document.getElementById("eliminar-email").value;
  var password = document.getElementById("eliminar-password").value;
  var confirmar = document.getElementById('eliminar-confirmar').checked;
  
  if (confirmar && password.length >= 5 && 
      _email.includes('@') && email.includes('@') &&
      _email.slice(-1) != '@' && _email.charAt(0) != '@' &&
      email.slice(-1) != '@' && email.charAt(0) != '@') {
    // Si cumple el formato de email/contraseña, realiza la petición
    if (_email != email) {
      alert('Error: No coincide el email con el del usuario registrado.');
    }
    else {
      $.ajax({
      type: "GET",  
      url: "/ecoz/borrarUsuario",
      data: {email: email, contrasena: md5(password).substr(0, 10)},
      success: function(usuarioVO) {
        if (usuarioVO.hasOwnProperty('error')) {
          alert(usuarioVO.error);
        }
        else {
          alert('Usuario eliminado.');
          cerrarSesion();
        }
      },
      
      error: function(){  
        alert('Error: No se ha podido establecer la conexión.');
      }
    });
    }
  }
}
