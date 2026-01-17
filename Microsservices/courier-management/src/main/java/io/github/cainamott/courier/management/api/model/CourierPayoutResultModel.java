package io.github.cainamott.courier.management.api.model;

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
