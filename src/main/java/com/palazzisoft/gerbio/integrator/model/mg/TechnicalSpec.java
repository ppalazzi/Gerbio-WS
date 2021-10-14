package com.palazzisoft.gerbio.integrator.model.mg;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Builder
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TechnicalSpec {

    private String nombre;
    private String descripcion;
}
