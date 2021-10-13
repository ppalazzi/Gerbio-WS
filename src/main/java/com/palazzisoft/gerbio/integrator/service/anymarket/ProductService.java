package com.palazzisoft.gerbio.integrator.service.anymarket;

import com.palazzisoft.gerbio.integrator.model.IntegratorError;
import com.palazzisoft.gerbio.integrator.model.anymarket.AnyProduct;
import com.palazzisoft.gerbio.integrator.model.anymarket.AnySku;
import com.palazzisoft.gerbio.integrator.repository.ProductRepository;
import com.palazzisoft.gerbio.integrator.response.ProductResponse;
import com.palazzisoft.gerbio.integrator.service.IntegratorErrorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.util.CollectionUtils.isEmpty;

@Service
@Slf4j
public class ProductService extends AbstractService<AnyProduct> {

    private final String URL_BASE = "/v2/products";

    private final ProductRepository productRepository;
    private final IntegratorErrorService integratorErrorService;


    public ProductService(WebClient webClient, final ProductRepository productRepository,
                          final IntegratorErrorService integratorErrorService) {
        super(webClient, AnyProduct.class);
        this.productRepository = productRepository;
        this.integratorErrorService = integratorErrorService;
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

    public ProductResponse getByOffset(int offset) {
        log.info("Retrieving Products");

        Mono<ProductResponse> response = webClient.get().uri(URL_BASE.concat("?offset=" + offset))
                .exchangeToMono(clientResponse -> {
            if (clientResponse.statusCode() == HttpStatus.OK) {
                log.debug("Products Status OK");
                return clientResponse.bodyToMono(ProductResponse.class);
            }
            else {
                log.error("Something went wrong when retrieving Products");
                integratorErrorService.saveError(
                        IntegratorError.builder()
                                .timestamp(LocalDateTime.now())
                                .type(clientResponse.statusCode().getReasonPhrase())
                                .errorMessage("Error retrieving products")
                                .className(this.getClass().getName())
                                .build());
                return Mono.just(null);
            }
        });

        return response.block();
    }

    public List<AnyProduct> getAll() {
        List<AnyProduct> allProducts = new ArrayList<>();

        int offset = 0;
        ProductResponse response = getByOffset(offset);

        while (!isEmpty(response.getContent())) {
            allProducts.addAll(response.getContent());
            response = getByOffset(offset +=5);
        }

        return allProducts;
    }

    @Transactional
    public void saveProducts(List<AnyProduct> products) {
       products.forEach(productRepository::save);
    }

    public AnyProduct saveAndPersist(AnyProduct anyProduct) {
        log.info("Saving and storing product with partner id {} ", anyProduct.getSkus().get(0).getPartnerId());

        AnyProduct mgProduct = save(anyProduct);
        mgProduct = productRepository.save(mgProduct);

        log.debug("Succesfully save and persisted partner id {}", anyProduct.getSkus().get(0).getPartnerId());

        return mgProduct;
    }

    public void updateProduct(AnyProduct anyProduct) {
        AnyProduct mgProduct = update(anyProduct, anyProduct.getId());
        mgProduct = productRepository.save(mgProduct);
    }
}
