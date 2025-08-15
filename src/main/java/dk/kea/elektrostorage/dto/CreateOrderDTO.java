// CreateOrderDTO.java
package dk.kea.elektrostorage.dto;

import jakarta.validation.constraints.NotNull;

public record CreateOrderDTO(@NotNull Long supplierId) {}
