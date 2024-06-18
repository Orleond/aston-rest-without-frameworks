package controllers;

import dto.ProductDto;
import entities.ProductEntity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;

import static org.mockito.Mockito.*;

class ProductControllerTest {

    @Mock
    ConcurrentHashMap<Integer, ProductEntity> products = mock(ConcurrentHashMap.class);
    @InjectMocks
    ProductController productController = mock(ProductController.class);
    HttpServletRequest req = mock(HttpServletRequest.class);
    HttpServletResponse resp = mock(HttpServletResponse.class);

    @Test
    void doGet() {
        ProductEntity productEntity = new ProductEntity(1, 1, 1, 1, new BigDecimal("199.99"));
        products.put(productEntity.getProductId(), productEntity);
        String pathInfo = "/products/1";

        when(req.getPathInfo()).thenReturn(pathInfo);
        when(products.get(1)).thenReturn(productEntity);
        productController.doGet(req, resp);
        verify(req, times(1));
    }

    @Test
    void doPost() {
        when(req.getParameter("product_id")).thenReturn("1");
        when(req.getParameter("type")).thenReturn("1");
        when(req.getParameter("brand_id")).thenReturn("1");
        when(req.getParameter("model_id")).thenReturn("1");
        when(req.getParameter("price")).thenReturn("199.99");

        productController.doPost(req, resp);
        verify(req, times(1));
    }

    @Test
    void doDelete() {
        String pathInfo = "/products/1";

        when(req.getPathInfo()).thenReturn(pathInfo);
        productController.doDelete(req, resp);
        verify(req, times(1));
    }

    @Test
    void doPut() {
        ProductEntity productEntity = new ProductEntity(1, 1, 1, 1, new BigDecimal("199.99"));
        String pathInfo = "/products/1";
        when(req.getParameter("type")).thenReturn("1");
        when(req.getParameter("brand_id")).thenReturn("1");
        when(req.getParameter("model_id")).thenReturn("1");
        when(req.getParameter("price")).thenReturn("199.99");

        when(req.getPathInfo()).thenReturn(pathInfo);
        when(products.get(1)).thenReturn(productEntity);
        verify(req, times(1));
    }
}
