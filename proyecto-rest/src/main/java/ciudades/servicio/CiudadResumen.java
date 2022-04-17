package ciudades.servicio;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlRootElement
public class CiudadResumen {
	private XMLGregorianCalendar updated;
	private String nombre;
	private int codigoPostal;
	private List<String> puntosInteres;

	public CiudadResumen() {
		this.puntosInteres = new LinkedList<>();
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(int codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public List<String> getPuntosInteres() {
		return puntosInteres;
	}

	public void setPuntosInteres(List<String> puntosInteres) {
		this.puntosInteres = puntosInteres;
	}

	@Override
	public String toString() {
		return "CiudadResumen [nombre=" + nombre + ", codigoPostal=" + codigoPostal
				+ ", puntosInteres=" + puntosInteres + "]";
	}

	public XMLGregorianCalendar getUpdated() {
		return updated;
	}

	public void setUpdated(XMLGregorianCalendar updated) {
		this.updated = updated;
	}

}
