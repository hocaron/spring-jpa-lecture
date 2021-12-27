package jpabook.jpashop.service;

import exception.NotEnoughStockException;
import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.OrderRepository;
import org.aspectj.weaver.ast.Not;
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
        Member member = createMember();

        Book book = createBook(1000, 100);

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
        Member member = createMember();
        Book book = createBook(1000, 100);

        int orderCount = 20;

        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //when
        orderService.cancelOrder(orderId);

        //then
        Order getOrder = orderRepository.findOne(orderId);
        assertEquals(100, book.getStockQuantity());
        assertEquals(OrderStatus.CANCEL, getOrder.getStatus());
    }

    @Test
    public void 상품주문_재고수량초과() throws Exception{
        //given
        Member member = createMember();
        Item item = createBook(100, 10);

        //when
        int orderCount = 11;
        //then
        NotEnoughStockException notEnoughStockException = assertThrows(NotEnoughStockException.class,
                () -> orderService.order(member.getId(), item.getId(), orderCount));
    }

    private Book createBook(int price, int stockQuantity) {
        Book book = new Book();
        book.setName("book1");
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("test1");
        member.setAddress(new Address("서울", "테스트", "0"));
        em.persist(member);
        return member;
    }
}
