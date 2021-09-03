package com.palazzisoft.gerbio.integrator.service.mg;

import com.palazzisoft.gerbio.integrator.catalogo.ContentRequest;
import com.palazzisoft.gerbio.integrator.catalogo.ProductsRequest;
import com.palazzisoft.gerbio.integrator.catalogo.WSMG;
import com.palazzisoft.gerbio.integrator.model.mg.Description;
import com.palazzisoft.gerbio.integrator.model.mg.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.nonNull;

@Service
@Slf4j
public class MGWebService {

    private WSMG wsmg;
    private String user;
    private String clientId;
    private String password;

    @Autowired
    public MGWebService(final WSMG wsmg,
                        @Value("${mg.user}") String user,
                        @Value("${mg.clientId}") String clientId,
                        @Value("${mg.password}") String password) {
        this.wsmg = wsmg;
        this.user = user;
        this.clientId = clientId;
        this.password = password;
    }

    public ProductsRequest getCatalog() {
        return wsmg.getWSMGSoap().getCatalog(clientId, user, password);
    }

    public List<Item> getContenido()
            throws ParserConfigurationException, IOException, SAXException {
        log.info("Trayendo al Contenido desde MSMG");

        ContentRequest request = wsmg.getWSMGSoap().getXMLContenido(clientId, user, password);

        Element element = (Element) request.getContent().getContent().get(0);
        NodeList itemsNodes = element.getFirstChild().getNextSibling().getFirstChild().getFirstChild().getChildNodes();

        return transformContentToObject(itemsNodes);
    }

    private List<Item> transformContentToObject(NodeList nodeList) throws ParserConfigurationException, IOException, SAXException {
        List<Item> items = new ArrayList<>();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Item item = Item.builder().build();

            Node nNode = nodeList.item(i);

            log.info("Current Element " + nNode.getNodeName());

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                Element elem = (Element) nNode;

                // articulo
                Node articuloNode = elem.getElementsByTagName("Articulo").item(0);
                Node partNumberOriNode = elem.getElementsByTagName("PartNumber_ori").item(0);
                Node partNumberNode = elem.getElementsByTagName("PartNumber").item(0);
                Node estadoNode = elem.getElementsByTagName("Estado").item(0);

                if (nonNull(articuloNode)) {
                    item.setArticulo(articuloNode.getTextContent());
                }
                if (nonNull(partNumberOriNode)) {
                    item.setPartNumberOri(partNumberOriNode.getTextContent());
                }
                if (nonNull(partNumberNode)) {
                    item.setPartNumber(partNumberNode.getTextContent());
                }
                if (nonNull(estadoNode)) {
                    item.setEstado(estadoNode.getTextContent());
                }

                // description
                Node descriptionNode = elem.getElementsByTagName("Description").item(0);
                if (nonNull(descriptionNode)) {
                    Description description = Description.builder().build();

                    if (nonNull(descriptionNode.getChildNodes().item(0))) {
                        description.setShort_(descriptionNode.getChildNodes().item(0).getTextContent());
                    }
                    if (nonNull(descriptionNode.getChildNodes().item(1))) {
                        description.setMaker(descriptionNode.getChildNodes().item(1).getTextContent());
                    }

                    item.setDescription(description);
                }

/*
                //String uid = elem.getAttribute("Articulo");
                //item.setArticulo(uid);
                Node node2 = elem.getElementsByTagName("lastname").item(0);
                String lname = node2.getTextContent();

                Node node3 = elem.getElementsByTagName("occupation").item(0);
                String occup = node3.getTextContent();

                System.out.printf("User id: %s%n", uid);
                System.out.printf("First name: %s%n", fname);
                System.out.printf("Last name: %s%n", lname);
                System.out.printf("Occupation: %s%n", occup);
 */
            }

            items.add(item);
        }

        return items;
    }
}
