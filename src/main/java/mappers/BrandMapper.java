package mappers;

import dto.BrandDto;
import entities.BrandEntity;

public class BrandMapper {
    private static final BrandMapper INSTANCE = new BrandMapper();

    public BrandDto mapToBrandDto(BrandEntity brandEntity) {
        return new BrandDto(brandEntity.getBrandId(), brandEntity.getName());
    }

    public BrandEntity mapToBrandEntity(BrandDto brandDto) {
        return new BrandEntity(brandDto.getBrandId(), brandDto.getName());
    }

    public static BrandMapper getInstance() { return INSTANCE; }
}
