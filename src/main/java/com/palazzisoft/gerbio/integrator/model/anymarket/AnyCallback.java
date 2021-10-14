package com.palazzisoft.gerbio.integrator.model.anymarket;

import lombok.*;

@Builder
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnyCallback {

    private Long id;
    private String url;
    private String token;
    private boolean orderActive;
    private boolean productActive;
    private boolean transmissionActive;
    private boolean questionActive;
}
