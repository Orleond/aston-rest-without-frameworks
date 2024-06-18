package mappers;

import dto.ProductDto;
import entities.ProductEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class ProductMapperTest {

    @Test
    void mapToProductDto() {
        ProductEntity productEntity = new ProductEntity(1, 1, 1, 1,
                new BigDecimal("199.99"));
        ProductDto productDto = ProductMapper.getInstance().mapToProductDto(productEntity);
        Assertions.assertEquals(new ProductDto(1,1, 1, 1,
                new BigDecimal("199.99")), productDto);
    }

    @Test
    void mapToProductEntity() {
        ProductDto productDto = new ProductDto(1, 1, 1, 1,
                new BigDecimal("199.99"));
        ProductEntity productEntity = ProductMapper.getInstance().mapToProductEntity(productDto);
        Assertions.assertEquals(new ProductEntity(1, 1, 1, 1,
                new BigDecimal("199.99")), productEntity);
    }
}
