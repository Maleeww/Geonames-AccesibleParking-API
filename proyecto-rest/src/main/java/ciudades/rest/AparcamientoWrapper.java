package ciudades.rest;

import javax.xml.bind.annotation.XmlRootElement;

import es.um.ciudad.Aparcamiento;

@XmlRootElement
public class AparcamientoWrapper {

	private Aparcamiento aparcamiento;

	public Aparcamiento getAparcamiento() {
		return aparcamiento;
	}

	public void setAparcamiento(Aparcamiento aparcamiento) {
		this.aparcamiento = aparcamiento;
	}
	
}
