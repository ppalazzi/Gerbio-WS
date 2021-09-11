package com.palazzisoft.gerbio.integrator.service.anymarket;

import com.palazzisoft.gerbio.integrator.model.anymarket.AnyBrand;
import com.palazzisoft.gerbio.integrator.repository.BrandRepository;
import com.palazzisoft.gerbio.integrator.response.BrandResponse;
import com.palazzisoft.gerbio.integrator.response.CategoryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class BrandService extends AbstractService<AnyBrand> {

    private final String URL_BASE = "/v2/brands";

    private final BrandRepository brandRepository;

    @Autowired
    public BrandService(final WebClient webClient, final BrandRepository brandRepository) {
        super(webClient, AnyBrand.class);
        this.brandRepository = brandRepository;
    }

    @Override
    protected String getURLBase() {
        return URL_BASE;
    }

    

    public BrandResponse getBrands() {
        log.info("Retrieving Brands");

        Mono<BrandResponse> response = webClient.get().uri(URL_BASE).exchangeToMono(clientResponse -> {
            if (clientResponse.statusCode() == HttpStatus.OK) {
                log.debug("Brands Status OK");
                return clientResponse.bodyToMono(BrandResponse.class);
            }
            else {
                log.error("Something went wrong when retrieving Brands");
                return Mono.just(null);
            }
        });

        return response.block();
    }
}
