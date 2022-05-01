package opiniones.servicio;

import java.net.URLDecoder;

import java.util.LinkedList;

import opiniones.modelo.Opinion;
import opiniones.repositorio.FactoriaRepositorioOpiniones;
import opiniones.repositorio.RepositorioOpiniones;

public class Programa {

	public static void main(String[] args) throws Exception {

		
		RepositorioOpiniones rep = FactoriaRepositorioOpiniones.getRepositorio();
		Opinion opinion = new Opinion();
		String paramurl = "localhost%3A8080%2Fapi%2Fciudades%2FLorca%2Fpuntos%2FCastillo_de_Lorca%2Faparcamientos%2F37.677385652447754_-1.7053383432526061";
		String decode = URLDecoder.decode(paramurl, "UTF-8");
		opinion.setUrlRecurso(decode);
		opinion.setValoraciones(new LinkedList<>());
		
		String res = rep.add(opinion);
		System.out.println(res);
		
		System.out.println(rep.getById(res).toString());
		System.out.println(rep.getByUrl(decode).toString());
		
		/*
		 * String punto =
		 * "localhost:8080/api/ciudades/Lorca/puntos/Castillo_de_Lorca/aparcamientos/37.677385652447754_-1.7053383432526061";
		 * String encoded = URLEncoder.encode(punto); String api =
		 * "localhost:9995/ws/opiniones/"; String prueba1 = api+encoded;
		 * System.out.println(prueba1);
		 */
		/*
		 * ServicioOpiniones servicio = ServicioOpiniones.getInstancia();
		 * 
		 * // Configura la encuesta
		 * 
		 * Opinion opinion = new Opinion();
		 * 
		 * encuesta.setTitulo("Fecha del parcial");
		 * encuesta.setApertura(LocalDateTime.now());
		 * encuesta.setCierre(LocalDateTime.now().plusDays(1));
		 * 
		 * Opcion opcion1 = new Opcion(); opcion1.setTexto("Jueves"); Opcion opcion2 =
		 * new Opcion(); opcion2.setTexto("Viernes");
		 * 
		 * Collections.addAll(encuesta.getOpciones(), opcion1, opcion2);
		 * 
		 * // Alta de la encuesta
		 * 
		 * String id = servicio.create(encuesta);
		 * 
		 * // Voto
		 * 
		 * servicio.votar(id, 1, "juan@um.es");
		 * 
		 * System.out.println("¿Ha votado juan? " + servicio.haVotado(id,
		 * "juan@um.es")); System.out.println("¿Ha votado jose? " +
		 * servicio.haVotado(id, "jose@um.es"));
		 * 
		 * for (EncuestaResumen resumen : servicio.getListadoResumen().getEncuestas()) {
		 * System.out.println(resumen);
		 * 
		 * if (! resumen.getId().equals(id)) servicio.remove(resumen.getId()); }
		 * 
		 * System.out.println("fin.");
		 */

	}
}
