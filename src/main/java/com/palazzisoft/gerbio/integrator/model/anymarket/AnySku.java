package com.palazzisoft.gerbio.integrator.model.anymarket;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;


@Builder
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AnySku {

    private Long id;
    private String title;
    private String partnerId;
    private String ean;
    private double amount;
    private double additionalTime;
    private double price;
    private double sellPrice;
    //private List<AnyVariationValue> variations;
    private List<Variation> variations;

}
