package dao;

import entities.BrandEntity;
import entities.ModelEntity;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import utils.ContainerUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
public class ModelDaoTest {
    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = ContainerUtil.run();
    private ModelEntity modelEntity;

    @BeforeAll
    static void beforeAll() {
        postgreSQLContainer.start();
    }

    @AfterAll
    static void afterAll() {
        ContainerUtil.stop();
    }

    @Test
    void testConnect() {
        assertTrue(ContainerUtil.postgreSQLContainer.isRunning());
    }

    @Test
    void findAll() {
        ModelDao modelDao = ModelDao.getInstance();

        List<ModelEntity> modelEntityList = modelDao.findAll();
        assertEquals(16, modelEntityList.size());
    }

    @Test
    void findById() {
        ModelEntity model = new ModelEntity(1, "MultiQuick 5 Vario");
        ModelDao modelDao = ModelDao.getInstance();

        ModelEntity secondModel = modelDao.findById(1).get();
        assertEquals(model, secondModel);
    }

    @Test
    void save() {
        modelEntity = new ModelEntity(17, "someModel");
        ModelDao modelDao = ModelDao.getInstance();
        modelDao.save(modelEntity);

        List<ModelEntity> modelEntityList = modelDao.findAll();
        assertEquals(17, modelEntityList.size());
    }

    @Test
    void update() {
        ModelDao modelDao = ModelDao.getInstance();
        List<ModelEntity> modelEntityList = modelDao.findAll();
        ModelEntity model = modelEntityList.get(1);
        model.setName("someModel");
        modelDao.update(model);

        assertEquals("someModel", modelEntityList.get(1).getName());

    }

    @Test
    void delete() {
        ModelDao modelDao = ModelDao.getInstance();
        modelDao.delete(17);

        List<ModelEntity> modelEntityList = modelDao.findAll();
        assertEquals(16, modelEntityList.size());
    }
}
