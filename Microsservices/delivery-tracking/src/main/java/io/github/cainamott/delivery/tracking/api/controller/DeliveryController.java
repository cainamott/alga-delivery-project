package io.github.cainamott.delivery.tracking.api.controller;


import io.github.cainamott.delivery.tracking.api.model.CourierIdInput;
import io.github.cainamott.delivery.tracking.api.model.DeliveryInput;
import io.github.cainamott.delivery.tracking.domain.model.Delivery;
import io.github.cainamott.delivery.tracking.domain.repository.DeliveryRepository;
import io.github.cainamott.delivery.tracking.domain.service.DeliveryCheckPointService;
import io.github.cainamott.delivery.tracking.domain.service.DeliveryPreparationService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/deliveries")
public class DeliveryController {

    private final DeliveryPreparationService deliveryPreparationService;
    private final DeliveryRepository repository;
    private final DeliveryCheckPointService deliveryCheckPointService;

    public DeliveryController(DeliveryPreparationService deliveryPreparationService, DeliveryRepository repository, DeliveryCheckPointService deliveryCheckPointService) {
        this.deliveryPreparationService = deliveryPreparationService;
        this.repository = repository;
        this.deliveryCheckPointService = deliveryCheckPointService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Delivery draft(@RequestBody @Valid DeliveryInput delivery){
        return deliveryPreparationService.draft(delivery);
    }

    @PutMapping("{deliveryId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Delivery edit(@PathVariable("id") UUID deliveryId, @RequestBody @Valid DeliveryInput delivery){
        return deliveryPreparationService.edit(deliveryId, delivery);
    }

    @GetMapping
    public PagedModel<Delivery> findAll (@PageableDefault Pageable pages){
        return new PagedModel<>(repository.findAll(pages));
    }

    @GetMapping("/{deliveryId}")
    public Delivery findById(@PathVariable UUID deliveryId){

        return repository.findById(deliveryId)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{deliveryId}/placement")
    public void place(@PathVariable UUID deliveryId){

        deliveryCheckPointService.place(deliveryId);

    }

    @PostMapping("/{deliveryId}/pickups")
    public void pickup(
            @PathVariable UUID deliveryId,
            @RequestBody @Valid CourierIdInput courierId){

        deliveryCheckPointService.pickup(deliveryId, courierId.getCourierId());
    }

    @PostMapping("/{deliveryId}/completion")
    public void complete(@PathVariable UUID deliveryId){

        deliveryCheckPointService.complete(deliveryId);
    }

}
