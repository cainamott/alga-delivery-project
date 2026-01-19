package io.github.cainamott.delivery.tracking.domain.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
@ToString
public class DeliveryFulfilledEvent {

    private final OffsetDateTime ocurredDate;
    private final UUID deliveryId;
}
