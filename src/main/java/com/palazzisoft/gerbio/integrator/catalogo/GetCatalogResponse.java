
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
     * Obtiene el valor de la propiedad getCatalogResult.
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
     * Define el valor de la propiedad getCatalogResult.
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
