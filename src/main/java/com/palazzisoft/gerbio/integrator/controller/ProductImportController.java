package com.palazzisoft.gerbio.integrator.controller;

import com.palazzisoft.gerbio.integrator.catalogo.ProductsRequest;
import com.palazzisoft.gerbio.integrator.exception.GerbioException;
import com.palazzisoft.gerbio.integrator.mapping.ItemToAnyProductMapper;
import com.palazzisoft.gerbio.integrator.model.IntegratorError;
import com.palazzisoft.gerbio.integrator.model.anymarket.AnyBrand;
import com.palazzisoft.gerbio.integrator.model.anymarket.AnyCategory;
import com.palazzisoft.gerbio.integrator.model.anymarket.AnyProduct;
import com.palazzisoft.gerbio.integrator.model.mg.Item;
import com.palazzisoft.gerbio.integrator.service.IntegratorErrorService;
import com.palazzisoft.gerbio.integrator.service.anymarket.BrandService;
import com.palazzisoft.gerbio.integrator.service.anymarket.CategoryService;
import com.palazzisoft.gerbio.integrator.service.anymarket.ProductService;
import com.palazzisoft.gerbio.integrator.service.mg.MGWebService;
import com.palazzisoft.gerbio.integrator.util.CSVCategoryReader;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/importing")
@Slf4j
@Profile("!test")
public class ProductImportController {

    private final CategoryService categoryService;
    private final BrandService brandService;
    private final ProductService productService;
    private final MGWebService mgWebService;
    private final MapperFacade mapper;
    private final IntegratorErrorService integratorErrorService;
    private final CSVCategoryReader csvCategoryReader;
    private List<String> variosCsv = new ArrayList<>();
    private List<String> componentesPCCsv = new ArrayList<>();
    private List<String> impresorasCsv = new ArrayList<>();
    private List<String> monitoresCsv = new ArrayList<>();
    private List<String> notebooksCsv = new ArrayList<>();
    private List<String> servidoresCsv = new ArrayList<>();


    public ProductImportController(final CategoryService categoryService, final BrandService brandService,
                                   final ProductService productService, final MGWebService mgWebService,
                                   final MapperFacade mapperFacade, final IntegratorErrorService integratorErrorService) throws IOException {
        this.categoryService = categoryService;
        this.productService = productService;
        this.brandService = brandService;
        this.mgWebService = mgWebService;
        this.mapper = mapperFacade;
        this.integratorErrorService = integratorErrorService;
        csvCategoryReader = new CSVCategoryReader();

    }

    @GetMapping
    @Scheduled(fixedDelay = 3600000, initialDelay = 3600000)
    public ResponseEntity<List<AnyProduct>> importProducts() throws GerbioException, IOException {
        log.info("Starting importing Product at {} ", Instant.now());

        variosCsv = csvCategoryReader.readVariosCSV();
        componentesPCCsv = csvCategoryReader.readComponentesCSV();
        impresorasCsv = csvCategoryReader.readImpresorasCSV();
        monitoresCsv = csvCategoryReader.readMonitoresCSV();
        notebooksCsv = csvCategoryReader.readNotebooksCSV();
        servidoresCsv = csvCategoryReader.readServidoresCSV();


        // retrieving all products from MG and DB
        List<AnyProduct> products = retrieveProductsFromMG();
        List<AnyProduct> currentDBProducts = productService.getAll();

        // retriveing brands and product
        List<AnyBrand> brands = brandService.findAll();
        List<AnyCategory> categories = categoryService.findAll();


        for (AnyProduct mgProduct : products) {
            if (mgProduct.getSkus().get(0).getAmount() > 0d) {
                importProduct(currentDBProducts, brands, categories, mgProduct);
            }
        }

        log.info("Product importation of {} items, run succesfully", products.size());

        return ResponseEntity.ok(products);
    }

