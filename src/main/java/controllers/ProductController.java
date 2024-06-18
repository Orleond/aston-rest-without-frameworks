package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.ProductDao;
import dto.ProductDto;
import entities.ProductEntity;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mappers.ProductMapper;
import utils.Utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@WebServlet(urlPatterns = "/products/*")
public class ProductController extends HttpServlet {
    private transient Map<Integer, ProductEntity> products;
    public static final String UTF_EIGHT = "UTF-8";

    @SuppressWarnings("unchecked")
    @Override
    public void init() {
        Object productsObject = getServletContext().getAttribute("products");
        if (!(productsObject instanceof ConcurrentHashMap)) {
            throw new IllegalStateException("Your repo does not initialize!");
        } else {
            products = (ConcurrentHashMap<Integer, ProductEntity>) getServletContext().getAttribute("products");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String pathInfo = req.getPathInfo();
        String[] parts = pathInfo.split("/");
        String param1 = parts[1];
        int taskId = -1;
        try {
             taskId = Integer.parseInt(param1);
        } catch (NumberFormatException e) {
            e.getStackTrace();
        }
        try {
            req.setCharacterEncoding(UTF_EIGHT);
        } catch (UnsupportedEncodingException e) {
            e.getStackTrace();
        }

        final ProductDto productDto = ProductMapper.getInstance().mapToProductDto(products.get(taskId));
        String json;
        try {
            json = new ObjectMapper().writeValueAsString(productDto);
            resp.getWriter().write(json);
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

        try {
            req.setCharacterEncoding(UTF_EIGHT);
        } catch (UnsupportedEncodingException e) {
            e.getStackTrace();
        }
        if (Utils.requestIsValid(req)) {
            int id = -1;
            int type = -1;
            int brand = -1;
            int model = -1;
            BigDecimal price = new BigDecimal(-1);

            try {
                id = Integer.parseInt(req.getParameter("product_id"));
                type = Integer.parseInt(req.getParameter("type"));
                brand = Integer.parseInt(req.getParameter("brand_id"));
                model = Integer.parseInt(req.getParameter("model_id"));
                price = BigDecimal.valueOf(Double.parseDouble(req.getParameter("price")));
            } catch (NumberFormatException e) {
                e.getStackTrace();
            }

            ProductDto product = new ProductDto(id, type, model, brand, price);
            ProductEntity productEntity = ProductMapper.getInstance().mapToProductEntity(product);
            ProductDao productDao = ProductDao.getInstance();
            products.put(product.getProductId(), productDao.save(productEntity));
        }
        resp.setStatus(201);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        String pathInfo = req.getPathInfo();
        String[] parts = pathInfo.split("/");
        int param1 = -1;
        try {
            param1 = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            e.getStackTrace();
        }

        if (param1 != -1) {
            ProductDao productDao = ProductDao.getInstance();
            productDao.delete(param1);
            products.remove(param1);
            resp.setStatus(202);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        String pathInfo = req.getPathInfo();
        String[] parts = pathInfo.split("/");
        int productId = -1;
        try {
            productId = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            e.getStackTrace();
        }

        try {
            req.setCharacterEncoding(UTF_EIGHT);
        } catch (UnsupportedEncodingException e) {
            e.getStackTrace();
        }

        int type = -1;
        int brand = -1;
        int model = -1;
        BigDecimal price = new BigDecimal(-1);

        try {
            type = Integer.parseInt(req.getParameter("type"));
            brand = Integer.parseInt(req.getParameter("brand_id"));
            model = Integer.parseInt(req.getParameter("model_id"));
            price = BigDecimal.valueOf(Double.parseDouble(req.getParameter("price")));
        } catch (NumberFormatException e) {
            e.getStackTrace();
        }

        ProductDto product = null;
        try {
            product = ProductMapper.getInstance().mapToProductDto(products.get(productId));
        } catch (NumberFormatException e) {
            e.getStackTrace();
        }
        if (product != null) {
            product.setType(type);
            product.setBrandId(brand);
            product.setModelId(model);
            product.setPrice(price);
            ProductDao productDao = ProductDao.getInstance();
            productDao.update(ProductMapper.getInstance().mapToProductEntity(product));
        }
        resp.setStatus(202);
    }
}
