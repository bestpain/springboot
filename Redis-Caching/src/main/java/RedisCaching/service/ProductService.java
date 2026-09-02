package RedisCaching.service;

import RedisCaching.entity.Product;
import RedisCaching.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Cacheable(value = "products", key = "#id")
    public Product getProduct(Long id) {
        System.out.println("🔥 DB HIT - getProduct");

        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }


    @CachePut(value = "products", key = "#id")
    public Product updateProduct(Long id, Product product) {
        System.out.println("🔥 DB HIT - updateProduct");

        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        existingProduct.setName(product.getName());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setStock(product.getStock());

        return productRepository.save(existingProduct);
    }

    @CacheEvict(value = "products", key = "#id")
    public void deleteProduct(Long id) {

        System.out.println("🔥 DB HIT - deleteProduct");

        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        productRepository.delete(existingProduct);
    }
}