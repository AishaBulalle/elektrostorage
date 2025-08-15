package dk.kea.elektrostorage.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class InventoryCount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "component_id")
    private Component component;

    private String countedBy;
    private int quantity;
    private LocalDateTime countedAt = LocalDateTime.now();

    public InventoryCount() {}

    public InventoryCount(Component component, String countedBy, int quantity) {
        this.component = component;
        this.countedBy = countedBy;
        this.quantity = quantity;
    }

    // Getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Component getComponent() { return component; }
    public void setComponent(Component component) { this.component = component; }

    public String getCountedBy() { return countedBy; }
    public void setCountedBy(String countedBy) { this.countedBy = countedBy; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public LocalDateTime getCountedAt() { return countedAt; }
    public void setCountedAt(LocalDateTime countedAt) { this.countedAt = countedAt; }
}
