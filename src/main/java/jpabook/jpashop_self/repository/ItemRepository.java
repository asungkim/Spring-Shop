package jpabook.jpashop_self.repository;

import jpabook.jpashop_self.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
