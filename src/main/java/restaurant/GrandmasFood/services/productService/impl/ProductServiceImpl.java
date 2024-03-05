package restaurant.GrandmasFood.services.productService.impl;

import jakarta.persistence.Transient;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import restaurant.GrandmasFood.common.constant.responses.IResponse;
import restaurant.GrandmasFood.common.converter.product.ProductConverter;
import restaurant.GrandmasFood.common.domains.dto.ProductDTO;
import restaurant.GrandmasFood.common.domains.entity.product.ProductEntity;
import restaurant.GrandmasFood.repository.productRepository.IProductRepository;
import restaurant.GrandmasFood.services.productService.IProductService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
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
            throw new ResponseStatusException(HttpStatus.CONFLICT, String.format(IResponse.CREATE_FAIL_NAME_EXISTS, product.getName()));
        }

        if (product.getPrice() < 10D){
            throw new ResponseStatusException(HttpStatus.CONFLICT, IResponse.PRICE_NOT_VALID);
        }

        BigDecimal price = BigDecimal.valueOf(product.getPrice()).setScale(2, RoundingMode.HALF_UP);
        product.setPrice(Double.valueOf(price.toString()));
        product.setUuid(UUID.randomUUID().toString());

        if ((product.getName() == null || product.getName().isEmpty()) || (product.getDescription() == null || product.getDescription().isEmpty()) ) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, IResponse.ERRORS_WITH_NAMES_OR_DESCRIPTION);
        } else product.setName(product.getName().toUpperCase());

        product.setRemoved(false);
        iProductRepository.save(product);

        return productConverter.convertProductEntityToDto(product);
    }

    @Override
    public ProductDTO getProduct(String uuid){

        try {
            UUID valid = UUID.fromString(uuid);
            System.out.println("The UUID is valid.");
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, IResponse.GET_FAIL_WITH_UUID);
        }

        ProductEntity product = iProductRepository.findProductByUuid(uuid).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND, IResponse.GET_FAIL_PRODUCT_NOT_FOUND));

        return productConverter.convertProductEntityToDto(product);
    }

    @Override
    public ProductDTO updateProduct(String uuid, ProductDTO productDto){
        ProductEntity product = productConverter.convertProductDtoToEntity(productDto);

        ProductEntity existingProduct = iProductRepository.findProductByUuid(uuid).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND, IResponse.GET_FAIL_WITH_UUID));

        if (equalsProducts(existingProduct, product)){
            throw new ResponseStatusException(HttpStatus.CONFLICT, IResponse.NOT_DIFFERENT_VALUES);
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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, IResponse.GET_FAIL_WITH_UUID);
        }

        ProductEntity existingProduct = iProductRepository.findProductByUuid(uuid).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND, IResponse.GET_FAIL_PRODUCT_NOT_FOUND));
        iProductRepository.deleteLogicProductById(existingProduct.getId());
    }
}
