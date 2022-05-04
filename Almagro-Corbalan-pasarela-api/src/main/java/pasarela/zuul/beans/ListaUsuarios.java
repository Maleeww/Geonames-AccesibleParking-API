package pasarela.zuul.beans;

import java.util.LinkedList;


public class ListaUsuarios {

	private static LinkedList<Usuario> lista = null;

	public static LinkedList<Usuario> getLista() {
		if (lista == null) {
				lista = new LinkedList<Usuario>();
		}
		return lista;
	}
	
	public static boolean add(Usuario u) {
		return lista.add(u);
	}
	
	public static Usuario getFromUserId(String userId) {
		for(Usuario u: lista) {
			if(u.getUserId().equals(userId)) return u;
		}
		return null; //EntidadNoEncontrada
	}
}
