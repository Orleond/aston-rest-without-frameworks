package dao;

import entities.ModelEntity;
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
public class ModelDao {
    private static final ModelDao INSTANCE = new ModelDao();
    private static final String DELETE_SQL = "DELETE from model WHERE model_id = ?;";
    private static final String SAVE_SQL = "INSERT INTO model(name) VALUES (?);";
    private static final String UPDATE_SQL = "UPDATE model SET name = ? WHERE model_id = ?;";
    private static final String FIND_ALL_SQL = "SELECT model_id, name FROM model ";
    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + "WHERE model_id = ?;";

    private ModelDao() {
    }

    public List<ModelEntity> findAll() {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            var resultSet = preparedStatement.executeQuery();

            List<ModelEntity> modelEntityList = new ArrayList<>();
            while (resultSet.next()) {
                modelEntityList.add(buildModelEntity(resultSet));
            }

            return modelEntityList;

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Optional<ModelEntity> findById(Integer id) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setInt(1, id);
            var resultSet = preparedStatement.executeQuery();

            ModelEntity model = null;
            if (resultSet.next()) {
                model = buildModelEntity(resultSet);
            }

            return Optional.ofNullable(model);

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public ModelEntity save(ModelEntity model) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, model.getModelId());

            preparedStatement.executeUpdate();
            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next())
                model.setModelId(generatedKeys.getInt("model_id"));
            return model;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean update(ModelEntity model) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, model.getName());
            preparedStatement.setInt(2, model.getModelId());

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

    private ModelEntity buildModelEntity(ResultSet resultSet) throws SQLException {
        return new ModelEntity(
                resultSet.getInt("model_id"),
                resultSet.getString("name"));
    }

    public static ModelDao getInstance() {
        return INSTANCE;
    }
}
