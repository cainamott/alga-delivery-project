package io.github.cainamott.delivery.tracking.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.util.UUID;


@Getter
@Setter(AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Item {

    @Id
    @EqualsAndHashCode.Include
    private UUID id;
    private String name;
    private Integer quantity;

    @ManyToOne(optional = false)
    @Getter(AccessLevel.PRIVATE)
    private Delivery delivery;

    public Item brandNew(String name, Integer quantity, Delivery delivery){

        Item item = new Item();
        item.id = UUID.randomUUID();
        item.name = name;
        item.setQuantity(quantity);
        item.setDelivery(delivery);
        return item;
    }
}
