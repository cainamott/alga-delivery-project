package io.github.cainamott.courier.management.domain.service;

import io.github.cainamott.courier.management.domain.model.Courier;
import io.github.cainamott.courier.management.domain.repository.CourierRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CourierDeliveryService {

    @Autowired
    private final CourierRepository repository;

    public void assign(UUID deliveryId){

        Courier courier = repository.findTop1ByOrderByLastFulfilledDeliveryByAsc().orElseThrow();

        courier.assign(deliveryId);
        repository.saveAndFlush(courier);

        log.info("Courier {}", courier.getId(), "Assigned {}", deliveryId);
    }

    public void fulfill(UUID deliveryId){

        Courier courier = repository.findByPendingDeliveries_id(deliveryId).orElseThrow();
        courier.fulfill(deliveryId);
        repository.saveAndFlush(courier);
    }
}
