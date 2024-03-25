package jpabook.jpashop_self.service;

import jpabook.jpashop_self.domain.Item;
import jpabook.jpashop_self.repository.ItemJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
public class ItemService {
    private final ItemJpaRepository itemJpaRepository;

    @Transactional
    public void saveItem(Item item) {
        itemJpaRepository.save(item);
    }

    @Transactional
    public void updateItem(Long itemId,String name,int price,int stockQuantity) {
        Item item = itemJpaRepository.findOne(itemId);
        item.setStockQuantity(stockQuantity);
        item.setName(name);
        item.setPrice(price);
    }

    public Item findOne(Long itemId) {
        return itemJpaRepository.findOne(itemId);
    }

    public List<Item> findItems() {
        return itemJpaRepository.findAll();
    }


}
