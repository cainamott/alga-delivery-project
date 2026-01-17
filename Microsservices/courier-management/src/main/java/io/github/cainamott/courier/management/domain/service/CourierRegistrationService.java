package io.github.cainamott.courier.management.domain.service;

import io.github.cainamott.courier.management.api.model.CourierInput;
import io.github.cainamott.courier.management.domain.model.Courier;
import io.github.cainamott.courier.management.domain.repository.CourierRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class CourierRegistrationService {

    private CourierRepository courierRepository;

    public CourierRegistrationService(CourierRepository courierRepository) {
        this.courierRepository = courierRepository;
    }

    public Courier create(@Valid CourierInput input) {
        Courier courier = Courier.brandNew(input.getName(), input.getPhone());
        return courierRepository.saveAndFlush(courier);
    }

    public Courier update(UUID courierId, @Valid CourierInput input) {

        Courier courier = courierRepository.findById(courierId)
                .orElseThrow();
        courier.setName(input.getName());
        courier.setPhone(input.getPhone());
        return courierRepository.saveAndFlush(courier);
    }
}
