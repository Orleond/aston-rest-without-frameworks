package mappers;

import dto.ModelDto;
import entities.ModelEntity;

public class ModelMapper {
    private static final ModelMapper INSTANCE = new ModelMapper();

    public ModelDto mapToModelDto(ModelEntity modelEntity) {
        return new ModelDto(modelEntity.getModelId(), modelEntity.getName());
    }

    public ModelEntity mapToModelEntity(ModelDto modelDto) {
        return new ModelEntity(modelDto.getModelId(), modelDto.getName());
    }

    public static ModelMapper getInstance() { return INSTANCE; }
}
