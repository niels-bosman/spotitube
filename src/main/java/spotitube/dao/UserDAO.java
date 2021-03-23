package spotitube.dao;

import spotitube.exceptions.UnauthorizedException;
import spotitube.domain.User;
import spotitube.dto.login.LoginRequestDTO;

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
    private static final String FETCH_USER_BY_TOKEN_QUERY = "SELECT id, name from user WHERE token = ?";

    /**
     * Get a specific user
     *
     * @param requestDTO The searchable user.
     * @return The user.
     * @throws UnauthorizedException the unauthorized exception
     */
    public User get(LoginRequestDTO requestDTO) throws UnauthorizedException
    {
        User user = new User();

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(LOGIN_QUERY);
            statement.setString(1, requestDTO.getUser());
            statement.setString(2, requestDTO.getPassword());
            ResultSet result = statement.executeQuery();

            if (!result.next()) {
                throw new UnauthorizedException();
            }

            user.setId(result.getInt("id"));
            user.setUsername(result.getString("username"));
            user.setPassword(result.getString("password"));
            user.setName(result.getString("name"));
            user.setToken();
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }

        return user;
    }

    /**
     * Add token to the user.
     *
     * @param user The user to add it to.
     */
    public boolean addToken(User user)
    {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(ADD_TOKEN_QUERY);
            statement.setString(1, user.getToken());
            statement.setInt(2, user.getId());

            return statement.executeUpdate() > 0;
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }

        return false;
    }

    /**
     * Verify user token.
     *
     * @param token The given token.
     * @return The verified user.
     */
    public User verifyToken(String token)
    {
        User user = new User();

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FETCH_USER_BY_TOKEN_QUERY);
            statement.setString(1, token);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setToken(token);
            }
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }

        return (user.getName() != null) ? user : null;
    }
}
