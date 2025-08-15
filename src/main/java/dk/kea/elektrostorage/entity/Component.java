package dk.kea.elektrostorage.entity;

import jakarta.persistence.*;

@Entity
public class Component {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Internt nummer (altid tal)

    @ManyToOne(optional = false)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @Column(nullable = false)
    private String externalSku; // Eksternt varenummer fra leverandøren

    private boolean discontinued = false; // Marker om komponenten er udgået
    private boolean orderable = true; // Om den kan bestilles (ellers kun via stykliste)

    // Constructors
    public Component() {}

    public Component(Supplier supplier, String externalSku, boolean orderable) {
        this.supplier = supplier;
        this.externalSku = externalSku;
        this.orderable = orderable;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Supplier getSupplier() { return supplier; }
    public void setSupplier(Supplier supplier) { this.supplier = supplier; }

    public String getExternalSku() { return externalSku; }
    public void setExternalSku(String externalSku) { this.externalSku = externalSku; }

    public boolean isDiscontinued() { return discontinued; }
    public void setDiscontinued(boolean discontinued) { this.discontinued = discontinued; }

    public boolean isOrderable() { return orderable; }
    public void setOrderable(boolean orderable) { this.orderable = orderable; }
}