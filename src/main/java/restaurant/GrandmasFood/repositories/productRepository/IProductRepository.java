package restaurant.GrandmasFood.repositories.productRepository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import restaurant.GrandmasFood.common.domains.entity.product.ProductEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IProductRepository extends JpaRepository<ProductEntity, Long> {

    @Query("SELECT p FROM product p WHERE p.uuid = :uuid AND p.removed = true")
    Optional<ProductEntity> findProductByUuid(@Param("uuid") String uuid);

    Optional<ProductEntity> findProductByName(String name);

    @Transactional
    @Modifying
    @Query("UPDATE product p SET p.available = false, p.removed = false WHERE p.id = :id_product")
    void deleteLogicProductById(@Param("id_product") Long id);
}
