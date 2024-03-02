package restaurant.GrandmasFood.services.productService;

import restaurant.GrandmasFood.common.domains.dto.ProductDTO;
import restaurant.GrandmasFood.common.domains.entity.product.ProductEntity;

public interface IProductService {
    ProductDTO createProduct(ProductEntity product);
    ProductDTO getProduct(String uuid);
    ProductDTO updateProduct(String uuid, ProductEntity product);
    Boolean equalsProducts(ProductEntity productExisting, ProductEntity productNew);
    void deleteProduct(String uuid);
}
