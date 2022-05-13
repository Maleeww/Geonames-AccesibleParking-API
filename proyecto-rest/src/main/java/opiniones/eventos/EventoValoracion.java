package opiniones.eventos;

import opiniones.modelo.Valoracion;

public class EventoValoracion {
	//TODO: Adaptar
	private String url;
	private Valoracion valoracion;
	private int numValoraciones;
	private double media;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Valoracion getValoracion() {
		return valoracion;
	}
	public void setValoracion(Valoracion valoracion) {
		this.valoracion = valoracion;
	}
	public int getnumValoraciones() {
		return numValoraciones;
	}
	public void setnumValoraciones(int nValoraciones) {
		this.numValoraciones = nValoraciones;
	}
	public double getMedia() {
		return media;
	}
	public void setMedia(double media) {
		this.media = media;
	}
	

	
}
