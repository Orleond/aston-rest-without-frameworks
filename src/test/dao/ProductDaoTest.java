package dao;

import entities.ProductEntity;
import entities.TypeEntity;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import utils.ContainerUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
public class ProductDaoTest {
    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = ContainerUtil.run();
    private ProductEntity productEntity;

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
        ProductDao productDao = ProductDao.getInstance();

        List<ProductEntity> productDaoList = productDao.findAll();
        assertEquals(16, productDaoList.size());
    }

    @Test
    void findById() {
        ProductEntity product = new ProductEntity(1, 2, 1, 1, new BigDecimal(249.00).setScale(2, RoundingMode.HALF_UP));
        ProductDao productDao = ProductDao.getInstance();

        ProductEntity secondProduct = productDao.findById(1).get();
        assertEquals(product, secondProduct);
    }

    @Test
    void save() {
        productEntity = new ProductEntity(17, 3, 4, 16, new BigDecimal(1999.00).setScale(2, RoundingMode.HALF_UP));
        ProductDao productDao = ProductDao.getInstance();
        productDao.save(productEntity);

        List<ProductEntity> productEntityList = productDao.findAll();
        assertEquals(17, productEntityList.size());
    }

    @Test
    void update() {
        ProductDao productDao = ProductDao.getInstance();
        List<ProductEntity> productEntityList = productDao.findAll();
        ProductEntity product = productEntityList.get(1);
        product.setType(1);
        productDao.update(product);

        assertEquals(1, productEntityList.get(1).getType());
    }

    @Test
    void delete() {
        ProductDao productDao = ProductDao.getInstance();
        productDao.delete(17);

        List<ProductEntity> productEntityList = productDao.findAll();
        assertEquals(16, productEntityList.size());
    }
}
