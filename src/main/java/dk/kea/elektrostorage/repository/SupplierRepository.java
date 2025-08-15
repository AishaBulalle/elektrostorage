package dk.kea.elektrostorage.repository;

import dk.kea.elektrostorage.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
}
