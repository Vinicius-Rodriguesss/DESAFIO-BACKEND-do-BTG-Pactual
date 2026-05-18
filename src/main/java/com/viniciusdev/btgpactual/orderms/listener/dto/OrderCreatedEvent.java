package com.viniciusdev.btgpactual.orderms.listener.dto;

import java.util.List;

public record OrderCreatedEvent(
        long codigoPedido,
        long codigoCliente,
        List<OrderItemEvent> itens) {
}
