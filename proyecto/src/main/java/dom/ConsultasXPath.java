package dom;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ConsultasXPath {
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
		
		DocumentBuilderFactory factoriadom = DocumentBuilderFactory.newInstance();
		DocumentBuilder analizador = factoriadom.newDocumentBuilder();
		
		Document documento = analizador.parse("xml/ciudadEjemplo.xml");

		XPathFactory factoria = XPathFactory.newInstance();
		XPath xpath = factoria.newXPath();

		
		XPathExpression consultaDirecciones = xpath.compile("/ciudad/aparcamiento/direccion"); // El operador // es en profundidad
		XPathExpression consultaPuntosInteres = xpath.compile("//puntoInteres/title"); 																				// @extraordinaria
		XPathExpression consultaTurnosConReserva = xpath.compile("//aparcamiento/latitud"); 
		
		NodeList resultado1 = (NodeList) consultaDirecciones.evaluate(documento, XPathConstants.NODESET);

		for (int i = 0; i < resultado1.getLength(); i++) {

			Node nodo = resultado1.item(i);

			System.out.println("Nodo: " + nodo.getNodeName());
			System.out.println("Contenido: " + nodo.getTextContent());
		}
		
		NodeList resultado2 = (NodeList) consultaPuntosInteres.evaluate(documento, XPathConstants.NODESET);
		
		for (int i = 0; i < resultado1.getLength(); i++) {
			
			Node nodo = resultado2.item(i);
			
			System.out.println("Nodo: " + nodo.getNodeName());
			System.out.println("Contenido: " + nodo.getTextContent());
		}
		
		NodeList resultado3 = (NodeList) consultaTurnosConReserva.evaluate(documento, XPathConstants.NODESET);
		
		for (int i = 0; i < resultado1.getLength(); i++) {
			
			Node nodo = resultado3.item(i);
			
			System.out.println("Nodo: " + nodo.getNodeName());
			System.out.println("Contenido: " + nodo.getTextContent());
		}
	}
}
