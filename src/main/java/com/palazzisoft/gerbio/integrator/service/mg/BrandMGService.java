package com.palazzisoft.gerbio.integrator.service.mg;

import com.palazzisoft.gerbio.integrator.catalogo.BrandRequest;
import com.palazzisoft.gerbio.integrator.model.anymarket.AnyBrand;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class BrandMGService {

    private final MGWebService mgWebService;
    private final MapperFacade mapper;

    public BrandMGService(final MGWebService mgWebService, final MapperFacade mapper) {
        this.mgWebService = mgWebService;
        this.mapper = mapper;
    }

    public List<AnyBrand> buildAnyBrands() {
        List<AnyBrand> anyBrands = new ArrayList<>();

        BrandRequest brandRequest = mgWebService.getBrands();
        brandRequest.getListBrands().getBrand().forEach(b -> {
            AnyBrand brand = mapper.map(b, AnyBrand.class);
            anyBrands.add(brand);
        });

        return anyBrands;
    }
}
