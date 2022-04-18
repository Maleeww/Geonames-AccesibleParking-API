package opiniones.repositorio;

import utils.PropertiesReader;

public class FactoriaRepositorioOpiniones {
	private static final String PROPIEDAD_IMPLEMENTACION = "opinion";

	private static RepositorioOpiniones repository = null;

	public static RepositorioOpiniones getRepositorio() {

		if (repository == null) {
			// Comprueba se existe una configuración específica para el
			// repositorio
			try {
				PropertiesReader properties = new PropertiesReader(
						RepositorioOpiniones.PROPERTIES);
				String clase = properties.getProperty(PROPIEDAD_IMPLEMENTACION);
				repository = (RepositorioOpiniones) Class.forName(clase)
						.getConstructor().newInstance();
			} catch (Exception e) {
				// Implementación por defecto
				repository = new RepositorioOpinionesXML();
			}

		}
		return repository;
	}

}
