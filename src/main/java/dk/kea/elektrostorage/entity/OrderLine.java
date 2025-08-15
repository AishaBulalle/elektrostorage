package dk.kea.elektrostorage.entity;

import jakarta.persistence.*;

@Entity
public class OrderLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(optional = false)
    @JoinColumn(name = "component_id")
    private Component component;

    private int quantity;

    public OrderLine() {}

    public OrderLine(Order order, Component component, int quantity) {
        this.order = order;
        this.component = component;
        this.quantity = quantity;
    }

    // Getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }

    public Component getComponent() { return component; }
    public void setComponent(Component component) { this.component = component; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
