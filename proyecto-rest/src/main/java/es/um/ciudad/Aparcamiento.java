//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2022.05.12 a las 12:14:02 PM CEST 
//


package es.um.ciudad;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para aparcamiento complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="aparcamiento"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="direccion" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="longitud" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="latitud" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="numeroValoraciones" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="calificacionMedia" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="urlOpinion" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "aparcamiento", propOrder = {
    "direccion",
    "longitud",
    "latitud",
    "numeroValoraciones",
    "calificacionMedia",
    "urlOpinion"
})
public class Aparcamiento {

    @XmlElement(required = true)
    protected String direccion;
    protected double longitud;
    protected double latitud;
    protected int numeroValoraciones;
    protected double calificacionMedia;
    @XmlElement(required = true)
    protected String urlOpinion;

    /**
     * Obtiene el valor de la propiedad direccion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Define el valor de la propiedad direccion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDireccion(String value) {
        this.direccion = value;
    }

    /**
     * Obtiene el valor de la propiedad longitud.
     * 
     */
    public double getLongitud() {
        return longitud;
    }

    /**
     * Define el valor de la propiedad longitud.
     * 
     */
    public void setLongitud(double value) {
        this.longitud = value;
    }

    /**
     * Obtiene el valor de la propiedad latitud.
     * 
     */
    public double getLatitud() {
        return latitud;
    }

    /**
     * Define el valor de la propiedad latitud.
     * 
     */
    public void setLatitud(double value) {
        this.latitud = value;
    }

    /**
     * Obtiene el valor de la propiedad numeroValoraciones.
     * 
     */
    public int getNumeroValoraciones() {
        return numeroValoraciones;
    }

    /**
     * Define el valor de la propiedad numeroValoraciones.
     * 
     */
    public void setNumeroValoraciones(int value) {
        this.numeroValoraciones = value;
    }

    /**
     * Obtiene el valor de la propiedad calificacionMedia.
     * 
     */
    public double getCalificacionMedia() {
        return calificacionMedia;
    }

    /**
     * Define el valor de la propiedad calificacionMedia.
     * 
     */
    public void setCalificacionMedia(double value) {
        this.calificacionMedia = value;
    }

    /**
     * Obtiene el valor de la propiedad urlOpinion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrlOpinion() {
        return urlOpinion;
    }

    /**
     * Define el valor de la propiedad urlOpinion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrlOpinion(String value) {
        this.urlOpinion = value;
    }

}
