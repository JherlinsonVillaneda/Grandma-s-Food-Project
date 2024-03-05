package restaurant.GrandmasFood.repository.productRepository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import restaurant.GrandmasFood.common.domains.entity.product.ProductEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface IProductRepository extends JpaRepository<ProductEntity, Long> {

    @Query("SELECT p FROM product p WHERE p.uuid = :uuid AND p.removed = false")
    Optional<ProductEntity> findProductByUuid(@Param("uuid") String uuid);

    Optional<ProductEntity> findProductByName(String name);

    @Query("SELECT p FROM product p WHERE p.name like %:name% order by p.name asc")
    List<ProductEntity> findProductsByName(@Param("name") String name);

    @Transactional
    @Modifying
    @Query("UPDATE product p SET p.available = false, p.removed = true WHERE p.id = :id_product")
    void deleteLogicProductById(@Param("id_product") Long id);
}
