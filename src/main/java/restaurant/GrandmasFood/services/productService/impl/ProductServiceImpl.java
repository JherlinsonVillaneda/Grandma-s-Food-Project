package restaurant.GrandmasFood.services.productService.impl;

import jakarta.persistence.Transient;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import restaurant.GrandmasFood.common.constant.responses.IResponse;
import restaurant.GrandmasFood.common.domains.dto.ProductDTO;
import restaurant.GrandmasFood.common.domains.entity.product.ProductEntity;
import restaurant.GrandmasFood.repositories.productRepository.IProductRepository;
import restaurant.GrandmasFood.services.productService.IProductService;

import java.text.DecimalFormat;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    IProductRepository iProductRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public ProductDTO createProduct(ProductEntity product) {
        Optional<ProductEntity> find = iProductRepository.findProductByName(product.getName());
        if (find.isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, IResponse.CREATE_FAIL);
        }
        DecimalFormat df = new DecimalFormat("#.00");

        if (product.getPrice() < 10D){
            throw new ResponseStatusException(HttpStatus.CONFLICT, IResponse.PRICE_NOT_VALID);
        }

        product.setPrice(Double.parseDouble(df.format(product.getPrice())));

        product.setUuid(UUID.randomUUID().toString());
        product.setName(product.getName().toUpperCase());
        product.setRemoved(false);
        iProductRepository.save(product);

        return modelMapper.map(product, ProductDTO.class);

    }

    @Override
    public ProductDTO getProduct(String uuid){
        ProductEntity product = iProductRepository.findProductByUuid(uuid).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND, IResponse.NOT_FOUND));

        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
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

            return modelMapper.map(existingProduct, ProductDTO.class);
        }
    }

    @Override
    public Boolean equalsProducts(ProductEntity productExisting, ProductEntity productNew){
        return productExisting.getName().equals(productNew.getName()) &&
                productExisting.getCategory().equals(productNew.getCategory()) &&
                productExisting.getDescription().equals(productNew.getDescription()) &&
                productExisting.getPrice().equals(productNew.getPrice()) &&
                productExisting.getAvailable() == productExisting.getAvailable();
    }

    @Override
    @Transient
    public void deleteProduct(String uuid) {
        ProductEntity existingProduct = iProductRepository.findProductByUuid(uuid).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND, IResponse.NOT_FOUND));
        iProductRepository.deleteLogicProductById(existingProduct.getId());
    }
}
