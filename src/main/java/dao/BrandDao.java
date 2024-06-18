package dao;

import entities.BrandEntity;
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
public class BrandDao {
    private static final BrandDao INSTANCE = new BrandDao();
    private static final BrandDao TEST_INSTANCE = new BrandDao();
    private static final String DELETE_SQL = "DELETE from brand WHERE brand_id = ?;";
    private static final String SAVE_SQL = "INSERT INTO brand(name) VALUES (?);";
    private static final String UPDATE_SQL = "UPDATE brand SET name = ? WHERE brand_id = ?;";
    private static final String FIND_ALL_SQL = "SELECT brand_id, name FROM brand ";
    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + " WHERE brand_id = ?";

    private BrandDao() {
    }
    private BrandDao(String userName, String password, String dbName) {

    }

    public List<BrandEntity> findAll() {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            var resultSet = preparedStatement.executeQuery();

            List<BrandEntity> brandEntityList = new ArrayList<>();
            while (resultSet.next()) {
                brandEntityList.add(buildBrandEntity(resultSet));
            }

            return brandEntityList;

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Optional<BrandEntity> findById(Integer id) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setInt(1, id);
            var resultSet = preparedStatement.executeQuery();

            BrandEntity brand = null;
            if (resultSet.next()) {
                brand = buildBrandEntity(resultSet);
            }

            return Optional.ofNullable(brand);

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public BrandEntity save(BrandEntity brand) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, brand.getBrandId());

            preparedStatement.executeUpdate();
            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next())
                brand.setBrandId(generatedKeys.getInt("brand_id"));
            return brand;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean update(BrandEntity brand) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SAVE_SQL)) {
            preparedStatement.setString(1, brand.getName());

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

    private BrandEntity buildBrandEntity(ResultSet resultSet) throws SQLException {
        return new BrandEntity(
                resultSet.getInt("brand_id"),
                resultSet.getString("name"));
    }

    public static BrandDao getInstance() {
        return INSTANCE;
    }
}
