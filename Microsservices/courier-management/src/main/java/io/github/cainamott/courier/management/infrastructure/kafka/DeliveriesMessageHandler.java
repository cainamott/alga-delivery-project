package io.github.cainamott.courier.management.infrastructure.kafka;

import io.github.cainamott.courier.management.domain.service.CourierDeliveryService;
import io.github.cainamott.courier.management.infrastructure.event.DeliveryFulfilledIntegrationEvent;
import io.github.cainamott.courier.management.infrastructure.event.DeliveryPlacedIntegrationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = {"deliveries.v1.events", }, groupId = "courier-management")
@Slf4j
@RequiredArgsConstructor
public class DeliveriesMessageHandler {

    private CourierDeliveryService service;

    @KafkaHandler(isDefault = true)
    public void defaultHandler(@Payload Object obj){
        log.info("Default Handler: {}", obj);
    }

    @KafkaHandler
    public void handle(@Payload DeliveryPlacedIntegrationEvent event){
        log.info("Received: {}", event);
        service.assign(event.getDeliveryId());
    }

    @KafkaHandler
    public void handle(@Payload DeliveryFulfilledIntegrationEvent event){
        log.info("Received: {}", event);
        service.fulfill(event.getDeliveryId());
    }
}
