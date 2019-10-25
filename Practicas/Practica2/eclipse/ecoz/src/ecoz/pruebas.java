package ecoz;

public class pruebas {
	
	public static void main(String[] args) {
		usuarioRepository u = new usuarioRepository();
		
		System.out.println(u.crearUsuario("bbb@aaa.aaa", "aaaa"));
		
		u.finalize();
	}
}
