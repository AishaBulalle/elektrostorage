// InventoryController.java
package dk.kea.elektrostorage.controller;

import dk.kea.elektrostorage.dto.CreateInventoryCountDTO;
import dk.kea.elektrostorage.dto.InventoryItemDTO;
import dk.kea.elektrostorage.service.InventoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
    private final InventoryService service;
    public InventoryController(InventoryService service) { this.service = service; }

    @GetMapping
    public List<InventoryItemDTO> listReceived() { return service.listReceived(); }

    @PostMapping("/counts")
    public ResponseEntity<Void> addCount(@Valid @RequestBody CreateInventoryCountDTO dto) {
        service.addCount(dto);
        return ResponseEntity.ok().build();
    }
}
