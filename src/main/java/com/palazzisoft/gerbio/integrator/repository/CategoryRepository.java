package com.palazzisoft.gerbio.integrator.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.palazzisoft.gerbio.integrator.model.Category;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;
import java.util.List;

@Repository
public class CategoryRepository {

    private final WebClient webClient;

    public CategoryRepository(final WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://sandbox-api.anymarket.com.br")
                .defaultHeader("gumgaToken", "L35024029G1625768667662R1037733922")
                .build();
    }

    public List<Category> getCategories() {
        return null;
        //return webClient.get().header("Content-Type", "application/json")
        //        .exchangeToMono("")
    }

    public Category addCategory(Category category) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String value = objectMapper.writeValueAsString(category);

        return this.webClient.post().uri(URI.create("http://sandbox-api.anymarket.com.br/v2/categories"))
                .header("Content-Type", "text/plain")
                .header("gumgaToken", "L35024029G1625768667662R1037733922")
                .bodyValue(value)
                .retrieve().bodyToMono(Category.class).block();
    }
}
