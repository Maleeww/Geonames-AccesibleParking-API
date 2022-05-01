package retrofit.opiniones;

import java.io.BufferedReader;
import java.net.URLEncoder;
import java.util.stream.Collectors;

import opiniones.modelo.*;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.jaxb.JaxbConverterFactory;

public class Programa {

	public static void main(String[] args) throws Exception {

		Retrofit retrofit = new Retrofit.Builder().baseUrl("http://localhost:8080/api/")
				.addConverterFactory(JacksonConverterFactory.create()).build();

		OpinionesRestClient service = retrofit.create(OpinionesRestClient.class);


		String url1 = "localhost:8080/api/ciudades/Lorca/puntos/Castillo_de_Lorca/aparcamientos/37.677381937417934_-1.7053470604319045";
		String url = URLEncoder.encode(url1,"UTF-8");


		// Create
		
		Response<Void> resultado = service.create(url).execute();

		String resultadoUrl = resultado.headers().get("Location");
		
		
		System.out.println("Opinión creada: " + resultadoUrl );

		
		// Get

		Opinion opinion = service.getByUrl(url1).execute().body();

		System.out.println("Opinion: " + opinion);


		// Valorar

		Response<Void> respuesta = service.valorar(url1, "antonio1@um.es", 2, "Mal").execute();

		System.out.println("Código de respuesta: " + respuesta.message());


		// Remove


		resultado = service.removeByUrl(url1).execute();

		System.out.println("Eliminación:" + resultado.isSuccessful());

	}
}