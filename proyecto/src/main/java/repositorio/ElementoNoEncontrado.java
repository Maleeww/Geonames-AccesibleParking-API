package repositorio;

/*
 * Excepción notificada si no existe el elemento dentro de la entidad.
 */

@SuppressWarnings("serial")
public class ElementoNoEncontrado extends Exception {
	public ElementoNoEncontrado(String msg, Throwable causa) {		
		super(msg, causa);
	}

	public ElementoNoEncontrado(String msg) {
		super(msg);		
	}
}
