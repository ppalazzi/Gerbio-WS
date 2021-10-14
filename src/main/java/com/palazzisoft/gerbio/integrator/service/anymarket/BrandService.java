package com.palazzisoft.gerbio.integrator.service.anymarket;

import com.palazzisoft.gerbio.integrator.model.anymarket.AnyBrand;
import com.palazzisoft.gerbio.integrator.repository.BrandRepository;
import com.palazzisoft.gerbio.integrator.response.BrandResponse;
import com.palazzisoft.gerbio.integrator.service.mg.BrandMGService;
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
public class BrandService extends AbstractService<AnyBrand> {

    private final String URL_BASE = "/v2/brands";

    private final BrandRepository brandRepository;
    private final BrandMGService brandMGService;

    @Autowired
    public BrandService(final WebClient webClient, final BrandRepository brandRepository,
                        final BrandMGService brandMGService) {
        super(webClient, AnyBrand.class);
        this.brandRepository = brandRepository;
        this.brandMGService = brandMGService;
    }

    @Override
    protected String getURLBase() {
        return URL_BASE;
    }

    public void synchronizeBrands() {
        List<AnyBrand> brandsMG = brandMGService.buildAnyBrands();

        for (AnyBrand currentBrand : brandsMG) {
            AnyBrand retrieved = brandRepository.findAnyBrandByPartnerId(currentBrand.getPartnerId());

            // if not in db, add to any market and db
            if (isNull(retrieved)) {
                AnyBrand retrievedFromAny = save(currentBrand);
                brandRepository.save(retrievedFromAny);
            }
        }
    }

    public BrandResponse getByOffset(int offset) {
        log.info("Retrieving Brands");

        Mono<BrandResponse> response = webClient.get().uri(URL_BASE.concat("?offset=" + offset))
                .exchangeToMono(clientResponse -> {
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

    public List<AnyBrand> getAll() {
        List<AnyBrand> allBrands = new ArrayList<>();

        int offset = 0;
        BrandResponse response = getByOffset(offset);

        while (!isEmpty(response.getContent())) {
            allBrands.addAll(response.getContent());
            response = getByOffset(offset +=5);
        }

        return allBrands;
    }

    @Transactional
    public void saveBrands(List<AnyBrand> anyBrandList) {
        for (AnyBrand anyBrand : anyBrandList) {
            brandRepository.save(anyBrand);
        }
    }

    public List<AnyBrand> findAll() {
        return brandRepository.findAll();
    }
}
