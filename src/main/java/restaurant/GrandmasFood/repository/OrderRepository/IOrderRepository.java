package restaurant.GrandmasFood.repository.OrderRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import restaurant.GrandmasFood.common.domains.entity.order.OrderEntity;

import java.util.Optional;

@Repository
public interface IOrderRepository extends JpaRepository<OrderEntity, Long> {
    @Query("SELECT o FROM order_entity o WHERE o.uuid = :uuid")
    Optional<OrderEntity> findOrderByUuid(String uuid);
}