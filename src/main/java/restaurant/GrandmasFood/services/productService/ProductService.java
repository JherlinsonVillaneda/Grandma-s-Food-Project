package restaurant.GrandmasFood.services.productService;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import restaurant.GrandmasFood.common.constant.responses.IResponse;
import restaurant.GrandmasFood.common.domains.dto.ProductDTO;
import restaurant.GrandmasFood.common.domains.entity.product.ProductEntity;
import restaurant.GrandmasFood.repositories.productRepository.IProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    IProductRepository iProductRepository;

    public ProductDTO createProduct(ProductEntity product) {
        Optional<ProductEntity> find = iProductRepository.findProductByName(product.getName());
        if (find.isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, IResponse.CREATE_FAIL);
        }

        product.setUuid(UUID.randomUUID().toString());
        product.setName(product.getName().toUpperCase());
        iProductRepository.save(product);

        return ProductDTO.builder()
                .uuid(product.getUuid())
                .name(product.getName())
                .category(product.getCategory())
                .description(product.getDescription())
                .price(product.getPrice())
                .available(product.getAvailable())
                .build();

    }

    public ProductDTO getProduct(String uuid){
        ProductEntity product = iProductRepository.findProductByUuid(uuid).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND, IResponse.NOT_FOUND));

        return ProductDTO.builder()
                .uuid(product.getUuid())
                .name(product.getName())
                .category(product.getCategory())
                .description(product.getDescription())
                .price(product.getPrice())
                .available(product.getAvailable())
                .build();
    }

    public ProductDTO updateProduct(String uuid, ProductEntity product){
        ProductEntity existingProduct = iProductRepository.findProductByUuid(uuid).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND, IResponse.NOT_FOUND));

        if (equalsProducts(existingProduct, product)){
            throw new ResponseStatusException(HttpStatus.CONFLICT, IResponse.NOT_DIFFERENT_VALUES);
        } else {
            existingProduct.setName(product.getName());
            existingProduct.setCategory(product.getCategory());
            existingProduct.setDescription(product.getDescription());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setAvailable(product.getAvailable());

            iProductRepository.save(existingProduct);

            return ProductDTO.builder()
                    .uuid(existingProduct.getUuid())
                    .name(product.getName())
                    .category(product.getCategory())
                    .description(product.getDescription())
                    .price(product.getPrice())
                    .available(product.getAvailable())
                    .build();
        }
    }

    public Boolean equalsProducts(ProductEntity productExisting, ProductEntity productNew){
        return productExisting.getName().equals(productNew.getName()) &&
                productExisting.getCategory().equals(productNew.getCategory()) &&
                productExisting.getDescription().equals(productNew.getDescription()) &&
                productExisting.getPrice().equals(productNew.getPrice()) &&
                productExisting.getAvailable() == productExisting.getAvailable();
    }

    //@Transient
    public void deleteProduct(String uuid) {
        ProductEntity existingProduct = iProductRepository.findProductByUuid(uuid).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND, IResponse.NOT_FOUND));
        iProductRepository.delete(existingProduct);
    }
}
