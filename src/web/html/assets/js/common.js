/**
 * @file	common.js
 * @author	Andrés Gavín Murillo 716358
 * @author	Eduardo Gimeno Soriano 721615
 * @author	Sergio Álvarez Peiro 740241
 * @date	Noviembre 2019
 * @coms	Sistemas de información - Práctica 3
 */

// Variables globales
var _usuarioVO;

// Capturar submit para que no recargue la página
$("form").submit(function() { return false; });

// Función de cerrar sesión
function cerrarSesion() {
  // Eliminar usuario de cookie y volver a index
  localStorage.removeItem('usuario');
  window.location.replace("/ecoz/index.html");
}

// Carga usuario de las cookies y si no hay, vuelve a la pantalla de inicio
$(document).ready(function() {
  var stored = localStorage['usuario'];
  if (stored) {
    _usuarioVO = JSON.parse(stored);
    
    // En la página inicial se cierra la sesión anterior
    if (window.location.pathname == '/ecoz/index.html') {
      cerrarSesion();
    }
  }
  else if (window.location.pathname != '/ecoz/index.html' &&
           window.location.pathname != '/ecoz/contrasena.html') {
    window.location.replace("/ecoz/index.html");
  }
});
