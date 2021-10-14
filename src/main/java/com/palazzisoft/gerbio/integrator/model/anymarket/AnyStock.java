package com.palazzisoft.gerbio.integrator.model.anymarket;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Builder
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AnyStock {

    private Long id;
    private String partnerId;
    private Integer quantity;
    private double cost;
    private double additionalTime;
    private Integer stockLocalId;
}
