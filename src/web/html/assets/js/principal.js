/**
 * @file	principal.js
 * @author	Andrés Gavín Murillo 716358
 * @author	Eduardo Gimeno Soriano 721615
 * @author	Sergio Álvarez Peiro 740241
 * @date	Noviembre 2019
 * @coms	Sistemas de información - Práctica 3
 */

var zonasInfo;

$(document).ready(function() {
  var co2 = 0, pm10 = 0, o3 = 0, no2 = 0;
  
  $.ajax({
      type: "GET",  
      url: "/ecoz/devolverzonasInfo",
      success: function(zonaVOList) {
        if (zonaVOList.hasOwnProperty('error')) {
          alert(zonaVOList.error);
        }
        else {
          // Guardar zonasInfo como JSON serializado
          zonasInfo = JSON.stringify(zonaVOList);
          for (var i = 0; i < zonasInfo.counters.length; i++) {
            zonasInfo.counters[i] = JSON.stringify(zonasInfo.counters[i]);
            
            // Datos totales
            co2 = co2 + zonasInfo.counters[i].co2;
            pm10 = pm10 + zonasInfo.counters[i].pm10;
            o3 = o3 + zonasInfo.counters[i].o3;
            no2 = no2 + zonasInfo.counters[i].no2;
          }
          
          document.getElementById("co2-media").innerHTML = co2 / zonasInfo.counters.length;
          document.getElementById("pm10-media").innerHTML = pm10 / zonasInfo.counters.length;
          document.getElementById("o3-media").innerHTML = o3 / zonasInfo.counters.length;
          document.getElementById("no2-media").innerHTML = no2 / zonasInfo.counters.length;
          
        }
      },
      
      error: function(){  
        alert('Error: No se ha podido establecer la conexión.');
      }
    });
});

