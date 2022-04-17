package ciudades.bean;

public class PuntoInteres {
	private String title;
	// private String feature;
	// private String countrycode;
	private String wikipediaUrl;
	private String ciudad;
	private double longitud;
	private double latitud;

	public PuntoInteres() {
		this.ciudad = "Lorca";
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public double getLongitud() {
		return longitud;
	}

	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}

	public double getLatitud() {
		return latitud;
	}

	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}

	public String getWikipediaUrl() {
		return wikipediaUrl;
	}

	public void setWikipediaUrl(String wikipediaUrl) {
		this.wikipediaUrl = wikipediaUrl;
	}

	@Override
	public String toString() {
		return "PuntoInteres [title=" + title + ", ciudad=" + ciudad + ", longitud=" + longitud + ", latitud=" + latitud
				+ "]";
	}

}
