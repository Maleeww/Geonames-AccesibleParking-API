package sax;

import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import es.um.ciudad.Aparcamiento;



public class Programa {

	public static void main(String[] args) throws Exception {
		SAXParser analizador = SAXParserFactory.newInstance().newSAXParser();
		
		Manejador manejador = new Manejador();
		
		System.out.println("Comienza analisis . . .");
		analizador.parse("xml/parking-movilidad-reducida.xml", manejador);
		System.out.println("Fin analisis");
		
		System.out.println("Aparcamientos: ");
		List<Aparcamiento> aparcamientos = manejador.getAparcamientos();
		for (Aparcamiento a : aparcamientos) {
			System.out.println(a);
		}
		System.out.println("Total: " + aparcamientos.size());
	}

}
