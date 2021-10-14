
package com.palazzisoft.gerbio.integrator.catalogo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="SendAltaPedidoResult" type="{http://tempuri.org/}PedidoRequest" minOccurs="0"/&gt;
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
    "sendAltaPedidoResult"
})
@XmlRootElement(name = "SendAltaPedidoResponse")
public class SendAltaPedidoResponse {

    @XmlElement(name = "SendAltaPedidoResult", namespace = "http://tempuri.org/")
    protected PedidoRequest sendAltaPedidoResult;

    /**
     * Gets the value of the sendAltaPedidoResult property.
     * 
     * @return
     *     possible object is
     *     {@link PedidoRequest }
     *     
     */
    public PedidoRequest getSendAltaPedidoResult() {
        return sendAltaPedidoResult;
    }

    /**
     * Sets the value of the sendAltaPedidoResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link PedidoRequest }
     *     
     */
    public void setSendAltaPedidoResult(PedidoRequest value) {
        this.sendAltaPedidoResult = value;
    }

}
