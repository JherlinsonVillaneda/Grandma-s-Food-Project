package restaurant.GrandmasFood.webApi.productController;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restaurant.GrandmasFood.common.constant.endpoints.IProductEndpoints;
import restaurant.GrandmasFood.common.domains.dto.ProductDTO;
import restaurant.GrandmasFood.services.productService.impl.ProductServiceImpl;
import restaurant.GrandmasFood.validator.product.ProductDtoValidator;
import restaurant.GrandmasFood.webApi.orderController.OrderController;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    ProductServiceImpl productService;

    @PostMapping(IProductEndpoints.PRODUCT_CREATE)
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO){
        ProductDTO productSaved = productService.createProduct(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(productSaved);
    }

    @GetMapping(IProductEndpoints.PRODUCT_GET)
    public ResponseEntity<ProductDTO> getProduct(@PathVariable("uuid") String  uuid){
        return new ResponseEntity<>(productService.getProduct(uuid), HttpStatus.OK);
    }

    @PutMapping(IProductEndpoints.PRODUCT_PUT)
    public ResponseEntity<Void> updateProduct(@PathVariable("uuid") String uuid, @RequestBody ProductDTO productDto){

        productService.updateProduct(uuid, productDto);
         return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping(IProductEndpoints.PRODUCT_DELETE)
    public ResponseEntity<Void> deleteProduct(@PathVariable("uuid") String uuid){
        productService.deleteProduct(uuid);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    //Bonus track
    @GetMapping(IProductEndpoints.GET_PRODUCT_WITH_PARAM)
    public ResponseEntity<List<ProductDTO>> getProducts(@RequestParam("q") String q){
        return new ResponseEntity<>(productService.getProductsByName(q), HttpStatus.OK);
    }


}
