package ciudades.loader;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLDecoder;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParserFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import es.um.ciudad.Aparcamiento;
import es.um.ciudad.Ciudad;
import es.um.ciudad.PuntoInteres;
import sax.Manejador;

public class CiudadLoader {

	public static void extraerInfoGeonames(Ciudad ciudad, int radio) throws Exception {
		int postalCode = ciudad.getCodigoPostal();
		int maxRows = 500;
		String geoUrl = "http://api.geonames.org/findNearbyWikipedia?postalcode=" + postalCode
				+ "&country=ES&lang=es&radius=" + radio + "&maxRows=" + maxRows
				+ "&username=miledavid&style=full";
		URL url = new URL(geoUrl);

		DocumentBuilder analizador = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document documento = analizador.parse(url.openStream());
		NodeList elementos = documento.getElementsByTagName("entry");

		for (int i = 0; i < elementos.getLength(); i++) {
			Element elemento = (Element) elementos.item(i);
			String feature = elemento.getElementsByTagName("feature").item(0).getTextContent();

			if (feature.equals("landmark") || feature.equals("waterbody")) {
				ciudad.getPuntoInteres().add(crearPuntoInteres(elemento));
			}
		}
	}

	public static PuntoInteres crearPuntoInteres(Element elemento) throws Exception {
		PuntoInteres pi = new PuntoInteres();

		Element eTitle = (Element) elemento.getElementsByTagName("title").item(0);
		String title = eTitle.getTextContent();
		pi.setTitle(title);

		Element eLat = (Element) elemento.getElementsByTagName("lat").item(0);
		String sLat = eLat.getTextContent();
		pi.setLatitud(Double.parseDouble(sLat));

		Element eLng = (Element) elemento.getElementsByTagName("lng").item(0);
		String sLng = eLng.getTextContent();
		pi.setLongitud(Double.parseDouble(sLng));

		Element eWikiName = (Element) elemento.getElementsByTagName("wikipediaUrl").item(0);
		String wikiName = URLDecoder.decode(eWikiName.getTextContent().split("/wiki/")[1], "UTF-8");
		pi.setWikiName(wikiName);

		extraerInfoDBPedia(pi);

		System.out.println(pi.getTitle());
		return pi;
	}

	public static void extraerInfoDBPedia(PuntoInteres pi) throws Exception {
		URL dbUrl = new URL("https://es.dbpedia.org/data/" + pi.getWikiName() + ".json");
		JsonObject dbObj = Json.createReader(new InputStreamReader(dbUrl.openStream(), "UTF-8"))
				.readObject();
		JsonObject rscObj = dbObj
				.getJsonObject("http://es.dbpedia.org/resource/" + pi.getWikiName());

		// resumen
		String resumen = rscObj.getJsonArray("http://dbpedia.org/ontology/abstract")
				.getJsonObject(0).getString("value");
		pi.setResumen(resumen);

		JsonArray tipoObj = rscObj.getJsonArray("http://es.dbpedia.org/property/tipo");
		if (tipoObj != null) {
			// tipo
			String tipo = tipoObj.getJsonObject(0).getString("value");
			if (tipo.contains("/")) {
				pi.setTipo(tipo.substring(tipo.lastIndexOf("/") + 1));
			} else
				pi.setTipo(tipo);
		}

		JsonArray propObj = rscObj.getJsonArray("http://es.dbpedia.org/property/propietario");
		if (propObj != null) {
			// propietario
			String propietario = propObj.getJsonObject(0).getString("value");
			pi.setPropietario(propietario);
		}
	}

	public static void extraerInfoAparcamientosLorca(Ciudad ciudad, String xml) throws Exception {
		Manejador manejador = new Manejador();
		SAXParserFactory.newInstance().newSAXParser().parse(xml, manejador);

		for (Aparcamiento a : manejador.getAparcamientos()) {
			ciudad.getAparcamiento().add(a);
		}
	}

	public static void extraerAparcamientosMalaga(Ciudad ciudad) throws Exception {
		JsonObject jsonObj = Json.createReader(new InputStreamReader(new FileInputStream("xml/da_aparcamientosMovilidadReducida-4326.geojson")))
				.readObject();
		
		JsonArray featArray = jsonObj.getJsonArray("features");

		for (int i = 0; i < featArray.size(); i++) {
			Aparcamiento aparcamiento = new Aparcamiento();
			JsonObject featObj = featArray.getJsonObject(i);

			JsonArray coordinates = featObj.getJsonObject("geometry").getJsonArray("coordinates");
			double lat = coordinates.getJsonNumber(0).doubleValue();
			aparcamiento.setLatitud(lat);
			
			double lon = coordinates.getJsonNumber(1).doubleValue(); 
			aparcamiento.setLongitud(lon);

			System.out.println(lat + ", " + lon);
			
			String descripcion = featObj.getJsonObject("properties").getString("description");
			String direccion = descripcion.substring(descripcion.indexOf("/b>") + 4, descripcion.indexOf("<br"));
			aparcamiento.setDireccion(direccion);
			
			System.out.println(direccion);
			
			ciudad.getAparcamiento().add(aparcamiento);
		}
	}
	
	public static void extraerAparcamientosTenerife(Ciudad ciudad) throws Exception {
		URL url = new URL("http://www.santacruzdetenerife.es/opendata/dataset/c82761b7-87bc-4706-829f-61ea8b32d29c/resource/712fc182-5b77-4e6d-a64e-d263ac3b3e1c/download/apar_mov_red.json");
		JsonReader jsonReader = Json.createReader(new InputStreamReader(url.openStream(), "UTF-8"));
		JsonObject jsonObj = jsonReader.readObject();
		
		JsonArray docsArray = jsonObj.getJsonArray("docs");
		
		for (int i = 0; i < docsArray.size(); i++) {
			Aparcamiento aparcamiento = new Aparcamiento();
			JsonObject docObj = docsArray.getJsonObject(i);
			
			JsonArray coordinates = docObj.getJsonObject("geometry").getJsonArray("coordinates");
			double lat = coordinates.getJsonNumber(0).doubleValue();
			aparcamiento.setLatitud(lat);
			
			double lon = coordinates.getJsonNumber(1).doubleValue(); 
			aparcamiento.setLongitud(lon);
			
			System.out.println(lat + ", " + lon);
			
			String direccion = docObj.getString("DIRECCION");
			aparcamiento.setDireccion(direccion);
			
			System.out.println(direccion);
			
			ciudad.getAparcamiento().add(aparcamiento);
		}
	}
}
