package restaurant.GrandmasFood.webApi.productsController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import restaurant.GrandmasFood.common.domains.entity.product.ProductEntity;
import restaurant.GrandmasFood.services.productService.ProductServiceImpl;

@RestController
public class productController {

    @Autowired
    ProductServiceImpl productService;


    public ProductEntity save(ProductEntity product){

        return productService.save(product);
    }

}
