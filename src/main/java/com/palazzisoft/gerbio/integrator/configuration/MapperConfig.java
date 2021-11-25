package com.palazzisoft.gerbio.integrator.configuration;

import com.palazzisoft.gerbio.integrator.catalogo.Brand;
import com.palazzisoft.gerbio.integrator.catalogo.Category;
import com.palazzisoft.gerbio.integrator.catalogo.Product;
import com.palazzisoft.gerbio.integrator.model.anymarket.AnyBrand;
import com.palazzisoft.gerbio.integrator.model.anymarket.AnyCategory;
import com.palazzisoft.gerbio.integrator.model.anymarket.AnyProduct;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
                .customize(new CustomMapper<>() {
                    @Override
                    public void mapAtoB(Product product, AnyProduct anyProduct, MappingContext context) {
                        super.mapAtoB(product, anyProduct, context);
                        double price = product.getPrecio();
                        BigDecimal bd = new BigDecimal(price).setScale(2, RoundingMode.HALF_UP);

                        anyProduct.getSkus().iterator().next().setSellPrice(bd.doubleValue());
                        anyProduct.getSkus().iterator().next().setPrice(bd.doubleValue());
                    }
                })
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
