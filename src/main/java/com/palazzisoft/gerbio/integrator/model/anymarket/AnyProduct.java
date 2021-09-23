package com.palazzisoft.gerbio.integrator.model.anymarket;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter @Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity(name = "Product")
public class AnyProduct {

    @Id
    private Long id;
    private String title;
    private String description;

    @ManyToOne
    private AnyCategory category;

    @ManyToOne
    private AnyBrand brand;

    @ManyToOne
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

    @ManyToMany(cascade = CascadeType.ALL)
    private List<AnyProductCharacteristic> characteristics;

    @OneToMany(cascade = CascadeType.ALL)
    private List<AnyImage> images;

    @OneToMany(cascade = CascadeType.ALL)
    private List<AnySku> skus = new ArrayList<>();
}
