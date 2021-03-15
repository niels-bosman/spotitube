package spotitube.dao;

import spotitube.domain.User;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    @Resource(name = "jdbc/spotitube")
    DataSource dataSource;

    private static final String LOGIN_USER_QUERY = "SELECT * from user WHERE username = ? AND password = ?";

    public User getUser(String username, String password) {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(LOGIN_USER_QUERY);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()){
                return new User(resultSet.getInt("userId"));
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return null;
    }
}
