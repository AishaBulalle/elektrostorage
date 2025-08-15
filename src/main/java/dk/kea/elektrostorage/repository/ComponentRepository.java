package dk.kea.elektrostorage.repository;

import dk.kea.elektrostorage.entity.Component;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComponentRepository extends JpaRepository<Component, Long> {
}
