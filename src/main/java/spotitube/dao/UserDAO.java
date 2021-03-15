package spotitube.dao;

import spotitube.domain.User;

import javax.annotation.Resource;
import javax.enterprise.inject.Default;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Default
public class UserDAO
{
    @Resource(name = "jdbc/spotitube")
    DataSource dataSource;

    private static final String LOGIN_QUERY = "SELECT * FROM user WHERE username = ? AND password = ?";
    private static final String ADD_TOKEN_QUERY = "UPDATE user SET token = ? WHERE id = ?";

    public User authenticate(String username, String password)
    {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(LOGIN_QUERY);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet result = statement.executeQuery();

            if (!result.next()) {
                return null;
            }

            String token = UUID.randomUUID().toString();
            User user = new User(result.getInt("id"));

            user.setToken(token);
            user.setUsername(result.getString("username"));
            user.setPassword(result.getString("password"));
            user.setName(result.getString("name"));

            this.addToken(user, token);

            return user;

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    public void addToken(User user, String token)
    {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(ADD_TOKEN_QUERY);
            statement.setString(1, token);
            statement.setInt(2, user.getId());
            statement.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
