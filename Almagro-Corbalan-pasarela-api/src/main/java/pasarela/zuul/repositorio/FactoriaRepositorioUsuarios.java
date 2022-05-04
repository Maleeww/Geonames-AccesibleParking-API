package pasarela.zuul.repositorio;

import utils.PropertiesReader;

public class FactoriaRepositorioUsuarios {
	private static final String PROPIEDAD_IMPLEMENTACION = "usuario";

	private static RepositorioUsuarios repository = null;

	public static RepositorioUsuarios getRepositorio() {

		if (repository == null) {
			// Comprueba se existe una configuración específica para el
			// repositorio
			try {
				PropertiesReader properties = new PropertiesReader(
						RepositorioUsuarios.PROPERTIES);
				String clase = properties.getProperty(PROPIEDAD_IMPLEMENTACION);
				repository = (RepositorioUsuarios) Class.forName(clase)
						.getConstructor().newInstance();
			} catch (Exception e) {
				// Implementación por defecto
				repository = new RepositorioUsuariosMongoDB();
			}

		}
		return repository;
	}

}
