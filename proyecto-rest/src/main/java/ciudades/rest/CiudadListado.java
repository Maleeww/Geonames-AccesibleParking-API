package ciudades.rest;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import ciudades.servicio.CiudadResumen;

@XmlRootElement
public class CiudadListado {

	public static class CiudadResumenExtendido {
		
		private String url;
		private CiudadResumen resumen;

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public CiudadResumen getResumen() {
			return resumen;
		}

		public void setResumen(CiudadResumen resumen) {
			this.resumen = resumen;
		}

	}
	
	private List<CiudadResumenExtendido> entrada;
	
	public CiudadListado() {
		this.setEntrada(new LinkedList<>());
	}

	public List<CiudadResumenExtendido> getEntrada() {
		return entrada;
	}

	public void setEntrada(List<CiudadResumenExtendido> entrada) {
		this.entrada = entrada;
	}
}
