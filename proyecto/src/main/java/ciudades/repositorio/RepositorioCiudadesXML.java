package ciudades.repositorio;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import es.um.ciudad.Aparcamiento;
import es.um.ciudad.Ciudad;
import es.um.ciudad.PuntoInteres;
import repositorio.ElementoNoEncontrado;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;

public class RepositorioCiudadesXML implements RepositorioCiudades {
 
	public final static String DIRECTORIO_CIUDADES = "ciudades/";
	public static final double EPSILON = 1E-15;

	static {

		File directorio = new File(DIRECTORIO_CIUDADES);

		if (!directorio.exists())
			directorio.mkdir();
	}

	private boolean doubleEquals(double a, double b) {
		return Math.abs(a - b) < EPSILON;
	}

	protected String getDocumento(String id) {
		return DIRECTORIO_CIUDADES + id + ".xml";
	}

	protected boolean checkDocumento(String id) {
		final String documento = getDocumento(id);
		File fichero = new File(documento);

		return fichero.exists();
	}

	protected void save(Ciudad ciudad) throws RepositorioException {
		final String documento = getDocumento(ciudad.getNombre());
		final File fichero = new File(documento);

		try {
			JAXBContext contexto = JAXBContext.newInstance("es.um.ciudad");
			Marshaller marshaller = contexto.createMarshaller();
			marshaller.setProperty("jaxb.formatted.output", true);
			marshaller.marshal(ciudad, fichero);

		} catch (Exception e) {
			e.printStackTrace();
			throw new RepositorioException( 
					"Error al guardar la ciudad "
							+ ciudad.getNombre(),
					e);
		}
	}

	protected Ciudad load(String id)
			throws RepositorioException, EntidadNoEncontrada {

		if (!checkDocumento(id))
			throw new EntidadNoEncontrada("No existe la ciudad con id " + id);

		final String documento = getDocumento(id);

		try {
			JAXBContext contexto = JAXBContext.newInstance("es.um.ciudad");
			Unmarshaller unmarshaller = contexto.createUnmarshaller();

			return (Ciudad) unmarshaller.unmarshal(new File(documento));
		} catch (Exception e) {
			throw new RepositorioException(
					"Error al cargar la ciudad con nombre: " + id, e);
		}
	}

	// ----------------------------METODOS HEREDADOS----------------------------

	@Override
	public String add(Ciudad entity) throws RepositorioException {
		save(entity);

		return entity.getNombre();
	}

	@Override
	public void update(Ciudad entity)
			throws RepositorioException, EntidadNoEncontrada {

		if (!checkDocumento(entity.getNombre()))
			throw new EntidadNoEncontrada(
					"La actividad no existe, id: " + entity.getNombre());

		save(entity);

	}

	@Override
	public void delete(Ciudad entity)
			throws RepositorioException, EntidadNoEncontrada {

		if (!checkDocumento(entity.getNombre()))
			throw new EntidadNoEncontrada(
					"La ciudad no existente: " + entity.getNombre());

		final String documento = getDocumento(entity.getNombre());

		File fichero = new File(documento);

		fichero.delete();

	}

	@Override
	public Ciudad getById(String id)
			throws RepositorioException, EntidadNoEncontrada {

		return load(id);
	}

	@Override
	public List<Ciudad> getAll() throws RepositorioException {

		LinkedList<Ciudad> resultado = new LinkedList<Ciudad>();

		for (String id : getIds()) {
			try {
				resultado.add(load(id));
			} catch (EntidadNoEncontrada e) {
				throw new RepositorioException(
						"Error al cargar la ciudad: " + id, e);
			}
		}

		return resultado;
	}

	@Override
	public List<String> getIds() {
		LinkedList<String> resultado = new LinkedList<>();

		File directorio = new File(DIRECTORIO_CIUDADES);

		File[] actividades = directorio
				.listFiles(f -> f.isFile() && f.getName().endsWith(".xml"));

		for (File file : actividades) {

			String id = file.getName().substring(0,	file.getName().length() - 4);

			resultado.add(id);
		}

		return resultado;
	}

	@Override
	public PuntoInteres getPuntoInteresById(Ciudad ciudad, String id) throws ElementoNoEncontrado {
		return ciudad.getPuntoInteres().stream()
			.filter(p -> p.getWikiName().equals(id))
			.findFirst()
			.orElseThrow(() -> new ElementoNoEncontrado("Punto de interés " + id + " no encontrado"));
	}

	@Override
	public Aparcamiento getAparcamiento(Ciudad ciudad, double lat, double lon)
			throws ElementoNoEncontrado {
		return ciudad.getAparcamiento().stream()
				.filter(a -> doubleEquals(a.getLatitud(), lat) && doubleEquals(a.getLongitud(), lon))
				.findFirst()
				.orElseThrow(() -> new ElementoNoEncontrado("Aparcamiento con coordenadas (" 
						+ lat + ", " + lon + ") no encontrado"));
	}

}
