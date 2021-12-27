package jpabook.jpashop.service;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {
        //given
        Member member = new Member();
        member.setName("test1");
        member.setAddress(new Address("서울", "테스트", "0"));
        em.persist(member);

        Book book = new Book();
        book.setName("book1");
        book.setPrice(1000);
        book.setStockQuantity(100);
        em.persist(book);

        int orderCount = 2;

        //when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //then
        Order getOrder = orderRepository.findOne(orderId);
        List<OrderItem> orderItems = getOrder.getOrderItems();
        assertThat(OrderStatus.ORDER).isEqualTo(getOrder.getStatus());
        Assertions.assertEquals(1000 * orderCount, getOrder.getTotalPrice());
        assertEquals(98, book.getStockQuantity());

    }

    @Test
    public void 주문취소() {
        //given

        //when

        //then
    }

    @Test
    public void 상품주문_재고수량초과() {
        //given

        //when

        //then
    }
}
