package restaurant.GrandmasFood.repository.OrderRepository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import restaurant.GrandmasFood.common.domains.entity.client.ClientEntity;
import restaurant.GrandmasFood.common.domains.entity.order.OrderEntity;
import restaurant.GrandmasFood.common.domains.entity.product.ProductEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface IOrderRepository extends JpaRepository<OrderEntity, Long> {

    Optional<OrderEntity> findOrderByUuid(String uuid);

    @Transactional
    @Modifying
    @Query(name = "DELETE FROM order_entity o WHERE o.product_uuid = :productEntity", nativeQuery = true)
    void deleteAllByProductUuid(@Param("productEntity") ProductEntity productEntity);

    @Transactional
    @Modifying
    @Query(name = "DELETE FROM order_entity o WHERE o.client_document = :clientEntity", nativeQuery = true)
    void deleteAllByClientDocument(@Param("clientEntity") ClientEntity clientEntity);
}