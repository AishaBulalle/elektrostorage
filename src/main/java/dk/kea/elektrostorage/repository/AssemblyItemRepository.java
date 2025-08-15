package dk.kea.elektrostorage.repository;

import dk.kea.elektrostorage.entity.AssemblyItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssemblyItemRepository extends JpaRepository<AssemblyItem, Long> { }
