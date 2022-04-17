package ciudades.rest;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import repositorio.ElementoNoEncontrado;

@Provider
public class TratamientoElementoNoEncontrado implements ExceptionMapper<ElementoNoEncontrado> {
	@Override
	public Response toResponse(ElementoNoEncontrado arg0) {

		return Response.status(Response.Status.NOT_FOUND).entity(arg0.getMessage()).build();
	}
}
