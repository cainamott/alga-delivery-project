package io.github.cainamott.delivery.tracking.domain.service;

import io.github.cainamott.delivery.tracking.api.model.ContactPointInput;
import io.github.cainamott.delivery.tracking.api.model.DeliveryInput;
import io.github.cainamott.delivery.tracking.api.model.ItemInput;
import io.github.cainamott.delivery.tracking.domain.exception.DomainException;
import io.github.cainamott.delivery.tracking.domain.model.ContactPoint;
import io.github.cainamott.delivery.tracking.domain.model.Delivery;
import io.github.cainamott.delivery.tracking.domain.repository.DeliveryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliveryPreparationService {

    private final DeliveryRepository deliveryRepository;

    private final DeliveryTimeEstimationService deliveryTimeEstimationService;

    private final CourierPayoutCalculationService courierPayoutCalculationService;

    @Transactional
    public Delivery draft(DeliveryInput input){
        Delivery delivery = Delivery.draft();
        handlePreparation(input, delivery);
        return deliveryRepository.saveAndFlush(delivery);
    }

    @Transactional
    public Delivery edit(UUID deliveryId, DeliveryInput input){
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new DomainException());
        delivery.removeItens();
        handlePreparation(input, delivery);
        return deliveryRepository.saveAndFlush(delivery);
    }

    private void handlePreparation(DeliveryInput deliveryInput, Delivery delivery) {

        ContactPointInput senderInput = new ContactPointInput();
        ContactPointInput recipientInput = new ContactPointInput();

        ContactPoint sender = ContactPoint
                .builder()
                .zipCode(senderInput.getZipCode())
                .phone(senderInput.getPhone())
                .name(senderInput.getName())
                .number(senderInput.getNumber())
                .street(senderInput.getStreet())
                .complement(senderInput.getComplement())
                .build();
        ContactPoint recipient = ContactPoint.builder()
                .zipCode(recipientInput.getZipCode())
                .phone(recipientInput.getPhone())
                .name(recipientInput.getName())
                .number(recipientInput.getNumber())
                .street(recipientInput.getStreet())
                .complement(recipientInput.getComplement())
                .build();

        DeliveryEstimate estimate = deliveryTimeEstimationService.estimate(sender, recipient);
        BigDecimal calculatedPayout = courierPayoutCalculationService.calculatePayout(estimate.getDistanceKm(), estimate.getEstimateTime());

        var distanceFee = calculateFee(estimate.getDistanceKm());

        var preparationDetails = Delivery.PreparationDetails
                .builder()
                .sender(sender)
                .recipient(recipient)
                .expectedDurationTime(estimate.getEstimateTime())
                .distanceFee(distanceFee)
                .courierPayout(calculatedPayout)
                .build();

        delivery.editPreparationDetails(preparationDetails);
        for(ItemInput itemInput : deliveryInput.getItemList()){

            delivery.addItem(itemInput.getName(), itemInput.getQuantity());
        }

    }

    private BigDecimal calculateFee(Double distanceInKm){
        return new BigDecimal("3").multiply(new BigDecimal(distanceInKm)).setScale(2, RoundingMode.HALF_EVEN);
    }
}
