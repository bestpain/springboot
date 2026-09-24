package APIDesign.Repository;

import APIDesign.Entity.Order;
import APIDesign.Enums.OrderStatus;
import org.springframework.data.jpa.domain.Specification;

public class OrderSpecification {

    public static Specification<Order> hasStatus(OrderStatus orderStatus) {

        return orderStatus == null
                ? null
                : (root, query, cb) ->
                cb.equal(root.get("status"), orderStatus);
    }

    public static Specification<Order> hasCustomerId(Integer  customerId) {

        return customerId == null
                ? null
                : (root, query, cb) ->
                cb.equal(root.get("customer").get("id"), customerId);
    }
}
