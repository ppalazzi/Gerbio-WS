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
public class Description {

    private String short_;
    private String maker;
    private String product;

    public String getDescriptionPretty() {
        return short_ + System.lineSeparator() + maker;
    }
}
