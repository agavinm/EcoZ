package ecoz;

import de.micromata.opengis.kml.v_2_2_0.Kml;

public class pruebas {
	
	public static void main(String[] args) {
		try {
			usuarioRepository u = new usuarioRepository();
			//zonaRepository z = new zonaRepository();
			
			u.crearUsuario("bbbb@aaa.aaa", "aaaa");
			u.crearUsuario("bbbb@aaa.aaa", "bbbb"); // Error provocado
			//System.out.println(z.crearZona("zona2", new Kml(), 0.0f, 0.0f, 0.0f, 0.0f));
			
			u.finalize();
			//z.finalize();
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
