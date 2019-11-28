/**
 * @file	perfil.js
 * @author	Andrés Gavín Murillo 716358
 * @author	Eduardo Gimeno Soriano 721615
 * @author	Sergio Álvarez Peiro 740241
 * @date	Noviembre 2019
 * @coms	Sistemas de información - Práctica 3
 */


var rutas, zonas;
var ultimoIndiceRuta;

// Carga usuario de las cookies y si no hay, vuelve a la pantalla de inicio
$(document).ready(function() {
  if (_usuarioVO) {
    if (_usuarioVO.nombre) {
      document.getElementById("modificar-nombre").value = _usuarioVO.nombre;
    }

    if (_usuarioVO.apellidos) {
      document.getElementById("modificar-apellidos").value = _usuarioVO.apellidos;
    }
    
    $.ajax({
      type: "GET",  
      url: "/ecoz/devolverZonas",
      success: function(zonaVOList) {
        if (zonaVOList.hasOwnProperty('error')) {
          alert(zonaVOList.error);
        }
        else {
          // Guardar zonas como JSON serializado
          zonas = zonaVOList;
          
          $.ajax({
            type: "GET",  
            url: "/ecoz/devolverRutas",
            data: {email: _usuarioVO.email, contrasena: _usuarioVO.contrasena},
            success: function(rutaVOList) {
              if (rutaVOList.hasOwnProperty('error')) {
                alert(rutaVOList.error);
              }
              else {
                rutas = rutaVOList;
                
                if (rutas.length == 1) {
                  cargarRuta(rutas[0], 1);
                  ultimoIndiceRuta = 0;
                }
                else if (rutas.length == 2) {
                  cargarRuta(rutas[0], 1);
                  cargarRuta(rutas[1], 2);
                  ultimoIndiceRuta = 1;
                }
                else if (rutas.length > 2) {
                  cargarRuta(rutas[0], 1);
                  cargarRuta(rutas[1], 2);
                  document.getElementById("flechas").style.display = "block";
                  ultimoIndiceRuta = 1;
                }
              }
            },
            
            error: function(){  
              alert('Error: No se ha podido establecer la conexión.');
            }
          });
        }
      },
      
      error: function(){  
        alert('Error: No se ha podido establecer la conexión.');
      }
    });
  }
});

// Mostrar la información de la ruta numRuta
function cargarRuta(ruta, numRuta) {
  $.ajax({
    type: "GET",  
    url: "/ecoz/discurrePor",
    data: {rutaId: ruta.id},
    success: function(discurreVOList) {
      if (discurreVOList.hasOwnProperty('error')) {
        alert(discurreVOList.error);
      }
      else {
        var contaminacion = 0;
        for (var z = 0; z < zonas.length; z++) {
          for (var z_r = 0; z_r < discurreVOList.length; z_r++) {
            if (discurreVOList[z_r].zona.nombre == zonas[z].nombre) {
              contaminacion = contaminacion + zonas[z].co2;
            }
          }
        }
      
        if (numRuta == 1) {
          document.getElementById("ruta1").style.display = "block";
          document.getElementById("rutaNombre1").innerHTML = "Ruta ".concat((ultimoIndiceRuta).toString());
          document.getElementById("origen1").innerHTML = "Origen:<br>".concat(ruta.fichero.routes[0].legs[0].start_address);
          document.getElementById("destino1").innerHTML = "Destino:<br>".concat(ruta.fichero.routes[0].legs[0].end_address);
          document.getElementById("distancia1").innerHTML = "Distancia total:<br>".concat(ruta.fichero.routes[0].legs[0].distance.text);
          document.getElementById("tiempo1").innerHTML = "Tiempo de recorrido:<br>".concat(ruta.fichero.routes[0].legs[0].duration.text);
          document.getElementById("contaminacion1").innerHTML = contaminacion.toFixed(2);
        }
        else if (numRuta == 2) {
          document.getElementById("ruta2").style.display = "block";
          document.getElementById("rutaNombre2").innerHTML = "Ruta ".concat((ultimoIndiceRuta+1).toString());
          document.getElementById("origen2").innerHTML = "Origen:<br>".concat(ruta.fichero.routes[0].legs[0].start_address);
          document.getElementById("destino2").innerHTML = "Destino:<br>".concat(ruta.fichero.routes[0].legs[0].end_address);
          document.getElementById("distancia2").innerHTML = "Distancia total:<br>".concat(ruta.fichero.routes[0].legs[0].distance.text);
          document.getElementById("tiempo2").innerHTML = "Tiempo de recorrido:<br>".concat(ruta.fichero.routes[0].legs[0].duration.text);
          document.getElementById("contaminacion2").innerHTML = contaminacion.toFixed(2);
        }
      }
    },
      
    error: function(){  
      alert('Error: No se ha podido establecer la conexión.');
    }
  });
}

// Retrocede en las rutas mostradas
function retrocederRutas() {
  if (ultimoIndiceRuta > 1) {
    ultimoIndiceRuta = ultimoIndiceRuta - 1;
    cargarRuta(rutas[ultimoIndiceRuta], 2);
    cargarRuta(rutas[ultimoIndiceRuta-1], 1);
  }
}

// Retrocede en las rutas mostradas
function avanzarRutas() {
  if (ultimoIndiceRuta < rutas.length-1) {
    ultimoIndiceRuta = ultimoIndiceRuta + 1;
    cargarRuta(rutas[ultimoIndiceRuta], 2);
    cargarRuta(rutas[ultimoIndiceRuta-1], 1);
  }
}

// Muestra la ruta en el mapa
function mostrarRuta(numRuta) {
  if (numRuta == 1) {
    if (ultimoIndiceRuta == 0) {
      localStorage['rutaSeleccionada'] = JSON.stringify(rutas[ultimoIndiceRuta]);
    }
    else {
      localStorage['rutaSeleccionada'] = JSON.stringify(rutas[ultimoIndiceRuta-1]);
    }
  }
  else if (numRuta == 2) {
    localStorage['rutaSeleccionada'] = JSON.stringify(rutas[ultimoIndiceRuta]);
  }
  window.location.replace("/ecoz/ruta.html");
}

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
