package FilteringSorting.service;

import FilteringSorting.dto.PageResponse;
import FilteringSorting.dto.ProductResponse;
import FilteringSorting.dto.ProductSearchRequest;
import FilteringSorting.entity.Product;
import FilteringSorting.exceptions.InvalidSortException;
import FilteringSorting.repository.ProductRepository;
import FilteringSorting.repository.ProductSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private static final Set<String> ALLOWED_SORT_FIELDS =
            Set.of("id", "name", "price", "category");

    // using Pageable for filtering
    public PageResponse<ProductResponse> getProductsByCriteriaV1(Pageable pageable) {
//        String[] sorting = sortBy.split(",");
//        Sort.Direction direction = Sort.Direction.fromString(sorting[1]);
        Page<Product> result = productRepository.findAll(pageable);

        return transformResultToPageResponse(result);
    }

    // Pageable + Filtering + Custom query
    public PageResponse<ProductResponse> getProductsByCriteriaV2(int page, int size, String sortBy,
                                                                 String category, BigDecimal min, BigDecimal max) {

        Sort sort = createSort(sortBy);

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Product> result = productRepository.filterByCriteria(category, min, max, pageable);

        return transformResultToPageResponse(result);

    }

    // JpaSpecificationExecutor + Specification - for dynamic filtering where clause
    public PageResponse<ProductResponse> getProductsByCriteria(ProductSearchRequest productSearchRequest) {

//        Specification<Product> productSpecification =
//                ((root, query, criteriaBuilder) -> {
//                    return category == null ? null : criteriaBuilder.equal(root.get("category"), category);
//                });


        Specification<Product> productSpecification =
                Specification.allOf(
                        ProductSpecification.hasCategory(productSearchRequest.category()),
                        ProductSpecification.priceGreaterThanOrEqualTo(
                                productSearchRequest.minPrice()
                        ),
                        ProductSpecification.priceLessThanOrEqualTo(
                                productSearchRequest.maxPrice()
                        )
                );

        Sort sort = createSort(productSearchRequest.sort());

        Pageable pageable = PageRequest.of(productSearchRequest.page(),
                productSearchRequest.size(), sort);

        Page<Product> productPage = productRepository.findAll(productSpecification, pageable);

        return transformResultToPageResponse(productPage);
    }

    private PageResponse<ProductResponse> transformResultToPageResponse(Page<Product> result) {
        Page<ProductResponse> productResponses = result.map(product ->
                new ProductResponse(product.getId(), product.getName(), product.getPrice(), product.getCategory()));

        return new PageResponse<>(productResponses.getContent(), result.getNumber(), result.getSize(), result.getTotalElements(), result.getTotalPages());
    }

    private Sort createSort(String sortBy) {

        String[] parts = sortBy.split(",");

        if (parts.length != 2) {
            throw new InvalidSortException(
                    "Sort must be in format: field,direction"
            );
        }

        String field = parts[0];

        if (!ALLOWED_SORT_FIELDS.contains(field)) {
            throw new InvalidSortException(
                    "Sorting by '" + field + "' is not allowed"
            );
        }

        Sort.Direction direction;

        try {
            direction = Sort.Direction.fromString(parts[1]);
        } catch (IllegalArgumentException e) {
            throw new InvalidSortException(
                    "Sort direction must be 'asc' or 'desc'"
            );
        }

        return Sort.by(direction, field);
    }

}
