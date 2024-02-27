package restaurant.GrandmasFood.services.productService;

import org.springframework.beans.factory.annotation.Autowired;
import restaurant.GrandmasFood.common.domains.entity.product.ProductEntity;
import restaurant.GrandmasFood.repositories.productRepository.IProductRepository;

import java.util.Optional;

public class ProductServiceImpl implements  IProductService{

    @Autowired
    IProductRepository iProductRepository;


    @Override
    public void save(ProductEntity product) {
        iProductRepository.save(product);

    }

    @Override
    public Optional<ProductEntity> findByUuid(String uuid) {
        return iProductRepository.findByUuid(uuid);
    }

    @Override
    public void delete(String uuid) {
        iProductRepository.deleteByUuid(uuid);

    }
}
