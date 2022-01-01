package jpabook.jpashop.api;


import jpabook.jpashop.domain.Order;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderSImpleApiController {

    private final OrderRepository orderRepository;

    @GetMapping("api/v1/simple-orders")
    public List<Order> orderV1() {
        return orderRepository.findAllByString(new OrderSearch());
    }
}
