package com.palazzisoft.gerbio.integrator.model.anymarket;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Builder
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity(name = "Image")
public class AnyImage {

    @Id
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
