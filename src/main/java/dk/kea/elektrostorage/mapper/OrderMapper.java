// OrderMapper.java
package dk.kea.elektrostorage.mapper;

import dk.kea.elektrostorage.dto.OrderDTO;
import dk.kea.elektrostorage.dto.OrderLineDTO;
import dk.kea.elektrostorage.entity.Order;
import dk.kea.elektrostorage.entity.OrderLine;

import java.util.List;

public class OrderMapper {
    public static OrderDTO toDTO(Order o) {
        List<OrderLineDTO> lines = o.getLines().stream().map(OrderMapper::toDTO).toList();
        String status = o.getReceivedDate()!=null ? "RECEIVED" :
                o.getFinalizedDate()!=null ? "SENT" : "DRAFT";
        return new OrderDTO(
                o.getId(),
                o.getSupplier()!=null ? o.getSupplier().getId() : null,
                o.getTrackingCode(),
                o.getFinalizedDate(),
                o.getExpectedDate(),
                o.getReceivedDate(),
                status,
                lines
        );
    }
    public static OrderLineDTO toDTO(OrderLine l) {
        return new OrderLineDTO(
                l.getId(),
                l.getComponent()!=null ? l.getComponent().getId() : null,
                l.getQuantity()
        );
    }
}
