package com.palazzisoft.gerbio.integrator.mapping;

import com.palazzisoft.gerbio.integrator.catalogo.Product;
import com.palazzisoft.gerbio.integrator.model.anymarket.AnyProduct;
import ma.glasnost.orika.MapperFacade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductToAnyProductMapperTest {

    @Autowired
    private MapperFacade mapper;

    @Test
    public void testMGProductToAnyProductMapper() {
        Product mgProduct = buildMGProduct();
        AnyProduct anyProduct = mapper.map(mgProduct, AnyProduct.class);

        assertEquals(anyProduct.getId().toString(), mgProduct.getPartNumber());
        assertEquals(anyProduct.getDescription(), mgProduct.getDescripcion());
        assertEquals(anyProduct.getBrand().getId().toString(), mgProduct.getCodMarca());
        assertEquals(anyProduct.getBrand().getName(), mgProduct.getMarca());
        assertEquals(anyProduct.getCategory().getId().toString(), mgProduct.getCodCategoria());
        assertEquals(anyProduct.getCategory().getName(), mgProduct.getCategoria());
        assertEquals(anyProduct.getPriceFactor(), mgProduct.getPrecio());
    }

    private Product buildMGProduct(){
        Product mgProduct = new Product();
        mgProduct.setPartNumber("1234");
        mgProduct.setDescripcion("descripcion");
        mgProduct.setCodMarca("567");
        mgProduct.setMarca("Brand");
        mgProduct.setCodCategoria("890");
        mgProduct.setCategoria("Categoria");
        mgProduct.setPrecio(124.5);
        return mgProduct;
    }

}
