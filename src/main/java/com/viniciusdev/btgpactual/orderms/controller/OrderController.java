package com.viniciusdev.btgpactual.orderms.controller;

import com.viniciusdev.btgpactual.orderms.controller.dto.ApiResponse;
import com.viniciusdev.btgpactual.orderms.controller.dto.OrderResponse;
import com.viniciusdev.btgpactual.orderms.controller.dto.PaginationResponse;
import com.viniciusdev.btgpactual.orderms.service.OrderService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class OrderController {

    public final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/test")
    public String test(){
        return "Funcionando...";
    }

    @GetMapping("/customers/{customerId}/orders")
    public ResponseEntity<ApiResponse<OrderResponse>> listOrders(
            @PathVariable("customerId") Long customerId,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "page", defaultValue = "10") Integer pageSize){

        var pageResponse = orderService.findAllByCustomerId(
                customerId,
                PageRequest.of(page, pageSize)
        );

        var totalOnOrders = orderService.findTotal(customerId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        Map.of("totalOnOrders", totalOnOrders),
                        pageResponse.getContent(),
                        PaginationResponse.fromPage(pageResponse)
                )
        );
    }

}
