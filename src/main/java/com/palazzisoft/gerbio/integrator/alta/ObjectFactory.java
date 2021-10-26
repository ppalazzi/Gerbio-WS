
package com.palazzisoft.gerbio.integrator.alta;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.palazzisoft.gerbio.integrator.alta package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.palazzisoft.gerbio.integrator.alta
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link OPedido }
     * 
     */
    public OPedido createOPedido() {
        return new OPedido();
    }

    /**
     * Create an instance of {@link SendAltaPedido }
     * 
     */
    public SendAltaPedido createSendAltaPedido() {
        return new SendAltaPedido();
    }

    /**
     * Create an instance of {@link OPedido.IDs }
     * 
     */
    public OPedido.IDs createOPedidoIDs() {
        return new OPedido.IDs();
    }

    /**
     * Create an instance of {@link OPedido.UFI }
     * 
     */
    public OPedido.UFI createOPedidoUFI() {
        return new OPedido.UFI();
    }

    /**
     * Create an instance of {@link ArrayOfProducto }
     * 
     */
    public ArrayOfProducto createArrayOfProducto() {
        return new ArrayOfProducto();
    }

    /**
     * Create an instance of {@link SendAltaPedidoResponse }
     * 
     */
    public SendAltaPedidoResponse createSendAltaPedidoResponse() {
        return new SendAltaPedidoResponse();
    }

    /**
     * Create an instance of {@link PedidoRequest }
     * 
     */
    public PedidoRequest createPedidoRequest() {
        return new PedidoRequest();
    }

    /**
     * Create an instance of {@link PedidoResponse }
     * 
     */
    public PedidoResponse createPedidoResponse() {
        return new PedidoResponse();
    }

    /**
     * Create an instance of {@link Producto }
     * 
     */
    public Producto createProducto() {
        return new Producto();
    }

}
