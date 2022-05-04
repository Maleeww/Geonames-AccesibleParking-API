package pasarela.zuul.repositorio;

import pasarela.zuul.beans.Usuario;
import pasarela.zuul.seguridad.AvailableRoles;
import repositorio.RepositorioException;

public class CargadorUsuarios {
	public static void main(String[] args) throws RepositorioException {
		Usuario marcos = new Usuario();
		marcos.setRol(AvailableRoles.ADMINISTRADOR);
		marcos.setEmail("marcos.menarguez@gmail.com");
		marcos.setUserId("MarcosMenarguez");
		
		Usuario david = new Usuario();
		david.setRol(AvailableRoles.ADMINISTRADOR);
		david.setEmail("dvdalco2@gmail.com");
		david.setUserId("Maleeww");
		
		Usuario david2 = new Usuario();
		david2.setRol(AvailableRoles.USUARIO);
		david2.setEmail("malwtroyard@hotmail.com");
		david2.setUserId("david-almagroc");
		
		RepositorioUsuarios repo = FactoriaRepositorioUsuarios.getRepositorio();
		String res1 = repo.add(marcos);
		String res2 = repo.add(david);
		String res3 = repo.add(david2);
		
		System.out.println(res1 + res2 + res3);
	}
}
