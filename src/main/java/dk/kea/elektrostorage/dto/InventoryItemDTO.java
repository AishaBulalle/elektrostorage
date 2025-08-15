// InventoryItemDTO.java
package dk.kea.elektrostorage.dto;

public record InventoryItemDTO(Long componentId, String externalSku, int totalReceived) {}
