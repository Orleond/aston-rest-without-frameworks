package mappers;

import dto.ProductDto;
import entities.ProductEntity;

public class ProductMapper {
    private static final ProductMapper INSTANCE = new ProductMapper();

    public ProductDto mapToProductDto(ProductEntity productEntity) {
        return new ProductDto(productEntity.getProductId(),
                productEntity.getType(),
                productEntity.getBrandId(),
                productEntity.getModelId(),
                productEntity.getPrice());
    }

    public ProductEntity mapToProductEntity(ProductDto productDto) {
        return new ProductEntity(productDto.getProductId(),
                productDto.getType(),
                productDto.getBrandId(),
                productDto.getModelId(),
                productDto.getPrice());
    }

    public static ProductMapper getInstance() { return INSTANCE; }
}
