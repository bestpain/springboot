package nplusone.repository;

import nplusone.dto.OrderWithUserResponse;
import nplusone.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Order, Integer> {

    @Query("select new nplusone.dto.OrderWithUserResponse(o.id,u.id,u.name,o.amount,o.status,o.createdAt) from Order o join o.user u")
    public List<OrderWithUserResponse> getUserDetailsForEachOrder();

    @Query("select o from Order o join fetch o.user")
    public List<Order> getOrderAlongWithUser();
}
