package ciudades.loader;

import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import ciudades.repositorio.FactoriaRepositorioCiudades;
import es.um.ciudad.Ciudad;

public class ProgramaMalaga {
	private static final int RADIO_MALAGA = 14;

	public static void main(String[] args) throws Exception {
		Ciudad malaga = new Ciudad();
		malaga.setNombre("Malaga");
		malaga.setCodigoPostal(29001);
		GregorianCalendar c = new GregorianCalendar();
		XMLGregorianCalendar date = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		malaga.setUpdated(date);
		
		CiudadLoader.extraerInfoGeonames(malaga, RADIO_MALAGA);
		CiudadLoader.extraerAparcamientosMalaga(malaga);
	
		FactoriaRepositorioCiudades.getRepositorio().add(malaga);
		System.out.println("Ciudad creada: " + malaga.getNombre());
		System.out.println("Fin.");
	}

}
