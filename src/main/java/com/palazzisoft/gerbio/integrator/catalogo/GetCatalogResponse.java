
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
 *         &lt;element name="GetCatalogResult" type="{http://tempuri.org/}ProductsRequest" minOccurs="0"/&gt;
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
    "getCatalogResult"
})
@XmlRootElement(name = "GetCatalogResponse")
public class GetCatalogResponse {

    @XmlElement(name = "GetCatalogResult")
    protected ProductsRequest getCatalogResult;

    /**
     * Gets the value of the getCatalogResult property.
     * 
     * @return
     *     possible object is
     *     {@link ProductsRequest }
     *     
     */
    public ProductsRequest getGetCatalogResult() {
        return getCatalogResult;
    }

    /**
     * Sets the value of the getCatalogResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductsRequest }
     *     
     */
    public void setGetCatalogResult(ProductsRequest value) {
        this.getCatalogResult = value;
    }

}
