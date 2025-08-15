// FinalizeOrderDTO.java
package dk.kea.elektrostorage.dto;

import java.time.LocalDate;

public record FinalizeOrderDTO(String trackingCode, LocalDate expectedDate) {}
