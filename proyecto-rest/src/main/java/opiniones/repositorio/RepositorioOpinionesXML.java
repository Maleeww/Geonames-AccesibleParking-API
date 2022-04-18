package opiniones.repositorio;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import es.um.ciudad.Aparcamiento;
import es.um.ciudad.Ciudad;
import es.um.ciudad.PuntoInteres;
import opiniones.modelo.Opinion;
import repositorio.ElementoNoEncontrado;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;

public class RepositorioOpinionesXML implements RepositorioOpiniones {
 
	public final static String DIRECTORIO_OPINIONES = "opiniones/";
	public static final double EPSILON = 1E-15;

	static {

		File directorio = new File(DIRECTORIO_OPINIONES);

		if (!directorio.exists())
			directorio.mkdir();
	}

	private boolean doubleEquals(double a, double b) {
		return Math.abs(a - b) < EPSILON;
	}

	protected String getDocumento(String id) {
		return DIRECTORIO_OPINIONES + id + ".xml";
	}

	protected boolean checkDocumento(String id) {
		final String documento = getDocumento(id);
		File fichero = new File(documento);

		return fichero.exists();
	}

	protected void save(Opinion opinion) throws RepositorioException {
		final String documento = getDocumento(opinion.getNombre());
		final File fichero = new File(documento);

		try {
			JAXBContext contexto = JAXBContext.newInstance("es.um.ciudad");
			Marshaller marshaller = contexto.createMarshaller();
			marshaller.setProperty("jaxb.formatted.output", true);
			marshaller.marshal(opinion, fichero);

		} catch (Exception e) {
			e.printStackTrace();
			throw new RepositorioException( 
					"Error al guardar la ciudad "
							+ opinion.getNombre(),
					e);
		}
	}

	protected Opinion load(String id)
			throws RepositorioException, EntidadNoEncontrada {

		if (!checkDocumento(id))
			throw new EntidadNoEncontrada("No existe la opinion con id " + id);

		final String documento = getDocumento(id);

		try {
			JAXBContext contexto = JAXBContext.newInstance("opiniones.modelo");
			Unmarshaller unmarshaller = contexto.createUnmarshaller();

			return (Opinion) unmarshaller.unmarshal(new File(documento));
		} catch (Exception e) {
			throw new RepositorioException(
					"Error al cargar la opinión con nombre: " + id, e);
		}
	}

	// ----------------------------METODOS HEREDADOS----------------------------

	@Override
	public String add(Opinion entity) throws RepositorioException {
		save(entity);

		return entity.getNombre();
	}

	@Override
	public void update(Opinion entity)
			throws RepositorioException, EntidadNoEncontrada {

		if (!checkDocumento(entity.getNombre()))
			throw new EntidadNoEncontrada(
					"La actividad no existe, id: " + entity.getNombre());

		save(entity);

	}

	@Override
	public void delete(Opinion entity)
			throws RepositorioException, EntidadNoEncontrada {

		if (!checkDocumento(entity.getNombre()))
			throw new EntidadNoEncontrada(
					"La ciudad no existente: " + entity.getNombre());

		final String documento = getDocumento(entity.getNombre());

		File fichero = new File(documento);

		fichero.delete();

	}

	@Override
	public Opinion getById(String id)
			throws RepositorioException, EntidadNoEncontrada {

		return load(id);
	}

	@Override
	public List<Opinion> getAll() throws RepositorioException {

		LinkedList<Opinion> resultado = new LinkedList<Opinion>();

		for (String id : getIds()) {
			try {
				resultado.add(load(id));
			} catch (EntidadNoEncontrada e) {
				throw new RepositorioException(
						"Error al cargar la opinión: " + id, e);
			}
		}

		return resultado;
	}

	@Override
	public List<String> getIds() {
		LinkedList<String> resultado = new LinkedList<>();

		File directorio = new File(DIRECTORIO_OPINIONES);

		File[] actividades = directorio
				.listFiles(f -> f.isFile() && f.getName().endsWith(".xml"));

		for (File file : actividades) {

			String id = file.getName().substring(0,	file.getName().length() - 4);

			resultado.add(id);
		}

		return resultado;
	}

	@Override
	public Opinion getByUrl(String urlRecurso) throws EntidadNoEncontrada {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * @Override public PuntoInteres getPuntoInteresById(Ciudad ciudad, String id)
	 * throws ElementoNoEncontrado { return ciudad.getPuntoInteres().stream()
	 * .filter(p -> p.getWikiName().equals(id)) .findFirst() .orElseThrow(() -> new
	 * ElementoNoEncontrado("Punto de interés " + id + " no encontrado")); }
	 * 
	 * @Override public Aparcamiento getAparcamiento(Ciudad ciudad, double lat,
	 * double lon) throws ElementoNoEncontrado { return
	 * ciudad.getAparcamiento().stream() .filter(a -> doubleEquals(a.getLatitud(),
	 * lat) && doubleEquals(a.getLongitud(), lon)) .findFirst() .orElseThrow(() ->
	 * new ElementoNoEncontrado("Aparcamiento con coordenadas (" + lat + ", " + lon
	 * + ") no encontrado")); }
	 */
}
