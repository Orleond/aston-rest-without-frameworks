package dao;

import entities.TypeEntity;
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
public class TypeDao {
    private static final TypeDao INSTANCE = new TypeDao();
    private static final String DELETE_SQL = "DELETE from type WHERE type_id = ?;";
    private static final String SAVE_SQL = "INSERT INTO type(type) VALUES (?);";
    private static final String UPDATE_SQL = "UPDATE type SET type = ? WHERE type_id = ?;";
    private static final String FIND_ALL_SQL = "SELECT type_id, type FROM type ";
    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + "WHERE type_id = ?;";


    private TypeDao() {
    }

    public List<TypeEntity> findAll() {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            var resultSet = preparedStatement.executeQuery();

            List<TypeEntity> typeEntityList = new ArrayList<>();
            while (resultSet.next()) {
                typeEntityList.add(buildTypeEntity(resultSet));
            }

            return typeEntityList;

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Optional<TypeEntity> findById(Integer id) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setInt(1, id);
            var resultSet = preparedStatement.executeQuery();

            TypeEntity type = null;
            if (resultSet.next()) {
                type = buildTypeEntity(resultSet);
            }

            return Optional.ofNullable(type);

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public TypeEntity save(TypeEntity type) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, type.getTypeId());

            preparedStatement.executeUpdate();
            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next())
                type.setTypeId(generatedKeys.getInt("type_id"));
            return type;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean update(TypeEntity type) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, type.getType());
            preparedStatement.setInt(2, type.getTypeId());

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

    private TypeEntity buildTypeEntity(ResultSet resultSet) throws SQLException {
        return new TypeEntity(
                resultSet.getInt("type_id"),
                resultSet.getString("type"));
    }

    public static TypeDao getInstance() {
        return INSTANCE;
    }
}
