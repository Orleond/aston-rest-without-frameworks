package dao;

import entities.ProductEntity;
import exceptions.DaoException;
import lombok.Getter;
import utils.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
public class ProductDao {
    private static final ProductDao INSTANCE = new ProductDao();
    private static final String DELETE_SQL = "DELETE from product WHERE product_id = ?;";
    private static final String SAVE_SQL = "INSERT INTO product(type, brand_id, model_id, price) VALUES (?, ?, ?, ?);";
    private static final String UPDATE_SQL = "UPDATE product SET type = ?, brand_id = ?, model_id = ?, price = ? WHERE product_id = ?;";
    private static final String FIND_ALL_FULL_NAMING_SQL = "SELECT product_id AS Id, t.type AS Type, b.name AS Brand, m.name AS Model, price AS Price FROM product JOIN type t on product.type = t.type_id JOIN brand b on product.brand_id = b.brand_id JOIN model m on product.model_id = m.model_id ";
    private static final String FIND_ALL_SQL = "SELECT product_id, type, brand_id, model_id, price FROM product ";
    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + "WHERE product_id = ?;";

    private ProductDao() {
    }

    public List<ProductEntity> findAll() {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            var resultSet = preparedStatement.executeQuery();

            List<ProductEntity> productEntityList = new ArrayList<>();
            while (resultSet.next()) {
                productEntityList.add(buildProductEntity(resultSet));
            }

            return productEntityList;

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Optional<ProductEntity> findById(Integer id) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setInt(1, id);
            var resultSet = preparedStatement.executeQuery();

            ProductEntity product = null;
            if (resultSet.next()) {
                product = buildProductEntity(resultSet);
            }

            return Optional.ofNullable(product);

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public ProductEntity save(ProductEntity product) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, product.getType());
            preparedStatement.setInt(2, product.getBrandId());
            preparedStatement.setInt(3, product.getModelId());
            preparedStatement.setBigDecimal(4, product.getPrice());

            preparedStatement.executeUpdate();
            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next())
                product.setProductId(generatedKeys.getInt("product_id"));
            return product;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean update(ProductEntity product) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setInt(1, product.getType());
            preparedStatement.setInt(2, product.getBrandId());
            preparedStatement.setInt(3, product.getModelId());
            preparedStatement.setBigDecimal(4, product.getPrice());
            preparedStatement.setInt(5, product.getProductId());

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean delete(Integer id) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private ProductEntity buildProductEntity(ResultSet resultSet) throws SQLException {
        return new ProductEntity(
                resultSet.getInt("product_id"),
                resultSet.getInt("type"),
                resultSet.getInt("brand_id"),
                resultSet.getInt("model_id"),
                resultSet.getBigDecimal("price"));
    }

    public static ProductDao getInstance() {
        return INSTANCE;
    }
}
