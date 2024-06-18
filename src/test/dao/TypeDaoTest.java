package dao;

import entities.ModelEntity;
import entities.TypeEntity;
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
public class TypeDaoTest {
    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = ContainerUtil.run();
    private TypeEntity typeEntity;

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
        TypeDao typeDao = TypeDao.getInstance();

        List<TypeEntity> typeEntityList = typeDao.findAll();
        assertEquals(3, typeEntityList.size());
    }

    @Test
    void findById() {
        TypeEntity type = new TypeEntity(1, "Миксер");
        TypeDao typeDao = TypeDao.getInstance();

        TypeEntity secondType = typeDao.findById(1).get();
        assertEquals(type, secondType);
    }

    @Test
    void save() {
        typeEntity = new TypeEntity(4, "someType");
        TypeDao typeDao = TypeDao.getInstance();
        typeDao.save(typeEntity);

        List<TypeEntity> typeEntityList = typeDao.findAll();
        assertEquals(4, typeEntityList.size());
    }

    @Test
    void update() {
        TypeDao typeDao = TypeDao.getInstance();
        List<TypeEntity> typeEntityList = typeDao.findAll();
        TypeEntity type = typeEntityList.get(1);
        type.setType("someType");
        typeDao.update(type);

        assertEquals("someType", typeEntityList.get(1).getType());

    }

    @Test
    void delete() {
        TypeDao typeDao = TypeDao.getInstance();
        typeDao.delete(4);

        List<TypeEntity> typeEntityList = typeDao.findAll();
        assertEquals(3, typeEntityList.size());
    }
}
