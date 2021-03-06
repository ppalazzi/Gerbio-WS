package com.palazzisoft.gerbio.integrator.service.mg;

import com.palazzisoft.gerbio.integrator.alta.OPedido;
import com.palazzisoft.gerbio.integrator.alta.PedidoRequest;
import com.palazzisoft.gerbio.integrator.alta.WSMGAltaNdp;
import com.palazzisoft.gerbio.integrator.catalogo.*;
import com.palazzisoft.gerbio.integrator.model.mg.Description;
import com.palazzisoft.gerbio.integrator.model.mg.Item;
import com.palazzisoft.gerbio.integrator.model.mg.TechnicalSpec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

@Service
@Slf4j
public class MGWebService {

    private WSMG wsmg;
    private WSMGAltaNdp wsmgAltaNdp;
    private String user;
    private String clientId;
    private String password;

    @Autowired
    public MGWebService(final WSMG wsmg,
                        final WSMGAltaNdp wsmgAltaNdp,
                        @Value("${mg.user}") String user,
                        @Value("${mg.clientId}") String clientId,
                        @Value("${mg.password}") String password) {
        this.wsmg = wsmg;
        this.wsmgAltaNdp = wsmgAltaNdp;
        this.user = user;
        this.clientId = clientId;
        this.password = password;
    }

    public ProductsRequest getCatalog() {
        return wsmg.getWSMGSoap().getCatalog(clientId, user, password);
    }

    public BrandRequest getBrands() {
        return wsmg.getWSMGSoap().getBrands(clientId, user, password);
    }

    public CategoryRequest getCategories() {
        return wsmg.getWSMGSoap().getCategories(clientId, user, password);
    }

    public PedidoRequest notifyOrderInMG(OPedido oPedido) {
        return wsmgAltaNdp.getWSMGAltaNdpSoap().sendAltaPedido(clientId, user, password, oPedido);
    }

    public List<Item> getContenido() {
        log.info("Trayendo al Contenido desde MSMG");

        ContentRequest request = wsmg.getWSMGSoap().getXMLContenido(clientId, user, password);

        Element element = (Element) request.getContent().getContent().get(0);
        NodeList itemsNodes = element.getFirstChild().getNextSibling().getFirstChild().getFirstChild().getChildNodes();

        return transformContentToObject(itemsNodes);
    }

    private List<Item> transformContentToObject(NodeList nodeList)  {
        List<Item> items = new ArrayList<>();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Item item = Item.builder().build();

            Node nNode = nodeList.item(i);

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

                    int size = descriptionNode.getChildNodes().getLength();
                    for (int j = 0 ; j < size; j++) {
                        Node node = descriptionNode.getChildNodes().item(j);
                        if (node.getLocalName().equals("Maker")) {
                            description.setMaker(node.getTextContent());
                        }
                        if (node.getLocalName().equals("Short")) {
                            description.setShort_(node.getTextContent());
                        }
                        if (node.getLocalName().equals("Product")) {
                            description.setProduct(node.getTextContent());
                        }
                    }

                    item.setDescription(description);
                }

                Node picturesNode = elem.getElementsByTagName("Pictures").item(0);
                if (nonNull(picturesNode)){
                    List<String> urls = new ArrayList<>();
                    for (int j = 0; j < picturesNode.getChildNodes().getLength(); j++){
                        if(nonNull(picturesNode.getChildNodes().item(j))){
                            Node pictureNode = picturesNode.getChildNodes().item(j);
                            urls.add(pictureNode.getAttributes().getNamedItem("url").getNodeValue());
                        }
                    }
                    item.setPicturesUrls(urls);
                }

                Node detailsNode = elem.getElementsByTagName("Details").item(0);
                if (nonNull(detailsNode)){
                    List<TechnicalSpec> technicalSpecs = new ArrayList<>();
                    NodeList especificaciones = detailsNode.getFirstChild().getFirstChild().getChildNodes();
                    for (int k = 0; k < especificaciones.getLength(); k ++) {
                        TechnicalSpec technicalSpec = new TechnicalSpec();
                        technicalSpec.setNombre(especificaciones.item(k).getAttributes().item(0).getNodeValue());
                        if (especificaciones.item(k).getFirstChild().getFirstChild() != null) {
                            technicalSpec.setDescripcion(especificaciones.item(k).getFirstChild().getFirstChild().getNodeValue());
                        }
                        technicalSpecs.add(technicalSpec);
                    }
                    item.setTechnicalSpecList(technicalSpecs);
                }
            }

            items.add(item);
        }

        return items;
    }
}
