package opiniones.rest;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;


import opiniones.modelo.Opinion;
import opiniones.servicio.IServicioOpiniones;
import opiniones.servicio.ServicioOpiniones;
import pasarela.zuul.seguridad.AvailableRoles;
import pasarela.zuul.seguridad.Secured;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;

@Path("opiniones")
public class OpinionesControladorRest {

	@Context
	private SecurityContext securityContext;
	
	private IServicioOpiniones servicio = ServicioOpiniones.getInstancia();
	
	@Context
	private UriInfo uriInfo;
	
//	String create(Encuesta encuesta) throws RepositorioException;
//	
//	boolean haVotado(String id, String usuario) throws RepositorioException, EntidadNoEncontrada;
//	
//	void votar(String id, int opcion, String usuario) throws RepositorioException, EntidadNoEncontrada;
//	
//	Encuesta getById(String id) throws RepositorioException, EntidadNoEncontrada;
//	
	
	
//	@GET
//	@Path("/{id}")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response getById(@PathParam("id") String id) throws Exception {
//		
//		
//		Opinion opinion = servicio.getById(id);
//		
//		return Response.status(Status.OK).entity(opinion).build();
//	}
	
	// curl -i -X POST -H "Accept: application/json" http://localhost:8080/api/opiniones/
	// curl -i -X POST -H "Accept: application/json" http://localhost:8080/api/opiniones/localhost%3A8080%2Fapi%2Fciudades%2FLorca%2Fpuntos%2FCastillo_de_Lorca%2Faparcamientos%2F37.677385652447754_-1.7053383432526061


	//  curl -i -X POST --data "url=localhost%3A8080%2Fapi%2Fciudades%2FLorca%2Fpuntos%2FCastillo_de_Lorca%2Faparcamientos%2F37.677385652447754_-1.7053383432526061" localhost:8080/api/opiniones

	//@Produces(MediaType.APPLICATION_JSON)
	//@Path("/{url}")
	@Secured(AvailableRoles.ADMINISTRADOR)
	@POST
	public Response create(@FormParam("url")String paramurl) throws RepositorioException, UnsupportedEncodingException{
		
		/*
		 * Opinion opinion = new Opinion();
		 * opinion.setUrlRecurso(URLDecoder.decode(paramurl, "UTF-8"));
		 * opinion.setValoraciones(new LinkedList<>());
		 */
		String decodedUrl = URLDecoder.decode(paramurl, "UTF-8");
		String id = servicio.create(decodedUrl);
				
		URI url = uriInfo.getAbsolutePathBuilder().path(decodedUrl).build();
		
		if(id==null) Response.seeOther(url).build();

		return Response.created(url).build();
		}
	
	@DELETE
	@Path("/{url}")
	//@Path("/{id}") // URL?
	//@Path("/{url:http://localhost:8080/api/ciudades/(Lorca|Malaga))/puntos/*.}")
	public Response removeActividad(@PathParam("url") String url) throws Exception {

		servicio.removeByUrl(url);

		return Response.status(Response.Status.NO_CONTENT).build();

	}
	
	@POST
	@Path("/{url}")
	public Response valorar(@PathParam("url") String url,@FormParam("email") String email,@FormParam("nota") int nota,@FormParam("comentario") String comentario) throws RepositorioException, EntidadNoEncontrada {
		
		if(comentario==null||comentario.isEmpty())servicio.valorar(url, email, nota);
		else servicio.valorar(url, email, nota, comentario);
		
		return Response.noContent().build();
		
	}
	
//	void remove(String id) throws RepositorioException, EntidadNoEncontrada;
//	
//	ListadoEncuestas getListadoResumen() throws RepositorioException, EntidadNoEncontrada;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{url}")
	public Response getValoraciones(@PathParam("url") String url) throws Exception {
		Opinion opinion = servicio.getByUrl(url);
		//LinkedList<Valoracion> valoraciones = opinion.getValoraciones();
		//double notaMedia = opinion.getMediaValoraciones();
		/*
		 * ListadoOpinion listado = servicio.getListadoResumen();
		 * 
		 * LinkedList<ResumenExtendido> resumenesExtendidos = new LinkedList<>();
		 * 
		 * for (EncuestaResumen resumen : listado.getEncuestas()) {
		 * 
		 * ResumenExtendido resumenExtendido = new ResumenExtendido();
		 * resumenExtendido.setResumen(resumen);
		 * 
		 * String url =
		 * uriInfo.getAbsolutePathBuilder().path(resumen.getId()).build().toString();
		 * 
		 * resumenExtendido.setUrl(url);
		 * 
		 * resumenesExtendidos.add(resumenExtendido); }
		 * 
		 * Listado resultado = new Listado();
		 * resultado.setEncuestas(resumenesExtendidos);
		 */
		return Response.ok(opinion).build();
	}
	
	
}