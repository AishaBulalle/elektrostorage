// OrderService.java
package dk.kea.elektrostorage.service;

import dk.kea.elektrostorage.dto.*;
import dk.kea.elektrostorage.entity.Component;
import dk.kea.elektrostorage.entity.Order;
import dk.kea.elektrostorage.entity.OrderLine;
import dk.kea.elektrostorage.repository.ComponentRepository;
import dk.kea.elektrostorage.repository.OrderLineRepository;
import dk.kea.elektrostorage.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepo;
    private final OrderLineRepository lineRepo;
    private final SupplierService supplierService;
    private final ComponentRepository componentRepo;

    public OrderService(OrderRepository orderRepo, OrderLineRepository lineRepo,
                        SupplierService supplierService, ComponentRepository componentRepo) {
        this.orderRepo = orderRepo;
        this.lineRepo = lineRepo;
        this.supplierService = supplierService;
        this.componentRepo = componentRepo;
    }

    public List<OrderDTO> findAll() {
        return orderRepo.findAll().stream().map(dk.kea.elektrostorage.mapper.OrderMapper::toDTO).toList();
    }

    @Transactional
    public OrderDTO create(CreateOrderDTO dto) {
        var supplier = supplierService.getOrThrow(dto.supplierId());
        Order o = new Order();
        o.setSupplier(supplier);
        o = orderRepo.save(o);
        return dk.kea.elektrostorage.mapper.OrderMapper.toDTO(o);
    }

    @Transactional
    public OrderDTO addLine(Long orderId, AddOrderLineDTO dto) {
        Order o = orderRepo.findById(orderId).orElseThrow(() -> new IllegalArgumentException("Order not found: " + orderId));
        if (o.getFinalizedDate()!=null) throw new IllegalStateException("Cannot add lines to finalized order");
        Component comp = componentRepo.findById(dto.componentId())
                .orElseThrow(() -> new IllegalArgumentException("Component not found: " + dto.componentId()));
        OrderLine l = new OrderLine(o, comp, dto.quantity());
        lineRepo.save(l);
        o.getLines().add(l);
        return dk.kea.elektrostorage.mapper.OrderMapper.toDTO(o);
    }

    @Transactional
    public OrderDTO finalizeOrder(Long id, FinalizeOrderDTO dto) {
        Order o = orderRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Order not found: " + id));
        if (o.getFinalizedDate()!=null) throw new IllegalStateException("Order already finalized");
        if (o.getLines().isEmpty()) throw new IllegalStateException("Cannot finalize empty order");
        o.setTrackingCode(dto.trackingCode());
        o.setExpectedDate(dto.expectedDate());
        o.setFinalizedDate(LocalDate.now());
        return dk.kea.elektrostorage.mapper.OrderMapper.toDTO(o);
    }

    @Transactional
    public OrderDTO receive(Long id) {
        Order o = orderRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Order not found: " + id));
        if (o.getFinalizedDate()==null) throw new IllegalStateException("Order not finalized");
        if (o.getReceivedDate()!=null) throw new IllegalStateException("Order already received");
        o.setReceivedDate(LocalDate.now());
        return dk.kea.elektrostorage.mapper.OrderMapper.toDTO(o);
    }
}
