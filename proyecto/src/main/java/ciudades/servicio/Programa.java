package ciudades.servicio;

import java.util.List;

import es.um.ciudad.Aparcamiento;
import es.um.ciudad.PuntoInteres;

public class Programa {

	public static void main(String[] args) throws Exception {
		IServicioCiudades servicio = ServicioCiudades.getInstancia();

		// 1. Obtener las ciudades que pueden ser consultadas
		List<CiudadResumen> ciudades = servicio.getListadoCiudades();
		System.out.println("-----------------------------------------");
		System.out.println("1. Ciudades disponibles:");
		ciudades.forEach(System.out::println);
		System.out.println();

		// 2. Conocer los sitios de interés turístico de una ciudad.
		List<PuntoInteres> puntos = servicio.getPuntosInteresCiudad(ciudades.get(0).getNombre());
		System.out.println("-----------------------------------------");
		System.out.println("2. Puntos de interés de " + ciudades.get(0).getNombre() + ":");
		puntos.forEach(pi -> System.out.println(pi.getTitle()));
		System.out.println();

		// 3. Dado un sitio de interés, obtener plazas de aparcamiento cercanas.
		List<Aparcamiento> aparcamientos = servicio
				.getAparcamientosCercanos(ciudades.get(0).getNombre(), puntos.get(0).getWikiName());
		System.out.println("-----------------------------------------");
		System.out.println("3. Aparcamientos cercanos a " + puntos.get(0).getTitle() + ":");
		aparcamientos.forEach(a -> System.out.println(a.getDireccion()));
		System.out.println();

		// 4. Obtener la información de una plaza de aparcamiento.
		Aparcamiento aparcamiento = servicio.getAparcamiento(ciudades.get(0).getNombre(),
				aparcamientos.get(0).getLatitud(), aparcamientos.get(0).getLongitud());
		System.out.println("-----------------------------------------");
		System.out.println("4. Información aparcamiento: ");
		System.out.println(aparcamiento.getDireccion());
		System.out.println(aparcamiento.getLatitud() + ", " + aparcamiento.getLongitud());
	}

}
