package com.palazzisoft.gerbio.integrator.model.anymarket;

import lombok.*;

@Builder
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnyVariationType {

    private Long id;
    private String name;
    private boolean visualVariation;
}
