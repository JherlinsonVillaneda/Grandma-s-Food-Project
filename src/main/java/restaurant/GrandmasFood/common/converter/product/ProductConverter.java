package restaurant.GrandmasFood.common.converter.product;

import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import restaurant.GrandmasFood.common.domains.dto.ProductDTO;
import restaurant.GrandmasFood.common.domains.entity.product.ProductEntity;
import restaurant.GrandmasFood.config.MapperConfig;

@Component
public class ProductConverter {
    public ProductEntity convertProductDtoToEntity(ProductDTO productDTO){
        ProductEntity product = new ProductEntity();
        try {
            product = MapperConfig.modelMapper().map(productDTO, ProductEntity.class);
        } catch (Exception e) {
            product = null;
        }
        return product;
    }

    public ProductDTO convertProductEntityToDto(ProductEntity productEntity){
        ProductDTO product = new ProductDTO();
        try {
            product = MapperConfig.modelMapper().map(productEntity, ProductDTO.class);
            product.setUuid(productEntity.getUuid());
        } catch (Exception e) {
            product = null;
        }
        return product;
    }
}
