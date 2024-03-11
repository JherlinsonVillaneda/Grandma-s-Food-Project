package restaurant.GrandmasFood.repository.OrderRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import restaurant.GrandmasFood.common.domains.entity.order.OrderEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface IOrderRepository extends JpaRepository<OrderEntity, Long> {
    @Query("SELECT o FROM order_entity o WHERE o.uuid = :uuid")
    Optional<OrderEntity> findOrderByUuid(@Param("uuid") String uuid);

    @Query(value = "SELECT o FROM order_entity o WHERE o.productUuid = :productUuid", nativeQuery = true)
    List<OrderEntity> findOrdersByProductUuid(@Param("productUuid") String productUuid);

    @Modifying
    @Query(value = "DELETE FROM order_entity o WHERE o.product_uuid = :productUuid", nativeQuery = true)
    void deleteAllByProductUuid(@Param("productUuid") String productUuid);
}