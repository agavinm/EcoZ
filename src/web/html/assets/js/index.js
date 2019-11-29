/**
 * @file	index.js
 * @author	Andrés Gavín Murillo 716358
 * @author	Eduardo Gimeno Soriano 721615
 * @author	Sergio Álvarez Peiro 740241
 * @date	Noviembre 2019
 * @coms	Sistemas de información - Práctica 3
 */

// Función de inicio de sesión
function acceder() {
  var email = document.getElementById("acceder-email").value;
  var password = document.getElementById("acceder-password").value;
  
  if (password.length >= 5 && email.includes('@') &&
      email.slice(-1) != '@' && email.charAt(0) != '@') {
    // Si cumple el formato de email/contraseña, realiza la petición
    $.ajax({
      type: "GET",  
      url: "/ecoz/iniciarUsuario",
      data: {email: email, contrasena: md5(password).substr(0, 10)},
      success: function(usuarioVO) {
        if (usuarioVO.hasOwnProperty('error')) {
          alert(usuarioVO.error);
        }
        else {
          // Guardar usuario como JSON serializado
          localStorage['usuario'] = JSON.stringify(usuarioVO);
          
          window.location.replace("/ecoz/principal.html");
        }
      },
      
      error: function(){  
        alert('Error: No se ha podido establecer la conexión.');
      }
    });
  }
};

// Función de registrar
function registrar() {
  var email = document.getElementById("registrar-email").value;
  var password = document.getElementById("registrar-password1").value;
  var password2 = document.getElementById("registrar-password2").value;
  
  if (password.length >= 5 && password == password2 && email.includes('@') &&
      email.slice(-1) != '@' && email.charAt(0) != '@') {
    // Si cumple el formato de email/contraseña, realiza la petición
    $.ajax({
      type: "GET",  
      url: "/ecoz/registrarUsuario",
      data: {email: email, contrasena: md5(password).substr(0, 10)},
      success: function(usuarioVO) {
        if (usuarioVO.hasOwnProperty('error')) {
          alert(usuarioVO.error);
        }
        else {
          // Guardar usuario como JSON serializado
          localStorage['usuario'] = JSON.stringify(usuarioVO);
          
          window.location.replace("/ecoz/principal.html");
        }
      },
      
      error: function(){  
        alert('Error: No se ha podido establecer la conexión.');
      }
    });
  }
  else if (password != password2) {
    alert('Error: Las contraseñas no coinciden.');
  }
};
