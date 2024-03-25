package jpabook.jpashop_self.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jpabook.jpashop_self.domain.Item;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("B") @Getter
@Setter
public class Book extends Item {
    private String author;
    private String isbn;
}
