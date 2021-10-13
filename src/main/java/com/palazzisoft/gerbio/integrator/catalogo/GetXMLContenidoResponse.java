
package com.palazzisoft.gerbio.integrator.catalogo;

import javax.xml.bind.annotation.*;


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
 *         &lt;element name="GetXMLContenidoResult" type="{http://tempuri.org/}ContentRequest" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "getXMLContenidoResult"
})
@XmlRootElement(name = "GetXMLContenidoResponse")
public class GetXMLContenidoResponse {

    @XmlElement(name = "GetXMLContenidoResult")
    protected ContentRequest getXMLContenidoResult;

    /**
     * Obtiene el valor de la propiedad getXMLContenidoResult.
     * 
     * @return
     *     possible object is
     *     {@link ContentRequest }
     *     
     */
    public ContentRequest getGetXMLContenidoResult() {
        return getXMLContenidoResult;
    }

    /**
     * Define el valor de la propiedad getXMLContenidoResult.
     * 
     * @param value
     *     allowed object is
     *     {@link ContentRequest }
     *     
     */
    public void setGetXMLContenidoResult(ContentRequest value) {
        this.getXMLContenidoResult = value;
    }

}
