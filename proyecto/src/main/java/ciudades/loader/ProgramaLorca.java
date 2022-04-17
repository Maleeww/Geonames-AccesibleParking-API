package ciudades.loader;

import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import ciudades.repositorio.FactoriaRepositorioCiudades;
import es.um.ciudad.Ciudad;

public class ProgramaLorca {
	private static final int RADIO_LORCA = 14;

	public static void main(String[] args) throws Exception {
		Ciudad lorca = new Ciudad();
		lorca.setNombre("Lorca");
		lorca.setCodigoPostal(30800);
		GregorianCalendar c = new GregorianCalendar();
		XMLGregorianCalendar date = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		lorca.setUpdated(date);

		CiudadLoader.extraerInfoGeonames(lorca, RADIO_LORCA);
		CiudadLoader.extraerInfoAparcamientosLorca(lorca, "xml/parking-movilidad-reducida.xml");
		
		FactoriaRepositorioCiudades.getRepositorio().add(lorca);
		System.out.println("Ciudad creada: " + lorca.getNombre());
		System.out.println("Fin.");
	}
}
