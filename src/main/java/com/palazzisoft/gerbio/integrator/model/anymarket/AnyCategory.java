package com.palazzisoft.gerbio.integrator.model.anymarket;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Builder
@Getter @Setter @Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity(name = "Category")
public class AnyCategory {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @ManyToOne
    private AnyCategory parent;

    private String partnerId;
    private String definitionPriceScope;

}
