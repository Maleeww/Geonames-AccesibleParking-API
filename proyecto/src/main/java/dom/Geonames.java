
package dom;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import ciudades.bean.PuntoInteres;

public class Geonames {

	// http://api.geonames.org/findNearbyWikipedia?postalcode=30800&country=ES&lang=es&radius=20&maxRows=500&username=miledavid&style=full
	// argumentos separados por &
	// postalcode=30800 lorca
	// country=ES si no busca en FR
	// lang=es traduce al castellano
	// username=miledavid para usar nuestro usuarioo

	public static void main(String[] args)
			throws URISyntaxException, ParserConfigurationException, SAXException, IOException {

		// Construcci�n de nuestra b�squeda
		int postalcode = 30800; // Lorca
		int radius = 14; // A partir de 14 salen lugares fuera de Lorca
		int maxrows = 500; // maximo sin premium
		String geourl = "http://api.geonames.org/findNearbyWikipedia?postalcode=" + postalcode
				+ "&country=ES&lang=es&radius=" + radius + "&maxRows=" + maxrows + "&username=miledavid&style=full";
		URL url = new URL(geourl);

		DocumentBuilderFactory factoria = DocumentBuilderFactory.newInstance();
		DocumentBuilder analizador = factoria.newDocumentBuilder();

		// Abrimos stream desde la url y lo parseamos al doc
		InputStream stream = url.openStream();

		// InputStream inputStream= new FileInputStream(geourl);
		Charset cs = Charset.forName("ISO-8859-1");
		Reader reader = new InputStreamReader(stream, cs);
		InputSource is = new InputSource(reader);
		is.setEncoding("ISO-8859-1");

		Document documento = analizador.parse(is);

		// Buscamos los elementos que queremos
		// e iteramos sacando los elementos que necesitamos para crear los objetos
		NodeList elementos = documento.getElementsByTagName("entry");
		ArrayList<PuntoInteres> arrayPI = new ArrayList<>();

		for (int i = 0; i < elementos.getLength(); i++) {
			Element elemento = (Element) elementos.item(i);
			Element title = (Element) elemento.getElementsByTagName("title").item(0);
			String titl = title.getTextContent();

			Element elat = (Element) elemento.getElementsByTagName("lat").item(0);
			String slat = elat.getTextContent();

			Element elng = (Element) elemento.getElementsByTagName("lng").item(0);
			String slng = elng.getTextContent();

			Double lat = Double.parseDouble(slat);
			Double lng = Double.parseDouble(slng);

			Element ewikiurl = (Element) elemento.getElementsByTagName("wikipediaUrl").item(0);
			String wikiurl = ewikiurl.getTextContent();

			PuntoInteres pi = new PuntoInteres();
			pi.setTitle(titl);
			pi.setLatitud(lat);
			pi.setLongitud(lng);
			pi.setWikipediaUrl(wikiurl); // iglesia de santiago? colegiata? // museo arqueologico?
			arrayPI.add(pi);
		}

		// for(PuntoInteres pi : arrayPI) {
		// System.out.println(pi);
		// }

		// split con separator /wiki/ y cogemos str[1]
		// json -> http://es.dbpedia.org/resource/Tiata ->
		// http://www.w3.org/2000/01/rdf-schema#comment
		for (PuntoInteres pp : arrayPI) {
//			PuntoInteres pp = arrayPI.get(0);
			String name = pp.getWikipediaUrl().split("/wiki/")[1];
			String dbstring = "https://es.dbpedia.org/data/" + name + ".json";
			URL dburl = new URL(dbstring);
			System.out.println(dburl);
			InputStream dbstream = dburl.openStream();
			Charset css = Charset.forName("UTF-8");
			Reader reader2 = new InputStreamReader(dbstream, css);
			// Document dbdoc = analizador.parse(stream);

			JsonReader jsonReader = Json.createReader(reader2);
			JsonObject dbobj = jsonReader.readObject();
//			System.out.println(dbobj);
			String rsc = "http://es.dbpedia.org/resource/" + name;
			String decoded = URLDecoder.decode(rsc, "UTF-8");
			JsonObject dbobj1 = dbobj.getJsonObject(decoded);

			JsonArray dbObjCom = dbobj1.getJsonArray("http://dbpedia.org/ontology/abstract");
			JsonObject commentObj = dbObjCom.getJsonObject(0);
			String commentSt = commentObj.getString("value");
			// String comment = commentJS.toString();
			System.out.println(commentSt);

			JsonArray dbobjImg = dbobj1.getJsonArray("http://es.dbpedia.org/property/imagen");
			if (dbobjImg != null) {
				JsonObject imgObj = dbobjImg.getJsonObject(0);
				String imgSt;
				try {
					imgSt = imgObj.getString("value");
					System.out.println(imgSt);

				} catch (ClassCastException e) {
					System.out.println("La imagen de " + name + " no se encuentra en una URL");
				}
				// String comment = commentJS.toString();
			}

			JsonArray dbobjTipo = dbobj1.getJsonArray("http://es.dbpedia.org/property/tipo");
			if (dbobjTipo != null) {
				JsonObject tipoObj = dbobjTipo.getJsonObject(0);
				String tipoSt = tipoObj.getString("value");
				// String comment = commentJS.toString();
				System.out.println(tipoSt);
			}

			JsonArray dbobjPropietario = dbobj1.getJsonArray("http://es.dbpedia.org/property/propietario");
			if (dbobjPropietario != null) {
				JsonObject propietarioObj = dbobjPropietario.getJsonObject(0);
				String propietarioSt = propietarioObj.getString("value");
				// String comment = commentJS.toString();
				System.out.println(propietarioSt);
			}
			// Una vez tengo todo, lo meto en el objeto? XML?
		}

	}

}
