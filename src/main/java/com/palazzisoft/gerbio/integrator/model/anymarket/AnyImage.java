package com.palazzisoft.gerbio.integrator.model.anymarket;

import lombok.*;

@Builder
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnyImage {

    private Long id;
    private Integer index;
    private boolean main;
    private String url;
    private String thumbnailUrl;
    private String lowResolutionUrl;
    private String standardUrl;
    private String originalImage;
    private String variation;
    private String status;
    private String statusMessage;
    private Integer standardWidth;
    private Integer standardHeight;
    private Integer originalWidth;
    private Integer originalHeight;
}
