package restaurant.GrandmasFood.repositories.productRepository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import restaurant.GrandmasFood.common.domains.entity.product.ProductEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IProductRepository extends JpaRepository<ProductEntity, Long> {
    Optional<ProductEntity> findProductByUuid(@Param("uuid") String uuid);
    Optional<ProductEntity> findProductByName(String name);
}
