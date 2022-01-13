package com.palazzisoft.gerbio.integrator.runner;

import com.palazzisoft.gerbio.integrator.exception.GerbioException;
import com.palazzisoft.gerbio.integrator.model.anymarket.AnyBrand;
import com.palazzisoft.gerbio.integrator.model.anymarket.AnyCategory;
import com.palazzisoft.gerbio.integrator.model.anymarket.AnyProduct;
import com.palazzisoft.gerbio.integrator.service.anymarket.BrandService;
import com.palazzisoft.gerbio.integrator.service.anymarket.CategoryService;
import com.palazzisoft.gerbio.integrator.service.anymarket.OriginService;
import com.palazzisoft.gerbio.integrator.service.anymarket.ProductService;
import com.palazzisoft.gerbio.integrator.util.CSVCategoryReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

@Profile("!test")
@Component
@Slf4j
public class IntegratorRunner implements ApplicationRunner {

    private final BrandService brandService;
    private final CategoryService categoryService;
    private final ProductService productService;
    private final OriginService originService;
    private final CSVCategoryReader csvCategoryReader;

    public IntegratorRunner(final BrandService brandService, final CategoryService categoryService,
                            final ProductService productService, final OriginService originService) throws IOException {
        this.brandService = brandService;
        this.categoryService = categoryService;
        this.productService = productService;
        this.originService = originService;
        csvCategoryReader = new CSVCategoryReader();
    }

    @Override
    public void run(ApplicationArguments args) throws IOException {
        log.debug("Initializing Gerbio Integrator App");

        log.info("Retrieving from AnyMarket brands, categories and products to be persisted in DB");

        List<String> variosCsv = csvCategoryReader.readVariosCSV();
        List<String> componentesPCCsv = csvCategoryReader.readComponentesCSV();
        List<String> impresorasCsv = csvCategoryReader.readImpresorasCSV();
        List<String> monitoresCsv = csvCategoryReader.readMonitoresCSV();
        List<String> notebooksCsv = csvCategoryReader.readNotebooksCSV();
        List<String> servidoresCsv = csvCategoryReader.readServidoresCSV();

        originService.storeInitOrigins();

        try {
            List<AnyBrand> brands = brandService.getAll();
            brandService.saveBrands(brands);
            brandService.synchronizeBrands();

            List<AnyCategory> categories = categoryService.getAll();
            categoryService.saveCategories(categories);
            categoryService.synchronizeCategories();

            List<AnyCategory> categoriesAfterSync = categoryService.findAll();

            List<AnyProduct> products = productService.getAll();

            for (AnyProduct product : products) {
                if (variosCsv.contains(product.getCategory().getId().toString())) {
                    product.setCategory(findCategoryByPartnerId(categoriesAfterSync, "GER_VS").get());
                } else if (componentesPCCsv.contains(product.getCategory().getId().toString())) {
                    product.setCategory(findCategoryByPartnerId(categoriesAfterSync, "GER_CAPC").get());
                } else if (impresorasCsv.contains(product.getCategory().getId().toString())) {
                    product.setCategory(findCategoryByPartnerId(categoriesAfterSync, "GER_IPS").get());
                } else if (servidoresCsv.contains(product.getCategory().getId().toString())) {
                    product.setCategory(findCategoryByPartnerId(categoriesAfterSync, "GER_SCS").get());
                } else if (notebooksCsv.contains(product.getCategory().getId().toString())) {
                    product.setCategory(findCategoryByPartnerId(categoriesAfterSync, "GER_NPAT").get());
                } else if (monitoresCsv.contains(product.getCategory().getId().toString())) {
                    product.setCategory(findCategoryByPartnerId(categoriesAfterSync, "GER_MT").get());
                }
            }

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

    private Optional<AnyCategory> findCategoryByPartnerId(List<AnyCategory> category, String partnerId) {
        return category.stream().filter(c -> c.getPartnerId().equals(partnerId)).findFirst();
    }
}
