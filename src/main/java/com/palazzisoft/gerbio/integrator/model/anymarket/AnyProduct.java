package com.palazzisoft.gerbio.integrator.model.anymarket;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Builder
@Getter @Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AnyProduct {

    private Long id;
    private String title;
    private String description;
    private AnyCategory category;
    private AnyBrand brand;
    private AnyOrigin origin;
    private String model;
    private String videoUrl;
    private String gender;
    private Integer warrantyTime;
    private String warrantyText;
    private double height;
    private double width;
    private double weight;
    private double length;
    private double priceFactor;
    private boolean calculatedPrice;
    private String definitionPriceScope;
    private boolean hasVariations;
    private List<AnyProductCharacteristic> characteristics;
    private List<AnyImage> images;
    private List<AnySku> skus;
}
