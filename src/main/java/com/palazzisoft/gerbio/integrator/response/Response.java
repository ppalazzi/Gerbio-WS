package com.palazzisoft.gerbio.integrator.response;

import com.palazzisoft.gerbio.integrator.model.anymarket.Link;
import com.palazzisoft.gerbio.integrator.model.anymarket.Page;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> {

    private List<Link> links;
    private List<T> content;
    private Page page;
}
