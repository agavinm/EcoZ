package ecoz;

import java.io.File;

import de.micromata.opengis.kml.v_2_2_0.Kml;

public class pruebas {
	
	public static void main(String[] args) {
		try {
			/*System.out.println("Pruebas usuarioRepository");
			System.out.println("=========================");
			usuarioRepository u = new usuarioRepository();
			
			u.crearUsuario("bbbb@aaa.aaa", "aaaa");
			u.crearUsuario("bbbb@aaa.aaa", "bbbb"); // Error provocado
			
			u.finalize();*/
			
			System.out.println("Pruebas zonaRepository");
			System.out.println("======================");
			zonaRepository z = new zonaRepository();
			
			z.crearZona("zona-1", new Kml(), 1.0f, 1.0f, 1.0f, 1.0f);
			z.actualizarCO2("zona-1", 2.0f);
			z.actualizarO3("zona-1", 2.0f);
			z.actualizarNO2("zona-1", 2.0f);
			z.actualizarPM10("zona-1", 2.0f);
			z.actualizarFichero("zona-1", new Kml());
			System.out.println(z.seleccionarCO2("zona-1"));
			System.out.println(z.seleccionarO3("zona-1"));
			System.out.println(z.seleccionarNO2("zona-1"));
			System.out.println(z.seleccionarPM10("zona-1"));
			Kml kml = z.seleccionarFichero("zona-1");
			kml.marshal(new File("tmp.txt"));
			z.borrarZona("zona-1");
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
