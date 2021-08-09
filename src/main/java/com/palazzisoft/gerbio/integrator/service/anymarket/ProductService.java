package com.palazzisoft.gerbio.integrator.service.anymarket;

import com.palazzisoft.gerbio.integrator.model.anymarket.AnyProduct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
public class ProductService extends AbstractService<AnyProduct> {

    private final String URL_BASE = "/v2/products";

    public ProductService(WebClient webClient) {
        super(webClient, AnyProduct.class);
    }

    @Override
    protected String getURLBase() {
        return URL_BASE;
    }
}
