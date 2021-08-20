package com.palazzisoft.gerbio.integrator.mapping;

import com.palazzisoft.gerbio.integrator.catalogo.Product;
import com.palazzisoft.gerbio.integrator.model.anymarket.AnyProduct;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import ma.glasnost.orika.impl.DefaultMapperFactory;

public class ProductToAnyProductMapper extends ConfigurableMapper {

    protected void configure(MapperFactory factory) {
        factory.classMap(Product.class, AnyProduct.class)
                .field("partNumber", "id")
                .field("descripcion", "description")
                .field("codMarca", "brand.id")
                .field("marca", "brand.name")
                .field("codCategoria", "category.id")
                .field("categoria", "category.name")
                .field("precio", "priceFactor")
                .byDefault()
                .register();
    }
}


