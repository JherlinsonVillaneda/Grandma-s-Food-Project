package restaurant.GrandmasFood.services.productService.impl;

import jakarta.persistence.Transient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import restaurant.GrandmasFood.common.converter.product.ProductConverter;
import restaurant.GrandmasFood.common.domains.dto.ProductDTO;
import restaurant.GrandmasFood.common.domains.entity.product.ProductEntity;
import restaurant.GrandmasFood.exception.product.ConflictProductException;
import restaurant.GrandmasFood.exception.product.NotProductFoundException;
import restaurant.GrandmasFood.exception.product.ProductAlreadyExistsException;
import restaurant.GrandmasFood.repository.productRepository.IProductRepository;
import restaurant.GrandmasFood.services.productService.IProductService;
import restaurant.GrandmasFood.validator.product.ProductDtoValidator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    IProductRepository iProductRepository;

    @Autowired
    ProductConverter productConverter;

    @Autowired
    ProductDtoValidator productDtoValidator;

    @Override
    public ProductDTO createProduct(ProductDTO productDto) {
        productDtoValidator.validateDtoProductToCreate(productDto);
        ProductEntity product = productConverter.convertProductDtoToEntity(productDto);

        validateIfProductExistsByName(productDto.getName());

        BigDecimal price = BigDecimal.valueOf(product.getPrice()).setScale(2, RoundingMode.HALF_UP);
        product.setPrice(Double.valueOf(price.toString()));
        product.setUuid(UUID.randomUUID().toString());

        product.setName(product.getName().toUpperCase());

        product.setRemoved(false);
        iProductRepository.save(product);

        return productConverter.convertProductEntityToDto(product);
    }

    @Override
    public ProductDTO getProduct(String uuid){
        productDtoValidator.validateUuidFormat(uuid);
        ProductEntity product = getProductByUuid(uuid);

        return productConverter.convertProductEntityToDto(product);
    }

    @Override
    public ProductDTO updateProduct(String uuid, ProductDTO productDto){
        productDtoValidator.validateDtoProductToUpdate(uuid, productDto);
        ProductEntity product = productConverter.convertProductDtoToEntity(productDto);

        ProductEntity existingProduct = getProductByUuid(uuid);

        if (!product.getName().equals(existingProduct.getName())) validateIfProductExistsByName(product.getName());

        equalsProducts(existingProduct, product);

        existingProduct.setName(product.getName().toUpperCase());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setAvailable(product.getAvailable());

        iProductRepository.save(existingProduct);

        return productConverter.convertProductEntityToDto(existingProduct);
    }

    @Override
    @Transient
    public void deleteProduct(String uuid) {
        productDtoValidator.validateUuidFormat(uuid);
        ProductEntity existingProduct = getProductByUuid(uuid);
        iProductRepository.deleteLogicProductById(existingProduct.getId());
    }

    //Bonus track
    @Override
    public List<ProductDTO> getProductsByName(String query){
        productDtoValidator.validateQueryParam(query);

        List<ProductEntity> productEntityList = iProductRepository.findProductsByName(query.toUpperCase());

        validateListOfProducts(productEntityList);

        return productEntityList.stream().map(p -> productConverter.convertProductEntityToDto(p)).toList();

    }

    //Auxiliaries
    private ProductEntity getProductByUuid(String uuid){
        return iProductRepository.findProductByUuid(uuid).orElseThrow(()->
                new NotProductFoundException("Product with the UUID provided is not found."));
    }

    private void validateIfProductExistsByName(String name) {
        if (iProductRepository.findProductByName(name).isPresent()){
            throw new ProductAlreadyExistsException(String.format("The name %s in the product in the request already exists.", name));
        }
    }


    private void equalsProducts(ProductEntity productExisting, ProductEntity productNew){
        if(
                productExisting.getName().equals(productNew.getName()) &&
                productExisting.getCategory().equals(productNew.getCategory()) &&
                productExisting.getDescription().equals(productNew.getDescription()) &&
                productExisting.getPrice().equals(productNew.getPrice()) &&
                productExisting.getAvailable() == productExisting.getAvailable()
        ) throw new ConflictProductException("The values of the request are similar at the actual product.");;
    }

    private void validateListOfProducts(List<ProductEntity> productEntities){
        if (productEntities.isEmpty()){
            throw new NotProductFoundException("There are no products for the given value.");
        }
    }
}
