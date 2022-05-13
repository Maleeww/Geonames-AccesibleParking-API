package opiniones.servicio;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.time.LocalDateTime;
import java.util.LinkedList;

import javax.json.bind.Jsonb;
import javax.json.bind.spi.JsonbProvider;
import javax.jws.WebService;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import opiniones.eventos.EventoValoracion;
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
	
	protected void notificarEvento(EventoValoracion evento) {

		try {
		ConnectionFactory factory = new ConnectionFactory();
		// TODO uri

		
		String uri = "amqps://jwrctuai:nqs_d_DfoxuIANsa94_Vis2-TFNqDjYE@whale.rmq.cloudamqp.com/jwrctuai";
		factory.setUri(uri);

		Connection connection = factory.newConnection();

		Channel channel = connection.createChannel();

		/** Declaración del Exchange **/

		final String exchangeName = "amq.direct";

		boolean durable = true;
		channel.exchangeDeclare(exchangeName, "direct", durable);

		/** Envío del mensaje **/

		Jsonb contexto = JsonbProvider.provider().create().build();

		String cadenaJSON = contexto.toJson(evento);

		String mensaje = cadenaJSON;

		String routingKey = "arso";
		channel.basicPublish(exchangeName, routingKey, new AMQP.BasicProperties.Builder()
				.contentType("application/json")
				.build(), mensaje.getBytes());

		channel.close();
		connection.close();
		} catch(Exception e) {

			throw new RuntimeException(e);
		}
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
	 
	  // curl -i -X POST --data "email=pepe@um.es&nota=5" http://localhost:8080/api/opiniones/localhost%3A8080%2Fapi%2Fciudades%2FLorca%2Fpuntos%2FCastillo_de_Lorca%2Faparcamientos%2F37.677385652447754_-1.7053383432526061


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

		EventoValoracion evento = new EventoValoracion();
		evento.setMedia(opinion.getMediaValoraciones());
		evento.setUrl(urlRecurso);
		evento.setnumValoraciones(opinion.getNumValoraciones());
		evento.setValoracion(v);
		notificarEvento(evento);
		
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
	public Opinion getByUrl(String urlRecurso) throws EntidadNoEncontrada, UnsupportedEncodingException {
		if (urlRecurso == null || urlRecurso.isEmpty())
			throw new IllegalArgumentException("url: no debe ser nulo ni vacio");
		
		String decoded = URLDecoder.decode(urlRecurso, "UTF-8");
		return repositorio.getByUrl(decoded);
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
