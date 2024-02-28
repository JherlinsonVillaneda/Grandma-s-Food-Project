package restaurant.GrandmasFood.webApi.productsController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restaurant.GrandmasFood.common.constant.endpoints.IProductEndpoints;
import restaurant.GrandmasFood.common.domains.entity.product.ProductEntity;
import restaurant.GrandmasFood.services.productService.ProductService;

import java.util.UUID;

@RestController
public class productController {

    @Autowired
    ProductService productService;

    @PostMapping(IProductEndpoints.PRODUCT_CREATE)
    public ResponseEntity<ProductEntity> createProduct(@RequestBody ProductEntity productEntity){
        ProductEntity productSaved = productService.createProduct(productEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(productSaved);

    }

    @GetMapping(IProductEndpoints.PRODUCT_FIND)
    public ResponseEntity<ProductEntity> getProduct(@PathVariable("uuid") String  uuid){
        return new ResponseEntity<>(productService.getProduct(uuid), HttpStatus.OK);
    }

    @DeleteMapping(IProductEndpoints.PRODUCT_DELETE)
    public ResponseEntity<Void> deleteProduct(@PathVariable("uuid") String uuid){
        productService.deleteProduct(uuid);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
