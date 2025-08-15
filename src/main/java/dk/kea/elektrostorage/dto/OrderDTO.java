// OrderDTO.java
package dk.kea.elektrostorage.dto;

import java.time.LocalDate;
import java.util.List;

public record OrderDTO(
        Long id,
        Long supplierId,
        String trackingCode,
        LocalDate finalizedDate,
        LocalDate expectedDate,
        LocalDate receivedDate,
        String status,
        List<OrderLineDTO> lines
) {}
