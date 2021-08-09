package com.palazzisoft.gerbio.integrator.model.anymarket;

import lombok.*;

@Builder
@Getter @Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Page {

    private Integer size;
    private Integer totalElements;
    private Integer totalPages;
}
