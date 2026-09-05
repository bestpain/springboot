package FilteringSorting.repository;

import FilteringSorting.entity.Product;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

public class ProductSpecification {

    public static Specification<Product> hasCategory(String category) {

        return category == null
                ? null
                : (root, query, cb) ->
                cb.equal(root.get("category"), category);
    }

    public static Specification<Product> priceGreaterThanOrEqualTo(
            BigDecimal minPrice) {

        return minPrice == null ? null : (root, query, cb) ->
                cb.greaterThanOrEqualTo(
                        root.get("price"),
                        minPrice
                );
    }

    public static Specification<Product> priceLessThanOrEqualTo(
            BigDecimal maxPrice) {

        return maxPrice == null ? null : (root, query, cb) ->
                cb.lessThanOrEqualTo(
                        root.get("price"),
                        maxPrice
                );
    }
}
