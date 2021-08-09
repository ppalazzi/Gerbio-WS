package com.palazzisoft.gerbio.integrator.model.anymarket;

import lombok.*;

@Builder
@Getter @Setter @Data
@AllArgsConstructor
@NoArgsConstructor
public class Link {

    private String rel;
    private String href;
}
