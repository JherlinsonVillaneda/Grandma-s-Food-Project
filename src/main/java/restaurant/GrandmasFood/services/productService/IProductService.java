package restaurant.GrandmasFood.services.productService;

import org.springframework.validation.BindingResult;
import restaurant.GrandmasFood.common.domains.dto.ProductDTO;
import restaurant.GrandmasFood.common.domains.entity.product.ProductEntity;

import java.util.List;

public interface IProductService {
    ProductDTO createProduct(ProductDTO product);
    ProductDTO getProduct(String uuid);
    ProductDTO updateProduct(String uuid, ProductDTO product);
    void deleteProduct(String uuid);
    List<ProductDTO> getProductsByName(String query);
}
