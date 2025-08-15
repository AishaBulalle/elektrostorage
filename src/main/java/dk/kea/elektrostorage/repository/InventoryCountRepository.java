package dk.kea.elektrostorage.repository;

import dk.kea.elektrostorage.entity.InventoryCount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryCountRepository extends JpaRepository<InventoryCount, Long> { }
