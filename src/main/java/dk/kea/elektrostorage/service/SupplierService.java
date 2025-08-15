// SupplierService.java
package dk.kea.elektrostorage.service;

import dk.kea.elektrostorage.entity.Supplier;
import dk.kea.elektrostorage.repository.SupplierRepository;
import org.springframework.stereotype.Service;

@Service
public class SupplierService {
    private final SupplierRepository repo;
    public SupplierService(SupplierRepository repo) { this.repo = repo; }
    public Supplier getOrThrow(Long id) {
        return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Supplier not found: " + id));
    }
}
