package com.palazzisoft.gerbio.integrator.catalogo;

import com.palazzisoft.gerbio.integrator.mapping.ItemToAnyProductMapper;
import com.palazzisoft.gerbio.integrator.model.anymarket.AnyProduct;
import com.palazzisoft.gerbio.integrator.model.mg.Item;
import com.palazzisoft.gerbio.integrator.service.mg.MGWebService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.List;


@SpringBootTest
@Slf4j
public class WSMGTest {

    @Autowired
    private MGWebService mgWebService;

    @Test
    public void testSarasus() throws TransformerException, IOException, ParserConfigurationException, SAXException {
        ProductsRequest  productRequest = mgWebService.getCatalog();
        List<Item> items = mgWebService.getContenido();

        ItemToAnyProductMapper itemToAnyProductMapper = new ItemToAnyProductMapper();
        for (Item item : items){
            AnyProduct anyProduct = new AnyProduct();
            anyProduct = itemToAnyProductMapper.mapItemToAnyProduct(anyProduct, item);
        }
    }
}
