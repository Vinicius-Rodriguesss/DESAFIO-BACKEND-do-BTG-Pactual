package com.viniciusdev.btgpactual.orderms.listener;

import com.viniciusdev.btgpactual.orderms.listener.dto.OrderCreatedEvent;
import com.viniciusdev.btgpactual.orderms.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import static com.viniciusdev.btgpactual.orderms.config.RabbitMqConfig.ORDER_CREATED_QUEUE;

@Component
public class OrderCreatedListener {

    private Logger logger = LoggerFactory.getLogger(OrderCreatedListener.class);

    public final OrderService orderService;

    public OrderCreatedListener(OrderService orderService) {
        this.orderService = orderService;
    }

    // O método onde irá fazer o consumo da fila do rabbitMQ
    @RabbitListener(queues = ORDER_CREATED_QUEUE)
    // dentro de <> é payLoad
    public void listen(Message<OrderCreatedEvent> message){
        logger.info("Message consumed: {}", message);
        orderService.save(message.getPayload());
    }
}
