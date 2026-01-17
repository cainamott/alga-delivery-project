package io.github.cainamott.courier.management.api.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CourierInput {

    @NotBlank
    private String name;

    @NotBlank
    private String phone;
}
