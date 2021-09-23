package com.palazzisoft.gerbio.integrator.service.anymarket;

import com.palazzisoft.gerbio.integrator.model.anymarket.AnyOrigin;
import com.palazzisoft.gerbio.integrator.repository.OriginRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class OriginService {

    private final OriginRepository originRepository;

    public OriginService(final OriginRepository originRepository) {
        this.originRepository = originRepository;
    }

    public void storeInitOrigins() {
        originRepository.save(AnyOrigin.builder().id(0l).description("Domestico").build());
        originRepository.save(AnyOrigin.builder().id(1l).description("Extranjero").build());
        originRepository.save(AnyOrigin.builder().id(2l).description("Extranjero").build());
        originRepository.save(AnyOrigin.builder().id(3l).description("Nacional").build());
        originRepository.save(AnyOrigin.builder().id(4l).description("Nacional").build());
        originRepository.save(AnyOrigin.builder().id(5l).description("Nacional").build());
        originRepository.save(AnyOrigin.builder().id(6l).description("Extranjero").build());
        originRepository.save(AnyOrigin.builder().id(7l).description("Extranjero").build());
    }

    public AnyOrigin persist(AnyOrigin origin) {
        return originRepository.save(origin);
    }

    public List<AnyOrigin> getOrigins() {
        return originRepository.findAll();
    }
}
