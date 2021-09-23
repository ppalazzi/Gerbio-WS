package com.palazzisoft.gerbio.integrator.service.anymarket;

import com.palazzisoft.gerbio.integrator.model.anymarket.AnyCategory;
import com.palazzisoft.gerbio.integrator.repository.CategoryRepository;
import com.palazzisoft.gerbio.integrator.response.CategoryResponse;
import com.palazzisoft.gerbio.integrator.service.mg.CategoryMGService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;
import static org.springframework.util.CollectionUtils.isEmpty;

@Service
@Slf4j
public class CategoryService extends AbstractService<AnyCategory> {

    private static final String URL_BASE = "/v2/categories";

    private final CategoryRepository categoryRepository;
    private final CategoryMGService categoryMGService;

    @Autowired
    public CategoryService(WebClient webClient, final CategoryRepository categoryRepository,
                            final CategoryMGService categoryMGService) {
        super(webClient, AnyCategory.class);
        this.categoryRepository = categoryRepository;
        this.categoryMGService = categoryMGService;
    }

    @Override
    protected String getURLBase() {
        return URL_BASE;
    }

    public void synchronizeCategories() {
        List<AnyCategory> categoriesMG = categoryMGService.buildAnyCategory();

        for (AnyCategory categoryMG : categoriesMG) {
            AnyCategory retrieved = categoryRepository.findAnyCategoryByPartnerId(categoryMG.getPartnerId());

            // if not in db, add to any market and db
            if (isNull(retrieved)) {
                categoryMG.setDefinitionPriceScope("SKU");
                categoryMG.setName(categoryMG.getName().replaceAll("/", "-"));
                AnyCategory retrievedFromAny = save(categoryMG);
                categoryRepository.save(retrievedFromAny);
            }
        }
    }

    public CategoryResponse getByOffset(int offset) {
        log.info("Retrieving Category");

        Mono<CategoryResponse> response = webClient.get().uri(URL_BASE.concat("?offset=" + offset))
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode() == HttpStatus.OK) {
                        log.debug("Categories Status OK");
                        return clientResponse.bodyToMono(CategoryResponse.class);
                    } else {
                        log.error("Something went wrong when retrieving Categories");
                        return Mono.just(null);
                    }
                });

        return response.block();
    }

    public List<AnyCategory> getAll() {
        List<AnyCategory> allCategories = new ArrayList<>();

        int offset = 0;
        CategoryResponse response = getByOffset(offset);

        while (!isEmpty(response.getContent())) {
            allCategories.addAll(response.getContent());
            response = getByOffset(offset += 5);
        }

        return allCategories;
    }

    @Transactional
    public void saveCategories(List<AnyCategory> anyCategoryList) {
        for (AnyCategory anyCategory : anyCategoryList) {
            categoryRepository.save(anyCategory);
        }
    }

    public List<AnyCategory> findAll() {
        return categoryRepository.findAll();
    }
}
