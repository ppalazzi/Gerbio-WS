package com.palazzisoft.gerbio.integrator.catalogo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.palazzisoft.gerbio.integrator.model.anymarket.*;
import com.palazzisoft.gerbio.integrator.service.anymarket.BrandService;
import com.palazzisoft.gerbio.integrator.service.anymarket.CategoryService;
import com.palazzisoft.gerbio.integrator.service.anymarket.ProductService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.util.Lists.newArrayList;

@SpringBootTest
@Slf4j
public class AnyMarketTest {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private ProductService productService;

    @Test
    void testCatalog() {
        List<AnyCategory> response = categoryService.getAll();
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

    @SneakyThrows
    @Test
    void testProducts() {
        AnyProduct product = buildAnyProduct();

        ObjectMapper mapper = new ObjectMapper();
        String value = mapper.writeValueAsString(product);

        log.info("PRINTABLE: {} ", value);

        AnyProduct productCreated = productService.save(product);

        log.info("Nuevo producto tiene id {} ", productCreated.getId());
    }

    private AnyProduct buildAnyProduct() {
        AnyBrand brand = AnyBrand.builder().id(222721L).build();
        AnyCategory category = AnyCategory.builder().id(763121L).build();
        AnyOrigin origin = AnyOrigin.builder().id(1L).build();
        AnyImage image = AnyImage.builder()
                .originalImage("https://microglobalpromos.com.ar/2021/img/072021/BUNMK1292_1.jpg")
                .url("https://microglobalpromos.com.ar/2021/img/072021/BUNMK1292_1.jpg")
                .main(true)
                .build();
        AnySku sku = AnySku.builder()
                .price(200.22d)
                .amount(3)
                .title("El titulo del SKU 1234567")
                .partnerId("1234567")
                .build();

        AnyProduct product = AnyProduct.builder()
                .brand(brand)
                .category(category)
                .description("Cajon de Cerveza de la más alta calidad")
                .title("Cajon de Cerveza")
                .origin(origin)
                .model("Es modelo y le encanta defilar")
                .warrantyText("1 Año")
                .warrantyTime(3)
                .height(50d)
                .width(45d)
                .weight(100)
                //.definitionPriceScope("SKU")
                .images(newArrayList(image))
                .priceFactor(1d)
                .calculatedPrice(false)
                .skus(newArrayList(sku))
                .build();

        return product;
    }
}
