package opiniones.repositorio;

import es.um.ciudad.Aparcamiento;
import es.um.ciudad.Ciudad;
import es.um.ciudad.PuntoInteres;
import opiniones.modelo.Opinion;
import repositorio.ElementoNoEncontrado;
import repositorio.EntidadNoEncontrada;
import repositorio.Repositorio;
import repositorio.RepositorioException;

public interface RepositorioOpiniones extends Repositorio<Opinion, String> {

	Opinion getByUrl(String urlRecurso) throws EntidadNoEncontrada;
	
	//PuntoInteres getPuntoInteresById(Opinion opinion, String id) throws ElementoNoEncontrado;
	
	//Aparcamiento getAparcamiento(Ciudad ciudad, double lat, double lon) throws ElementoNoEncontrado;
}
