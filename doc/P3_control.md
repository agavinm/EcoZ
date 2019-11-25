# Operaciones necesarias para la capa control  
  
## Discurre  
  - /registrarDiscurre rutaId zonaNombre (devuelve DiscurreVO)  
  - /discurrePor rutaId (devuelve ArrayList<ZonaVO>)  
  - /borrarDiscurre rutaId zonaNombre

## Ruta  
  - /registrarRuta fichero email contrasena (devuelve RutaVO)  
  - /borrarRuta rutaId email contrasena  
  - /devolverRutas email contrasena (devuelve ArrayList<RutaVO>)  

## Usuario  
  - /registrarUsuario email contrasena (devuelve UsuarioVO)  
  - /iniciarUsuario email contrasena (devuelve UsuarioVO)  
  - /actualizarUsuario email nombre? apellidos? contrasena (devuelve UsuarioVO)  
  - /actualizarContrasena email contrasenaActual contrasenaNueva (devuelve UsuarioVO)  
  - /borrarUsuario email contrasenaActual  

## Zona  
  - /devolverZonas (devuelve ArrayList<ZonaVO>)  


Nota: Los parámetros con VO (ej borrarRuta) no son los objetos en sí, sino el identificador correspondiente. Los resultados que devuelve con VO tampoco son los objetos en sí, sino los JSON correspondientes.
