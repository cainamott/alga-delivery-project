package io.github.cainamott.delivery.tracking.domain.service;

import io.github.cainamott.delivery.tracking.domain.model.ContactPoint;

public interface DeliveryTimeEstimationService {

    DeliveryEstimate estimate(ContactPoint sender, ContactPoint recipient);
}
