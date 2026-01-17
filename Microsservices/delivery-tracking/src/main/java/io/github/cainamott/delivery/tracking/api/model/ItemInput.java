package io.github.cainamott.delivery.tracking.api.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemInput {

    @NotBlank
    private String name;

    @NotNull
    private Integer quantity;
}
