
package com.palazzisoft.gerbio.integrator.alta;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PedidoResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PedidoResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="NumNdp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IDResponse" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/&gt;
 *         &lt;element name="ReProceso" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PedidoResponse", propOrder = {
    "numNdp",
    "idResponse",
    "reProceso"
})
public class PedidoResponse {

    @XmlElement(name = "NumNdp", namespace = "http://tempuri.org/")
    protected String numNdp;
    @XmlElement(name = "IDResponse", namespace = "http://tempuri.org/")
    @XmlSchemaType(name = "unsignedInt")
    protected long idResponse;
    @XmlElement(name = "ReProceso", namespace = "http://tempuri.org/")
    protected String reProceso;

    /**
     * Gets the value of the numNdp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumNdp() {
        return numNdp;
    }

    /**
     * Sets the value of the numNdp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumNdp(String value) {
        this.numNdp = value;
    }

    /**
     * Gets the value of the idResponse property.
     * 
     */
    public long getIDResponse() {
        return idResponse;
    }

    /**
     * Sets the value of the idResponse property.
     * 
     */
    public void setIDResponse(long value) {
        this.idResponse = value;
    }

    /**
     * Gets the value of the reProceso property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReProceso() {
        return reProceso;
    }

    /**
     * Sets the value of the reProceso property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReProceso(String value) {
        this.reProceso = value;
    }

}
