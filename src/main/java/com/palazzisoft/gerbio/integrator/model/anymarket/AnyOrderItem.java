package com.palazzisoft.gerbio.integrator.model.anymarket;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Builder
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity(name = "OrderItem")
public class AnyOrderItem {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private AnyProduct product;

    @ManyToOne
    private AnySku sku;

    private Integer amount;
    private Integer unit;

}
