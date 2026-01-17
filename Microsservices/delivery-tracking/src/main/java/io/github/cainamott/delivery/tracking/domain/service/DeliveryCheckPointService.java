package io.github.cainamott.delivery.tracking.domain.service;

import io.github.cainamott.delivery.tracking.domain.exception.DomainException;
import io.github.cainamott.delivery.tracking.domain.model.Delivery;
import io.github.cainamott.delivery.tracking.domain.repository.DeliveryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class DeliveryCheckPointService {

    private DeliveryRepository repository;



    public void place(UUID deliveryId){

        Delivery delivery = repository.findById(deliveryId).orElseThrow(
                () -> new DomainException()
        );
        delivery.place();
        repository.saveAndFlush(delivery);
    }

    public void pickup(UUID deliveryId, UUID courierId){

        Delivery delivery = repository.findById(deliveryId).orElseThrow(
                () -> new DomainException()
        );
        delivery.pickUp(courierId);
        repository.saveAndFlush(delivery);
    }

    public void complete(UUID deliveryId){

        Delivery delivery = repository.findById(deliveryId).orElseThrow(
                () -> new DomainException()
        );
        delivery.markAsDelivered();
        repository.saveAndFlush(delivery);
    }

}
