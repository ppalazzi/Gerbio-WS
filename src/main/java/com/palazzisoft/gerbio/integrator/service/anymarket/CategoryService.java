package com.palazzisoft.gerbio.integrator.service.anymarket;

import com.palazzisoft.gerbio.integrator.model.anymarket.AnyCategory;
import com.palazzisoft.gerbio.integrator.response.CategoryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class CategoryService extends AbstractService<AnyCategory> {

    private static final String URL_BASE = "/v2/categories";

    @Autowired
    private CategoryService(WebClient webClient) {
        super(webClient, AnyCategory.class);
    }

    @Override
    protected String getURLBase() {
        return URL_BASE;
    }

    public CategoryResponse getCategories() {
        log.info("Retrieving Catalogs");

        Mono<CategoryResponse> response = webClient.get().uri(URL_BASE).exchangeToMono(clientResponse -> {
            if (clientResponse.statusCode() == HttpStatus.OK) {
                log.debug("Categories Status OK");
                return clientResponse.bodyToMono(CategoryResponse.class);
            }
            else {
                log.error("Something went wrong when retrieving Categories");
                return Mono.just(null);
            }
        });

        return response.block();
    }
}
