// CreateComponentDTO.java
package dk.kea.elektrostorage.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateComponentDTO(
        @NotNull Long supplierId,
        @NotBlank String externalSku,
        boolean orderable
) {}
