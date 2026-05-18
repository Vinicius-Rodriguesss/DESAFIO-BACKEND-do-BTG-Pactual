package com.viniciusdev.btgpactual.orderms.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.math.BigDecimal;
import java.util.List;

@Document(collection = "tb_orders")

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity {

    @MongoId
    private long orderId;

    // ID do cliente
    @Indexed(name = "customer_id_index")
    private long customerId;
    /*
       O BigDecimal Salva o dado como string na base de dados
       Porem precisamos somar esse numero. Então precisamos converter
       para que salve essa dado em valor numerico
     */
    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal total;

    // Lista de produtos
    private List<OrderItem> orderItems;

}