// Inicializar el mapa centrado en Zaragoza
function initMap() {
    // Botones para calcular rutas y reiniciar
    document.getElementById("calcular").onclick = calcularRutas;
    document.getElementById("reiniciar").onclick = reiniciarMapa;

    // Centro del mapa
    var zaragoza = {lat: 41.65606, lng: -0.87734};
    
    // Generar el mapa
    var map = new google.maps.Map(document.getElementById("map"), {zoom: 12, center: zaragoza});
    
    // Renderizador y servicio de direcciones
    var directionsService = new google.maps.DirectionsService();
    
    var line1 = new google.maps.Polyline({
        strokeColor: '#F47FFF',
        strokeOpacity: 0.7,
        strokeWeight: 8
        });

    var line2 = new google.maps.Polyline({
        strokeColor: '#1abc9c',
        strokeOpacity: 0.7,
        strokeWeight: 8
        });

    var line3 = new google.maps.Polyline({
        strokeColor: '#fc8403',
        strokeOpacity: 0.7,
        strokeWeight: 8
        });
    
    var directionsRenderer = [];
    directionsRenderer[0] = new google.maps.DirectionsRenderer({polylineOptions: line1});
    directionsRenderer[1] = new google.maps.DirectionsRenderer({polylineOptions: line2});
    directionsRenderer[2] = new google.maps.DirectionsRenderer({polylineOptions: line3});
    var markerOrigin;
    var markerDestination;
    
    // Añadir el marcador del origen
    function placeOrigin(position, map) {
        markerOrigin = new google.maps.Marker({
            position: position,
            map: map
        });
    }

    // Añadir el marcador del destino
    function placeDestination(position, map) {
        markerDestination = new google.maps.Marker({
            position: position,
            map: map
        });
    }
    
    // Listener de añadir marcadores
    var numMarcadores = 0;
    map.addListener('click', function(e) {
        if (numMarcadores == 0) {
            placeOrigin(e.latLng, map);
            ++numMarcadores;
        }
        else if (numMarcadores == 1) {
            placeDestination(e.latLng, map);
            ++numMarcadores;
        }
    });
    
    // Aray de coordenadas de cada ruta
    var points1 = [];
    var points2 = [];
    var points3 = [];
    
    // Zonas del mapa
    var zone = [];
    zone[9] = new google.maps.LatLngBounds(
        new google.maps.LatLng(41.6152883,-0.8767636), //BL
        new google.maps.LatLng(41.652719,-0.8456517) //TR
    )
    zone[8] = new google.maps.LatLngBounds(
        new google.maps.LatLng(41.604812,-0.908929), //BL
        new google.maps.LatLng(41.632403,-0.876313) //TR
    );
    zone[7] = new google.maps.LatLngBounds(
        new google.maps.LatLng(41.6326279,-0.9078558), //BL
        new google.maps.LatLng(41.652861,-0.876114) //TR
    );
    zone[6] = new google.maps.LatLngBounds(
        new google.maps.LatLng(41.652861,-0.876114), //BL
        new google.maps.LatLng(41.676396,-0.814344) //TR
    );
    zone[5] = new google.maps.LatLngBounds(
        new google.maps.LatLng(41.620467,-0.94186), //BL
        new google.maps.LatLng(41.652861,-0.907356) //TR
    );
    zone[4] = new google.maps.LatLngBounds(
        new google.maps.LatLng(41.644459,-0.96821), //BL
        new google.maps.LatLng(41.66652,-0.941431)  //TR
    );
    zone[3] = new google.maps.LatLngBounds(
        new google.maps.LatLng(41.653246,-0.9416883), //BL
        new google.maps.LatLng(41.676138,-0.907356) //TR
    );
    zone[2] = new google.maps.LatLngBounds(
        new google.maps.LatLng(41.652861,-0.907356), //BL
        new google.maps.LatLng(41.676138,-0.875256) //TR
    );
    zone[1] = new google.maps.LatLngBounds(
        new google.maps.LatLng(41.676138,-0.875256), //BL
        new google.maps.LatLng(41.697609,-0.84779) //TR
    );
    zone[0] = new google.maps.LatLngBounds(
        new google.maps.LatLng(41.676138,-0.907356), //BL
        new google.maps.LatLng(41.697803,-0.875256) //TR
    );
    
    
    
    // Lista de zonas por las que pasa cada ruta
    var zonesOfRoute = [];
    zonesOfRoute[0] = [];
    zonesOfRoute[1] = [];
    zonesOfRoute[2] = [];
    
    // Añade al array de zonesOfRoute las zonas por las que pasa unos puntos.
    function discurrePor(listaPuntos,numRuta) {
        for (var k in listaPuntos) {
            for (var j = 0; j < 10; ++j) {
                if (zone[j].contains(listaPuntos[k])) {
                    var nuevaZona = "Zona".concat(String(j+1));
                    if (!(zonesOfRoute[numRuta-1].includes(nuevaZona))) {
                        zonesOfRoute[numRuta-1].push(nuevaZona);
                    }
                }
            }
        }
    }
    
    // Generar y mostrar las rutas en el mapa
    var numRutas = 0;
    function calcularRutas() {
        if (numMarcadores == 2) {
            var request = {
                origin: markerOrigin.getPosition(),
                destination: markerDestination.getPosition(),
                travelMode: 'WALKING',
                provideRouteAlternatives: true,
                region: 'ES'
            };
            directionsService.route(request, function(result, status) {
            if (status == 'OK') {
                for (var i = 0; i < 3; ++i) {
                    if (i < result.routes.length) {
                        // Dibujar la ruta en el mapa
                        directionsRenderer[i].setDirections(result);
                        directionsRenderer[i].setRouteIndex(i);
                        directionsRenderer[i].setMap(map);
                        var data;
                        if (i == 0) {
                            var pollution1= 0;
                            points1 = result.routes[0].overview_path;
                            discurrePor(points1,1);
                            var zones1 = zonesOfRoute[0];
                            for (var l in zones1) {
                                pollution1 = pollution1 + parseInt(zonasInfo.zones1[l].c02,10);
                            }
                            if (zones1.length != 0) { pollution1 = pollution1 / zones1.length; }
                            data = result.routes[0].legs[0];
                            loadRoute1(data.distance.text,data.duration.text,pollution1);
                            ++numRutas;
                        }
                        else if (i == 1) {
                            var pollution2 = 0;
                            points2 = result.routes[1].overview_path;
                            discurrePor(points2,2);
                            var zones2 = zonesOfRoute[1];
                            for (var m in zones2) {
                                pollution2 = pollution2 + parseInt(zonasInfo.zones2[m].c02,10);
                            }
                            if (zones2.length != 0) { pollution2 = pollution2 / zones2.length; }
                            data = result.routes[1].legs[0];
                            loadRoute2(data.distance.text,data.duration.text,pollution2);
                            ++numRutas;
                        }
                        else if (i == 2) {
                            var pollution3 = 0;
                            points3 = result.routes[2].overview_path;
                            discurrePor(points3,3);
                            var zones3 = zonesOfRoute[2];
                            for (var n in zones3) {
                                pollution3 = pollution3 + parseInt(zonasInfo.zones3[n].c02,10);
                            }
                            if (zones3.length != 0) { pollution3 = pollution3 / zones3.length; }
                            data = result.routes[2].legs[0];
                            loadRoute3(data.distance.text,data.duration.text,pollution3);
                            ++numRutas;
                        }
                    }
                }
                // Borar markers
                markerOrigin.setMap(null);
                markerDestination.setMap(null);
            }
            });
        }
    }

    // Borrar marcadores y rutas del mapa
    function reiniciarMapa() {
        // Limpiar el mapa
        map.setCenter(zaragoza);
        map.setZoom(12);
        numMarcadores = 0;
        numRutas = 0;
        markerOrigin.setMap(null);
        markerDestination.setMap(null);
        directionsRenderer[0].setMap(null);
        directionsRenderer[1].setMap(null);
        directionsRenderer[2].setMap(null);
        // Ocultar paneles derechera
        document.getElementById("route1").style.display = "none";
        document.getElementById("route2").style.display = "none";
        document.getElementById("route3").style.display = "none";
        document.getElementById("separator1").style.display = "none";
        document.getElementById("separator2").style.display = "none";
    }
    
    function highlight1() {
        directionsRenderer[1].setMap(null);
        directionsRenderer[2].setMap(null);
    }
    
    function highlight2() {
        directionsRenderer[0].setMap(null);
        directionsRenderer[2].setMap(null);
    }
    
    function highlight3() {
        directionsRenderer[0].setMap(null);
        directionsRenderer[1].setMap(null);
    }
    
    function displayAll() {
        directionsRenderer[0].setMap(map);
        if (numRutas > 1) {
            directionsRenderer[1].setMap(map);
        }
        if (numRutas > 2) {
            directionsRenderer[2].setMap(map);
        }
    }
    
    // Mostrar la información de la ruta1
    function loadRoute1 (distance,time,pollution) {
        var r1 = document.getElementById("route1");
        var t1 = document.getElementById("time1");
        var d1 = document.getElementById("distance1");
        var p1 = document.getElementById("pollution1");
        r1.style.display = "block";
        t1.innerHTML = "Tiempo:<br>".concat(time);
        d1.innerHTML = "Distancia:<br>".concat(distance);
        p1.innerHTML = pollution;
        r1.addEventListener('mouseover',highlight1);
        r1.addEventListener('mouseleave',displayAll);
    }

    // Mostrar la información de la ruta2
    function loadRoute2 (distance,time,pollution) {
        var hr1 = document.getElementById("separator1");
        hr1.style.display = "block";
        var r2 = document.getElementById("route2");
        var t2 = document.getElementById("time2");
        var d2 = document.getElementById("distance2");
        var p2 = document.getElementById("pollution2");
        r2.style.display = "block";
        t2.innerHTML = "Tiempo:<br>".concat(time);
        d2.innerHTML = "Distancia:<br>".concat(distance);
        p2.innerHTML = pollution;
        r2.addEventListener('mouseover',highlight2);
        r2.addEventListener('mouseleave',displayAll);
    }

    // Mostrar la información de la ruta3
    function loadRoute3 (distance,time,pollution) {
        var hr2 = document.getElementById("separator2");
        hr2.style.display = "block";
        var r3 = document.getElementById("route3");
        var t3 = document.getElementById("time3");
        var d3 = document.getElementById("distance3");
        var p3 = document.getElementById("pollution3");
        r3.style.display = "block";
        t3.innerHTML = "Tiempo:<br>".concat(time);
        d3.innerHTML = "Distancia:<br>".concat(distance);
        p3.innerHTML = pollution;
        r3.addEventListener('mouseover',highlight3);
        r3.addEventListener('mouseleave',displayAll);
    }
}
