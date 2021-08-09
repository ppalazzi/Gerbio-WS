package com.palazzisoft.gerbio.integrator.service.anymarket;

import com.palazzisoft.gerbio.integrator.model.anymarket.AnyBrand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
public class BrandService extends AbstractService<AnyBrand> {

    private final String URL_BASE = "/v2/brands";

    @Autowired
    public BrandService(WebClient webClient) {
        super(webClient, AnyBrand.class);
    }

    @Override
    protected String getURLBase() {
        return URL_BASE;
    }
}
