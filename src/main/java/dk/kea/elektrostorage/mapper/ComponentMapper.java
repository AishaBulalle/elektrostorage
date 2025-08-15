// ComponentMapper.java
package dk.kea.elektrostorage.mapper;

import dk.kea.elektrostorage.dto.ComponentDTO;
import dk.kea.elektrostorage.entity.Component;

public class ComponentMapper {
    public static ComponentDTO toDTO(Component c) {
        return new ComponentDTO(
                c.getId(),
                c.getSupplier()!=null ? c.getSupplier().getId() : null,
                c.getExternalSku(),
                c.isDiscontinued(),
                c.isOrderable()
        );
    }
}
