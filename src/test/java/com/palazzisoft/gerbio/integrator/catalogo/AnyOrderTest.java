package com.palazzisoft.gerbio.integrator.catalogo;

import com.palazzisoft.gerbio.integrator.model.anymarket.AnyOrder;
import com.palazzisoft.gerbio.integrator.service.anymarket.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@Slf4j
@ActiveProfiles("test")
public class AnyOrderTest {

    @Autowired
    private OrderService orderService;

    @Test
    public void testRetrievingAnExistingOrderFromSandbox() {
        AnyOrder order = orderService.getById(76005L);

        Assertions.assertThat(order).isNotNull();
    }
}
