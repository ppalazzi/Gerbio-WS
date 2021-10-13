
package com.palazzisoft.gerbio.integrator.catalogo;

import javax.xml.namespace.QName;
import javax.xml.ws.*;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.3.2
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "WSMG", targetNamespace = "http://tempuri.org/", wsdlLocation = "https://ecommerce.microglobal.com.ar/WSMG_back/WSMG.asmx?WSDL")
public class WSMG
    extends Service
{

    private final static URL WSMG_WSDL_LOCATION;
    private final static WebServiceException WSMG_EXCEPTION;
    private final static QName WSMG_QNAME = new QName("http://tempuri.org/", "WSMG");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("https://ecommerce.microglobal.com.ar/WSMG_back/WSMG.asmx?WSDL");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        WSMG_WSDL_LOCATION = url;
        WSMG_EXCEPTION = e;
    }

    public WSMG() {
        super(__getWsdlLocation(), WSMG_QNAME);
    }

    public WSMG(WebServiceFeature... features) {
        super(__getWsdlLocation(), WSMG_QNAME, features);
    }

    public WSMG(URL wsdlLocation) {
        super(wsdlLocation, WSMG_QNAME);
    }

    public WSMG(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, WSMG_QNAME, features);
    }

    public WSMG(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public WSMG(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns WSMGSoap
     */
    @WebEndpoint(name = "WSMGSoap")
    public WSMGSoap getWSMGSoap() {
        return super.getPort(new QName("http://tempuri.org/", "WSMGSoap"), WSMGSoap.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns WSMGSoap
     */
    @WebEndpoint(name = "WSMGSoap")
    public WSMGSoap getWSMGSoap(WebServiceFeature... features) {
        return super.getPort(new QName("http://tempuri.org/", "WSMGSoap"), WSMGSoap.class, features);
    }

    private static URL __getWsdlLocation() {
        if (WSMG_EXCEPTION!= null) {
            throw WSMG_EXCEPTION;
        }
        return WSMG_WSDL_LOCATION;
    }

}