    private void importProduct(List<AnyProduct> currentDBProducts, List<AnyBrand> brands, List<AnyCategory> categories, AnyProduct mgProduct) {
        final String partnerId = mgProduct.getSkus().get(0).getPartnerId();
        Optional<AnyProduct> baseEquivalent = currentDBProducts.stream()
                .filter(p -> p.getSkus().get(0).getPartnerId().equals(partnerId)).findFirst();

        if (baseEquivalent.isPresent()) {
            AnyProduct baseProduct = baseEquivalent.get();

            // if price or stock has changed, update DB and Anymarket
            if (baseProduct.getSkus().get(0).getPrice() != mgProduct.getSkus().get(0).getPrice()
                    || baseProduct.getSkus().get(0).getAmount() != mgProduct.getSkus().get(0).getAmount()) {
                baseProduct.getSkus().get(0).setAmount(mgProduct.getSkus().get(0).getAmount());
                baseProduct.getSkus().get(0).setPrice(mgProduct.getSkus().get(0).getPrice());
                baseProduct.getSkus().get(0).setSellPrice(mgProduct.getSkus().get(0).getPrice());
                productService.updateAndPersist(baseProduct);
            }
        } else {
            // add to database and anymarket
            Optional<AnyBrand> currentBrand = findBrandByPartnerId(brands, mgProduct.getBrand().getPartnerId());
            Optional<AnyCategory> currentCategory = findCategoryByPartnerId(categories, mgProduct.getCategory().getPartnerId());

            if (currentBrand.isPresent() && currentCategory.isPresent()) {
                log.debug("Brands and Category found");

                mgProduct.setBrand(currentBrand.get());

                if (variosCsv.contains(currentCategory.get().getId().toString())) {
                    mgProduct.setCategory(findCategoryByPartnerId(categories, "VS").get());
                } else if (componentesPCCsv.contains(currentCategory.get().getId().toString())) {
                    mgProduct.setCategory(findCategoryByPartnerId(categories, "CAPC").get());
                } else if (impresorasCsv.contains(currentCategory.get().getId().toString())) {
                    mgProduct.setCategory(findCategoryByPartnerId(categories, "IPS").get());
                } else if (servidoresCsv.contains(currentCategory.get().getId().toString())) {
                    mgProduct.setCategory(findCategoryByPartnerId(categories, "SCS").get());
                } else if (notebooksCsv.contains(currentCategory.get().getId().toString())) {
                    mgProduct.setCategory(findCategoryByPartnerId(categories, "NPAT").get());
                } else if (monitoresCsv.contains(currentCategory.get().getId().toString())) {
                    mgProduct.setCategory(findCategoryByPartnerId(categories, "MT").get());
                } else {
                    mgProduct.setCategory(currentCategory.get());
                }

                importSingleProduct(mgProduct);
            } else {
                log.info("Brand or category not found, will synchronize again {} {} ", currentBrand, currentCategory);
                syncronizeBrandsAndProducts();
            }

        }
    }

    private void syncronizeBrandsAndProducts() {
        try {
            brandService.synchronizeBrands();
            categoryService.synchronizeCategories();
        } catch (GerbioException e) {
            log.error("Error synchronizing brands and categories");
            integratorErrorService.saveError(IntegratorError.builder()
                    .className(this.getClass().getName())
                    .errorMessage("Error synchronizing brands and categories ")
                    .timestamp(LocalDateTime.now())
                    .stackTrace(e.getMessage())
                    .type(IntegratorError.ErrorType.SYNCHRONIZING)
                    .build());
        }
    }

    private void importSingleProduct(AnyProduct product) {
        try {
            productService.saveAndPersist(product);
        } catch (Exception e) {
            log.error("Error importing products with Id {} ", product.getSkus().get(0).getPartnerId());
            integratorErrorService.saveError(IntegratorError.builder()
                    .className(this.getClass().getName())
                    .errorMessage("Error Importing Product with id  " + product.getSkus().get(0).getPartnerId())
                    .timestamp(LocalDateTime.now())
                    .stackTrace(e.getMessage())
                    .type(IntegratorError.ErrorType.IMPORT)
                    .build());
        }
    }

    private List<AnyProduct> retrieveProductsFromMG() throws GerbioException {
        try {
            log.info("Retrieving products from MG");
            ProductsRequest productRequest = mgWebService.getCatalog();
            List<Item> items = mgWebService.getContenido();

            List<AnyProduct> anyProducts = new ArrayList<>();

            productRequest.getListProducts().getProduct().forEach(p -> {
                AnyProduct product = mapper.map(p, AnyProduct.class);
                anyProducts.add(product);
            });

            ItemToAnyProductMapper itemToAnyProductMapper = new ItemToAnyProductMapper();
            for (Item item : items) {
                Optional<AnyProduct> anyProductOptional = anyProducts.stream().filter(ap -> ap.getSkus().get(0).getPartnerId()
                        .equals(item.getPartNumber())).findFirst();
                if (anyProductOptional.isPresent()) {
                    itemToAnyProductMapper.mapItemToAnyProduct(anyProductOptional.get(), item);
                }
            }

            return anyProducts;
        } catch (Exception e) {
            integratorErrorService.saveError(IntegratorError.builder()
                    .className(this.getClass().getName())
                    .errorMessage("Error Retrieving Products From MG with Brands and Categories")
                    .timestamp(LocalDateTime.now())
                    .stackTrace(e.getMessage())
                    .type(IntegratorError.ErrorType.IMPORT)
                    .build());

            throw new GerbioException(e);
        }
    }

    private Optional<AnyBrand> findBrandByPartnerId(List<AnyBrand> brands, String partnerId) {
        return brands.stream().filter(b -> b.getPartnerId().equals(partnerId)).findFirst();
    }

    private Optional<AnyCategory> findCategoryByPartnerId(List<AnyCategory> category, String partnerId) {
        return category.stream().filter(c -> c.getPartnerId().equals(partnerId)).findFirst();
    }
}
