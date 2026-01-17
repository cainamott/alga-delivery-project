package io.github.cainamott.delivery.tracking.domain.service;

import java.math.BigDecimal;
import java.time.Duration;

public interface CourierPayoutCalculationService {
    BigDecimal calculatePayout(Double distanceKm, Duration estimatedTime);
}
