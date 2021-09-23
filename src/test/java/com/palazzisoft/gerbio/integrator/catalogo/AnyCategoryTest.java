package com.palazzisoft.gerbio.integrator.catalogo;


import com.palazzisoft.gerbio.integrator.model.anymarket.AnyCategory;
import com.palazzisoft.gerbio.integrator.response.CategoryResponse;
import com.palazzisoft.gerbio.integrator.service.anymarket.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@Slf4j
@ActiveProfiles("test")
public class AnyCategoryTest {

    @Autowired
    private CategoryService categoryService;

    @Test
    void homologateCategoriesCAT01() {
        // CAT-01

        AnyCategory category1 = AnyCategory.builder()
                .name("Impresoras")
                .definitionPriceScope("SKU")
                .partnerId("PRINTCAT")
                .build();

        AnyCategory category2 = AnyCategory.builder()
                .name("Computoras")
                .definitionPriceScope("SKU")
                .partnerId("LAPCAT")
                .build();

        AnyCategory category3 = AnyCategory.builder()
                .name("TVs")
                .definitionPriceScope("SKU")
                .partnerId("TVCAT")
                .build();

        categoryService.save(category1);
        categoryService.save(category2);
        categoryService.save(category3);
    }

    @Test
    void homologateCategoriasCat02() {
        // CAT-02

        AnyCategory estufa = AnyCategory.builder()
                .name("Estufa")
                .definitionPriceScope("SKU")
                .partnerId("EstufaCAT")
                .build();

        estufa = categoryService.save(estufa);


        AnyCategory sublevel1 = AnyCategory.builder()
                .name("Electrica")
                .definitionPriceScope("SKU")
                .partnerId("EstufaCAT-E")
                .parent(estufa)
                .build();

        AnyCategory sublevel2 = AnyCategory.builder()
                .name("A Gas")
                .definitionPriceScope("SKU")
                .partnerId("EstufaCAT-G")
                .parent(estufa)
                .build();

        categoryService.save(sublevel1);
        categoryService.save(sublevel2);
    }

    @Test
    void homologationCategoriesCAT03() {
        // CAT-03

        AnyCategory estufaAGas = categoryService.getById(764968L);

        AnyCategory sublevel2 = AnyCategory.builder()
                .name("Tiro Balanceado")
                .definitionPriceScope("SKU")
                .partnerId("EstufaCAT-G-Bal")
                .parent(estufaAGas)
                .build();

        categoryService.save(sublevel2);
    }

    @Test
    void homologacionCategoriaCAT04() {
        // CAT-04

        AnyCategory computadora = categoryService.getById(764964L);

        computadora.setName("Ordenadores");

        computadora = categoryService.update(computadora, computadora.getId());

        log.info("Nombre nuevo es {}", computadora.getName());
    }

    @Test
    void homologacionCategoriaCAT05() {
        // CAT-05

        AnyCategory estufaAGas = categoryService.getById(764968L);
        estufaAGas.setName("A Gas Renombrada");

        categoryService.update(estufaAGas, estufaAGas.getId());
    }

    @Test
    void homologacionCategoriaCAT06() {
        // CAT-06
        AnyCategory tiroBalanceado = categoryService.getById(764969L);

        tiroBalanceado.setName("Tiro Balanceado (Con salida)");
        categoryService.update(tiroBalanceado, tiroBalanceado.getId());
    }

    @Test
    void homologacionCategoriaCAT07() {
        // CAT-07

        AnyCategory tv = categoryService.getById(764965L);
        AnyCategory response = categoryService.delete(tv.getId());
    }

    @Test
    void testAllCategories() {
        List<AnyCategory> categories = categoryService.getAll();
        log.info(categories.toString());
    }
}
