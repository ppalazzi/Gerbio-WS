
package com.palazzisoft.gerbio.integrator.catalogo;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
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
     * Gets the value of the getXMLContenidoResult property.
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
     * Sets the value of the getXMLContenidoResult property.
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
