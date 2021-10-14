package com.palazzisoft.gerbio.integrator.service.anymarket;

import com.palazzisoft.gerbio.integrator.model.anymarket.AnyCallback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


@Service
@Slf4j
public class CallbackService extends AbstractService<AnyCallback> {

    private final String URL_BASE = "/v2/callbacks";

    public CallbackService(WebClient webClient) {
        super(webClient, AnyCallback.class);
    }

    @Override
    protected String getURLBase() {
        return URL_BASE;
    }
}
