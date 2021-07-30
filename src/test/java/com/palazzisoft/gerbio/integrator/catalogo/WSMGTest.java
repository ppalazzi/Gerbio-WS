package com.palazzisoft.gerbio.integrator.catalogo;

import com.palazzisoft.gerbio.integrator.service.mg.MGWebService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

@SpringBootTest
@Slf4j
public class WSMGTest {

    @Autowired
    private MGWebService mgWebService;

    @Test
    public void testSarasus() throws TransformerException, IOException {
        WSMG w = new WSMG();
        ProductsRequest productsRequest = mgWebService.getCatalog();

        ContentRequest request = w.getWSMGSoap().getXMLContenido("927262", "", "DFO257");

        org.w3c.dom.Element element = (org.w3c.dom.Element) request.getContent().getContent().get(0);

        TransformerFactory transFactory = TransformerFactory.newInstance();
        Transformer transformer = transFactory.newTransformer();
        StringWriter buffer = new StringWriter();
        //transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.transform(new DOMSource(element),
                new StreamResult(buffer));
        String str = buffer.toString();

        SOAPMessage message = getSOAPMessageFromXMLString(str,"","");

                /*
        FileWriter fileWriter = new FileWriter("queen.xml");
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.print(str);
        printWriter.close();

        productsRequest.getListProducts().getProduct().forEach(p -> log.info(p.getCategoria()));
         */
    }

    public static SOAPMessage getSOAPMessageFromXMLString(String xmlString,
                                                          String namespacePrefix, String namespaceURI) {

        SOAPMessage soapMessage = null;
        try {
            InputStream is = new ByteArrayInputStream(xmlString.getBytes());
            soapMessage = MessageFactory.newInstance().createMessage(null, is);

            SOAPEnvelope soapEnvelope = soapMessage.getSOAPPart().getEnvelope();
            soapEnvelope.addNamespaceDeclaration(namespacePrefix, namespaceURI);

            soapMessage.saveChanges();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return soapMessage;
    }
}
