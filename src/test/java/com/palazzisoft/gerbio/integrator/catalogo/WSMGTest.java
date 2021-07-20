package com.palazzisoft.gerbio.integrator.catalogo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class WSMGTest {

    @Test
    public void testSarasus() {
        WSMG w = new WSMG();
        ProductsRequest productsRequest = w.getWSMGSoap().getCatalog("927262", "", "DFO257");

        productsRequest.getListProducts().getProduct().forEach(p -> log.info(p.getCategoria()));
    }
}
