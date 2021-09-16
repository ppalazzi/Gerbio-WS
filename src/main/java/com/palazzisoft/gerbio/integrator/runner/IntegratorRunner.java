package com.palazzisoft.gerbio.integrator.runner;

import com.palazzisoft.gerbio.integrator.response.BrandResponse;
import com.palazzisoft.gerbio.integrator.response.CategoryResponse;
import com.palazzisoft.gerbio.integrator.service.anymarket.BrandService;
import com.palazzisoft.gerbio.integrator.service.anymarket.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class IntegratorRunner implements ApplicationRunner {

    private final BrandService brandService;
    private final CategoryService categoryService;

    public IntegratorRunner(final BrandService brandService, final CategoryService categoryService) {
        this.brandService = brandService;
        this.categoryService = categoryService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("Initializing Gerbio Integrator App");

        BrandResponse brands = brandService.getBrands();
        brandService.saveBrands(brands.getContent());
        CategoryResponse categoryResponse = categoryService.getCategories();
        categoryService.saveCategories(categoryResponse.getContent());
    }
}
