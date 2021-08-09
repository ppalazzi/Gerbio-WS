package com.palazzisoft.gerbio.integrator.model.anymarket;

import lombok.*;

@Builder
@Getter @Setter @Data
@AllArgsConstructor
@NoArgsConstructor
public class AnyCategory {

    private Long id;
    private String name;
    private String path;
    private String partnerId;
    private String definitionPriceScope;

}
