package com.palazzisoft.gerbio.integrator.catalogo;

import com.palazzisoft.gerbio.integrator.model.anymarket.AnyCallback;
import com.palazzisoft.gerbio.integrator.service.anymarket.CallbackService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class AnyCallbackTest {

    @Autowired
    private CallbackService callbackService;

    @Test
    void creatingANewCAllback() {
        AnyCallback callback = AnyCallback.builder()
                .orderActive(true)
                .productActive(true)
                .questionActive(false)
                .url("http://www.gerbio-callbakk")
                .transmissionActive(false)
                .build();

        AnyCallback expected = callbackService.save(callback);
        Assertions.assertThat(expected).isNotNull();
    }

    @Test
    void updateCallback() {
        AnyCallback callback = callbackService.getById(6524L);
        callback.setUrl("3.144.79.37:8089");

        callbackService.update(null, callback.getId());
    }

    @Test
    void deleteCallback() {
        AnyCallback callback = callbackService.getById(6524L);

        callbackService.delete(callback.getId());
    }
}
