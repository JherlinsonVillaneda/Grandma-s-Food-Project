package restaurant.GrandmasFood.common.converter.product;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.stereotype.Component;
import restaurant.GrandmasFood.common.domains.entity.product.CategoryProduct;
import restaurant.GrandmasFood.exception.product.ProductValidationException;

import java.io.IOException;

@Component
public class CategoryProductDeserializer extends JsonDeserializer<CategoryProduct> {
    @Override
    public CategoryProduct deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String value = jsonParser.getValueAsString();
        if (value != null && !value.isEmpty()) {
            try {
                return CategoryProduct.valueOf(value);
            } catch (IllegalArgumentException e) {
                throw new ProductValidationException("FATAL: The name of the product category has errors. Operation cancelled.");
            }
        } else {
            return null;
        }
    }

}
