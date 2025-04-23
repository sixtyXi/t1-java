package t1.task4;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDao {
    private final HikariDataSource dataSource;

    public UserDao(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public long create(User user) throws Exception {
        if (user == null || user.getUsername() == null) {
            throw new IllegalArgumentException("username не может быть null");
        }
        String sql = "insert into users (username) values (?) returning id";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getUsername());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                   return resultSet.getLong("id");
                }
                throw new DataAccessException("Ошибка создания пользователя");
            } catch (SQLException e) {
                throw new DataAccessException("Ошибка при создании пользователя", e);
            }
        }
    }

    public User get(long id) throws Exception {
        String sql = "select * from users where id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new User(resultSet.getLong("id"), resultSet.getString("username"));
                }
                throw new DataAccessException("Не найден пользователь с id: " + id);
            } catch (SQLException e) {
                throw new DataAccessException("Ошибка получения пользователя", e);
            }
        }
    }

    public void update(User user) throws Exception {
        if (user == null || user.getId() == null || user.getUsername() == null || user.getUsername().isEmpty()) {
            throw new IllegalArgumentException("id, username не должны быть null");
        }

        String sql = "update users set username = ? where id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setLong(2, user.getId());

            int updated = preparedStatement.executeUpdate();
            if (updated != 1) {
                throw new DataAccessException("Не найден пользователь с id: " + user.getId());
            }
        } catch (SQLException e) {
            throw new DataAccessException("Ошибка обновления пользователя", e);
        }
    }

    public void delete(long id) throws Exception {
        String sql = "delete from users where id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            int deleted = preparedStatement.executeUpdate();
            if (deleted != 1) {
                throw new DataAccessException("Не найден пользователь с id: " + id);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Ошибка при удалении пользователя", e);
        }
    }

    public List<User> getAll() throws Exception {
        String sql = "select * from users";

        try (Connection connection = dataSource.getConnection();) {
            try (ResultSet resultSet = connection.createStatement().executeQuery(sql)) {
                List<User> users = new ArrayList<>();
                while (resultSet.next()) {
                    users.add(new User(resultSet.getLong("id"), resultSet.getString("username")));
                }
                return users;
            }
        } catch (SQLException e) {
            throw new DataAccessException("Ошибка при получении списка пользователей", e);
        }
    }

}
