package com.palazzisoft.gerbio.integrator.model.anymarket;

import lombok.*;

@Builder
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnyBrand {

    private Long id;
    private String name;
    private String reducedName;
    private String partnerId;
}
