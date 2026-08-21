package InventoryManagment.repository;

import InventoryManagment.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    public Optional<Product> findByUuid(UUID id);

    @Modifying
    @Query("update Product p set p.availableQuantity = p.availableQuantity - :quantity where p.uuid = :id and p.availableQuantity >= :quantity")
    public int updateQuantity(UUID id , int quantity);
}
