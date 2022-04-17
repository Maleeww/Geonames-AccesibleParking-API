package ciudades.servicio;

import java.util.List;
import java.util.stream.Collectors;

import ciudades.repositorio.FactoriaRepositorioCiudades;
import ciudades.repositorio.RepositorioCiudades;
import es.um.ciudad.Aparcamiento;
import es.um.ciudad.Ciudad;
import es.um.ciudad.PuntoInteres;
import repositorio.ElementoNoEncontrado;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;

public class ServicioCiudades implements IServicioCiudades {

	public static final int DISTANCIA = 200;

	private RepositorioCiudades repositorio = FactoriaRepositorioCiudades.getRepositorio();

	private static ServicioCiudades instancia;

	private ServicioCiudades() {
	}

	public static ServicioCiudades getInstancia() {
		if (instancia == null)
			instancia = new ServicioCiudades();
		return instancia;
	}

	// METODOS DE APOYO

	public static double distance(double lat1, double lon1, double el1, double lat2, double lon2,
			double el2) {

		final int R = 6371; // Radius of the earth

		double latDistance = Math.toRadians(lat2 - lat1);
		double lonDistance = Math.toRadians(lon2 - lon1);
		double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
				+ Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
						* Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double distance = R * c * 1000; // convert to meters

		double height = el1 - el2;

		distance = Math.pow(distance, 2) + Math.pow(height, 2);

		return Math.sqrt(distance);
	}

	private double distance(PuntoInteres pi, Aparcamiento a) {
		return distance(pi.getLatitud(), pi.getLongitud(), 0, a.getLatitud(), a.getLongitud(), 0);
	}

	private CiudadResumen toResumen(Ciudad ciudad) {
		CiudadResumen cr = new CiudadResumen();

		cr.setNombre(ciudad.getNombre());
		cr.setCodigoPostal(ciudad.getCodigoPostal());
		ciudad.getPuntoInteres().stream().forEach(p -> cr.getPuntosInteres().add(p.getTitle()));
		cr.setUpdated(ciudad.getUpdated());

		return cr;
	}

	// METODOS SERVICIO

	@Override
	public List<CiudadResumen> getListadoCiudades() throws RepositorioException {
		return repositorio.getAll().stream().map(c -> toResumen(c)).collect(Collectors.toList());
	}

	@Override
	public List<PuntoInteres> getPuntosInteresCiudad(String ciudad)
			throws RepositorioException, EntidadNoEncontrada {
		return repositorio.getById(ciudad).getPuntoInteres();
	}

	@Override
	public List<Aparcamiento> getAparcamientosCercanos(String ciudad, String puntoInteres)
			throws RepositorioException, EntidadNoEncontrada, ElementoNoEncontrado {
		Ciudad c = repositorio.getById(ciudad);

		PuntoInteres pi = repositorio.getPuntoInteresById(c, puntoInteres);

		return c.getAparcamiento().stream().filter(a -> distance(pi, a) < DISTANCIA)
				.collect(Collectors.toList());
	}

	@Override
	public Aparcamiento getAparcamiento(String ciudad, double lat, double lon)
			throws ElementoNoEncontrado, RepositorioException, EntidadNoEncontrada {
		return repositorio.getAparcamiento(repositorio.getById(ciudad), lat, lon);
	}
}
