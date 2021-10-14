package com.palazzisoft.gerbio.integrator.model.mg;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Item {

    private String articulo;
    private String partNumber;
    private String partNumberOri;
    private String estado;
    private Description description;
    private List<TechnicalSpec> technicalSpecList;
    private List<String> picturesUrls;
}
