package ciudades.bean;

public class Aparcamiento {
	private String ciudad;
	private String direccion;
	private double longitud;
	private double latitud;
	
	public Aparcamiento() {
		this.ciudad = "Lorca";
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
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

	@Override
	public String toString() {
		return "Aparcamiento [ciudad=" + ciudad + ", direccion=" + direccion
				+ ", longitud=" + longitud + ", latitud=" + latitud + "]";
	}

}
