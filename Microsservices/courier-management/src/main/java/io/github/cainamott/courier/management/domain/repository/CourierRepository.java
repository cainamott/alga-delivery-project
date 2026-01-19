package io.github.cainamott.courier.management.domain.repository;

import io.github.cainamott.courier.management.domain.model.Courier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CourierRepository extends JpaRepository<Courier, UUID> {
    Optional<Courier> findTop1ByOrderByLastFulfilledDeliveryByAsc();

    Optional<Courier> findByPendingDeliveries_id(UUID deliveryId);
}
