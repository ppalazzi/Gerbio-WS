
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
 *         &lt;element name="GetCategoriesResult" type="{http://tempuri.org/}CategoryRequest" minOccurs="0"/&gt;
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
    "getCategoriesResult"
})
@XmlRootElement(name = "GetCategoriesResponse")
public class GetCategoriesResponse {

    @XmlElement(name = "GetCategoriesResult")
    protected CategoryRequest getCategoriesResult;

    /**
     * Obtiene el valor de la propiedad getCategoriesResult.
     * 
     * @return
     *     possible object is
     *     {@link CategoryRequest }
     *     
     */
    public CategoryRequest getGetCategoriesResult() {
        return getCategoriesResult;
    }

    /**
     * Define el valor de la propiedad getCategoriesResult.
     * 
     * @param value
     *     allowed object is
     *     {@link CategoryRequest }
     *     
     */
    public void setGetCategoriesResult(CategoryRequest value) {
        this.getCategoriesResult = value;
    }

}
