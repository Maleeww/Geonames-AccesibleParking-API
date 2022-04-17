package ciudades.rest;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import es.um.ciudad.PuntoInteres;

@XmlRootElement
public class PuntoInteresListado {

	public static class PuntoInteresExtendido {
		private PuntoInteres puntoInteres;
		private String url;

		public PuntoInteres getPuntoInteres() {
			return puntoInteres;
		}

		public void setPuntoInteres(PuntoInteres puntoInteres) {
			this.puntoInteres = puntoInteres;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

	}
	
	private List<PuntoInteresExtendido> entrada;
	
	public PuntoInteresListado() {
		this.setEntrada(new LinkedList<>());
	}

	public List<PuntoInteresExtendido> getEntrada() {
		return entrada;
	}

	public void setEntrada(List<PuntoInteresExtendido> entrada) {
		this.entrada = entrada;
	}
	
}
