package com.palazzisoft.gerbio.integrator.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter @Data
public class Category {

    private Long id;
    private String name;
    private String path;
    private String partnerId;
    private String definitionPriceScope;

}
