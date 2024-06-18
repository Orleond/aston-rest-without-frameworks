package utils;

import dao.ProductDao;
import entities.ProductEntity;
import lombok.Getter;

import java.util.List;

public class InitializeUtil {
    @Getter
    private static final List<ProductEntity> products;

    static {
        ProductDao productDao = ProductDao.getInstance();
        products = productDao.findAll();
    }
    private InitializeUtil() {
    }
}
