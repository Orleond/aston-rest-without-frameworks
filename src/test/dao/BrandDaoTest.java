package dao;

import entities.BrandEntity;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.containers.PostgreSQLContainer;
import utils.ContainerUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
class BrandDaoTest {

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = ContainerUtil.run();
    private BrandEntity brandEntity;

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
        BrandDao brandDao = BrandDao.getInstance();

        List<BrandEntity> brandEntityList = brandDao.findAll();
        assertEquals(4, brandEntityList.size());
    }

    @Test
    void findById() {
        BrandEntity brand = new BrandEntity(1, "Braun");
        BrandDao brandDao = BrandDao.getInstance();

        BrandEntity secondBrand = brandDao.findById(1).get();
        assertEquals(brand, secondBrand);
    }

    @Test
    void save() {
        brandEntity = new BrandEntity(5, "Браун");
        BrandDao brandDao = BrandDao.getInstance();
        brandDao.save(brandEntity);

        List<BrandEntity> brandEntityList = brandDao.findAll();
        assertEquals(5, brandEntityList.size());
    }

    @Test
    void update() {
        BrandDao brandDao = BrandDao.getInstance();
        List<BrandEntity> brandEntityList = brandDao.findAll();
        BrandEntity brand = brandEntityList.get(1);
        brand.setName("Брауни");
        brandDao.update(brand);

        assertEquals("Брауни", brandEntityList.get(1).getName());

    }

    @Test
    void delete() {
        BrandDao brandDao = BrandDao.getInstance();
        brandDao.delete(5);


        List<BrandEntity> brandEntityList = brandDao.findAll();
        assertEquals(4, brandEntityList.size());
    }
}