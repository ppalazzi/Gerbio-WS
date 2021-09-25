package com.palazzisoft.gerbio.integrator.configuration;

import com.palazzisoft.gerbio.integrator.catalogo.Brand;
import com.palazzisoft.gerbio.integrator.catalogo.Category;
import com.palazzisoft.gerbio.integrator.catalogo.Product;
import com.palazzisoft.gerbio.integrator.model.anymarket.AnyBrand;
import com.palazzisoft.gerbio.integrator.model.anymarket.AnyCategory;
import com.palazzisoft.gerbio.integrator.model.anymarket.AnyProduct;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public MapperFacade mapper() {
        return mapperConfig().getMapperFacade();
    }

    private MapperFactory mapperConfig() {
        MapperFactory config = new DefaultMapperFactory.Builder().build();
        registerProductToAnyProduct(config);
        registerBrands(config);
        registerCategories(config);
        return config;
    }

    private void registerProductToAnyProduct(MapperFactory factory) {
        factory.classMap(Product.class, AnyProduct.class)
                .field("partNumber", "skus[0].partnerId")
                .field("descripcion", "description")
                .field("codMarca", "brand.name")
                .field("marca", "brand.partnerId")
                .field("codCategoria", "category.partnerId")
                .field("categoria", "category.name")
                .field("precio", "skus[0].price")
                .field("precio", "skus[0].sellPrice")
                .field("stock", "skus[0].amount")
                .field("upc", "skus[0].ean")
                .byDefault()
                .register();
    }

    private void registerBrands(MapperFactory factory) {
        factory.classMap(Brand.class, AnyBrand.class)
                .field("codMarca","partnerId")
                .field("marca", "name")
                .register();
    }

    private void registerCategories(MapperFactory factory) {
        factory.classMap(Category.class, AnyCategory.class)
                .field("codCategoria", "partnerId")
                .field("categoria", "name")
                .register();
    }
}
