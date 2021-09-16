package com.palazzisoft.gerbio.integrator.model.anymarket;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter @Setter @Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity(name = "Category")
public class AnyCategory {

    @Id
    private Long id;
    private String name;

    @JoinColumn(name = "id", insertable = false, updatable = false)
    @ManyToOne
    private AnyCategory parent;

    private String partnerId;
    private String definitionPriceScope;

}
