package dk.kea.elektrostorage.repository;

import dk.kea.elektrostorage.entity.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderLineRepository extends JpaRepository<OrderLine, Long> { }
