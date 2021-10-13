package com.palazzisoft.gerbio.integrator.model.anymarket;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity(name = "ProductCharacteristic")
public class AnyProductCharacteristic {

    @Id
    private Integer index;

    private String name;
    private String value;
}
