// InventoryService.java
package dk.kea.elektrostorage.service;

import dk.kea.elektrostorage.dto.CreateInventoryCountDTO;
import dk.kea.elektrostorage.dto.InventoryItemDTO;
import dk.kea.elektrostorage.entity.InventoryCount;
import dk.kea.elektrostorage.entity.Order;
import dk.kea.elektrostorage.repository.ComponentRepository;
import dk.kea.elektrostorage.repository.InventoryCountRepository;
import dk.kea.elektrostorage.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class InventoryService {
    private final OrderRepository orderRepo;
    private final ComponentRepository componentRepo;
    private final InventoryCountRepository countRepo;

    public InventoryService(OrderRepository orderRepo, ComponentRepository componentRepo, InventoryCountRepository countRepo) {
        this.orderRepo = orderRepo;
        this.componentRepo = componentRepo;
        this.countRepo = countRepo;
    }

    public List<InventoryItemDTO> listReceived() {
        Map<Long, Integer> totals = new HashMap<>();
        for (Order o : orderRepo.findByReceivedDateIsNotNull()) {
            o.getLines().forEach(l -> totals.merge(l.getComponent().getId(), l.getQuantity(), Integer::sum));
        }
        return totals.entrySet().stream().map(e -> {
            var comp = componentRepo.findById(e.getKey()).orElseThrow();
            return new InventoryItemDTO(comp.getId(), comp.getExternalSku(), e.getValue());
        }).toList();
    }

    @Transactional
    public void addCount(CreateInventoryCountDTO dto) {
        var comp = componentRepo.findById(dto.componentId()).orElseThrow(() -> new IllegalArgumentException("Component not found: " + dto.componentId()));
        countRepo.save(new InventoryCount(comp, dto.countedBy(), dto.quantity()));
    }
}
