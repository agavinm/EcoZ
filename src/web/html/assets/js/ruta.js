/**
 * @file	ruta.js
 * @author	Andrés Gavín Murillo 716358
 * @author	Eduardo Gimeno Soriano 721615
 * @author	Sergio Álvarez Peiro 740241
 * @date	Noviembre 2019
 * @coms	Sistemas de información - Práctica 3
 */

var zonasInfo;
var zonesOfRoute = [];
var pollution = 0;
var rutaSeleccionada;

// Recuperar zonas y ruta
function init() {
  rutaSeleccionada = JSON.parse(localStorage['rutaSeleccionada']);
  if (rutaSeleccionada) {
    // Obtener información de todas las zonas
    var co2 = 0, pm10 = 0, o3 = 0, no2 = 0;
    
    $.ajax({
      type: "GET",  
      url: "/ecoz/devolverZonas",
      success: function(zonaVOList) {
        if (zonaVOList.hasOwnProperty('error')) {
          alert(zonaVOList.error);
        }
        else {
          // Guardar zonasInfo como JSON serializado
          zonasInfo = zonaVOList;
          for (var i = 0; i < zonasInfo.length; i++) {
            // Datos totales
            co2 = co2 + zonasInfo[i].co2;
            pm10 = pm10 + zonasInfo[i].pm10;
            o3 = o3 + zonasInfo[i].o3;
            no2 = no2 + zonasInfo[i].no2;
          }
                  
          document.getElementById("co2-media").value = co2 / zonasInfo.length;
          document.getElementById("pm10-media").value = pm10 / zonasInfo.length;
          document.getElementById("o3-media").value = o3 / zonasInfo.length;
          document.getElementById("no2-media").value = no2 / zonasInfo.length;
                      
          $.ajax({
            type: "GET",  
            url: "/ecoz/discurrePor",
            data: {rutaId: rutaSeleccionada.id},
            success: function(discurreVOList) {
              if (discurreVOList.hasOwnProperty('error')) {
                alert(discurreVOList.error);
              }
              else {
                for (var z = 0; z < zonasInfo.length; z++) {
                  for (var z_r = 0; z_r < discurreVOList.length; z_r++) {
                    if (discurreVOList[z_r].zona.nombre == zonasInfo[z].nombre) {
                      zonesOfRoute.push(zonasInfo[z]);
                    }
                  }
                }
    
                for (var i = 0; i < zonesOfRoute.length; i++) {
                    pollution = pollution + zonesOfRoute[i].co2;
                }
                if (zonesOfRoute.length != 0) { 
                  pollution = pollution / zonesOfRoute.length;
                  loadRoute(rutaSeleccionada.fichero.routes[0].legs[0].distance.text, rutaSeleccionada.fichero.routes[0].legs[0].duration.text, pollution);
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
  else {
    alert('Error: No se ha podido cargar la ruta.');
  }
}

// Mostrar la información de la ruta cargada
function loadRoute (distance,time,pollution) {
    var r1 = document.getElementById("route1");
    var t1 = document.getElementById("time1");
    var d1 = document.getElementById("distance1");
    var p1 = document.getElementById("pollution1");
    t1.innerHTML = "Tiempo:<br>".concat(time);
    d1.innerHTML = "Distancia:<br>".concat(distance);
    p1.innerHTML = pollution.toFixed(2);
}


// Inicializar el mapa centrado en Zaragoza
function initMapRoute() {
    init();
    
    // Botón para borrar la ruta
    document.getElementById("delete").onclick = deleteRoute;

    // Centro del mapa
    var zaragoza = {lat: 41.65606, lng: -0.87734};
    
    // Generar el mapa
    var map = new google.maps.Map(document.getElementById("map"), {zoom: 12, center: zaragoza});
    
    // Renderizar la ruta
    var directionsRenderer = new google.maps.DirectionsRenderer();
    directionsRenderer.setDirections(rutaSeleccionada.fichero);
    directionsRenderer.setMap(map);
}

function regresar() {
  window.location.replace("/ecoz/principal.html");
}

function deleteRoute() {
    // Borrar todos los discurrePor
    for (var j in zonesOfRoute) {
        $.ajax({
          type: "GET",  
          url: "/ecoz/borrarDiscurre",
          data: {rutaId: rutaSeleccionada.id, zonaNombre: zonesOfRoute[j].nombre},
          error: function() {  
            alert('Error: No se ha podido establecer la conexión.');
          }
        });
    }
    // Borrar ruta
    $.ajax({
      type: "GET",  
      url: "/ecoz/borrarRuta",
      data: {rutaId: rutaSeleccionada.id, email: _usuarioVO.email, contrasena: _usuarioVO.contrasena},
      success: function() {
        window.location.replace("/ecoz/perfil.html");
      },
      error: function() {  
        alert('Error: No se ha podido establecer la conexión.');
      }
    });
}
