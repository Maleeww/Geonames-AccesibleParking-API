package opiniones.graphql;

import com.coxautodev.graphql.tools.GraphQLRootResolver;

import opiniones.modelo.Opinion;
import opiniones.servicio.ServicioOpiniones;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;

public class Query implements GraphQLRootResolver {
    
    public Opinion getByUrl(String url) 
    		throws RepositorioException, EntidadNoEncontrada {
        
    	return ServicioOpiniones.getInstancia().getByUrl(url);
    }
}