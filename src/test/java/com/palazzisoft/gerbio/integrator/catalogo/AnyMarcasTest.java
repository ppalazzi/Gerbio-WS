package com.palazzisoft.gerbio.integrator.catalogo;

import com.palazzisoft.gerbio.integrator.model.anymarket.AnyBrand;
import com.palazzisoft.gerbio.integrator.service.anymarket.BrandService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class AnyMarcasTest {

    @Autowired
    private BrandService brandService;

    @Test
    void test01() {
        // MAR-01
        AnyBrand hp = AnyBrand.builder()
                .name("Hewlett-Packard")
                .reducedName("HP").partnerId("HP").build();

        AnyBrand compact = AnyBrand.builder()
                .name("Compact")
                .reducedName("Compact").partnerId("CP").build();

        brandService.save(hp);
        brandService.save(compact);
    }

    @Test
    void test02() {
        // MAR-02

        AnyBrand hp = brandService.getById(222971L);
        AnyBrand cp = brandService.getById(222972L);

        hp.setName("Hewlett Packard");
        cp.setName("Compact Presario");

        brandService.update(hp, hp.getId());
        brandService.update(cp, cp.getId());
    }

    @Test
    void test03() {
        // MAR-03

        AnyBrand cp = brandService.getById(222972L);

        brandService.delete(cp.getId());
    }
}
