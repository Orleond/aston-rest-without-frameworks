package controllers;

import entities.ProductEntity;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import utils.InitializeUtil;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@WebListener
public class ContextListener implements ServletContextListener {
    private Map<Integer, ProductEntity> products;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        final ServletContext servletContext = sce.getServletContext();

        products = new ConcurrentHashMap<>();

        List<ProductEntity> productList = InitializeUtil.getProducts();
        productList.forEach(product -> this.products.put(product.getProductId(), product));

        servletContext.setAttribute("products", products);

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        products = null;
    }
}
