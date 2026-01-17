package io.github.cainamott.courier.management.domain.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class CourierCalculationPayoutService {

    public static BigDecimal calculate(Double distanceInKm){
        return new BigDecimal("10").multiply(new BigDecimal((distanceInKm))).setScale(2, RoundingMode.HALF_DOWN);
    }

}
