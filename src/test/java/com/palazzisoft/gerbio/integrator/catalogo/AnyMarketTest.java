package com.palazzisoft.gerbio.integrator.catalogo;

import com.palazzisoft.gerbio.integrator.model.anymarket.AnyBrand;
import com.palazzisoft.gerbio.integrator.model.anymarket.AnyCategory;
import com.palazzisoft.gerbio.integrator.response.CategoryResponse;
import com.palazzisoft.gerbio.integrator.service.anymarket.BrandService;
import com.palazzisoft.gerbio.integrator.service.anymarket.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class AnyMarketTest {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BrandService brandService;

    @Test
    void testCatalog() {
        CategoryResponse response = categoryService.getCategories();
        log.info("Respuesta es {} ", response);

        AnyCategory categoryById = categoryService.getById(763121L);

        log.info("Category traiga correctamente {} ", categoryById);

    }

    @Test
    void testBrands() {
        AnyBrand brand = AnyBrand.builder().name("Coca Cola").reducedName("Coca").partnerId("12345").build();

        AnyBrand stored = brandService.save(brand);

        AnyBrand retrieved = brandService.getById(stored.getId());

        log.info("BRand is {} ", retrieved);
    }
}
