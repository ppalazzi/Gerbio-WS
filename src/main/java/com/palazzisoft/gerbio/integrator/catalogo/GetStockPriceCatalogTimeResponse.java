
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
 *         &lt;element name="GetStockPriceCatalogTimeResult" type="{http://tempuri.org/}ProductsRequest" minOccurs="0"/&gt;
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
    "getStockPriceCatalogTimeResult"
})
@XmlRootElement(name = "GetStockPriceCatalogTimeResponse")
public class GetStockPriceCatalogTimeResponse {

    @XmlElement(name = "GetStockPriceCatalogTimeResult")
    protected ProductsRequest getStockPriceCatalogTimeResult;

    /**
     * Gets the value of the getStockPriceCatalogTimeResult property.
     * 
     * @return
     *     possible object is
     *     {@link ProductsRequest }
     *     
     */
    public ProductsRequest getGetStockPriceCatalogTimeResult() {
        return getStockPriceCatalogTimeResult;
    }

    /**
     * Sets the value of the getStockPriceCatalogTimeResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductsRequest }
     *     
     */
    public void setGetStockPriceCatalogTimeResult(ProductsRequest value) {
        this.getStockPriceCatalogTimeResult = value;
    }

}
