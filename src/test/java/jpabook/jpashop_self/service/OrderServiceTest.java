package jpabook.jpashop_self.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop_self.domain.*;
import jpabook.jpashop_self.domain.item.Book;
import jpabook.jpashop_self.exception.NotEnoughStockException;
import jpabook.jpashop_self.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {
    @PersistenceContext
    EntityManager em;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 상품주문 () throws Exception {
        // given
        Member member = createMember();

        Book book = createBook("JPA", 10, 10000);

        int orderCount = 2;

        // when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
        Order order = orderRepository.findOne(orderId);


        // then
        assertEquals("상품 주문시 상태는 ORDER", OrderStatus.ORDER, order.getStatus());
        assertEquals("주문한 상품 종류 수가 정확해야 한다.",1, order.getOrderItems().size());
        assertEquals("주문 가격은 가격 * 수량이다.", 10000 * 2, order.getTotalPrice());
        assertEquals("주문 수량만큼 재고가 줄어야 한다.",8, book.getStockQuantity());
    }



    @Test(expected = NotEnoughStockException.class)
    public void 상품주문_재고수량초과 () throws Exception {
        // given
        Member member = createMember();
        Book book = createBook("JPA", 10, 10000);
        int orderCount = 11;
        // when
        orderService.order(member.getId(), book.getId(), orderCount);

        // then
        fail("재고 수량 부족으로 예외가 발생해야한다");
    }

    @Test
    public void 주문취소 () throws Exception {
        // given
        Member member = createMember();
        Item item = createBook("JPA", 10, 10000);
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        // when
        orderService.cancelOrder(orderId);

        // then
        Order getOrder = orderRepository.findOne(orderId);
        assertEquals("주문 취소시 상태는 CANCEL 이다", OrderStatus.CANCEL, getOrder.getStatus());
        assertEquals("주문이 취소된 상품은 그만큼 재고가 증가해야 한다.", 10, item.getStockQuantity());
    }

    private Book createBook(String name, int stockQuantity, int price) {
        Book book = new Book();
        book.setName(name);
        book.setStockQuantity(stockQuantity);
        book.setPrice(price);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "강가", "12345"));
        em.persist(member);
        return member;
    }
}