package restaurant.GrandmasFood.validator.product;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import restaurant.GrandmasFood.common.constant.responses.IProductResponse;
import restaurant.GrandmasFood.common.domains.dto.ProductDTO;
import restaurant.GrandmasFood.common.domains.entity.product.CategoryProduct;
import restaurant.GrandmasFood.exception.product.BadRequestProductException;
import restaurant.GrandmasFood.exception.product.ProductValidationException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class ProductDtoValidator {

    public void validateDtoProductToCreate(ProductDTO productDTO) {
        final List<String> errorList = new ArrayList<>();

        if (productDTO == null) {
            throw new ProductValidationException("");
        }

        if (!StringUtils.hasLength(StringUtils.trimAllWhitespace(productDTO.getName()))) {
            errorList.add("product name can't be empty");
        }

        if (productDTO.getCategory() != null && !productDTO.getCategory().toString().isEmpty()) {
            try {
                CategoryProduct.valueOf(productDTO.getCategory().toString());
            } catch (IllegalArgumentException e) {
                errorList.add(String.format("product category name is invalid (%s isn't a valid Category value)", productDTO.getCategory().name()));
                throw new ProductValidationException(
                        String.format("Validation exception: %s", String.join(", ", errorList))
                );
            }
        } else {
            errorList.add("product category cannot be null or empty");
        }

        if (!StringUtils.hasLength(StringUtils.trimAllWhitespace(productDTO.getDescription()))) {
            errorList.add("product description can't be null");
        }

        if (productDTO.getPrice() < 1000D) {
            errorList.add("the price can't be less than 1000.00");
        }

        if (productDTO.getAvailable()==null){
            errorList.add("the available product can't be null.");
        }

        if (!errorList.isEmpty()) {
            throw new ProductValidationException(
                    String.format("Validation exception: %s", String.join(", ", errorList))
            );
        }
    }

    public void validateUuidFormat(String  uuid){
        try {
            UUID valid = UUID.fromString(uuid);
        } catch (IllegalArgumentException e) {
            throw new BadRequestProductException("The UUID given is invalid.");
        }
    }

    public void validateQueryParam(String query){
        if (query.isBlank() || query.isEmpty()){
            throw new BadRequestProductException("The value of the query is invalid.");
        }
    }

    public void validateDtoProductToUpdate(String uuid, ProductDTO productDTO){
        final List<String> errorList = new ArrayList<>();

        if (productDTO == null) {
            throw new ProductValidationException("");
        }

        if (!StringUtils.hasLength(StringUtils.trimAllWhitespace(productDTO.getName()))) {
            errorList.add("product name can't be empty");
        }

        if (productDTO.getCategory() != null && !productDTO.getCategory().toString().isEmpty()) {
            try {
                CategoryProduct.valueOf(productDTO.getCategory().toString());
            } catch (IllegalArgumentException e) {
                errorList.add(String.format("product category name is invalid (%s isn't a valid Category value)", productDTO.getCategory().name()));
                throw new ProductValidationException(
                        String.format("Validation exception: %s", String.join(", ", errorList))
                );
            }
        } else {
            errorList.add("product category cannot be null or empty");
        }

        if (!StringUtils.hasLength(StringUtils.trimAllWhitespace(productDTO.getDescription()))) {
            errorList.add("product description can't be null");
        }

        if (productDTO.getPrice()==null){
            errorList.add("the product price can't be null.");
        } else if (productDTO.getPrice() < 1000D) {
            errorList.add("the product price can't be less than 1000.00");
        }

        if (productDTO.getAvailable()==null){
            errorList.add("the available product can't be null.");
        }

        if (!errorList.isEmpty()) {
            throw new ProductValidationException(
                    String.format("Validation exception: %s", String.join(", ", errorList))
            );
        }
    }
}
