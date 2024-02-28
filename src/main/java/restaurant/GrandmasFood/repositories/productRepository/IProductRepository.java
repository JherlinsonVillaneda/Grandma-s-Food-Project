package restaurant.GrandmasFood.repositories.productRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import restaurant.GrandmasFood.common.domains.entity.product.ProductEntity;

import java.util.Optional;
import java.util.UUID;

public interface IProductRepository extends JpaRepository<ProductEntity, Long> {
    Optional<ProductEntity> findProductByUuid(UUID uuid);
    Optional<ProductEntity> findProductByName(String name);
    void deleteProductByUuid(UUID uuid);


}
