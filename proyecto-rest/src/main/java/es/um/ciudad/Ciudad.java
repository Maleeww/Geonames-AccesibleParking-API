//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2022.05.12 a las 12:14:02 PM CEST 
//


package es.um.ciudad;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Clase Java para anonymous complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="puntoInteres" type="{http://www.um.es/ciudad}puntoInteres" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="aparcamiento" type="{http://www.um.es/ciudad}aparcamiento" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="nombre" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="codigoPostal" use="required" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *       &lt;attribute name="updated" use="required" type="{http://www.w3.org/2001/XMLSchema}dateTime" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "puntoInteres",
    "aparcamiento"
})
@XmlRootElement(name = "ciudad")
public class Ciudad {

    protected List<PuntoInteres> puntoInteres;
    protected List<Aparcamiento> aparcamiento;
    @XmlAttribute(name = "nombre", required = true)
    protected String nombre;
    @XmlAttribute(name = "codigoPostal", required = true)
    protected int codigoPostal;
    @XmlAttribute(name = "updated", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar updated;

    /**
     * Gets the value of the puntoInteres property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the puntoInteres property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPuntoInteres().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PuntoInteres }
     * 
     * 
     */
    public List<PuntoInteres> getPuntoInteres() {
        if (puntoInteres == null) {
            puntoInteres = new ArrayList<PuntoInteres>();
        }
        return this.puntoInteres;
    }

    /**
     * Gets the value of the aparcamiento property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the aparcamiento property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAparcamiento().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Aparcamiento }
     * 
     * 
     */
    public List<Aparcamiento> getAparcamiento() {
        if (aparcamiento == null) {
            aparcamiento = new ArrayList<Aparcamiento>();
        }
        return this.aparcamiento;
    }

    /**
     * Obtiene el valor de la propiedad nombre.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Define el valor de la propiedad nombre.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombre(String value) {
        this.nombre = value;
    }

    /**
     * Obtiene el valor de la propiedad codigoPostal.
     * 
     */
    public int getCodigoPostal() {
        return codigoPostal;
    }

    /**
     * Define el valor de la propiedad codigoPostal.
     * 
     */
    public void setCodigoPostal(int value) {
        this.codigoPostal = value;
    }

    /**
     * Obtiene el valor de la propiedad updated.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getUpdated() {
        return updated;
    }

    /**
     * Define el valor de la propiedad updated.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setUpdated(XMLGregorianCalendar value) {
        this.updated = value;
    }

}
