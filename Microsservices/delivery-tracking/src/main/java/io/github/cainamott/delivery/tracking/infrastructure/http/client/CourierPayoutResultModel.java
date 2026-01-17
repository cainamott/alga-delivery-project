package io.github.cainamott.delivery.tracking.infrastructure.http.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class CourierPayoutResultModel {

    private BigDecimal payoutFee;
}
