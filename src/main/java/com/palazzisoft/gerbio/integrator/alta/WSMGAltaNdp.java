
package com.palazzisoft.gerbio.integrator.alta;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.3.2
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "WSMGAltaNdp", targetNamespace = "http://tempuri.org/", wsdlLocation = "https://ws.microglobal.com.ar/WSMGAltaNdp_test/WSMGAltaNdp.asmx?WSDL")
public class WSMGAltaNdp
    extends Service
{

    private final static URL WSMGALTANDP_WSDL_LOCATION;
    private final static WebServiceException WSMGALTANDP_EXCEPTION;
    private final static QName WSMGALTANDP_QNAME = new QName("http://tempuri.org/", "WSMGAltaNdp");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("https://ws.microglobal.com.ar/WSMGAltaNdp_test/WSMGAltaNdp.asmx?WSDL");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        WSMGALTANDP_WSDL_LOCATION = url;
        WSMGALTANDP_EXCEPTION = e;
    }

    public WSMGAltaNdp() {
        super(__getWsdlLocation(), WSMGALTANDP_QNAME);
    }

    public WSMGAltaNdp(WebServiceFeature... features) {
        super(__getWsdlLocation(), WSMGALTANDP_QNAME, features);
    }

    public WSMGAltaNdp(URL wsdlLocation) {
        super(wsdlLocation, WSMGALTANDP_QNAME);
    }

    public WSMGAltaNdp(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, WSMGALTANDP_QNAME, features);
    }

    public WSMGAltaNdp(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public WSMGAltaNdp(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns WSMGAltaNdpSoap
     */
    @WebEndpoint(name = "WSMGAltaNdpSoap")
    public WSMGAltaNdpSoap getWSMGAltaNdpSoap() {
        return super.getPort(new QName("http://tempuri.org/", "WSMGAltaNdpSoap"), WSMGAltaNdpSoap.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns WSMGAltaNdpSoap
     */
    @WebEndpoint(name = "WSMGAltaNdpSoap")
    public WSMGAltaNdpSoap getWSMGAltaNdpSoap(WebServiceFeature... features) {
        return super.getPort(new QName("http://tempuri.org/", "WSMGAltaNdpSoap"), WSMGAltaNdpSoap.class, features);
    }

    private static URL __getWsdlLocation() {
        if (WSMGALTANDP_EXCEPTION!= null) {
            throw WSMGALTANDP_EXCEPTION;
        }
        return WSMGALTANDP_WSDL_LOCATION;
    }

}
