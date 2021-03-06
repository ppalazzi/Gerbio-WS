package com.palazzisoft.gerbio.integrator.runner;

import com.palazzisoft.gerbio.integrator.exception.GerbioException;
import com.palazzisoft.gerbio.integrator.model.anymarket.AnyBrand;
import com.palazzisoft.gerbio.integrator.model.anymarket.AnyCategory;
import com.palazzisoft.gerbio.integrator.model.anymarket.AnyProduct;
import com.palazzisoft.gerbio.integrator.service.anymarket.BrandService;
import com.palazzisoft.gerbio.integrator.service.anymarket.CategoryService;
import com.palazzisoft.gerbio.integrator.service.anymarket.OriginService;
import com.palazzisoft.gerbio.integrator.service.anymarket.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Profile("!test")
@Component
@Slf4j
public class IntegratorRunner implements ApplicationRunner {

    private final BrandService brandService;
    private final CategoryService categoryService;
    private final ProductService productService;
    private final OriginService originService;

    public IntegratorRunner(final BrandService brandService, final CategoryService categoryService,
                            final ProductService productService, final OriginService originService) throws IOException {
        this.brandService = brandService;
        this.categoryService = categoryService;
        this.productService = productService;
        this.originService = originService;
    }

    @Override
    public void run(ApplicationArguments args) throws IOException {
        log.debug("Initializing Gerbio Integrator App");

        log.info("Retrieving from AnyMarket brands, categories and products to be persisted in DB");

        originService.storeInitOrigins();

        try {
            List<AnyBrand> brands = brandService.getAll();
            brandService.saveBrands(brands);
            brandService.synchronizeBrands();

            List<AnyCategory> categories = categoryService.getAll();
            categoryService.saveCategories(categories);
            categoryService.synchronizeCategories();

            List<AnyProduct> products = productService.getAll();

            productService.saveProducts(products);

        }
        catch (GerbioException e ) {
            log.error("Error synchronizing data", e);
        }
        catch (Exception e) {
            log.error("Error unexpected", e);
        }

        log.info("Synchronization fully performed");
    }

}
