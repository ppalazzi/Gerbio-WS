package com.palazzisoft.gerbio.integrator.service.anymarket;

import com.palazzisoft.gerbio.integrator.exception.GerbioException;
import com.palazzisoft.gerbio.integrator.model.IntegratorError;
import com.palazzisoft.gerbio.integrator.model.anymarket.AnyCategory;
import com.palazzisoft.gerbio.integrator.model.anymarket.AnyOrigin;
import com.palazzisoft.gerbio.integrator.repository.CategoryRepository;
import com.palazzisoft.gerbio.integrator.response.CategoryResponse;
import com.palazzisoft.gerbio.integrator.service.IntegratorErrorService;
import com.palazzisoft.gerbio.integrator.service.mg.CategoryMGService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
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
    private final IntegratorErrorService integratorErrorService;

    @Autowired
    public CategoryService(WebClient webClient, final CategoryRepository categoryRepository,
                            final CategoryMGService categoryMGService,
                           final IntegratorErrorService integratorErrorService) {
        super(webClient, AnyCategory.class);
        this.categoryRepository = categoryRepository;
        this.categoryMGService = categoryMGService;
        this.integratorErrorService = integratorErrorService;
    }

    @Override
    protected String getURLBase() {
        return URL_BASE;
    }

    public void synchronizeCategories() throws GerbioException {
        try {
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
        catch (Exception e) {
            integratorErrorService.saveError(IntegratorError.builder()
                    .className(this.getClass().getName())
                    .errorMessage("Error Syncronizing Categories")
                    .timestamp(LocalDateTime.now())
                    .stackTrace(e.getMessage())
                    .type(IntegratorError.ErrorType.BRAND)
                    .build());

            throw new GerbioException(e);
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

    public List<AnyCategory> getAll() throws GerbioException {
        try {
            List<AnyCategory> allCategories = new ArrayList<>();

            int offset = 0;
            CategoryResponse response = getByOffset(offset);

            while (!isEmpty(response.getContent())) {
                allCategories.addAll(response.getContent());
                response = getByOffset(offset += 5);
            }

            return allCategories;
        }
        catch (Exception e) {
            integratorErrorService.saveError(IntegratorError.builder()
                    .className(this.getClass().getName())
                    .errorMessage("Error Retrieving Categories From AnyMarket")
                    .timestamp(LocalDateTime.now())
                    .stackTrace(e.getMessage())
                    .type(IntegratorError.ErrorType.CATEGORY)
                    .build());

            throw new GerbioException(e);
        }
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
