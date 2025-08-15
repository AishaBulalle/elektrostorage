// CreateInventoryCountDTO.java
package dk.kea.elektrostorage.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CreateInventoryCountDTO(@NotNull Long componentId, @Min(0) int quantity, String countedBy) {}
