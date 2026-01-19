package io.github.cainamott.courier.management.infrastructure.event;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class DeliveryPlacedIntegrationEvent {

    private OffsetDateTime occuredAt;
    private UUID deliveryId;
}
