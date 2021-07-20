package com.palazzisoft.gerbio.integrator.catalogo;

import com.palazzisoft.gerbio.integrator.model.Category;
import com.palazzisoft.gerbio.integrator.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;

@SpringBootTest
@Slf4j
public class AnyMarketTest {

    @Autowired
    private WebClient webClient;

    @Autowired
    private CategoryRepository categoryRepository;

    void testAnyMethod() throws Exception {
        Category cat = Category.builder().id(1L).name("carlos").definitionPriceScope("123")
                .path("path")
                .build();

        Category responseCat = categoryRepository.addCategory(cat);

        String response = webClient.get().uri(URI.create("http://sandbox-api.anymarket.com.br/v2/categories"))
                .header("gumgaToken", "L35024029G1625768667662R1037733922")
                .header("Content-Type", "application/json").exchangeToMono(res -> {
                            if (res.statusCode().equals(HttpStatus.OK)) {
                                log.info("DI 200");
                                return res.bodyToMono(String.class);
                            }
                            else {
                                log.error("la cague");

                                return res.createException()
                                        .flatMap(Mono::error);
                            }
                       }).block();

       log.info("response", response);
        log.info("response", responseCat);
    }
}
