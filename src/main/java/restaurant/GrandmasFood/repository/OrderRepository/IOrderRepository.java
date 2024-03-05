package restaurant.GrandmasFood.repository.OrderRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import restaurant.GrandmasFood.common.domains.entity.order.OrderEntity;

import java.util.Optional;

@Repository
public interface IOrderRepository extends JpaRepository<OrderEntity, Long> {
    Optional<OrderEntity> findOrderByUuid(String uuid);
}