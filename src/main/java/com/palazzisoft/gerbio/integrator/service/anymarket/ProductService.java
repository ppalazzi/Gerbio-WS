package com.palazzisoft.gerbio.integrator.service.anymarket;

import com.palazzisoft.gerbio.integrator.model.anymarket.AnyProduct;
import com.palazzisoft.gerbio.integrator.model.anymarket.AnySku;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

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

    public AnyProduct updateProductSku(AnySku sku, Long productId) {
        log.info("Updating SKU with data {} ", sku);
        return webClient.put().uri(getURLBase().concat("/").concat(productId.toString()) + "/skus/" + sku.getId())
                .body(Mono.just(sku), AnyProduct.class).retrieve().bodyToMono(AnyProduct.class)
                .doOnEach(data ->  log.info(data.toString()))
                .doOnError(error -> log.error(error.toString()))
                .block();
    }
}
