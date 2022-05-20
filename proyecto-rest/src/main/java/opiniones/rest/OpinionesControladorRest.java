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
	
	
	//  curl -i -X POST --data "url=localhost%3A8080%2Fapi%2Fciudades%2FLorca%2Fpuntos%2FCastillo_de_Lorca%2Faparcamientos%2F37.677385652447754_-1.7053383432526061" localhost:8080/api/opiniones


	@Secured(AvailableRoles.ADMINISTRADOR)
	@POST
	public Response create(@FormParam("url")String paramurl) throws RepositorioException, UnsupportedEncodingException{
		
		String decodedUrl = URLDecoder.decode(paramurl, "UTF-8");
		String id = servicio.create(decodedUrl);
				
		URI url = uriInfo.getAbsolutePathBuilder().path(decodedUrl).build();
		
		if(id==null) Response.seeOther(url).build();

		return Response.created(url).build();
		}
	
	// curl -i -X DELETE localhost:8080/api/opiniones/localhost%3A8080%2Fapi%2Fciudades%2FLorca%2Fpuntos%2FCastillo_de_Lorca%2Faparcamientos%2F37.677385652447754_-1.7053383432526061

	
	@DELETE
	@Path("/{url}")
	@Secured(AvailableRoles.ADMINISTRADOR)
	public Response removeByUrl(@PathParam("url") String url) throws Exception {

		servicio.removeByUrl(url);

		return Response.status(Response.Status.NO_CONTENT).build();

	}
	
	// curl -i -X POST --data "email=david.almagroc@um.es&nota=2&comentario=aaaa" localhost:8080/api/opiniones/localhost%3A8080%2Fapi%2Fciudades%2FLorca%2Fpuntos%2FCastillo_de_Lorca%2Faparcamientos%2F37.677385652447754_-1.7053383432526061
	
	@POST
	@Path("/{url}")
	@Secured(AvailableRoles.USUARIO)
	public Response valorar(@PathParam("url") String url,@FormParam("email") String email,@FormParam("nota") int nota,@FormParam("comentario") String comentario) throws RepositorioException, EntidadNoEncontrada {
		
		if(comentario==null||comentario.isEmpty())servicio.valorar(url, email, nota);
		else servicio.valorar(url, email, nota, comentario);
		
		return Response.noContent().build();
		
	}
	
	// curl -i -X GET localhost:8080/api/opiniones/localhost%3A8080%2Fapi%2Fciudades%2FLorca%2Fpuntos%2FCastillo_de_Lorca%2Faparcamientos%2F37.677385652447754_-1.7053383432526061
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{url}")
	@Secured(AvailableRoles.USUARIO)
	public Response getValoraciones(@PathParam("url") String url) throws Exception {
		Opinion opinion = servicio.getByUrl(url);
				return Response.ok(opinion).build();
	}
	
	
}