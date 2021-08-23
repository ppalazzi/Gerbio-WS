package com.palazzisoft.gerbio.integrator.service.anymarket;

import com.palazzisoft.gerbio.integrator.model.anymarket.AnyStock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@Slf4j
public class StockService extends AbstractService<List<AnyStock>> {

    private final String URL_BASE = "/v2/stocks";

    public StockService(WebClient webClient) {
        super(webClient, List.class);
    }

    @Override
    protected String getURLBase() {
        return URL_BASE;
    }
}
