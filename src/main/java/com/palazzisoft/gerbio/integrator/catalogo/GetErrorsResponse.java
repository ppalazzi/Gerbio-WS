
package com.palazzisoft.gerbio.integrator.catalogo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
     * Obtiene el valor de la propiedad getErrorsResult.
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
     * Define el valor de la propiedad getErrorsResult.
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
