package com.palazzisoft.gerbio.integrator.catalogo;

import com.palazzisoft.gerbio.integrator.mapping.ItemToAnyProductMapper;
import com.palazzisoft.gerbio.integrator.model.anymarket.AnyProduct;
import com.palazzisoft.gerbio.integrator.model.mg.Item;
import com.palazzisoft.gerbio.integrator.service.mg.MGWebService;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;


@SpringBootTest
@Slf4j
@ActiveProfiles("test")
public class WSMGTest {

    @Autowired
    private MGWebService mgWebService;

    @Autowired
    private MapperFacade mapper;

    @Test
    public void testSarasus() {
        ProductsRequest  productRequest = mgWebService.getCatalog();
        List<Item> items = mgWebService.getContenido();

        List<AnyProduct> anyProducts = Lists.newArrayList();

        productRequest.getListProducts().getProduct().forEach(p -> {
            AnyProduct product = mapper.map(p, AnyProduct.class);
            anyProducts.add(product);
        });

        ItemToAnyProductMapper itemToAnyProductMapper = new ItemToAnyProductMapper();
        for (Item item : items){
            Optional<AnyProduct> anyProductOptional = anyProducts.stream().filter(ap -> ap.getSkus().get(0).getPartnerId().equals(item.getPartNumber())).findFirst();
            if (anyProductOptional.isPresent()) {
                AnyProduct product = itemToAnyProductMapper.mapItemToAnyProduct(anyProductOptional.get(), item);
            }
        }
    }
}
