// ComponentService.java
package dk.kea.elektrostorage.service;

import dk.kea.elektrostorage.dto.ComponentDTO;
import dk.kea.elektrostorage.dto.CreateComponentDTO;
import dk.kea.elektrostorage.entity.Component;
import dk.kea.elektrostorage.repository.ComponentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ComponentService {
    private final ComponentRepository repo;
    private final SupplierService supplierService;

    public ComponentService(ComponentRepository repo, SupplierService supplierService) {
        this.repo = repo;
        this.supplierService = supplierService;
    }

    public List<ComponentDTO> findAll() {
        return repo.findAll().stream().map(dk.kea.elektrostorage.mapper.ComponentMapper::toDTO).toList();
    }

    @Transactional
    public ComponentDTO create(CreateComponentDTO dto) {
        var supplier = supplierService.getOrThrow(dto.supplierId());
        Component c = new Component(supplier, dto.externalSku(), dto.orderable());
        c = repo.save(c);
        return dk.kea.elektrostorage.mapper.ComponentMapper.toDTO(c);
    }

    @Transactional
    public ComponentDTO discontinue(Long id) {
        Component c = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Component not found: " + id));
        if (c.isDiscontinued()) throw new IllegalStateException("Component already discontinued");
        c.setDiscontinued(true);
        return dk.kea.elektrostorage.mapper.ComponentMapper.toDTO(c);
    }

    public Component getOrThrow(Long id) {
        return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Component not found: " + id));
    }
}
