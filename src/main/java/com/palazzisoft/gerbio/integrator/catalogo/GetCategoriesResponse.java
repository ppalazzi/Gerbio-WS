
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
     * Gets the value of the getCategoriesResult property.
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
     * Sets the value of the getCategoriesResult property.
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
