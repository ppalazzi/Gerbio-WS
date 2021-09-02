package com.palazzisoft.gerbio.integrator.service.anymarket;

import com.palazzisoft.gerbio.integrator.model.anymarket.AnyVariationGet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
public class VariationService extends AbstractService<AnyVariationGet> {

    private final String URL_BASE = "/v2/variations";

    public VariationService(WebClient webClient) {
        super(webClient, AnyVariationGet.class);
    }

    @Override
    protected String getURLBase() {
        return URL_BASE;
    }
}
