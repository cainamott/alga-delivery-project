package io.github.cainamott.delivery.tracking.infrastructure.http.client;

import io.github.cainamott.delivery.tracking.domain.service.CourierPayoutCalculationService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange("/api/v1/couriers")
public interface CourierApiClient {

    @PostExchange("/payout-calculation")
    CourierPayoutResultModel payoutCalculation(
            @RequestBody CourierPayoutCalculationInput input);
}
