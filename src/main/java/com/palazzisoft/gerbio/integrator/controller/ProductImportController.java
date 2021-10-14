package com.palazzisoft.gerbio.integrator.controller;

import com.palazzisoft.gerbio.integrator.catalogo.ProductsRequest;
import com.palazzisoft.gerbio.integrator.mapping.ItemToAnyProductMapper;
import com.palazzisoft.gerbio.integrator.model.anymarket.AnyBrand;
import com.palazzisoft.gerbio.integrator.model.anymarket.AnyCategory;
import com.palazzisoft.gerbio.integrator.model.anymarket.AnyProduct;
import com.palazzisoft.gerbio.integrator.model.mg.Item;
import com.palazzisoft.gerbio.integrator.service.anymarket.BrandService;
import com.palazzisoft.gerbio.integrator.service.anymarket.CategoryService;
import com.palazzisoft.gerbio.integrator.service.anymarket.ProductService;
import com.palazzisoft.gerbio.integrator.service.mg.MGWebService;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
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

    public ProductImportController(final CategoryService categoryService, final BrandService brandService,
                                   final ProductService productService, final MGWebService mgWebService,
                                   final MapperFacade mapperFacade) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.brandService = brandService;
        this.mgWebService = mgWebService;
        this.mapper = mapperFacade;
    }

    @GetMapping
    //@Scheduled(fixedRate = 60000)
    public ResponseEntity<List<AnyProduct>> importProducts() {
        log.info("Starting importing Product at {} ", Instant.now());

        // retrieving all products
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
            // compare amount and update stock and price if required
        }
        else {
            // add to database and anymarket
            Optional<AnyBrand> currentBrand = findBrandByPartnerId(brands, mgProduct.getBrand().getPartnerId());
            Optional<AnyCategory> currentCategory = findCategoryByPartnerId(categories, mgProduct.getCategory().getPartnerId());

            if (currentBrand.isPresent() && currentCategory.isPresent()) {
                log.debug("Brands and Category found");

                mgProduct.setBrand(currentBrand.get());
                mgProduct.setCategory(currentCategory.get());
                AnyProduct anyResponse = productService.saveAndPersist(mgProduct);
            }
            else {
                log.error("Brand or category not found {} {} ", currentBrand, currentCategory);
            }

        }
    }

    private List<AnyProduct> retrieveProductsFromMG() {
        ProductsRequest productRequest = mgWebService.getCatalog();
        List<Item> items = mgWebService.getContenido();

        List<AnyProduct> anyProducts = new ArrayList<>();

        productRequest.getListProducts().getProduct().forEach(p -> {
            AnyProduct product = mapper.map(p, AnyProduct.class);
            anyProducts.add(product);
        });

        ItemToAnyProductMapper itemToAnyProductMapper = new ItemToAnyProductMapper();
        for (Item item : items){
            Optional<AnyProduct> anyProductOptional = anyProducts.stream().filter(ap -> ap.getSkus().get(0).getPartnerId().equals(item.getPartNumber())).findFirst();
            if (anyProductOptional.isPresent()) {
                itemToAnyProductMapper.mapItemToAnyProduct(anyProductOptional.get(), item);
            }
        }

        return anyProducts;
    }

    private Optional<AnyBrand> findBrandByPartnerId(List<AnyBrand> brands, String partnerId) {
        return brands.stream().filter(b -> b.getPartnerId().equals(partnerId)).findFirst();
    }

    private Optional<AnyCategory> findCategoryByPartnerId(List<AnyCategory> category, String partnerId) {
        return category.stream().filter(c -> c.getPartnerId().equals(partnerId)).findFirst();
    }
}
