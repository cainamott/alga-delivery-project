package io.github.cainamott.delivery.tracking.domain.model;

import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Builder
@Embeddable
public class ContactPoint {

    private String zipCode;
    private String street;
    private String number;
    private String complement;
    private String name;
    private String phone;
}
