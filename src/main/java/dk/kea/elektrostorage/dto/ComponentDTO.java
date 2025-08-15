// ComponentDTO.java
package dk.kea.elektrostorage.dto;

public record ComponentDTO(
        Long id,
        Long supplierId,
        String externalSku,
        boolean discontinued,
        boolean orderable
) {}
