package com.palazzisoft.gerbio.integrator.controller;

import com.palazzisoft.gerbio.integrator.alta.OPedido;
import com.palazzisoft.gerbio.integrator.alta.PedidoRequest;
import com.palazzisoft.gerbio.integrator.alta.ArrayOfProducto;
import com.palazzisoft.gerbio.integrator.alta.Producto;
import com.palazzisoft.gerbio.integrator.catalogo.Product;
import com.palazzisoft.gerbio.integrator.model.IntegratorError;
import com.palazzisoft.gerbio.integrator.model.anymarket.AnyCallbackNotification;
import com.palazzisoft.gerbio.integrator.model.anymarket.AnyOrder;
import com.palazzisoft.gerbio.integrator.service.IntegratorErrorService;
import com.palazzisoft.gerbio.integrator.service.anymarket.OrderService;
import com.palazzisoft.gerbio.integrator.service.mg.MGWebService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/order")
@Slf4j
public class CallbackController {

    private final OrderService orderService;
    private final MGWebService mgWebService;
    private final String clienteId;
    private final String tienda;
    private final IntegratorErrorService integratorErrorService;

    public CallbackController(final OrderService orderService, final MGWebService mgWebService,
                              final @Value("${mg.clientId}") String clienteId, final @Value("${mg.tienda}") String tienda,
                              final IntegratorErrorService integratorErrorService) {
        this.integratorErrorService = integratorErrorService;
        this.orderService = orderService;
        this.mgWebService = mgWebService;
        this.clienteId = clienteId;
        this.tienda = tienda;
    }

    @PostMapping
    public ResponseEntity<AnyOrder> get(@RequestBody AnyCallbackNotification notification) {
        log.info("Recibo {}", notification);

        if (notification.getType().equals("ORDER")) {
            try {
                Long orderId = NumberUtils.toLong(notification.getContent().getId());
                log.info("Procesando Orden con id {} ", orderId);

                AnyOrder order = orderService.getById(orderId);

                if (order.getStatus().equals("PAID_WAITING_SHIP")) {
                    order = orderService.saveOrder(order);
                    log.info("Order con id {} guardada en base ", order.getId());

                    OPedido oPedido = createPedido(order);
                    PedidoRequest pedidoRequest = mgWebService.notifyOrderInMG(oPedido);

                    // pedido exitoso
                    if (pedidoRequest != null && pedidoRequest.getResult() == 0) {
                        log.info("Response :" + pedidoRequest.getResult() + " , Message : " + pedidoRequest.getPedidoResponse().getNumNdp());
                    }
                    else {
                        log.error("Error notificando a MG sobre el producto {} ", order.getId());
                        integratorErrorService.saveError(IntegratorError.builder()
                                .className(this.getClass().getName())
                                .errorMessage("Error Notifying Order to MG")
                                .timestamp(LocalDateTime.now())
                                .stackTrace("")
                                .type(IntegratorError.ErrorType.IMPORT)
                                .build());
                    }
                }
                else {
                    log.info("Order {} todavía no paga y con status {} ", order.getId(),  order.getStatus());
                }

            }
            catch (Exception e) {
                log.error("Error retrieving order for {} ", notification);
                integratorErrorService.saveError(IntegratorError.builder()
                        .className(this.getClass().getName())
                        .errorMessage("Error Retrieving Order")
                        .timestamp(LocalDateTime.now())
                        .stackTrace(e.getCause().toString())
                        .type(IntegratorError.ErrorType.ORDER)
                        .build());
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
        ids.setTienda(tienda);

        OPedido pedido = new OPedido();
        pedido.setMoneda("PES");
        pedido.setIDs(ids);
        pedido.setTipoEnvio("RETIRA");
        pedido.setObservacion("Retira en MG");
        pedido.setSucursal("");
        pedido.setProductos(productos);
        pedido.setUFI(ufi);
        pedido.setObservacionDespacho("Retira en MG");

        return pedido;
    }
}