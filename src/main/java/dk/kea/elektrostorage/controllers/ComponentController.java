// ComponentController.java
package dk.kea.elektrostorage.controller;

import dk.kea.elektrostorage.dto.ComponentDTO;
import dk.kea.elektrostorage.dto.CreateComponentDTO;
import dk.kea.elektrostorage.service.ComponentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/components")
public class ComponentController {
    private final ComponentService service;
    public ComponentController(ComponentService service) { this.service = service; }

    @GetMapping
    public List<ComponentDTO> all() { return service.findAll(); }

    @PostMapping
    public ResponseEntity<ComponentDTO> create(@Valid @RequestBody CreateComponentDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PatchMapping("/{id}/discontinue")
    public ResponseEntity<ComponentDTO> discontinue(@PathVariable Long id) {
        return ResponseEntity.ok(service.discontinue(id));
    }
}
