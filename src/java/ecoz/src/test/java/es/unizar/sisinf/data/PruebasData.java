/**
 * @file	PruebasData.java
 * @author	Andrés Gavín Murillo 716358
 * @author	Eduardo Gimeno Soriano 721615
 * @author	Sergio Álvarez Peiro 740241
 * @date	Octubre 2019
 * @coms	Sistemas de información - Práctica 2
 */

package es.unizar.sisinf.data;

import es.unizar.sisinf.data.dao.*;
import es.unizar.sisinf.data.vo.*;
import java.io.File;
import java.util.ArrayList;
import de.micromata.opengis.kml.v_2_2_0.Kml;

public class PruebasData {
	
	public static void pruebasUsuario() throws Exception {
		System.out.println();
		System.out.println("=========================");
		System.out.println("Pruebas usuario");
		System.out.println("=========================");
		UsuarioDAO u = new UsuarioDAO();
		
		String email = "pepix@unizar.es";
		u.create(new UsuarioVO(email, null, null, "aaaa"));
		u.create(new UsuarioVO(email+"a", null, null, "aaaa"));
		u.create(new UsuarioVO(email+"b", null, null, "aaaa"));
		System.out.println(u.findById(new UsuarioVO(email, null, null, null)));
		u.update(new UsuarioVO(email, "pepi", "Pepito Pepón", "ipep"));
		System.out.println(u.findAll());
		u.delete(new UsuarioVO(email, null, null, null));
	}
	
	public static void pruebasZona() throws Exception {
		System.out.println();
		System.out.println("=========================");
		System.out.println("Pruebas zona");
		System.out.println("======================");
		ZonaDAO z = new ZonaDAO();
		
		String zona = "zona2";
		z.create(new ZonaVO(zona, new Kml(), 1.0f, 1.0f, 1.0f, 1.0f));
		z.create(new ZonaVO(zona+"a", new Kml(), 1.0f, 1.0f, 1.0f, 1.0f));
		z.create(new ZonaVO(zona+"b", new Kml(), 1.0f, 1.0f, 1.0f, 1.0f));
		System.out.println(z.findById(new ZonaVO(zona, null, null, null, null, null)));
		z.update(new ZonaVO(zona, new Kml(), 2.0f, 3.0f, 4.0f, 5.0f));
		System.out.println(z.findAll());
		Kml kml = z.findById(new ZonaVO(zona, null, null, null, null, null)).getFichero();
		kml.marshal(new File("tmp.txt"));
		z.delete(new ZonaVO(zona, null, null, null, null, null));
	}
	
	public static void pruebasRuta() throws Exception {
		System.out.println();
		System.out.println("=========================");
		System.out.println("Pruebas ruta");
		System.out.println("======================");
		UsuarioDAO u = new UsuarioDAO();
		RutaDAO r = new RutaDAO();

		ArrayList<UsuarioVO> usuarios = u.findAll();
		Integer ruta = 5;
		
		r.create(new RutaVO(ruta, new Kml(), usuarios.get(0)));
		r.create(new RutaVO(ruta+1, new Kml(), usuarios.get(0)));
		r.create(new RutaVO(ruta+2, new Kml(), usuarios.get(0)));
		System.out.println(r.findById(new RutaVO(ruta, null, null)));
		r.update(new RutaVO(ruta, new Kml(), usuarios.get(usuarios.size()-1)));
		System.out.println(r.findAll());
		r.delete(new RutaVO(ruta, null, null));
	}
	
	public static void pruebasDiscurre() throws Exception {
		System.out.println();
		System.out.println("=========================");
		System.out.println("Pruebas discurre");
		System.out.println("======================");
		RutaDAO r = new RutaDAO();
		ZonaDAO z = new ZonaDAO();
		DiscurreDAO d = new DiscurreDAO();

		ArrayList<RutaVO> rutas = r.findAll();
		ArrayList<ZonaVO> zonas = z.findAll();
		
		d.create(new DiscurreVO(rutas.get(0), zonas.get(0)));
		System.out.println(d.findById(new DiscurreVO(rutas.get(0), zonas.get(0))));
		System.out.println(d.findAll());
		d.delete(new DiscurreVO(rutas.get(0), zonas.get(0)));
	}
	
	public static void borrarTodo() throws Exception {
		System.out.println();
		
		UsuarioDAO u = new UsuarioDAO();
		ArrayList<UsuarioVO> usuarios = u.findAll();
		for (UsuarioVO i : usuarios) {
			u.delete(i);
		}
		
		RutaDAO r = new RutaDAO();
		ArrayList<RutaVO> rutas = r.findAll();
		for (RutaVO i : rutas) {
			r.delete(i);
		}
		
		ZonaDAO z = new ZonaDAO();
		ArrayList<ZonaVO> zonas = z.findAll();
		for (ZonaVO i : zonas) {
			z.delete(i);
		}
		
		DiscurreDAO d = new DiscurreDAO();
		ArrayList<DiscurreVO> discurres = d.findAll();
		for (DiscurreVO i : discurres) {
			d.delete(i);
		}
		
		System.out.println("HECHO");
	}
	
	public static void main(String[] args) {
		try {
			pruebasUsuario();
			
			pruebasZona();
			
			pruebasRuta();
			
			pruebasDiscurre();
			
			borrarTodo();
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
