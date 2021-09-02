package com.palazzisoft.gerbio.integrator.catalogo;

import com.palazzisoft.gerbio.integrator.model.anymarket.AnyVariationGet;
import com.palazzisoft.gerbio.integrator.model.anymarket.AnyVariationValue;
import com.palazzisoft.gerbio.integrator.service.anymarket.VariationService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class AnyVariationTest {

    @Autowired
    private VariationService variationService;

    @Test
    void createNewVariation() {
        AnyVariationValue value110 = AnyVariationValue.builder()
                .description("110")
                .build();

        AnyVariationValue value220 = AnyVariationValue.builder()
                .description("210")
                .build();

        AnyVariationValue value440 = AnyVariationValue.builder()
                .description("440")
                .build();

        AnyVariationGet variationGet = AnyVariationGet.builder()
                .name("Voltage")
                .visualVariation(false)
                .values(Lists.newArrayList(value110, value220, value440))
                .build();

        variationService.save(variationGet);
    }

    @Test
    void updateVariation() {
        AnyVariationValue value330 = AnyVariationValue.builder()
                .description("330")
                .build();

        AnyVariationGet variation = variationService.getById(32072L);
        variation.getValues().add(value330);
    }
}
