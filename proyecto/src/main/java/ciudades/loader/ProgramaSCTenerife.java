package ciudades.loader;

import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import ciudades.repositorio.FactoriaRepositorioCiudades;
import es.um.ciudad.Ciudad;

public class ProgramaSCTenerife {
	private static final int RADIO_TENERIFE = 4;

	public static void main(String[] args) throws Exception {
		Ciudad tenerife = new Ciudad();
		tenerife.setNombre("Santa_Cruz_de_Tenerife");
		tenerife.setCodigoPostal(38007);
		GregorianCalendar c = new GregorianCalendar();
		XMLGregorianCalendar date = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		tenerife.setUpdated(date);

		CiudadLoader.extraerInfoGeonames(tenerife, RADIO_TENERIFE);
		CiudadLoader.extraerAparcamientosTenerife(tenerife);
	
		FactoriaRepositorioCiudades.getRepositorio().add(tenerife);
		System.out.println("Ciudad creada: " + tenerife.getNombre());
		System.out.println("Fin.");
	}
	
}
