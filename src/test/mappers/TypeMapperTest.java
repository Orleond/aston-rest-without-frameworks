package mappers;

import dto.TypeDto;
import entities.TypeEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TypeMapperTest {
    @Test
    void mapToTypeDto() {
        TypeEntity typeEntity = new TypeEntity(1, "Type");
        TypeDto typeDto = TypeMapper.getInstance().mapToTypeDto(typeEntity);
        Assertions.assertEquals(new TypeDto(1, "Type"), typeDto);
    }

    @Test
    void mapToTypeEntity() {
        TypeDto typeDto = new TypeDto(1, "Type");
        TypeEntity typeEntity = TypeMapper.getInstance().mapToTypeEntity(typeDto);
        Assertions.assertEquals(new TypeEntity(1, "Type"), typeEntity);
    }
}
