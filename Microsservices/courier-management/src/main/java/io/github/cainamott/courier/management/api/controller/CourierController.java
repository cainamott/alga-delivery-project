package io.github.cainamott.courier.management.api.controller;

import io.github.cainamott.courier.management.api.model.CourierInput;
import io.github.cainamott.courier.management.api.model.CourierPayoutCalculationInput;
import io.github.cainamott.courier.management.api.model.CourierPayoutResultModel;
import io.github.cainamott.courier.management.domain.model.Courier;
import io.github.cainamott.courier.management.domain.repository.CourierRepository;
import io.github.cainamott.courier.management.domain.service.CourierCalculationPayoutService;
import io.github.cainamott.courier.management.domain.service.CourierRegistrationService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/couriers")
public class CourierController {
    private final CourierRepository courierRepository;
    private final CourierRegistrationService courierRegistrationService;
    private final CourierCalculationPayoutService courierCalculationPayoutService;

    public CourierController(CourierRepository courierRepository, CourierRegistrationService courierRegistrationService, CourierCalculationPayoutService courierCalculationPayoutService) {
        this.courierRepository = courierRepository;
        this.courierRegistrationService = courierRegistrationService;
        this.courierCalculationPayoutService = courierCalculationPayoutService;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Courier create(@Valid @RequestBody CourierInput input){
        return courierRegistrationService.create(input);

    }

    @PutMapping("/{courierId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Courier edit(@PathVariable UUID courierId, @RequestBody CourierInput input ){
        return courierRegistrationService.update(courierId, input);
    }

    @GetMapping
    public PagedModel<Courier> findAll(@PageableDefault Pageable pages){
        return new PagedModel<>(
                courierRepository.findAll(pages)
        );
    }

    @GetMapping("{courierId}")
    public Courier findById(@PathVariable UUID courierId){
       return courierRepository.findById(courierId).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/payout-calculation")
    public CourierPayoutResultModel calculateCourierPayment(@RequestBody CourierPayoutCalculationInput input){

        BigDecimal payoutFee = CourierCalculationPayoutService.calculate(input.getDistanceInKm());
        return new CourierPayoutResultModel(payoutFee);
    }
}
