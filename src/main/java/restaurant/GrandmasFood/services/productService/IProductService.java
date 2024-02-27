package restaurant.GrandmasFood.services.productService;

import restaurant.GrandmasFood.common.domains.entity.product.ProductEntity;

import java.util.Optional;

public interface IProductService {

    void save(ProductEntity product);
    Optional<ProductEntity> findByUuid(String uuid);

    void delete(String uuid);




}
