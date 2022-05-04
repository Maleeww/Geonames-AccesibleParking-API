package pasarela.zuul.repositorio;

import pasarela.zuul.beans.Usuario;
import repositorio.EntidadNoEncontrada;
import repositorio.Repositorio;

public interface RepositorioUsuarios extends Repositorio<Usuario, String> {

	Usuario getByUserId(String userId) throws EntidadNoEncontrada;

}
