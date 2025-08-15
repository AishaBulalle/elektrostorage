package dk.kea.elektrostorage.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Assembly {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // Resultatet af styklisten: den komponent vi bygger (kan være null)
    @ManyToOne
    @JoinColumn(name = "resulting_component_id")
    private Component resultingComponent;

    @OneToMany(mappedBy = "assembly", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AssemblyItem> items = new ArrayList<>();

    public Assembly() {}

    // Getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Component getResultingComponent() { return resultingComponent; }
    public void setResultingComponent(Component resultingComponent) { this.resultingComponent = resultingComponent; }

    public List<AssemblyItem> getItems() { return items; }
    public void setItems(List<AssemblyItem> items) { this.items = items; }
}
