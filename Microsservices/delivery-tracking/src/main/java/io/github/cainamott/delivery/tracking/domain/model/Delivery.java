package io.github.cainamott.delivery.tracking.domain.model;

import io.github.cainamott.delivery.tracking.domain.events.DeliveryFulfilledEvent;
import io.github.cainamott.delivery.tracking.domain.events.DeliveryPickedUpEvent;
import io.github.cainamott.delivery.tracking.domain.events.DeliveryPlacedEvent;
import io.github.cainamott.delivery.tracking.domain.exception.DomainException;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.*;

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Delivery extends AbstractAggregateRoot<Delivery> {

    @Id
    @EqualsAndHashCode.Include
    private UUID id;

    private UUID courierId;

    private DeliveryStatus deliveryStatus;

    private OffsetDateTime placedAt;
    private OffsetDateTime assignedAt;
    private OffsetDateTime expectedDeliveryAt;
    private OffsetDateTime fulfilledAt;

    private BigDecimal distanceFee;
    private BigDecimal courierPayout;
    private BigDecimal totalCost;

    private Integer totalItens;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "zipCode", column = @Column(name = "sender_zip_code")),
            @AttributeOverride(name = "street", column = @Column(name = "sender_street")),
            @AttributeOverride(name = "number", column = @Column(name = "sender_number")),
            @AttributeOverride(name = "complement", column = @Column(name = "sender_complement")),
            @AttributeOverride(name = "name", column = @Column(name = "sender_name")),
            @AttributeOverride(name = "phone", column = @Column(name = "sender_phone"))

    })
    private ContactPoint sender;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "zipCode", column = @Column(name = "recipient_zip_code")),
            @AttributeOverride(name = "street", column = @Column(name = "recipient_street")),
            @AttributeOverride(name = "number", column = @Column(name = "recipient_number")),
            @AttributeOverride(name = "complement", column = @Column(name = "recipient_complement")),
            @AttributeOverride(name = "name", column = @Column(name = "recipient_name")),
            @AttributeOverride(name = "phone", column = @Column(name = "recipient_phone"))

    })
    private ContactPoint recipient;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "delivery")
    private List<Item> itemList = new ArrayList<>();

    public List<Item> getItemList() {
        return Collections.unmodifiableList(this.itemList);
    }

    public UUID addItem(String name, int quantity){
        Item item = new Item();
        item.brandNew(name, quantity, this);
        itemList.add(item);
        calculateTotalItems();
        return item.getId();
    }

    public void removeItem(UUID id){
        itemList.removeIf(item -> item.getId().equals(id));
        calculateTotalItems();
    }

    public void removeItens(){
        itemList.clear();
        calculateTotalItems();
    }

    public void changeItemQtd(UUID id, Integer quantity){
        Item itemModificado = getItemList().stream().filter(item -> item.getId().equals(id)).findFirst().orElseThrow();
        itemModificado.setQuantity(quantity);
        calculateTotalItems();
    }

    public void editPreparationDetails(PreparationDetails details){
        setSender(details.getSender());
        setRecipient(details.getRecipient());
        setDistanceFee(details.getDistanceFee());
        setCourierPayout(details.getCourierPayout());
        setExpectedDeliveryAt(OffsetDateTime.now().plus(details.getExpectedDurationTime()));
        setTotalCost(this.getDistanceFee().add(this.getCourierPayout()));
    }

    public void place(){

        verifyIfCanBePlaced();
        this.setDeliveryStatus(DeliveryStatus.WAITING_FOR_COURIER);
        this.setPlacedAt(OffsetDateTime.now());
        super.registerEvent(new DeliveryPlacedEvent(this.placedAt, this.getId()));
    }

    private void verifyIfCanBePlaced() {
        if(!isFilled()){
            throw new DomainException();
        }
        if(!getDeliveryStatus().equals(DeliveryStatus.DRAFT)){
            throw new DomainException();
        }
    }

    private boolean isFilled() {
        if(getSender() != null
        && getRecipient() != null
        && getTotalCost() != null){
            return true;
        }
        else return false;

    }

    public void pickUp(UUID id){

        this.setCourierId(id);
        this.setDeliveryStatus(DeliveryStatus.IN_TRANSIT);
        this.setAssignedAt(OffsetDateTime.now());
        super.registerEvent(new DeliveryPickedUpEvent(this.assignedAt, this.getId()));
    }

    public void markAsDelivered(){
        this.setDeliveryStatus(DeliveryStatus.DELIVERED);
        this.setFulfilledAt(OffsetDateTime.now());
        super.registerEvent(new DeliveryFulfilledEvent(this.fulfilledAt, this.getId()));
    }

    private void calculateTotalItems(){
        int total = getItemList().stream().mapToInt(Item::getQuantity).sum();
        setTotalItens(total);
    }

    private void verifyIfCanBeEdited(){
        if(!getDeliveryStatus().equals(DeliveryStatus.DRAFT)){
            throw new DomainException();
        }
    }

    public static Delivery draft(){
        Delivery delivery = new Delivery();
        delivery.id = UUID.randomUUID();
        delivery.deliveryStatus = DeliveryStatus.DRAFT;
        delivery.totalCost = BigDecimal.ZERO;
        delivery.setTotalItens(0);
        delivery.courierPayout = BigDecimal.ZERO;
        delivery.distanceFee = BigDecimal.ZERO;
        return delivery;
    }

    private void changeStatus(DeliveryStatus newStatus){
        if(newStatus != null && getDeliveryStatus().canNotChangeTo(newStatus)){
            throw new DomainException();
        }
         this.setDeliveryStatus(newStatus);
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class PreparationDetails{

        private ContactPoint recipient;
        private ContactPoint sender;
        private BigDecimal courierPayout;
        private BigDecimal distanceFee;
        private Duration expectedDurationTime;
    }

}
