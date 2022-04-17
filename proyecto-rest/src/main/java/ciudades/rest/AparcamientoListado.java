package ciudades.rest;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import es.um.ciudad.Aparcamiento;

@XmlRootElement
public class AparcamientoListado {

	public static class AparcamientoExtendido {
		private Aparcamiento aparcamiento;
		private String url;

		public Aparcamiento getAparcamiento() {
			return aparcamiento;
		}

		public void setAparcamiento(Aparcamiento aparcamiento) {
			this.aparcamiento = aparcamiento;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

	}
	
	private List<AparcamientoExtendido> entrada;
	
	public AparcamientoListado() {
		this.setEntrada(new LinkedList<>());
	}

	public List<AparcamientoExtendido> getEntrada() {
		return entrada;
	}

	public void setEntrada(List<AparcamientoExtendido> entrada) {
		this.entrada = entrada;
	}

}
