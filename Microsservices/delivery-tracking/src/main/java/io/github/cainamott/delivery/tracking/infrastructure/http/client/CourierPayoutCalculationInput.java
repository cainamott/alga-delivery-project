package io.github.cainamott.delivery.tracking.infrastructure.http.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CourierPayoutCalculationInput {

    private Double distanceInKm;
}
