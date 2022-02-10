package com.palazzisoft.gerbio.integrator.model.anymarket;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

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

    @Column(columnDefinition = "TEXT")
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
    private double height = 1d;
    private double width = 1d;
    private double weight = 1d;
    private double length = 1d;
    private double priceFactor;
    private boolean calculatedPrice;
    private String definitionPriceScope;
    private boolean hasVariations;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<AnyProductCharacteristic> characteristics;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<AnyImage> images;

    @OneToMany(cascade = CascadeType.ALL)
    private List<AnySku> skus = new ArrayList<>();
}
