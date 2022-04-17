package ciudades.repositorio;

import es.um.ciudad.Aparcamiento;
import es.um.ciudad.Ciudad;
import es.um.ciudad.PuntoInteres;
import repositorio.ElementoNoEncontrado;
import repositorio.Repositorio;

public interface RepositorioCiudades extends Repositorio<Ciudad, String> {
	
	PuntoInteres getPuntoInteresById(Ciudad ciudad, String id) throws ElementoNoEncontrado;
	
	Aparcamiento getAparcamiento(Ciudad ciudad, double lat, double lon) throws ElementoNoEncontrado;
}
