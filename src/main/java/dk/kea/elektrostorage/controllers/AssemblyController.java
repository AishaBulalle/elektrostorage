// AssemblyController.java
package dk.kea.elektrostorage.controller;

import dk.kea.elektrostorage.dto.AssemblyDTO;
import dk.kea.elektrostorage.dto.AssemblyItemDTO;
import dk.kea.elektrostorage.service.AssemblyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assemblies")
public class AssemblyController {
    private final AssemblyService service;
    public AssemblyController(AssemblyService service) { this.service = service; }

    @GetMapping
    public List<AssemblyDTO> all() { return service.findAll(); }

    @GetMapping("/{id}")
    public AssemblyDTO get(@PathVariable Long id) { return service.get(id); }

    @PostMapping
    public ResponseEntity<AssemblyDTO> create(@RequestParam String name,
                                              @RequestParam(required = false) Long resultingComponentId,
                                              @RequestBody List<AssemblyItemDTO> items) {
        return ResponseEntity.ok(service.create(name, resultingComponentId, items));
    }
}
