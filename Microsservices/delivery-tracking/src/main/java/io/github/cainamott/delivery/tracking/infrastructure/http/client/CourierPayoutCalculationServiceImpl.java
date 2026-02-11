package io.github.cainamott.delivery.tracking.infrastructure.http.client;

import io.github.cainamott.delivery.tracking.domain.exception.BadGatewayException;
import io.github.cainamott.delivery.tracking.domain.exception.GatewayTimeOutException;
import io.github.cainamott.delivery.tracking.domain.service.CourierPayoutCalculationService;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.math.BigDecimal;
import java.time.Duration;

@Service
@RequiredArgsConstructor
public class CourierPayoutCalculationServiceImpl implements CourierPayoutCalculationService {

    private final CourierApiClient courierApiClient;

    @Override
    public BigDecimal calculatePayout(Double distanceKm, Duration estimatedTime) {
        try{
            CourierPayoutResultModel courierPayoutResultModel = courierApiClient.payoutCalculation(
                    new CourierPayoutCalculationInput(distanceKm));
            return courierPayoutResultModel.getPayoutFee();
        }catch(ResourceAccessException e){
            throw new GatewayTimeOutException();
        }catch(HttpServerErrorException | IllegalArgumentException | CallNotPermittedException e){
            throw new BadGatewayException();
        }
    }
}
