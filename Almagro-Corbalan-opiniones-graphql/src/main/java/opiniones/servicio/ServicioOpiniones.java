package opiniones.servicio;

import java.time.LocalDateTime;
import java.util.LinkedList;

import javax.jws.WebService;

import opiniones.modelo.Opinion;
import opiniones.modelo.Valoracion;
import opiniones.repositorio.FactoriaRepositorioOpiniones;
import opiniones.repositorio.RepositorioOpiniones;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;

@WebService(endpointInterface = "opiniones.servicio.IServicioOpiniones")
public class ServicioOpiniones implements IServicioOpiniones {

	private RepositorioOpiniones repositorio = FactoriaRepositorioOpiniones.getRepositorio();
	
	/** Patrón Singleton **/
	
	private static ServicioOpiniones instancia;

	private ServicioOpiniones() {
		
	}
	
	public static ServicioOpiniones getInstancia() {

		if (instancia == null)
			instancia = new ServicioOpiniones();

		return instancia;
	}
	
	
	@Override
	public String create(String url) throws RepositorioException {
		
		// Control de integridad de los datos
		
		if(url == null || url.isEmpty()) throw new IllegalArgumentException("url: no debe ser nulo ni vacio");

		Opinion op = new Opinion();
		op.setUrlRecurso(url);
		op.setValoraciones(new LinkedList<Valoracion>());
		
		// 1. Campos obligatorios
		/*
		 * if (opinion.getUrlRecurso() == null || opinion.getUrlRecurso().isEmpty())
		 * throw new IllegalArgumentException("url: no debe ser nulo ni vacio");
		 * 
		 * if (opinion.getValoraciones() == null) throw new
		 * IllegalArgumentException("valoraciones: no debe ser una coleccion nula");
		 * 
		 * for (Valoracion valoracion : opinion.getValoraciones()) { if
		 * (valoracion.getEmail() == null || valoracion.getEmail().isEmpty()) throw new
		 * IllegalArgumentException("opcion, email: no debe ser nulo ni vacio");
		 * 
		 * if (valoracion.getNota() < 1 || valoracion.getNota() > 5) throw new
		 * IllegalArgumentException("nota: no debe ser mayor que 5 ni menor que 1");
		 * 
		 * if(valoracion.getFechaCreacion()==null) throw new
		 * IllegalArgumentException("fecha: no debe ser nulo");
		 */
			/* Sí puede ser nulo o vacío
			 * if (valoracion.getComentario() == null ||
			 * valoracion.getComentario().isEmpty()) throw new
			 * IllegalArgumentException("comentario: no debe ser nulo ni vacio");
			 */

		
		String id = repositorio.add(op);
		System.out.println(op.toString());
		return id;
	}

	
	  @Override public Valoracion haValorado(String url, String email) throws
	  RepositorioException, EntidadNoEncontrada {
	  
	  if (email == null || email.isEmpty()) throw new
	  IllegalArgumentException("email: no debe ser nulo ni vacio");
	  
	  Opinion opinion = repositorio.getByUrl(url);
	  
	  for (Valoracion v : opinion.getValoraciones()) if
	  (v.getEmail().equals(email)) return v;
	  
	  return null;
	  
	  }
	 

	@Override
	public void valorar(String urlRecurso, String email, int nota, String comentario) throws RepositorioException, EntidadNoEncontrada {
				
		Opinion opinion = repositorio.getByUrl(urlRecurso);
		
		if (nota < 1 || nota > 5 )
			throw new IllegalArgumentException("nota: valor no valido");
		
		if (email == null || email.isEmpty())
			throw new IllegalArgumentException("email: no debe ser nulo ni vacio");
		//Valoracion v = haValorado(urlRecurso, email);
		//if ( v != null)
		opinion.getValoraciones().removeIf(v -> v.getEmail().equals(email)); //se reemplaza
		
		LocalDateTime fechaCreacion = LocalDateTime.now();
		
		Valoracion v = new Valoracion();
		v.setEmail(email);
		v.setNota(nota);
		v.setFechaCreacion(fechaCreacion.toString());
		if(comentario!=null&&!comentario.isEmpty())v.setComentario(comentario);
		opinion.getValoraciones().add(v);
		
		repositorio.update(opinion);
		
	}
	
	//Overload
	public void valorar(String urlRecurso, String email, int nota) throws RepositorioException, EntidadNoEncontrada {
		valorar(urlRecurso, email, nota, "");
	}

	@Override
	public Opinion getById(String id) throws RepositorioException, EntidadNoEncontrada {
		
		return repositorio.getById(id);
	}
	
	@Override
	public Opinion getByUrl(String urlRecurso) throws EntidadNoEncontrada {
		if (urlRecurso == null || urlRecurso.isEmpty())
			throw new IllegalArgumentException("url: no debe ser nulo ni vacio");
		
		return repositorio.getByUrl(urlRecurso);
	}

	@Override
	public void remove(String id) throws RepositorioException, EntidadNoEncontrada {
		
		Opinion opinion = repositorio.getById(id);
		
		repositorio.delete(opinion);
		
	}
	
	@Override
	public void removeByUrl(String urlRecurso) throws EntidadNoEncontrada, RepositorioException{
		if (urlRecurso == null || urlRecurso.isEmpty())
			throw new IllegalArgumentException("url: no debe ser nulo ni vacio");
		
		repositorio.delete(repositorio.getByUrl(urlRecurso));
	}


}
