package ciudades.servicio;

import java.util.List;

import es.um.ciudad.Aparcamiento;
import es.um.ciudad.PuntoInteres;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;

public interface IServicioCiudades {

	List<CiudadResumen> getListadoCiudades() throws RepositorioException;
	
	List<PuntoInteres> getPuntosInteresCiudad(String ciudad) throws RepositorioException, EntidadNoEncontrada;
	
	List<Aparcamiento> getAparcamientosCercanos(String ciudad, String puntoInteres) throws Exception;
	
	Aparcamiento getAparcamiento(String ciudad, double lat, double lon) throws Exception;
}
