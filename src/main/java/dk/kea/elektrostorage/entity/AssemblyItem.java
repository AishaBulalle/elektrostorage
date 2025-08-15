package dk.kea.elektrostorage.entity;

import jakarta.persistence.*;

@Entity
public class AssemblyItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "assembly_id")
    private Assembly assembly;

    @ManyToOne(optional = false)
    @JoinColumn(name = "component_id")
    private Component component;

    private int quantity;

    public AssemblyItem() {}

    public AssemblyItem(Assembly assembly, Component component, int quantity) {
        this.assembly = assembly;
        this.component = component;
        this.quantity = quantity;
    }

    // Getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Assembly getAssembly() { return assembly; }
    public void setAssembly(Assembly assembly) { this.assembly = assembly; }

    public Component getComponent() { return component; }
    public void setComponent(Component component) { this.component = component; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
