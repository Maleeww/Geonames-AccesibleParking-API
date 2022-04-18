package opiniones.servicio;

import java.util.LinkedList;

public class ListadoOpiniones {

	public static class OpinionResumen {
			// TODO ADAPTAR A OPINION

		
		private String id;
		private String titulo;
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getTitulo() {
			return titulo;
		}
		public void setTitulo(String titulo) {
			this.titulo = titulo;
		}
		@Override
		public String toString() {
			return "OpinionResumen [id=" + id + ", titulo=" + titulo + "]";
		}	
		
	}
	
	private LinkedList<OpinionResumen> opiniones = new LinkedList<>();
	
	public LinkedList<OpinionResumen> getOpiniones() {
		return opiniones;
	}
	
	public void setOpiniones(LinkedList<OpinionResumen> opiniones) {
		this.opiniones = opiniones;
	}

	@Override
	public String toString() {
		return "ListadoOpiniones [opiniones=" + opiniones + "]";
	}
	
}
