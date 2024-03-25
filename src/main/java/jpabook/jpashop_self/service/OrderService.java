package jpabook.jpashop_self.service;

import jpabook.jpashop_self.domain.*;
import jpabook.jpashop_self.repository.ItemJpaRepository;
import jpabook.jpashop_self.repository.MemberRepository;
import jpabook.jpashop_self.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemJpaRepository itemJpaRepository;

    // 주문
    @Transactional
    public Long order(Long memberId,Long itemId,int count) {
        Member member = memberRepository.findMember(memberId);
        Item item = itemJpaRepository.findOne(itemId);

        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        Order order = Order.createOrder(member, delivery, orderItem);

        orderRepository.save(order);
        return order.getId();
    }

    // 취소
    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findOne(orderId);
        order.cancel();
    }

    // 검색
    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAllByString(orderSearch);
    }


}
