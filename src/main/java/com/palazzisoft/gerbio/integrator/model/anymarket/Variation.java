package com.palazzisoft.gerbio.integrator.model.anymarket;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Variation {

    @JsonProperty("Voltage")
    private String Voltage;
    //private String Color;
}
