package opiniones.servicio;

import javax.jws.WebService;

import opiniones.modelo.Opinion;
import opiniones.modelo.Valoracion;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;

@WebService
public interface IServicioOpiniones {

	String create(Opinion opinion) throws RepositorioException;
	
	//boolean haVotado(String id, String usuario) throws RepositorioException, EntidadNoEncontrada;
	
	//void votar(String id, int opcion, String usuario) throws RepositorioException, EntidadNoEncontrada;
	
	Opinion getById(String id) throws RepositorioException, EntidadNoEncontrada;
	
	void remove(String id) throws RepositorioException, EntidadNoEncontrada;
	
	ListadoOpiniones getListadoResumen() throws RepositorioException, EntidadNoEncontrada;

	void valorar(String urlRecurso, String email, int nota, String comentario)
			throws RepositorioException, EntidadNoEncontrada;

	Valoracion haValorado(String id, String email) throws RepositorioException, EntidadNoEncontrada;

	Opinion getByUrl(String urlRecurso) throws EntidadNoEncontrada;

	void removeByUrl(String urlRecurso) throws EntidadNoEncontrada, RepositorioException;
}
