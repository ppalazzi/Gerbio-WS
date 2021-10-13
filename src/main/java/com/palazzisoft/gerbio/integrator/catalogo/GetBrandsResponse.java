
package com.palazzisoft.gerbio.integrator.catalogo;

import javax.xml.bind.annotation.*;


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
     * Obtiene el valor de la propiedad getBrandsResult.
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
     * Define el valor de la propiedad getBrandsResult.
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
