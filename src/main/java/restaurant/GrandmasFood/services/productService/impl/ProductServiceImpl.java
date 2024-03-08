package restaurant.GrandmasFood.services.productService.impl;

import jakarta.persistence.Transient;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import restaurant.GrandmasFood.common.constant.responses.IResponse;
import restaurant.GrandmasFood.common.converter.product.ProductConverter;
import restaurant.GrandmasFood.common.domains.dto.ProductDTO;
import restaurant.GrandmasFood.common.domains.entity.product.ProductEntity;
import restaurant.GrandmasFood.exception.product.ConflictProductException;
import restaurant.GrandmasFood.exception.product.NotProductFoundException;
import restaurant.GrandmasFood.exception.product.ProductAlreadyExistsException;
import restaurant.GrandmasFood.exception.product.BadRequestProductException;
import restaurant.GrandmasFood.repository.productRepository.IProductRepository;
import restaurant.GrandmasFood.services.productService.IProductService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    IProductRepository iProductRepository;

    @Autowired
    ProductConverter productConverter;

    @Override
    public ProductDTO createProduct(@Valid ProductDTO productDto) {
        ProductEntity product = productConverter.convertProductDtoToEntity(productDto);

        Optional<ProductEntity> find = iProductRepository.findProductByName(product.getName());
        if (find.isPresent()){
            throw new ProductAlreadyExistsException("The name of the product in the request already exists.");
        }

        if (product.getPrice() < 10D){
            throw new BadRequestProductException("The value of the price is invalid.");
        }

        BigDecimal price = BigDecimal.valueOf(product.getPrice()).setScale(2, RoundingMode.HALF_UP);
        product.setPrice(Double.valueOf(price.toString()));
        product.setUuid(UUID.randomUUID().toString());

        if ((product.getName() == null || product.getName().isEmpty()) || (product.getDescription() == null || product.getDescription().isEmpty()) ) {
            throw new BadRequestProductException("The name or the description of the product is invalid.");
        } else product.setName(product.getName().toUpperCase());

        product.setRemoved(false);
        iProductRepository.save(product);

        return productConverter.convertProductEntityToDto(product);
    }

    @Override
    public ProductDTO getProduct(String uuid){

        try {
            UUID valid = UUID.fromString(uuid);
        } catch (IllegalArgumentException e) {
            throw new BadRequestProductException("The UUID given is invalid.");
        }

        ProductEntity product = iProductRepository.findProductByUuid(uuid).orElseThrow(()->
                new NotProductFoundException("Product with the UUID provided is not found."));

        return productConverter.convertProductEntityToDto(product);
    }

    @Override
    public ProductDTO updateProduct(String uuid, ProductDTO productDto){
        ProductEntity product = productConverter.convertProductDtoToEntity(productDto);

        ProductEntity existingProduct = iProductRepository.findProductByUuid(uuid).orElseThrow(()->
                new NotProductFoundException("Product with the UUID provided is not found."));

        if (equalsProducts(existingProduct, product)){
                throw new ConflictProductException("The values of the request are similar at the actual product.");
        } else {
            existingProduct.setName(product.getName());
            existingProduct.setCategory(product.getCategory());
            existingProduct.setDescription(product.getDescription());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setAvailable(product.getAvailable());

            iProductRepository.save(existingProduct);

            return productConverter.convertProductEntityToDto(existingProduct);
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
        try {
            UUID valid = UUID.fromString(uuid);
        } catch (IllegalArgumentException e) {
            throw new BadRequestProductException("The UUID given is invalid.");
        }

        ProductEntity existingProduct = iProductRepository.findProductByUuid(uuid).orElseThrow(()->
                new NotProductFoundException("Product with the UUID provided is not found."));
        iProductRepository.deleteLogicProductById(existingProduct.getId());
    }

    //Bonus track
    @Override
    public List<ProductDTO> getProductsByName(String query){
        if (query.isBlank() || query.isEmpty()){
            throw new BadRequestProductException("The value of the query is invalid.");
        }

        List<ProductEntity> productEntityList = iProductRepository.findProductsByName(query.toUpperCase());
        List<ProductDTO> productDTOList = new ArrayList<>();

        if (productEntityList.isEmpty()){
            throw new NotProductFoundException("There are no products for the given value.");
        }

        for(ProductEntity product : productEntityList){
            productDTOList.add(productConverter.convertProductEntityToDto(product));
        }

        return productDTOList;
    }
}
