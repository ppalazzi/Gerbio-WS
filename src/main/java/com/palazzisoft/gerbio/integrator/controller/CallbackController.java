package com.palazzisoft.gerbio.integrator.controller;

import com.palazzisoft.gerbio.integrator.catalogo.*;
import com.palazzisoft.gerbio.integrator.model.anymarket.*;
import com.palazzisoft.gerbio.integrator.service.anymarket.OrderService;
import com.palazzisoft.gerbio.integrator.service.mg.MGWebService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping(value = "/order")
@Slf4j
public class CallbackController {

    private final OrderService orderService;
    private final MGWebService mgWebService;
    private final String clienteId;

    public CallbackController(final OrderService orderService, final MGWebService mgWebService,
                              final @Value("${mg.clientId}") String clienteId) {
        this.orderService = orderService;
        this.mgWebService = mgWebService;
        this.clienteId = clienteId;
    }

    @PostMapping
    public ResponseEntity<AnyOrder> get(@RequestBody AnyCallbackNotification notification) {
        log.info("Recibo {}", notification);

        if (notification.getType().equals("ORDER")) {
            try {
                Long orderId = NumberUtils.toLong(notification.getContent().getId());
                AnyOrder order = orderService.getById(orderId);

                // TODO notify MG previous creation of OPedido
                OPedido pedido = createPedido(order);
                mgWebService.notifyOrderInMG(pedido);
            }
            catch (Exception e) {
                log.error("Error retrieving order for {} ", notification);
            }
        }

        return ResponseEntity.ok(null);
    }

    private OPedido createPedido(AnyOrder order) {
        List<Product> productsMG = mgWebService.getCatalog().getListProducts().getProduct();

        ArrayOfProducto productos = new ArrayOfProducto();

        order.getItems().forEach(item -> {
            Optional<Product> productOptional = productsMG.stream()
                    .filter(p -> p.getPartNumber().equals(item.getSku().getPartnerId())).findFirst();

            if (productOptional.isPresent()) {
                Product product = productOptional.get();

                Producto producto = new Producto();
                producto.setPartNumber(product.getPartNumber());
                producto.setCantidad(item.getAmount().shortValue());

                productos.getProducto().add(producto);
            }
        });

        // TODO completar bien estos datos
        OPedido.UFI ufi = new OPedido.UFI();
        ufi.setNombre(order.getAccountName());
        ufi.setDocumento(order.getMarketPlace());
        ufi.setEmpresa(order.getMarketPlace());

        OPedido.IDs ids = new OPedido.IDs();
        ids.setCliente(clienteId);
        ids.setOrigen("TIENDA");
        ids.setID(order.getId().toString());
        ids.setTienda(""); // TODO debemos completar este dato

        OPedido pedido = new OPedido();
        pedido.setMoneda("PES");
        pedido.setIDs(ids);
        pedido.setTipoEnvio("RETIRA");
        pedido.setObservacion("Retira en MG");
        pedido.setSucursal("");
        pedido.setProductos(productos);
        pedido.setUFI(ufi);

        return pedido;
    }
}