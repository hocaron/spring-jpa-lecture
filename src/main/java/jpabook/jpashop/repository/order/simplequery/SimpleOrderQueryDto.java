package jpabook.jpashop.repository.order.simplequery;

import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SimpleOrderQueryDto {

    private Long id;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;

    public SimpleOrderQueryDto(Long orderId, String name, LocalDateTime localDateTime, OrderStatus orderStatus) {
        this.id = orderId;
        this.name = name;
        this.orderDate = localDateTime;
        this.orderStatus = orderStatus;
    }
}
