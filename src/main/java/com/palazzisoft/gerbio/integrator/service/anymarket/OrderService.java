package com.palazzisoft.gerbio.integrator.service.anymarket;

import com.palazzisoft.gerbio.integrator.model.anymarket.AnyOrder;
import com.palazzisoft.gerbio.integrator.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class OrderService extends AbstractService<AnyOrder> {

    private static final String URL_BASE = "/v2/orders";

    private final OrderRepository orderRepository;

    public OrderService(WebClient webClient, final OrderRepository orderRepository) {
        super(webClient, AnyOrder.class);
        this.orderRepository = orderRepository;
    }

    @Override
    protected String getURLBase() {
        return URL_BASE;
    }
}
