package com.palazzisoft.gerbio.integrator.model.anymarket;

import lombok.*;

@Builder
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnyVariationValue {

    private Long id;
    private String description;
    private AnyVariationType anyVariationType;
}
