package spotitube.dao;

import spotitube.domain.User;
import spotitube.dto.LoginRequestDTO;

import javax.annotation.Resource;
import javax.enterprise.inject.Default;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Default
public class UserDAO
{
    @Resource(name = "jdbc/spotitube")
    DataSource dataSource;

    private static final String LOGIN_QUERY = "SELECT * FROM user WHERE username = ? AND password = ?";
    private static final String ADD_TOKEN_QUERY = "UPDATE user SET token = ? WHERE id = ?";

    public User get(LoginRequestDTO requestDTO)
    {
        User user = new User();

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(LOGIN_QUERY);
            statement.setString(1, requestDTO.getUser());
            statement.setString(2, requestDTO.getPassword());
            ResultSet result = statement.executeQuery();

            if (!result.next()) {
                return null;
            }

            user.setId(result.getInt("id"));
            user.setUsername(result.getString("username"));
            user.setPassword(result.getString("password"));
            user.setName(result.getString("name"));
            user.setToken();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return user;
    }

    public void addToken(User user)
    {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(ADD_TOKEN_QUERY);
            statement.setString(1, user.getToken());
            statement.setInt(2, user.getId());
            statement.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
