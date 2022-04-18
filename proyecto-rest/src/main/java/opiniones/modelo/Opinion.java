package opiniones.modelo;

import java.util.HashMap;
import java.util.LinkedList;

import javax.xml.bind.annotation.XmlTransient;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Opinion {
	
	// TODO: identificador
	@JsonIgnore
	@BsonId
	private ObjectId id;
	private String urlRecurso;
	private LinkedList<Valoracion> valoraciones = new LinkedList<>();
	//private HashMap<String,Valoracion> valoracionesMap = new HashMap<>();
	
	@XmlTransient
	public ObjectId getId() {
		return id;
	}
	
	public void setId(ObjectId id) {
		this.id = id;
	}

	
	public String getUrlRecurso() {
		return urlRecurso;
	}

	public void setUrlRecurso(String urlRecurso) {
		this.urlRecurso = urlRecurso;
	}

	public LinkedList<Valoracion> getValoraciones() {
		return valoraciones;
	}

	public void setValoraciones(LinkedList<Valoracion> valoraciones) {
		this.valoraciones = valoraciones;
	}

	
	// Propiedad calculada

	public int getNumValoraciones() {
		return valoraciones.size();
	}
	
	
	// Propiedad calculada

	public double getMediaValoraciones() {
		double media = 0;
		for(Valoracion v:valoraciones) {
			media+=v.getNota();
		}
		if(valoraciones.size()>0) return media/valoraciones.size();
		else return media;
	}

	@Override
	public String toString() {
		return "Opinion [urlRecurso=" + urlRecurso + ", valoraciones=" + valoraciones + ", numValoraciones="
				+ getNumValoraciones() + ", mediaValoraciones=" + getMediaValoraciones() + "]";
	}
	
	

}
