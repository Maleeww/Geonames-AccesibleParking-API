package opiniones.graphql;

import com.coxautodev.graphql.tools.GraphQLRootResolver;

import opiniones.servicio.ServicioOpiniones;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;

public class Mutation implements GraphQLRootResolver {

	public String create(String urlRecurso) throws RepositorioException, EntidadNoEncontrada {

//    	Opinion opinion = new Opinion();
//    	opinion.setUrlRecurso(urlRecurso);
//    	opinion.setValoraciones(new LinkedList<Valoracion>());

		String id = ServicioOpiniones.getInstancia().create(urlRecurso);

		return id;
	}

	public boolean valorar(String urlRecurso, String email, int nota, String comentario)
			throws RepositorioException, EntidadNoEncontrada {

		if (comentario == null || comentario.isEmpty())
			ServicioOpiniones.getInstancia().valorar(urlRecurso, email, nota);
		else
			ServicioOpiniones.getInstancia().valorar(urlRecurso, email, nota);

		return true;
	}

	public boolean removeByUrl(String url) throws EntidadNoEncontrada, RepositorioException {
		ServicioOpiniones.getInstancia().removeByUrl(url);
		return true;
	}

}