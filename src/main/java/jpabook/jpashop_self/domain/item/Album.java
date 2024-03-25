package jpabook.jpashop_self.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jpabook.jpashop_self.domain.Item;
import lombok.Getter;

@Entity
@DiscriminatorValue("A")
@Getter
public class Album extends Item {
    private String artist;
    private String etc;
}
