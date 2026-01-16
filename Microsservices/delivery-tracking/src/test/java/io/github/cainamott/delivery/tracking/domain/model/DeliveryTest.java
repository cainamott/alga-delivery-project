package io.github.cainamott.delivery.tracking.domain.model;

import io.github.cainamott.delivery.tracking.domain.exception.DomainException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class DeliveryTest {

    @Test
    public void shouldChangeToPlaced(){
        Delivery delivery = Delivery.draft();
        delivery.editPreparationDetails(createValidPreparationDetails());
        delivery.place();

        assertEquals(DeliveryStatus.WAITING_FOR_COURIER, delivery.getDeliveryStatus());
        assertNotNull(delivery.getPlacedAt());
    }

    @Test
    public void shouldNotPlace(){
        Delivery delivery = Delivery.draft();

        assertThrows(DomainException.class, () -> {
            delivery.place();
        });

        assertEquals(DeliveryStatus.DRAFT, delivery.getDeliveryStatus());
        assertNull(delivery.getPlacedAt());
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