package sax;

import java.util.LinkedList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import ciudades.bean.Aparcamiento;


public class Manejador extends DefaultHandler {

	private boolean dentroParking;
	private boolean dentroDireccion;
	private boolean dentroLongitud;
	private boolean dentroLatitud;

	private List<Aparcamiento> aparcamientos;
	private Aparcamiento aparcamiento;

	@Override
	public void startDocument() throws SAXException {
		dentroParking = false;
		dentroDireccion = false;
		dentroLongitud = false;
		dentroLatitud = false;

		if (aparcamientos == null)
			aparcamientos = new LinkedList<>();
		else
			aparcamientos.clear();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if (qName.equals("parking")) {
			dentroParking = true;
			aparcamiento = new Aparcamiento();
		} else if (qName.equals("direccion")) {
			dentroDireccion = true;
		} else if (qName.equals("longitud")) {
			dentroLongitud = true;
		} else if (qName.equals("latitud")) {
			dentroLatitud = true;
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if (dentroParking) {
			if (dentroDireccion) {
				aparcamiento.setDireccion(new String(ch, start, length));
			} else if (dentroLongitud) {
				aparcamiento.setLongitud(
						Double.parseDouble(new String(ch, start, length)));
			} else if (dentroLatitud) {
				aparcamiento.setLatitud(
						Double.parseDouble(new String(ch, start, length)));
			}
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (qName.equals("parking")) {
			dentroParking = false;
			aparcamientos.add(aparcamiento);
		} else if (qName.equals("direccion")) {
			dentroDireccion = false;
		} else if (qName.equals("longitud")) {
			dentroLongitud = false;
		} else if (qName.equals("latitud")) {
			dentroLatitud = false;
		}
	}
	
	public List<Aparcamiento> getAparcamientos() {
		return aparcamientos;
	}
}
