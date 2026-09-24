package APIDesign.Repository;

import APIDesign.DTO.OrderSummaryDto;
import APIDesign.Entity.Order;
import APIDesign.Enums.OrderStatus;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>, JpaSpecificationExecutor<Order> {

    @Query("""
            select o.id, o.amount, o.status from Order as o
            where o.customer.id = :customerId
            and (:status is null or o.status = :status)
            """
    )
    public List<OrderSummaryDto> getOrdersForCustomer(@Param("customerId") int customerId, @Param("status") OrderStatus status);

    @Query("""
            select new APIDesign.DTO.OrderSummaryDto(o.id, o.amount, o.status) from Order as o
            where o.customer.id = :customerId
            and (:status is null or o.status = :status)
            """
    )
    public List<OrderSummaryDto> getOrdersForCustomerV2(@Param("customerId") int customerId, @Param("status") OrderStatus status);
}
