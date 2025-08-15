// OrderLineDTO.java
package dk.kea.elektrostorage.dto;

public record OrderLineDTO(Long id, Long componentId, int quantity) {}
