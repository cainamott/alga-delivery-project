package io.github.cainamott.delivery.tracking.api.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ContactPointInput {

    @NotBlank
    private String zipCode;

    @NotBlank
    private String number;

    @NotBlank
    private String street;

    private String complement;

    @NotBlank
    private String phone;

    @NotBlank
    private String name;
}
