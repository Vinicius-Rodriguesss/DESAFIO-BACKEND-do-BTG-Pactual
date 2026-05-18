package com.viniciusdev.btgpactual.orderms.service;

import com.viniciusdev.btgpactual.orderms.controller.dto.OrderResponse;
import com.viniciusdev.btgpactual.orderms.entity.OrderEntity;
import com.viniciusdev.btgpactual.orderms.entity.OrderItem;
import com.viniciusdev.btgpactual.orderms.listener.dto.OrderCreatedEvent;
import com.viniciusdev.btgpactual.orderms.repository.OrderRepository;
import org.bson.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final MongoTemplate mongoTemplate;

    public OrderService(OrderRepository orderRepository, MongoTemplate mongoTemplate) {
        this.orderRepository = orderRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public void save(OrderCreatedEvent event){
        var entity = new OrderEntity();
        entity.setOrderId(event.codigoPedido());
        entity.setCustomerId(event.codigoCliente());
        
        entity.setTotal(getTotal(event));

        entity.setOrderItems(event.itens().stream()
                .map(i -> new OrderItem(i.produto(), i.quantidade(), i.price()))
                .toList());

        orderRepository.save(entity);
    }

    public Page<OrderResponse> findAllByCustomerId(Long customerId, PageRequest pageRequest){
        var orders =  orderRepository.findAllByCustomerId(customerId, pageRequest);

        return orders.map(OrderResponse::fromEntity);
    }

    public BigDecimal findTotal(Long customerId){
        var aggregations = newAggregation(
                match(Criteria.where("customerId").is(customerId)),
                group().sum("total").as("total")
        );

        var response = mongoTemplate.aggregate(
                aggregations,
                "tb_orders",
                Document.class
        );

        var result = response.getUniqueMappedResult();

        if(result == null){
            return BigDecimal.ZERO;
        }

        return new BigDecimal(result.get("total").toString());
    }

    private BigDecimal getTotal(OrderCreatedEvent event) {
        return event.itens()
                .stream()
                .map(i -> i.price().multiply(BigDecimal.valueOf(i.quantidade())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }
}
