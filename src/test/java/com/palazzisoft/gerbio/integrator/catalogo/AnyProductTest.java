package com.palazzisoft.gerbio.integrator.catalogo;

import com.palazzisoft.gerbio.integrator.model.anymarket.*;
import com.palazzisoft.gerbio.integrator.service.anymarket.BrandService;
import com.palazzisoft.gerbio.integrator.service.anymarket.CategoryService;
import com.palazzisoft.gerbio.integrator.service.anymarket.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.util.Lists.list;
import static org.assertj.core.util.Lists.newArrayList;

@SpringBootTest
@Slf4j
public class AnyProductTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BrandService brandService;

    @Test
    void testProduct01() {
        AnyCategory category = categoryService.getById(764963L);
        AnyBrand brand = brandService.getById(222971L);
        AnyOrigin origin = AnyOrigin.builder().id(1L).build();

        // first product
        AnyImage image = AnyImage.builder()
                .originalImage("https://microglobalpromos.com.ar/2021/img/072021/BUNMK1292_1.jpg")
                .url("https://microglobalpromos.com.ar/2021/img/072021/BUNMK1292_1.jpg")
                .main(true)
                .build();

        AnySku sku = AnySku.builder()
                .price(8750)
                .amount(1)
                .title("SKU-PRINT-HP-1")
                .partnerId("HP-DES-1860")
                .build();

        AnyProduct impresoraHP = AnyProduct.builder()
                .category(category)
                .brand(brand)
                .description("HP Deskjet 1860")
                .title("HP Deskjet")
                .origin(origin)
                .model("1860")
                .warrantyText("1 Año")
                .warrantyTime(3)
                .height(50d)
                .width(45d)
                .weight(100)
                .definitionPriceScope("SKU")
                .images(newArrayList(image))
                .priceFactor(1d)
                .calculatedPrice(false)
                .skus(newArrayList(sku))
                .build();

            // second product
        AnySku sku2 = AnySku.builder()
                .price(16300)
                .amount(2)
                .title("Canon P150-12")
                .partnerId("CAN-SKU-1860")
                .build();

        AnyImage image2 = AnyImage.builder()
                .originalImage("https://microglobalpromos.com.ar/2022/img/072021/BUNMK1292_1.jpg")
                .url("https://microglobalpromos.com.ar/2022/img/072021/BUNMK1292_1.jpg")
                .main(true)
                .build();

        AnyProduct impresoraCanon = AnyProduct.builder()
                .category(category)
                .brand(brand)
                .description("Canon P150")
                .title("Canon Printer")
                .origin(origin)
                .model("P150")
                .warrantyText("2 Año")
                .warrantyTime(2)
                .height(75d)
                .width(40d)
                .weight(200)
                .definitionPriceScope("SKU")
                .images(newArrayList(image2))
                .priceFactor(1d)
                .calculatedPrice(false)
                .skus(newArrayList(sku2))
                .build();

        impresoraHP = productService.save(impresoraHP);
        impresoraCanon = productService.save(impresoraCanon);

        log.info(impresoraHP.getTitle());
        log.info(impresoraCanon.getTitle());
    }

    @Test
    void testProduct02() {
        AnyCategory category = categoryService.getById(764964L);
        AnyBrand brand = brandService.getById(222971L);
        AnyOrigin origin = AnyOrigin.builder().id(2L).build();

        // first product
        AnyProduct impresoraCanon = productService.getById(2274570L);

        AnyImage image1 = impresoraCanon.getImages().get(0);
        image1.setOriginalImage("https://microglobalpromos.com.ar/1-updated.jpg");
        image1.setUrl("https://microglobalpromos.com.ar/2022/img/072021/1-updated.jpg");

        impresoraCanon.setCategory(category);
        impresoraCanon.setBrand(brand);
        impresoraCanon.setDescription("Canon Updated");
        impresoraCanon.setOrigin(origin);
        impresoraCanon.setModel("Model Canon updated");
        impresoraCanon.setWarrantyText("1 Mes");
        impresoraCanon.setWarrantyTime(2);
        impresoraCanon.setHeight(35d);
        impresoraCanon.setWidth(100);
        impresoraCanon.setWeight(100d);
        impresoraCanon.setDefinitionPriceScope("SKU");
        impresoraCanon.setImages(list(image1));
        impresoraCanon.setPriceFactor(2d);
        impresoraCanon.setCalculatedPrice(false);

        // second product
        AnyProduct impresoraHP = productService.getById(2274569L);

        AnyImage image2 = impresoraHP.getImages().get(0);
        image2.setUrl("https://microglobalpromos.com.ar/1-updated.jpg");
        image2.setOriginalImage("https://microglobalpromos.com.ar/1-updated.jpg");

        impresoraHP.setCategory(category);
        impresoraHP.setBrand(brand);
        impresoraHP.setDescription("HP Updated");
        impresoraHP.setOrigin(origin);
        impresoraHP.setModel("Model HP updated");
        impresoraHP.setWarrantyText("2 Mes");
        impresoraHP.setWarrantyTime(4);
        impresoraHP.setHeight(35d);
        impresoraHP.setWidth(100);
        impresoraHP.setWeight(100d);
        impresoraHP.setDefinitionPriceScope("SKU_MARKETPLACE");
        impresoraHP.setImages(list(image2));
        impresoraHP.setPriceFactor(3d);
        impresoraHP.setCalculatedPrice(false);
        impresoraHP.setTitle("Canon Update");

        // persistencea
        //impresoraCanon = productService.update(impresoraCanon, impresoraCanon.getId());
        impresoraHP = productService.update(impresoraHP, impresoraHP.getId());

        log.info(impresoraHP.getTitle());
        log.info(impresoraCanon.getTitle());
    }
}
