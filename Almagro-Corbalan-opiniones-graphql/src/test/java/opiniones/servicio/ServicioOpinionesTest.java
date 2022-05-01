
package opiniones.servicio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.time.LocalDateTime;
import java.util.LinkedList;
import org.junit.Before;
import org.junit.Test;

import opiniones.modelo.Opinion;
import opiniones.modelo.Valoracion;
import opiniones.repositorio.FactoriaRepositorioOpiniones;
import opiniones.repositorio.RepositorioOpiniones;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;

public class ServicioOpinionesTest {
	private RepositorioOpiniones repo;
	private IServicioOpiniones serv;

	private String url = "localhost:8080/api/ciudades/Lorca/puntos/Castillo_de_Lorca/aparcamientos/37.677381937417934_-1.7053470604319045";

	@Before
	public void setUp() throws RepositorioException {
		serv = ServicioOpiniones.getInstancia();
		repo = FactoriaRepositorioOpiniones.getRepositorio();
		Opinion o = new Opinion();
		o.setUrlRecurso("url");
		o.setValoraciones(new LinkedList<>());
		serv.create(url);
	}

	@Test(expected = IllegalArgumentException.class)
	public void createOpinionUrlNull() throws RepositorioException {


		serv.create(null);
	}
	
	@Test
	public void createOpinionUrl() throws RepositorioException, EntidadNoEncontrada {


		serv.create(url);

		Opinion opinion = serv.getByUrl(url);
		assertNotNull(opinion);
		System.out.println(opinion);
	}

	/*
	 * @Test(expected = IllegalArgumentException.class) public void
	 * createOpinionValoracionesNull() throws RepositorioException { Opinion o = new
	 * Opinion(); o.setUrlRecurso("a"); o.setValoraciones(null);
	 * 
	 * serv.create(url); }
	 * 
	 * @Test(expected = IllegalArgumentException.class) public void
	 * createOpinionValoracionEmailNull() throws RepositorioException { Opinion o =
	 * new Opinion(); o.setUrlRecurso("a"); Valoracion v = new Valoracion();
	 * v.setFechaCreacion(LocalDateTime.now()); v.setNota(2); v.setEmail(null);
	 * LinkedList<Valoracion> valoraciones = new LinkedList<>();
	 * valoraciones.add(v); o.setValoraciones(valoraciones);
	 * 
	 * serv.create(url); }
	 * 
	 * @Test(expected = IllegalArgumentException.class) public void
	 * createOpinionValoracionFechaNull() throws RepositorioException { Opinion o =
	 * new Opinion(); o.setUrlRecurso("a"); Valoracion v = new Valoracion();
	 * v.setFechaCreacion(null); v.setNota(2); v.setEmail("jose@gmail.com");
	 * LinkedList<Valoracion> valoraciones = new LinkedList<>();
	 * valoraciones.add(v); o.setValoraciones(valoraciones);
	 * 
	 * serv.create(url); }
	 * 
	 * @Test(expected = IllegalArgumentException.class) public void
	 * createOpinionValoracionNotaFueraRango() throws RepositorioException { Opinion
	 * o = new Opinion(); o.setUrlRecurso("a"); Valoracion v = new Valoracion();
	 * v.setFechaCreacion(LocalDateTime.now()); v.setNota(10);
	 * v.setEmail("jose@gmail.com"); LinkedList<Valoracion> valoraciones = new
	 * LinkedList<>(); valoraciones.add(v); o.setValoraciones(valoraciones);
	 * 
	 * serv.create(url); }
	 */
	@Test(expected = IllegalArgumentException.class)
	public void valorarOpinionValoracionNotaFueraRango() throws RepositorioException, EntidadNoEncontrada {
		Opinion o = new Opinion();
		o.setUrlRecurso(url);
		o.setValoraciones(new LinkedList<>());
		serv.create(url);

		serv.valorar(url, "jose@gmail.com", 10);
	}

	@Test(expected = IllegalArgumentException.class)
	public void valorarOpinionValoracionEmailNull() throws RepositorioException, EntidadNoEncontrada {

		Opinion o = new Opinion();
		o.setUrlRecurso(url);
		o.setValoraciones(new LinkedList<>());
		serv.create(url);

		serv.valorar(url, null, 4);
	}

	@Test
	public void valorarOpinionValida() throws RepositorioException, EntidadNoEncontrada {
		Opinion o = new Opinion();
		o.setUrlRecurso(url);
		o.setValoraciones(new LinkedList<>());
		serv.create(url);
		serv.valorar(url, "jose@gmail.com", 4);

		o = serv.getByUrl(url);
		System.out.println(o.getValoraciones().get(0));
		assertEquals(o.getNumValoraciones(), 1);
	}
	
	@Test
	public void valorarOpinionUsuarioDuplicado() throws RepositorioException, EntidadNoEncontrada {
		
		serv.removeByUrl(url);
		serv.create(url);

		// Se tiene que sobreescribir con el 3
		serv.valorar(url, "jose@gmail.com", 4);
		serv.valorar(url, "jose@gmail.com", 3);

		Opinion o = serv.getByUrl(url);
		assertEquals(o.getNumValoraciones(), 1);
	}
	
	@Test
	public void valorarOpinionMedia() throws RepositorioException, EntidadNoEncontrada {
		serv.removeByUrl(url);
		serv.create(url);

		serv.valorar(url, "jose@gmail.com", 5);
		serv.valorar(url, "juan@gmail.com", 3);

		Opinion o = serv.getByUrl(url);
		assertEquals(o.getNumValoraciones(), 2);
		assertEquals(o.getMediaValoraciones(), 4.0, 0.1);
	}

	@Test
	public void getByUrl() throws RepositorioException, EntidadNoEncontrada {
		Opinion o = new Opinion();
		o.setUrlRecurso(url);
		o.setValoraciones(new LinkedList<>());
		serv.create(url);

		assertNotNull(serv.getByUrl(url));
	}

	@Test(expected = IllegalArgumentException.class)
	public void getByUrlNullUrl() throws RepositorioException, EntidadNoEncontrada {

		serv.getByUrl(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void getByUrlEmptyUrl() throws RepositorioException, EntidadNoEncontrada {

		serv.getByUrl("");
	}

	@Test(expected = EntidadNoEncontrada.class)
	public void getByUrlNotFound() throws RepositorioException, EntidadNoEncontrada {

		serv.getByUrl("aaaa");
	}

	@Test(expected = IllegalArgumentException.class)
	public void removeByUrlNullUrl() throws RepositorioException, EntidadNoEncontrada {

		serv.removeByUrl(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void removeByUrlEmptyUrl() throws RepositorioException, EntidadNoEncontrada {

		serv.removeByUrl("");
	}

	@Test(expected = EntidadNoEncontrada.class)
	public void removeByUrlNotFound() throws RepositorioException, EntidadNoEncontrada {
		serv.removeByUrl("aaa");
	}

	@Test
	public void removeByUrlTest() throws RepositorioException, EntidadNoEncontrada {

		serv.create(url);

		assertNotNull(serv.getByUrl(url));
		serv.removeByUrl(url);
		serv.getByUrl(url);

	}

}
