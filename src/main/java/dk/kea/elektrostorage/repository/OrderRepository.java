package dk.kea.elektrostorage.repository;

import dk.kea.elektrostorage.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByReceivedDateIsNotNull();
    // alternativt navn (samme effekt):
    // List<Order> findAllByReceivedDateNotNull();
}
