package jpabook.jpashop_self.domain;

import jakarta.persistence.*;
import jpabook.jpashop_self.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter @Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Item {

    @Id @GeneratedValue
    @Column(name = "ITEM_ID")
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;


    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity) {
        int restQuantity = this.stockQuantity - quantity;
        if (restQuantity < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity -= quantity;
    }



}

