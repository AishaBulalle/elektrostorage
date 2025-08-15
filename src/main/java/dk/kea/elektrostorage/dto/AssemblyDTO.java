// AssemblyDTO.java
package dk.kea.elektrostorage.dto;

import java.util.List;

public record AssemblyDTO(Long id, String name, Long resultingComponentId, List<AssemblyItemDTO> items) {}
