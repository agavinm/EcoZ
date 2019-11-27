// Inicializar el mapa centrado en Zaragoza
function initMap() {
    // Botones para calcular rutas y reiniciar
    document.getElementById("calcular").onclick = calcularRutas;
    document.getElementById("reiniciar").onclick = reiniciarMapa;

    // Centro del mapa
    var zaragoza = {lat: 41.65606, lng: -0.87734};
    
    // Generar el mapa
    var map = new google.maps.Map(document.getElementById("map"), {zoom: 12, center: zaragoza});
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
    
    // Generar y mostrar las rutas en el mapa
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
                            data = result.routes[0].legs[0];
                            loadRoute1(data.distance.text,data.duration.text,"0%");
                        }
                        else if (i == 1) {
                            data = result.routes[1].legs[0];
                            loadRoute2(data.distance.text,data.duration.text,"0%");
                        }
                        else if (i == 2) {
                            data = result.routes[2].legs[0];
                            loadRoute3(data.distance.text,data.duration.text,"0%");
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
        console.log('DENTRO1');
        directionsRenderer[1].setMap(null);
        directionsRenderer[2].setMap(null);
    }
    
    function highlight2() {
        console.log('DENTRO2');
        directionsRenderer[0].setMap(null);
        directionsRenderer[2].setMap(null);
    }
    
    function highlight3() {
        console.log('DENTRO3');
        directionsRenderer[0].setMap(null);
        directionsRenderer[1].setMap(null);
    }
    
    function displayAll() {
        console.log('FUERA');
        directionsRenderer[0].setMap(map);
        directionsRenderer[1].setMap(map);
        directionsRenderer[2].setMap(map);
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