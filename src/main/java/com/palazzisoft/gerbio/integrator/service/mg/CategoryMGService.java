package com.palazzisoft.gerbio.integrator.service.mg;

import com.palazzisoft.gerbio.integrator.catalogo.CategoryRequest;
import com.palazzisoft.gerbio.integrator.model.anymarket.AnyCategory;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CategoryMGService {

    private final MGWebService mgWebService;
    private final MapperFacade mapper;

    public CategoryMGService(final MGWebService mgWebService, final MapperFacade mapper) {
        this.mgWebService = mgWebService;
        this.mapper = mapper;
    }

    public List<AnyCategory> buildAnyCategory() {
        List<AnyCategory> anyCategories = new ArrayList<>();

        CategoryRequest categoryRequest = mgWebService.getCategories();
        categoryRequest.getListCategories().getCategory().forEach(c -> {
            AnyCategory category = mapper.map(c, AnyCategory.class);
            anyCategories.add(category);
        });

        return anyCategories;
    }
}
