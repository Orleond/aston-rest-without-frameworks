package mappers;

import dto.TypeDto;
import entities.TypeEntity;

public class TypeMapper {
    private static final TypeMapper INSTANCE = new TypeMapper();

    public TypeDto mapToTypeDto(TypeEntity typeEntity) {
        return new TypeDto(typeEntity.getTypeId(), typeEntity.getType());
    }

    public TypeEntity mapToTypeEntity(TypeDto typeDto) {
        return new TypeEntity(typeDto.getTypeId(), typeDto.getType());
    }

    public static TypeMapper getInstance() { return INSTANCE; }
}
