
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
 *         &lt;element name="GetBrandsResult" type="{http://tempuri.org/}BrandRequest" minOccurs="0"/&gt;
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
    "getBrandsResult"
})
@XmlRootElement(name = "GetBrandsResponse")
public class GetBrandsResponse {

    @XmlElement(name = "GetBrandsResult")
    protected BrandRequest getBrandsResult;

    /**
     * Gets the value of the getBrandsResult property.
     * 
     * @return
     *     possible object is
     *     {@link BrandRequest }
     *     
     */
    public BrandRequest getGetBrandsResult() {
        return getBrandsResult;
    }

    /**
     * Sets the value of the getBrandsResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link BrandRequest }
     *     
     */
    public void setGetBrandsResult(BrandRequest value) {
        this.getBrandsResult = value;
    }

}
