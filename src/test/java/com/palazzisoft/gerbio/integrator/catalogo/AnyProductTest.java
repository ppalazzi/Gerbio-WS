package com.palazzisoft.gerbio.integrator.catalogo;

import com.palazzisoft.gerbio.integrator.model.anymarket.*;
import com.palazzisoft.gerbio.integrator.service.anymarket.*;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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

    @Autowired
    private StockService stockService;

    @Autowired
    private VariationService variationService;

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

    // este es por precio por coste, hay que terminar de entenderlo bien.
    @Test
    void updateProductStock() {
        AnyProduct impresoraCanon = productService.getById(2274570L);

        AnyStock stock = AnyStock.builder()
                .id(impresoraCanon.getSkus().get(0).getId())
                .cost(19366d)
                .partnerId(impresoraCanon.getSkus().get(0).getPartnerId())
                .quantity(2)
                .build();

        List<AnyStock> stocks = stockService.update(Lists.newArrayList(stock));

        assertThat(stock.getCost()).isEqualTo(19366d);
    }

    @Test
    void updateProductById() {
        AnyProduct impresoraCanon = productService.getById(2274570L);
        AnySku sku = impresoraCanon.getSkus().get(0);
        sku.setPrice(36800d);

        AnyProduct updated = productService.updateProductSku(sku, impresoraCanon.getId());

        assertThat(updated.getSkus().get(0).getPrice()).isEqualTo(36800d);
    }

    @Test
    void updateStock() {
        AnyProduct impresoraHP = productService.getById(2274569L);

        AnySku sku = impresoraHP.getSkus().get(0);
        sku.setAmount(3);

        AnyStock stock = AnyStock.builder()
                .id(sku.getId())
                .cost(sku.getPrice())
                .partnerId(sku.getPartnerId())
                .quantity(78)
                .build();

        stockService.update(Lists.newArrayList(stock));
    }

    @Test
    void updateStockCrossDocking() {
        AnyProduct impresoraHP = productService.getById(2274569L);

        AnySku sku = impresoraHP.getSkus().get(0);
        sku.setAmount(4);
        sku.setAdditionalTime(9);

        AnyProduct updated = productService.updateProductSku(sku, impresoraHP.getId());

        AnyStock stock = AnyStock.builder()
                .id(sku.getId())
                .cost(sku.getPrice())
                .partnerId(sku.getPartnerId())
                .additionalTime(3.3d)
                .quantity(4)
                .build();

        stockService.update(Lists.newArrayList(stock));
    }

    @Test
    void updateStockPRiceAndDocking() {
        AnyProduct impresoraHP = productService.getById(2274569L);

        AnySku sku = impresoraHP.getSkus().get(0);

        AnyStock stock = AnyStock.builder()
                .id(sku.getId())
                .cost(sku.getPrice() + 1)
                .partnerId(sku.getPartnerId())
                .additionalTime(56)
                .quantity(2)
                .build();

        stockService.update(Lists.newArrayList(stock));
    }

    @Test
    void backStoToZero() {
        AnyProduct impresoraCanon = productService.getById(2274570L);
        AnySku sku = impresoraCanon.getSkus().get(0);

        AnyStock stock = AnyStock.builder()
                .id(sku.getId())
                .partnerId(sku.getPartnerId())
                .quantity(0)
                .cost(sku.getPrice())
                .build();

        stockService.update(Lists.newArrayList(stock));

    }

    @Test
    void addCharacteristicsToProduct() {
        AnyProduct impresoraCanon = productService.getById(2274570L);

        AnyProductCharacteristic caracteristics = AnyProductCharacteristic.builder()
                .index(0)
                .value("Azul")
                .name("Color")
                .build();

        impresoraCanon.setCharacteristics(Lists.newArrayList(caracteristics));

        productService.update(impresoraCanon, impresoraCanon.getId());
    }

    @Test
    void createNewProductToBePublished() {
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
                .price(84000)
                .amount(5)
                .title("Piano Yamaha P115 - SKU")
                .partnerId("PN-YMH-115-1")
                .build();

        AnyProduct piano = AnyProduct.builder()
                .category(category)
                .brand(brand)
                .description("Yamaha P115 imita al Grand Piano")
                .title("Piano Yamaha P115")
                .origin(origin)
                .model("P115")
                .warrantyText("5 Año")
                .warrantyTime(5)
                .height(600d)
                .width(400d)
                .weight(1200d)
                .definitionPriceScope("SKU")
                .images(newArrayList(image))
                .priceFactor(1d)
                .calculatedPrice(false)
                .skus(newArrayList(sku))
                .build();

        piano.setCharacteristics(list(AnyProductCharacteristic.builder()
                .name("Color")
                .value("Black")
                .index(0)
                .build()));

        productService.save(piano);
    }

    @Test
    void productWithVariations() {
        AnyCategory category = categoryService.getById(764963L);
        AnyBrand brand = brandService.getById(222971L);
        AnyOrigin origin = AnyOrigin.builder().id(1L).build();

        AnyVariationGet variation = variationService.getById(32073L);

        // first product
        AnyImage image = AnyImage.builder()
                .originalImage("https://microglobalpromos.com.ar/2021/img/072021/BUNMK1292_1.jpg")
                .url("https://microglobalpromos.com.ar/2021/img/072021/BUNMK1292_1.jpg")
                .main(true)
                .build();

        AnySku sku110 = AnySku.builder()
                .price(5000d)
                .amount(3)
                .title("Monitor Samsung - 110v")
                .partnerId("MNT-SMS-110-111")
                //.variations(Variation.builder().Voltage("110").build())
                .sellPrice(1d)
                .build();

        AnySku sku220 = AnySku.builder()
                .price(5000d)
                .amount(3)
                .title("Monitor Samsung - 220v")
                .partnerId("MNT-SMS-220-111")
                .sellPrice(1d)
                //.variations(Variation.builder().Voltage("210").build())
                .build();

        AnySku sku440 = AnySku.builder()
                .price(5000d)
                .amount(3)
                .title("Monitor Samsung - 440v")
                .partnerId("MNT-SMS-440-111")
                .sellPrice(1d)
                //.variations(Variation.builder().Voltage("440").build())
                .build();

        AnyProduct monitor = AnyProduct.builder()
                .category(category)
                .brand(brand)
                .description("Monitor Samsung PRO")
                .title("Monitor Samsung")
                .origin(origin)
                .model("3BNCERD")
                .warrantyText("5 Año")
                .warrantyTime(5)
                .height(600d)
                .width(400d)
                .weight(1200d)
                .definitionPriceScope("SKU")
                .images(newArrayList(image))
                .priceFactor(1d)
                .calculatedPrice(false)
                .hasVariations(true)
                .skus(newArrayList(sku110, sku220, sku440))
                .build();

        monitor.setCharacteristics(list(AnyProductCharacteristic.builder()
                .name("Color")
                .value("Black")
                .index(0)
                .build()));

        //productService.save(monitor);

        // producto 2

        AnySku sku110b = AnySku.builder()
                .price(1000d)
                .amount(2)
                .title("Fonola Retro - 110v")
                .partnerId("FNL-110-111")
                //.variations(Variation.builder().Voltage("110").build())
                .sellPrice(1d)
                .build();

        AnySku sku220b = AnySku.builder()
                .price(1000d)
                .amount(2)
                .title("Fonola Retro - 220v")
                .partnerId("FNL-220-111")
                .sellPrice(1d)
                //.variations(Variation.builder().Voltage("210").build())
                .build();

        AnySku sku440b = AnySku.builder()
                .price(5000d)
                .amount(3)
                .title("Fonola Retro - 440v")
                .partnerId("FNL-440-111")
                .sellPrice(1d)
                //.variations(Variation.builder().Voltage("440").build())
                .build();

        AnyProduct fonola = AnyProduct.builder()
                .category(category)
                .brand(brand)
                .description("Fonola ")
                .title("Fonola Retro")
                .origin(origin)
                .model("3BNCERD-852147")
                .warrantyText("3 Año")
                .warrantyTime(3)
                .height(600d)
                .width(400d)
                .weight(1200d)
                .definitionPriceScope("SKU")
                .images(newArrayList(image))
                .priceFactor(1d)
                .hasVariations(true)
                .calculatedPrice(false)
                .skus(newArrayList(sku110b, sku220b, sku440b))
                .build();

        productService.save(fonola);
    }

    @Test
    void testUpdateProduct1() {
        AnyProduct product = productService.getById(2279768L);

        product.setTitle("Monitor Samsung - Updated");
        product.setDescription("Monitor Samsung PRO - Updated");
        product.setWeight(3d);
        product.setPriceFactor(2d);
        product.setHeight(45d);
        product.setWarrantyTime(8);
        product.setWarrantyText("8 Años");
        product.setModel("3BNCERD-UPDATED");

        product.getCharacteristics().add(AnyProductCharacteristic.builder().index(1).name("Size").value("Huge").build());
        product.getImages().get(0).setUrl("https://microglobalpromos.com.ar/2021/img/072021/BUNMK1292_2.jpg");

        productService.update(product, product.getId());
    }
}
