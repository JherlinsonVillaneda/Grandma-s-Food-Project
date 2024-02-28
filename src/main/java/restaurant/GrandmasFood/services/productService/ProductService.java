package restaurant.GrandmasFood.services.productService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import restaurant.GrandmasFood.common.constant.responses.IResponse;
import restaurant.GrandmasFood.common.domains.entity.product.ProductEntity;
import restaurant.GrandmasFood.repositories.productRepository.IProductRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    IProductRepository iProductRepository;

    public ProductEntity createProduct(ProductEntity product) {
        Optional<ProductEntity> find = iProductRepository.findProductByName(product.getName());
        if (find.isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, IResponse.CREATE_FAIL);
        }
        return iProductRepository.save(product);
    }

    public ProductEntity getProduct(String uuid){
        UUID uuidConverter = UUID.fromString(uuid);
        return iProductRepository.findProductByUuid(uuidConverter).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND, IResponse.NOT_FOUND));
    }

    public void deleteProduct(String uuid) {
        UUID uuidConverter = UUID.fromString(uuid);
        ProductEntity existingProduct = iProductRepository.findProductByUuid(uuidConverter).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND, IResponse.NOT_FOUND));
        iProductRepository.deleteProductByUuid(uuidConverter);

    }
}
