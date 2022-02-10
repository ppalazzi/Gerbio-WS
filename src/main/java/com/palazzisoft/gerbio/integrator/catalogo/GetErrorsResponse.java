
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
 *         &lt;element name="GetErrorsResult" type="{http://tempuri.org/}ErrorRequest" minOccurs="0"/&gt;
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
    "getErrorsResult"
})
@XmlRootElement(name = "GetErrorsResponse")
public class GetErrorsResponse {

    @XmlElement(name = "GetErrorsResult")
    protected ErrorRequest getErrorsResult;

    /**
     * Gets the value of the getErrorsResult property.
     * 
     * @return
     *     possible object is
     *     {@link ErrorRequest }
     *     
     */
    public ErrorRequest getGetErrorsResult() {
        return getErrorsResult;
    }

    /**
     * Sets the value of the getErrorsResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ErrorRequest }
     *     
     */
    public void setGetErrorsResult(ErrorRequest value) {
        this.getErrorsResult = value;
    }

}
