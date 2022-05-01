package opiniones.repositorio;

import opiniones.modelo.Opinion;
import repositorio.EntidadNoEncontrada;
import repositorio.Repositorio;

public interface RepositorioOpiniones extends Repositorio<Opinion, String> {

	Opinion getByUrl(String urlRecurso) throws EntidadNoEncontrada;

}
