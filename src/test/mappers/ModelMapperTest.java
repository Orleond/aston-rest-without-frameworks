package mappers;

import dto.ModelDto;
import entities.ModelEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ModelMapperTest {
    @Test
    void mapToModelDto() {
        ModelEntity modelEntity = new ModelEntity(1, "Model");
        ModelDto modelDto = ModelMapper.getInstance().mapToModelDto(modelEntity);
        Assertions.assertEquals(new ModelDto(1, "Model"), modelDto);
    }

    @Test
    void mapToModelEntity() {
        ModelDto modelDto = new ModelDto(1, "Model");
        ModelEntity modelEntity = ModelMapper.getInstance().mapToModelEntity(modelDto);
        Assertions.assertEquals(new ModelEntity(1, "Model"), modelEntity);
    }
}
