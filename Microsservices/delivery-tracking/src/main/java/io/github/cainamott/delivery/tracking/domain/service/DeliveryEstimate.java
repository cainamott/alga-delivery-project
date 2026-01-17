package io.github.cainamott.delivery.tracking.domain.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Duration;

@Getter
@AllArgsConstructor
public class DeliveryEstimate {

    private Duration estimateTime;
    private Double distanceKm;
}
