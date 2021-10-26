
package com.palazzisoft.gerbio.integrator.alta;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PedidoRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PedidoRequest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="result" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="message" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="pedidoResponse" type="{http://tempuri.org/}PedidoResponse" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PedidoRequest", propOrder = {
    "result",
    "message",
    "pedidoResponse"
})
public class PedidoRequest {

    @XmlElement(namespace = "http://tempuri.org/")
    protected int result;
    @XmlElement(namespace = "http://tempuri.org/")
    protected String message;
    @XmlElement(namespace = "http://tempuri.org/")
    protected PedidoResponse pedidoResponse;

    /**
     * Gets the value of the result property.
     * 
     */
    public int getResult() {
        return result;
    }

    /**
     * Sets the value of the result property.
     * 
     */
    public void setResult(int value) {
        this.result = value;
    }

    /**
     * Gets the value of the message property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the value of the message property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessage(String value) {
        this.message = value;
    }

    /**
     * Gets the value of the pedidoResponse property.
     * 
     * @return
     *     possible object is
     *     {@link PedidoResponse }
     *     
     */
    public PedidoResponse getPedidoResponse() {
        return pedidoResponse;
    }

    /**
     * Sets the value of the pedidoResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link PedidoResponse }
     *     
     */
    public void setPedidoResponse(PedidoResponse value) {
        this.pedidoResponse = value;
    }

}
