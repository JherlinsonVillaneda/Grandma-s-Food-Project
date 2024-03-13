package restaurant.GrandmasFood.servicetest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import restaurant.GrandmasFood.common.converter.product.ProductConverter;
import restaurant.GrandmasFood.common.domains.dto.ProductDTO;
import restaurant.GrandmasFood.common.domains.entity.product.CategoryProduct;
import restaurant.GrandmasFood.common.domains.entity.product.ProductEntity;
import restaurant.GrandmasFood.exception.product.ConflictProductException;
import restaurant.GrandmasFood.exception.product.NotProductFoundException;
import restaurant.GrandmasFood.exception.product.ProductAlreadyExistsException;
import restaurant.GrandmasFood.repository.productRepository.IProductRepository;
import restaurant.GrandmasFood.services.productService.impl.ProductServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @InjectMocks
    ProductServiceImpl productService;

    @Mock
    IProductRepository iProductRepository;

    @Mock
    ProductConverter productConverter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreateProductSuccessfully(){
        //given
        ProductEntity productEntity = ProductEntity.builder()
                .uuid("1234")
                .name("COCA COLA")
                .category(CategoryProduct.KIDS_MEALS)
                .description("A soda")
                .price(10000D)
                .available(true)
                .removed(false)
                .build();

        ProductDTO productDTO = ProductDTO.builder()
                .name("COCA COLA")
                .category(CategoryProduct.KIDS_MEALS)
                .description("A soda")
                .price(10000D)
                .available(true)
                .build();

        //when
        Mockito.when(productConverter.convertProductDtoToEntity(productDTO)).thenReturn(productEntity);
        Mockito.when(iProductRepository.findProductByName(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(iProductRepository.save(productEntity)).thenReturn(productEntity);
        Mockito.when(productConverter.convertProductEntityToDto(productEntity)).thenReturn(productDTO);

        ProductDTO productSave = productService.createProduct(productDTO);

        //then
        assertThat(productSave).isNotNull();
        assertThat(productSave.getPrice()).isEqualTo(productEntity.getPrice());
        assertThat(productSave.getName()).isEqualTo(productEntity.getName());
        assertThat(productSave.getDescription()).isEqualTo(productEntity.getDescription());
        assertThat(productSave.getAvailable()).isEqualTo(productEntity.getAvailable());
    }

    @Test
    void testUpdateProductSuccessfully(){
        //given
        ProductEntity productEntity = ProductEntity.builder()
                .uuid("1234")
                .name("COCA COLA")
                .category(CategoryProduct.KIDS_MEALS)
                .description("A soda")
                .price(10000D)
                .available(true)
                .removed(false)
                .build();

        ProductEntity productEntityNew = ProductEntity.builder()
                .uuid("1234")
                .name("COCA COLA ZERO")
                .category(CategoryProduct.KIDS_MEALS)
                .description("A soda")
                .price(10000D)
                .available(true)
                .removed(false)
                .build();

        ProductDTO productDTO = ProductDTO.builder()
                .name("COCA COLA ZERO")
                .category(CategoryProduct.KIDS_MEALS)
                .description("A soda")
                .price(10000D)
                .available(true)
                .build();

        String uuid = "1234";

        //when
        Mockito.when(productConverter.convertProductDtoToEntity(productDTO)).thenReturn(productEntityNew);
        Mockito.when(iProductRepository.findProductByUuid(uuid)).thenReturn(Optional.of(productEntity));
        Mockito.when(iProductRepository.save(productEntity)).thenReturn(productEntity);
        Mockito.when(productConverter.convertProductEntityToDto(productEntity)).thenReturn(productDTO);

        ProductDTO productUpdate = productService.updateProduct(uuid, productDTO);

        //then
        assertThat(productUpdate).isNotNull();
        assertThat(productUpdate.getName()).isEqualTo(productDTO.getName());
        assertThat(productUpdate.getDescription()).isEqualTo(productDTO.getDescription());
        assertThat(productUpdate.getCategory()).isEqualTo(productDTO.getCategory());
    }

    @Test
    void testGetProductSuccessfully() {
        //given
        ProductEntity productEntity = ProductEntity.builder()
                .id(1L)
                .uuid("1234")
                .name("COCA COLA")
                .category(CategoryProduct.KIDS_MEALS)
                .description("A soda")
                .price(10000D)
                .available(true)
                .removed(false)
                .build();

        ProductDTO productDTO = ProductDTO.builder()
                .name("COCA COLA")
                .category(CategoryProduct.KIDS_MEALS)
                .description("A soda")
                .price(10000D)
                .available(true)
                .build();

        String uuid = "1234";

        //when
        Mockito.when(iProductRepository.findProductByUuid(uuid)).thenReturn(Optional.of(productEntity));
        Mockito.when(productConverter.convertProductEntityToDto(productEntity)).thenReturn(productDTO);
        ProductDTO productGet = productService.getProduct(uuid);

        //then
        assertThat(productGet).isNotNull();
        assertThat(productDTO.getName()).isEqualTo(productGet.getName());

    }

    @Test
    void testDeleteProductSuccessfully(){
        //given
        ProductEntity productEntity = ProductEntity.builder()
                .id(1L)
                .uuid("1234")
                .name("COCA COLA ZERO")
                .category(CategoryProduct.KIDS_MEALS)
                .description("A soda")
                .price(10000D)
                .available(true)
                .removed(false)
                .build();

        String uuid = "1234";

        //when
        Mockito.when(iProductRepository.findProductByUuid(uuid)).thenReturn(Optional.of(productEntity));
        productService.deleteProduct(uuid);

        //then
        Mockito.verify(iProductRepository, Mockito.times(1)).deleteLogicProductById(1L);
    }

    @Test
    void testGetProductByQuerySuccessfully(){

        //Given
        String query = "COCA";

        ProductEntity productEntity = ProductEntity.builder()
                .id(1L)
                .uuid("1234")
                .name("COCA COLA")
                .category(CategoryProduct.KIDS_MEALS)
                .description("A soda")
                .price(10000D)
                .available(true)
                .removed(false)
                .build();

        ProductDTO productDto = ProductDTO.builder()
                .name("COCA COLA")
                .category(CategoryProduct.KIDS_MEALS)
                .description("A soda")
                .price(10000D)
                .available(true)
                .build();

        List<ProductDTO> productDTOList = List.of(productDto);
        List<ProductEntity> productEntityList = List.of(productEntity);

        //When
        Mockito.when(iProductRepository.findProductsByName(query.toUpperCase())).thenReturn(productEntityList);
        Mockito.when(productConverter.convertProductEntityToDto(productEntity)).thenReturn(productDto);
        List<ProductDTO> productDTOListValidate = productService.getProductsByName(query);

        //Then
        assertThat(productDTOList).isEqualTo(productDTOListValidate);
    }

    //Exceptions test depressing
    @Test
    void testCreateProductFailed(){
        //given
        ProductEntity productEntity = ProductEntity.builder().name("coca").build();

        ProductDTO productDTO = ProductDTO.builder().name("coca").build();


        //when
        Mockito.when(productConverter.convertProductDtoToEntity(productDTO)).thenReturn(productEntity);
        Mockito.when(iProductRepository.findProductByName(Mockito.anyString())).thenReturn(Optional.of(productEntity));

        //then
        assertThrows(ProductAlreadyExistsException.class, () -> productService.createProduct(productDTO));
    }

    @Test
    void testUpdateProductFailedWithEqualsProducts(){
        //given
        ProductEntity productEntity = ProductEntity.builder()
                .uuid("1234")
                .name("COCA COLA")
                .category(CategoryProduct.KIDS_MEALS)
                .description("A soda")
                .price(10000D)
                .available(true)
                .removed(false)
                .build();

        ProductEntity productEntityNew = ProductEntity.builder()
                .uuid("1234")
                .name("COCA COLA")
                .category(CategoryProduct.KIDS_MEALS)
                .description("A soda")
                .price(10000D)
                .available(true)
                .removed(false)
                .build();

        ProductDTO productDTO = ProductDTO.builder()
                .name("COCA COLA ZERO")
                .category(CategoryProduct.KIDS_MEALS)
                .description("A soda")
                .price(10000D)
                .available(true)
                .build();

        String uuid = "1234";

        //when
        Mockito.when(productConverter.convertProductDtoToEntity(productDTO)).thenReturn(productEntityNew);
        Mockito.when(iProductRepository.findProductByUuid(uuid)).thenReturn(Optional.of(productEntity));

        //then
        assertThrows(ConflictProductException.class, () -> productService.updateProduct(uuid, productDTO));
    }

    @Test
    void testUpdateProductFailWithNotFoundProduct(){
        //given
        ProductEntity productEntityNew = ProductEntity.builder()
                .uuid("1234")
                .name("COCA COLA ZERO")
                .category(CategoryProduct.KIDS_MEALS)
                .description("A soda")
                .price(10000D)
                .available(true)
                .removed(false)
                .build();

        ProductDTO productDTO = ProductDTO.builder()
                .name("COCA COLA ZERO")
                .category(CategoryProduct.KIDS_MEALS)
                .description("A soda")
                .price(10000D)
                .available(true)
                .build();

        String uuid = "1234";

        //when
        Mockito.when(productConverter.convertProductDtoToEntity(productDTO)).thenReturn(productEntityNew);
        Mockito.when(iProductRepository.findProductByUuid(uuid)).thenReturn(Optional.empty());

        //then
        assertThrows(NotProductFoundException.class, () -> productService.updateProduct(uuid, productDTO));
    }

    @Test
    void testUpdateProductFailWithName(){
        //given
        ProductEntity productEntity = ProductEntity.builder()
                .uuid("1234")
                .name("COCA COLA")
                .category(CategoryProduct.KIDS_MEALS)
                .description("A soda")
                .price(10000D)
                .available(true)
                .removed(false)
                .build();

        ProductEntity productExisting = ProductEntity.builder()
                .uuid("1234")
                .name("COCA COLA ZERO")
                .category(CategoryProduct.KIDS_MEALS)
                .description("A soda")
                .price(10000D)
                .available(true)
                .removed(false)
                .build();

        ProductEntity productEntityNew = ProductEntity.builder()
                .uuid("1234")
                .name("COCA COLA ZERO")
                .category(CategoryProduct.KIDS_MEALS)
                .description("A soda")
                .price(10000D)
                .available(true)
                .removed(false)
                .build();

        ProductDTO productDTO = ProductDTO.builder()
                .name("COCA COLA ZERO")
                .category(CategoryProduct.KIDS_MEALS)
                .description("A soda")
                .price(10000D)
                .available(true)
                .build();

        String uuid = "1234";

        //when
        Mockito.when(productConverter.convertProductDtoToEntity(productDTO)).thenReturn(productEntityNew);
        Mockito.when(iProductRepository.findProductByUuid(uuid)).thenReturn(Optional.of(productEntity));
        Mockito.when(iProductRepository.findProductByName(productDTO.getName())).thenReturn(Optional.of(productExisting));

        //then
        assertThrows(ProductAlreadyExistsException.class, () -> productService.updateProduct(uuid, productDTO));
    }

    @Test
    void testGetProductByQueryFailed(){

        //Given
        String query = "COCA ZERO";

        //When
        Mockito.when(iProductRepository.findProductsByName(query.toUpperCase())).thenReturn(List.of());


        //Then
        assertThrows(NotProductFoundException.class, () -> productService.getProductsByName(query));
    }

}
