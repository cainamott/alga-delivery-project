package io.github.cainamott.delivery.tracking.infrastructure.fake;

import io.github.cainamott.delivery.tracking.domain.model.ContactPoint;
import io.github.cainamott.delivery.tracking.domain.service.DeliveryEstimate;
import io.github.cainamott.delivery.tracking.domain.service.DeliveryTimeEstimationService;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class DeliveryTimeEstimationServiceFakeImpl implements DeliveryTimeEstimationService {
    @Override
    public DeliveryEstimate estimate(ContactPoint sender, ContactPoint receiver) {
        return new DeliveryEstimate(Duration.ofHours(3), Double.valueOf(4.1));
    }
}
