package restaurant.GrandmasFood.webApi.productsController;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import restaurant.GrandmasFood.common.constant.endpoints.IProductEndpoints;
import restaurant.GrandmasFood.common.domains.dto.ProductDTO;
import restaurant.GrandmasFood.common.domains.entity.product.ProductEntity;
import restaurant.GrandmasFood.services.productService.impl.ProductServiceImpl;

@RestController
public class productController {

    @Autowired
    ProductServiceImpl productService;

    @PostMapping(IProductEndpoints.PRODUCT_CREATE)
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductEntity productEntity){
        ProductDTO productSaved = productService.createProduct(productEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(productSaved);
    }

    @GetMapping(IProductEndpoints.PRODUCT_GET)
    public ResponseEntity<ProductDTO> getProduct(@PathVariable("uuid") String  uuid){
        return new ResponseEntity<>(productService.getProduct(uuid), HttpStatus.OK);
    }

    @PutMapping(IProductEndpoints.PRODUCT_PUT)
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable("uuid") String uuid, @RequestBody ProductEntity product){
         return ResponseEntity.status(HttpStatus.ACCEPTED).body(productService.updateProduct(uuid, product));
    }

    @DeleteMapping(IProductEndpoints.PRODUCT_DELETE)
    public ResponseEntity<Void> deleteProduct(@PathVariable("uuid") String uuid){
        productService.deleteProduct(uuid);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
