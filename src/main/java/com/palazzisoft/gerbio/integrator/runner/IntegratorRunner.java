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

    private final List<String> variosCsv = new ArrayList<>();
    private final List<String> componentesPCCsv = new ArrayList<>();
    private final List<String> impresorasCsv = new ArrayList<>();
    private final List<String> monitoresCsv = new ArrayList<>();
    private final List<String> notebooksCsv = new ArrayList<>();
    private final List<String> servidoresCsv = new ArrayList<>();

    public IntegratorRunner(final BrandService brandService, final CategoryService categoryService,
                            final ProductService productService, final OriginService originService) {
        this.brandService = brandService;
        this.categoryService = categoryService;
        this.productService = productService;
        this.originService = originService;
    }

    private void readGerbioCategoriesFromCSV() throws IOException {
        InputStreamReader componentesCSV = new InputStreamReader(Objects.requireNonNull(Objects.requireNonNull(getClass().getClassLoader().getResource("category-componentespc.csv")).openStream()));
        InputStreamReader impresorasCSV = new InputStreamReader(Objects.requireNonNull(Objects.requireNonNull(getClass().getClassLoader().getResource("category-impresoras.csv")).openStream()));
        InputStreamReader monitoresCSV = new InputStreamReader(Objects.requireNonNull(Objects.requireNonNull(getClass().getClassLoader().getResource("category-monitores.csv")).openStream()));
        InputStreamReader notebooksCSV = new InputStreamReader(Objects.requireNonNull(Objects.requireNonNull(getClass().getClassLoader().getResource("category-notebooks.csv")).openStream()));
        InputStreamReader servidoresCSV = new InputStreamReader(Objects.requireNonNull(Objects.requireNonNull(getClass().getClassLoader().getResource("category-servidores.csv")).openStream()));
        InputStreamReader variosCSV = new InputStreamReader(Objects.requireNonNull(Objects.requireNonNull(getClass().getClassLoader().getResource("category-varios.csv")).openStream()));

        try (BufferedReader br = new BufferedReader(componentesCSV)) {
            String line;
            while ((line = br.readLine()) != null) {
                Set<String> values = Arrays.stream((line.split("\n"))).collect(Collectors.toSet());
                componentesPCCsv.addAll((values));
            }
        }

        try (BufferedReader br = new BufferedReader(impresorasCSV)) {
            String line;
            while ((line = br.readLine()) != null) {
                Set<String> values = Arrays.stream((line.split("\n"))).collect(Collectors.toSet());
                impresorasCsv.addAll((values));
            }
        }

        try (BufferedReader br = new BufferedReader(monitoresCSV)) {
            String line;
            while ((line = br.readLine()) != null) {
                Set<String> values = Arrays.stream((line.split("\n"))).collect(Collectors.toSet());
                monitoresCsv.addAll((values));
            }
        }

        try (BufferedReader br = new BufferedReader(notebooksCSV)) {
            String line;
            while ((line = br.readLine()) != null) {
                Set<String> values = Arrays.stream((line.split("\n"))).collect(Collectors.toSet());
                notebooksCsv.addAll((values));
            }
        }

        try (BufferedReader br = new BufferedReader(servidoresCSV)) {
            String line;
            while ((line = br.readLine()) != null) {
                Set<String> values = Arrays.stream((line.split("\n"))).collect(Collectors.toSet());
                servidoresCsv.addAll((values));
            }
        }

        try (BufferedReader br = new BufferedReader(variosCSV)) {
            String line;
            while ((line = br.readLine()) != null) {
                Set<String> values = Arrays.stream((line.split("\n"))).collect(Collectors.toSet());
                variosCsv.addAll((values));
            }
        }
    }

    @Override
    public void run(ApplicationArguments args) throws IOException {
        log.debug("Initializing Gerbio Integrator App");

        log.info("Retrieving from AnyMarket brands, categories and products to be persisted in DB");
        readGerbioCategoriesFromCSV();
        originService.storeInitOrigins();

        try {
            List<AnyBrand> brands = brandService.getAll();
            brandService.saveBrands(brands);
            brandService.synchronizeBrands();

            List<AnyCategory> categories = categoryService.getAll();
            categoryService.saveCategories(categories);
            categoryService.synchronizeCategories();
            categoryService.storeGerbioCategories();

            List<AnyCategory> categoriesAfterSync = categoryService.findAll();

            List<AnyProduct> products = productService.getAll();

            for (AnyProduct product : products) {
                if (variosCsv.contains(product.getCategory().getId().toString())) {
                    product.setCategory(findCategoryByPartnerId(categoriesAfterSync, "VS").get());
                } else if (componentesPCCsv.contains(product.getCategory().getId().toString())) {
                    product.setCategory(findCategoryByPartnerId(categoriesAfterSync, "CAPC").get());
                } else if (impresorasCsv.contains(product.getCategory().getId().toString())) {
                    product.setCategory(findCategoryByPartnerId(categoriesAfterSync, "IPS").get());
                } else if (servidoresCsv.contains(product.getCategory().getId().toString())) {
                    product.setCategory(findCategoryByPartnerId(categoriesAfterSync, "SCS").get());
                } else if (notebooksCsv.contains(product.getCategory().getId().toString())) {
                    product.setCategory(findCategoryByPartnerId(categoriesAfterSync, "NPAT").get());
                } else if (monitoresCsv.contains(product.getCategory().getId().toString())) {
                    product.setCategory(findCategoryByPartnerId(categoriesAfterSync, "MT").get());
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
