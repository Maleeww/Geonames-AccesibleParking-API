package ciudades.bean;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Ciudad")
public class Ciudad {
		
	@XmlAttribute(name = "id", required = true)
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlSchemaType(name = "NMTOKEN")
	@XmlID
	private String id;
	
	@XmlAttribute(name = "nombre", required = true)
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlSchemaType(name = "NMTOKEN")
	private String nombre;

	@XmlAttribute(name = "codigoPostal", required = true)
	@XmlSchemaType(name = "NMTOKEN")
	private int codigoPostal;

	@XmlElement(required = true)
	private List<Aparcamiento> aparcamientos;
	@XmlElement(required = true)
	private List<PuntoInteres> puntosInteres;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public List<Aparcamiento> getAparcamientos() {
		return aparcamientos;
	}

	public void setAparcamientos(List<Aparcamiento> aparcamientos) {
		this.aparcamientos = aparcamientos;
	}

	public List<PuntoInteres> getPuntosInteres() {
		return puntosInteres;
	}

	public void setPuntosInteres(List<PuntoInteres> puntosInteres) {
		this.puntosInteres = puntosInteres;
	}

	
	

}
