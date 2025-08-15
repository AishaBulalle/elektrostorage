// AddOrderLineDTO.java
package dk.kea.elektrostorage.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AddOrderLineDTO(@NotNull Long componentId, @Min(1) int quantity) {}
