package io.github.cainamott.delivery.tracking.domain.repository;

import io.github.cainamott.delivery.tracking.domain.model.ContactPoint;
import io.github.cainamott.delivery.tracking.domain.model.Delivery;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureDataJpa
class DeliveryRepositoryTest {

    @Autowired
    DeliveryRepository deliveryRepository;

    @Test
    public void shouldPersist(){
        Delivery delivery = Delivery.draft();

        delivery.editPreparationDetails(createValidPreparationDetails());

        delivery.addItem("Item1", 2);
        delivery.addItem("item2", 4);

        deliveryRepository.saveAndFlush(delivery);

        Delivery persistedDelivery = deliveryRepository.findById(delivery.getId()).orElseThrow();

        assertEquals(2, persistedDelivery.getItemList().size());
    }

    private Delivery.PreparationDetails createValidPreparationDetails() {

        ContactPoint sender = ContactPoint
                .builder()
                .street("Rua 1")
                .number("54")
                .phone("(51) 999990000")
                .name("Cainã")
                .complement("Apto 22")
                .zipCode("00000-000")
                .build();

        ContactPoint recipient = ContactPoint
                .builder()
                .street("Rua 134")
                .number("76")
                .phone("(51) 999459000")
                .name("Cainã Motta")
                .complement("Apto 243")
                .zipCode("00320-000")
                .build();

        return Delivery.PreparationDetails
                .builder()
                .sender(sender)
                .recipient(recipient)
                .distanceFee(new BigDecimal("100"))
                .courierPayout(new BigDecimal("42"))
                .expectedDurationTime(Duration.ofHours(1))
                .build();
    }
}