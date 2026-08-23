package nplusone.repository;

import nplusone.dto.UserOrderResponse;
import nplusone.entity.Order;
import nplusone.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select new nplusone.dto.UserOrderResponse(u.id,u.name,o.id,o.amount,o.status,o.createdAt) from User u left join u.orders o")
    public List<UserOrderResponse> findUsersOrders();

    @Query("select u from User u left join fetch u.orders")
    public List<User> getUsersWithOrders();
}
