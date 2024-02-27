package restaurant.GrandmasFood.repositories.productRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import restaurant.GrandmasFood.common.domains.entity.product.ProductEntity;

import java.util.Optional;

public interface IProductRepository extends JpaRepository<ProductEntity, Long> {
    Optional<ProductEntity> findByUuid(String uuid);

    void deleteByUuid(String uuid);

}
