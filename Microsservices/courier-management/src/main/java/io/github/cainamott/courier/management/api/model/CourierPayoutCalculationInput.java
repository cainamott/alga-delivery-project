package io.github.cainamott.courier.management.api.model;

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
