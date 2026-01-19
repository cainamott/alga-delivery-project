package io.github.cainamott.delivery.tracking.infrastructure.event;

import io.github.cainamott.delivery.tracking.domain.events.DeliveryFulfilledEvent;
import io.github.cainamott.delivery.tracking.domain.events.DeliveryPickedUpEvent;
import io.github.cainamott.delivery.tracking.domain.events.DeliveryPlacedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static io.github.cainamott.delivery.tracking.infrastructure.kafka.KafkaTopicConfig.deliveryEventsTopicName;

@Component
@Slf4j
@RequiredArgsConstructor
public class DeliveryDomainEventHandler {

    private IntegrationEventPublisher integrationEventPublisher;

    @EventListener
    public void handle(DeliveryPlacedEvent event){
        log.info(event.toString());
        integrationEventPublisher.publish(event, event.getDeliveryId().toString(), deliveryEventsTopicName);
    }

    @EventListener
    public void handle(DeliveryPickedUpEvent event){

        integrationEventPublisher.publish(event, event.getDeliveryId().toString(), deliveryEventsTopicName);
    }

    @EventListener
    public void handle(DeliveryFulfilledEvent event){

        integrationEventPublisher.publish(event, event.getDeliveryId().toString(), deliveryEventsTopicName);
    }
}
