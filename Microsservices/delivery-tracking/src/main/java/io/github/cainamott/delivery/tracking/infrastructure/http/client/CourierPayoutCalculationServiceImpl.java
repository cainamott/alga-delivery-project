package io.github.cainamott.delivery.tracking.infrastructure.http.client;

import io.github.cainamott.delivery.tracking.domain.service.CourierPayoutCalculationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;

@Service
@RequiredArgsConstructor
public class CourierPayoutCalculationServiceImpl implements CourierPayoutCalculationService {

    private final CourierApiClient courierApiClient;

    @Override
    public BigDecimal calculatePayout(Double distanceKm, Duration estimatedTime) {
        CourierPayoutResultModel courierPayoutResultModel = courierApiClient.payoutCalculation(
                new CourierPayoutCalculationInput(distanceKm));
        return courierPayoutResultModel.getPayoutFee();
    }
}
