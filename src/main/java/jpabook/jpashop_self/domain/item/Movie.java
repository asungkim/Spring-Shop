package jpabook.jpashop_self.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jpabook.jpashop_self.domain.Item;
import lombok.Getter;

@Entity
@Getter
@DiscriminatorValue("M")
public class Movie extends Item {
    private String actor;
    private String director;
}

