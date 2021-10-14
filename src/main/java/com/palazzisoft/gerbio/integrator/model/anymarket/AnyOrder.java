package com.palazzisoft.gerbio.integrator.model.anymarket;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity(name = "Order")
public class AnyOrder {

    @Id
    private Long id;

    private String accountName;
    private String marketPlaceId;
    private String marketPlaceNumber;
    private String marketPlace;
    private LocalDateTime createdAt;
    private LocalDateTime paymentDate;
    private String transmissionStatus;
    private String status;
    private String marketPlaceStatus;
    private double gross;
    private double total;

    @OneToMany(cascade = CascadeType.ALL)
    private List<AnyOrderItem> items;
}
