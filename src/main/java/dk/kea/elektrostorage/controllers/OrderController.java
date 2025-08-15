// OrderController.java
package dk.kea.elektrostorage.controller;

import dk.kea.elektrostorage.dto.*;
import dk.kea.elektrostorage.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService service;
    public OrderController(OrderService service) { this.service = service; }

    @GetMapping
    public List<OrderDTO> all() { return service.findAll(); }

    @PostMapping
    public ResponseEntity<OrderDTO> create(@Valid @RequestBody CreateOrderDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PostMapping("/{orderId}/lines")
    public ResponseEntity<OrderDTO> addLine(@PathVariable Long orderId, @Valid @RequestBody AddOrderLineDTO dto) {
        return ResponseEntity.ok(service.addLine(orderId, dto));
    }

    @PatchMapping("/{orderId}/finalize")
    public ResponseEntity<OrderDTO> finalizeOrder(@PathVariable Long orderId, @RequestBody FinalizeOrderDTO dto) {
        return ResponseEntity.ok(service.finalizeOrder(orderId, dto));
    }

    @PatchMapping("/{orderId}/receive")
    public ResponseEntity<OrderDTO> receive(@PathVariable Long orderId) {
        return ResponseEntity.ok(service.receive(orderId));
    }
}
