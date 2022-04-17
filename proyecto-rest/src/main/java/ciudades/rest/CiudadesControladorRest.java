package ciudades.rest;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.XMLGregorianCalendar;

import org.w3._2005.atom.Feed;
import org.w3._2005.atom.Feed.Entry;

import ciudades.rest.AparcamientoListado.AparcamientoExtendido;
import ciudades.rest.CiudadListado.CiudadResumenExtendido;
import ciudades.rest.PuntoInteresListado.PuntoInteresExtendido;
import ciudades.servicio.CiudadResumen;
import ciudades.servicio.ServicioCiudades;
import es.um.ciudad.Aparcamiento;
import es.um.ciudad.PuntoInteres;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api
@Path("ciudades")
public class CiudadesControladorRest {

	private ServicioCiudades servicio = ServicioCiudades.getInstancia();

	@Context
	private UriInfo uriInfo;

	// curl -i localhost:8080/api/ciudades
	// curl -i -H "Accept: application/json" localhost:8080/api/ciudades

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Consulta ciudades", notes = "Retorna la lista de ciudades disponibles en el servicio", response = CiudadListado.class)
	@ApiResponses(value = @ApiResponse(code = HttpServletResponse.SC_OK, message = ""))
	public Response getListadoCiudades() throws Exception {
		List<CiudadResumen> resultado = servicio.getListadoCiudades();
		CiudadListado listado = new CiudadListado();

		for (CiudadResumen cr : resultado) {
			CiudadResumenExtendido resumen = new CiudadResumenExtendido();
			resumen.setResumen(cr);
			resumen.setUrl(uriInfo.getAbsolutePathBuilder().path(cr.getNombre()).path("puntos")
					.build().toString());

			listado.getEntrada().add(resumen);
		}

		return Response.ok(listado).build();
	}
	
	// curl -i localhost:8080/api/ciudades/atom
	
	@GET
	@Path("/atom")
	@Produces({ MediaType.APPLICATION_ATOM_XML })
	@ApiOperation(value = "Consulta ciudades", notes = "Retorna la lista de ciudades disponibles en el servicio", response = Feed.class)
	@ApiResponses(value = @ApiResponse(code = HttpServletResponse.SC_OK, message = ""))
	public Response getListadoCiudadesAtom() throws Exception {
		List<CiudadResumen> resultado = servicio.getListadoCiudades();
		Feed listado = new Feed();
		listado.setId(uriInfo.getAbsolutePath().toString());
		listado.setTitle("ciudades");
		Feed.Author autor = new Feed.Author();
		autor.setName("MilenaDavid");
		listado.setAuthor(autor);
		XMLGregorianCalendar feedUpdated = resultado.get(0).getUpdated();

		for (CiudadResumen cr : resultado) {
			if(cr.getUpdated().compare(feedUpdated) == DatatypeConstants.GREATER) 
				feedUpdated = cr.getUpdated();
			Entry resumen = new Entry();
			resumen.setUpdated(cr.getUpdated());
			resumen.setId(uriInfo.getAbsolutePathBuilder().path(cr.getNombre()).path("puntos")
					.build().toString());
			resumen.setTitle(cr.getNombre());
			resumen.setContent(cr.toString());

			listado.getEntry().add(resumen);
		}
		
		listado.setUpdated(feedUpdated);

		return Response.ok(listado).build();
	}

	// curl -i localhost:8080/api/ciudades/Lorca/puntos
	// curl -i -H "Accept: application/json" localhost:8080/api/ciudades/Lorca/puntos

	@GET
	@Path("/{idCiudad}/puntos")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Consulta puntos de interés de una ciudad", notes = "Retorna la lista de puntos de interés dado el id de la ciudad", response = PuntoInteresListado.class)
	@ApiResponses(value = { @ApiResponse(code = HttpServletResponse.SC_OK, message = ""),
			@ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Ciudad no encontrada") })
	public Response getPuntosInteresCiudad(@PathParam("idCiudad") String ciudad) throws Exception {
		List<PuntoInteres> resultado = servicio.getPuntosInteresCiudad(ciudad);
		PuntoInteresListado listado = new PuntoInteresListado();

		for (PuntoInteres pi : resultado) {
			PuntoInteresExtendido pie = new PuntoInteresExtendido();

			pie.setPuntoInteres(pi);
			pie.setUrl(uriInfo.getAbsolutePathBuilder().path(pi.getWikiName()).path("aparcamientos")
					.build().toString());

			listado.getEntrada().add(pie);
		}

		return Response.ok(listado).build();
	}

	// curl -i localhost:8080/api/ciudades/Lorca/puntos/Castillo_de_Lorca/aparcamientos
	// curl -i -H "Accept: application/json" localhost:8080/api/ciudades/Lorca/puntos/Castillo_de_Lorca/aparcamientos

	@GET
	@Path("/{idCiudad}/puntos/{idPunto}/aparcamientos")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Consulta aparcamientos cercanos a un punto de interés", notes = "Retorna la lista de aparcamientos cercanos dado el id de un punto de interés", response = AparcamientoListado.class)
	@ApiResponses(value = { @ApiResponse(code = HttpServletResponse.SC_OK, message = ""),
			@ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Ciudad o punto de interés no encontrado")})
	public Response getAparcamientosCercanos(@PathParam("idCiudad") String ciudad,
			@PathParam("idPunto") String puntoInteres) throws Exception {
		List<Aparcamiento> resultado = servicio.getAparcamientosCercanos(ciudad, puntoInteres);
		AparcamientoListado listado = new AparcamientoListado();

		for (Aparcamiento a : resultado) {
			AparcamientoExtendido ae = new AparcamientoExtendido();

			ae.setAparcamiento(a);
			ae.setUrl(uriInfo.getAbsolutePathBuilder().path(a.getLatitud() + "_" + a.getLongitud())
					.build().toString());

			listado.getEntrada().add(ae);
		}

		return Response.ok(listado).build();
	}

	// curl -i localhost:8080/api/ciudades/Lorca/puntos/Castillo_de_Lorca/aparcamientos/37.677385652447754_-1.7053383432526061
	// curl -i -H "Accept: application/json" localhost:8080/api/ciudades/Lorca/puntos/Castillo_de_Lorca/aparcamientos/37.677385652447754_-1.7053383432526061

	@GET
	@Path("/{idCiudad}/puntos/{idPunto}/aparcamientos/{aparcamientoLat}_{aparcamientoLon}")
	@ApiOperation(value = "Consulta aparcamiento", notes = "Retorna información sobre un aparcamiento dadas sus coordenadas", response = Aparcamiento.class)
	@ApiResponses(value = { @ApiResponse(code = HttpServletResponse.SC_OK, message = ""),
			@ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Ciudad o aparcamiento no encontrado")})
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getAparcamiento(@PathParam("idCiudad") String ciudad,
			@PathParam("aparcamientoLat") double lat, @PathParam("aparcamientoLon") double lon)
			throws Exception {

		AparcamientoWrapper wrapper = new AparcamientoWrapper();
		wrapper.setAparcamiento(servicio.getAparcamiento(ciudad, lat, lon));
		return Response.ok(wrapper).build();
	}
}
