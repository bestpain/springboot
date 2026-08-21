package InventoryManagment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false ,updatable = false)
    private UUID uuid;

    @Setter
    @Column
    private String name;

    @Setter
    @Column
    private Integer availableQuantity;

    @Version
    private Long version;

    @PrePersist
    public void prePersist(){
        if(uuid == null) uuid = UUID.randomUUID();
    }
}
