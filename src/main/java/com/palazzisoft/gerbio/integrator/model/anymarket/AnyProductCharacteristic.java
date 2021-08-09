package com.palazzisoft.gerbio.integrator.model.anymarket;

import lombok.*;

@Builder
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnyProductCharacteristic {

    private Integer index;
    private String name;
    private String value;
}
