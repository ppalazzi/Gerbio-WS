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
import com.palazzisoft.gerbio.integrator.service.anymarket.StockService;
import com.palazzisoft.gerbio.integrator.service.mg.MGWebService;
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
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping(value = "/importing")
@Slf4j
@Profile("!test")
public class ProductImportController {

    private final CategoryService categoryService;
    private final BrandService brandService;
    private final ProductService productService;
    private final StockService stockService;
    private final MGWebService mgWebService;
    private final MapperFacade mapper;
    private final IntegratorErrorService integratorErrorService;


    public ProductImportController(final CategoryService categoryService, final BrandService brandService,
                                   final ProductService productService, final StockService stockService,
                                   final MGWebService mgWebService, final MapperFacade mapperFacade,
                                   IntegratorErrorService integratorErrorService) throws IOException {
        this.categoryService = categoryService;
        this.productService = productService;
        this.stockService = stockService;
        this.brandService = brandService;
        this.mgWebService = mgWebService;
        this.mapper = mapperFacade;
        this.integratorErrorService = integratorErrorService;
    }

    @GetMapping
    @Scheduled(fixedDelay = 3600000, initialDelay = 3600000)
    public ResponseEntity<List<AnyProduct>> importProducts() throws GerbioException, IOException {
        log.info("Starting importing Product at {} ", Instant.now());

        // retrieving all products from MG and DB
        List<AnyProduct> products = retrieveProductsFromMG();

        try {
            List<AnyProduct> currentDBProducts = productService.getAll();

            // retriveing brands and product
            List<AnyBrand> brands = brandService.findAll();
            List<AnyCategory> categories = categoryService.findAll();

            for (AnyProduct mgProduct : products) {
                importProduct(currentDBProducts, brands, categories, mgProduct);
            }

            updateStockOfMissingProducts(currentDBProducts, products);

            log.info("Product importation of {} items, run succesfully", products.size());
        }
        catch (Exception e) {
            log.error("Error on scheduling", e);
        }


        return ResponseEntity.ok(products);
    }

    private void updateStockOfMissingProducts(List<AnyProduct> currentDBProducts, List<AnyProduct> products) {
        for (AnyProduct productFromBase : currentDBProducts) {
            final String partnerId = productFromBase.getSkus().get(0).getPartnerId();

            Optional<AnyProduct> mgProduct = products.stream()
                    .filter(p -> p.getSkus().get(0).getPartnerId().equals(partnerId)).findFirst();

            if (!mgProduct.isPresent()) {
                log.info("Updating stock of partnerId {} to 0", partnerId);
                productFromBase.getSkus().get(0).setAmount(0);
                productService.updateAndPersist(productFromBase);
            }
        }
    }

    private void importProduct(List<AnyProduct> currentDBProducts, List<AnyBrand> brands, List<AnyCategory> categories, AnyProduct mgProduct) {
        final String partnerId = mgProduct.getSkus().get(0).getPartnerId();
        Optional<AnyProduct> baseEquivalent = currentDBProducts.stream()
                .filter(p -> p.getSkus().get(0).getPartnerId().equals(partnerId)).findFirst();

        Optional<AnyBrand> currentBrand = findBrandByPartnerId(brands, mgProduct.getBrand().getPartnerId());
        Optional<AnyCategory> currentCategory = findCategoryByPartnerId(categories, mgProduct.getCategory().getPartnerId());

        if (!currentBrand.isPresent() || !currentCategory.isPresent()) {
            log.info("Brand or category not found, will synchronize again {} {} ", currentBrand, currentCategory);
            syncronizeBrandsAndProducts();
            return;
        }

        if (baseEquivalent.isPresent()) {
            AnyProduct baseProduct = baseEquivalent.get();

            // if price, category or stock has changed, update DB and Anymarket
            if (baseProduct.getSkus().get(0).getPrice() != mgProduct.getSkus().get(0).getPrice()
                    || baseProduct.getSkus().get(0).getAmount() != mgProduct.getSkus().get(0).getAmount()
                    || !baseProduct.getCategory().getId().equals(currentCategory.get().getId())
                    || !baseProduct.getBrand().getId().equals(currentBrand.get().getId())
            ) {
                log.info("PRICE {} - {} = {}", baseProduct.getSkus().get(0).getPrice(), mgProduct.getSkus().get(0).getPrice(),
                        baseProduct.getSkus().get(0).getPrice() == mgProduct.getSkus().get(0).getPrice());
                log.info("AMOUNT {} - {} = {}", baseProduct.getSkus().get(0).getAmount(), mgProduct.getSkus().get(0).getAmount(),
                        baseProduct.getSkus().get(0).getAmount() == mgProduct.getSkus().get(0).getAmount());
                log.info("CATEGORY {} - {} = {}", baseProduct.getCategory().getId(), currentCategory.get().getId(),
                        !baseProduct.getCategory().getId().equals(currentCategory.get().getId()));
                log.info("BRAND {} - {} = {}", baseProduct.getBrand().getId(), currentBrand.get().getId(),
                        !baseProduct.getBrand().getId().equals(currentBrand.get().getId()));

                if (mgProduct.getSkus().get(0).getAmount() > 0d) {
                    baseProduct.getSkus().get(0).setAmount(mgProduct.getSkus().get(0).getAmount());
                } else {
                    log.info("partner id {} se setea stock en 0 ", partnerId);
                    baseProduct.getSkus().get(0).setAmount(0d);
                }

                baseProduct.getSkus().get(0).setSellPrice(mgProduct.getSkus().get(0).getPrice());
                baseProduct.getSkus().get(0).setPrice(mgProduct.getSkus().get(0).getPrice());
                
                currentCategory.ifPresent(baseProduct::setCategory);
                currentBrand.ifPresent(baseProduct::setBrand);
                productService.updateAndPersist(baseProduct);
            }
        } else {
            // add to database and anymarket
            if (currentBrand.isPresent() && currentCategory.isPresent()) {
                log.debug("Brands and Category found");
                mgProduct.setBrand(currentBrand.get());
                mgProduct.setCategory(currentCategory.get());
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
