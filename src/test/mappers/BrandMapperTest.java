package mappers;

import dto.BrandDto;
import entities.BrandEntity;
import org.junit.jupiter.api.Assertions;
import org.testng.annotations.Test;

class BrandMapperTest {
    @Test
    void mapToBrandDto() {
        BrandEntity brandEntity = new BrandEntity(1, "Brand");
        BrandDto brandDto = BrandMapper.getInstance().mapToBrandDto(brandEntity);
        Assertions.assertEquals(new BrandDto(1, "Brand"), brandDto);
    }

    @Test
    void mapToBrandEntity() {
        BrandDto brandDto = new BrandDto(1, "Brand");
        BrandEntity brandEntity = BrandMapper.getInstance().mapToBrandEntity(brandDto);
        Assertions.assertEquals(new BrandEntity(1, "Brand"), brandEntity);
    }
}
