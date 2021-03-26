package spotitube.daos;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import spotitube.data.dao.UserDAO;
import spotitube.data.domain.User;
import spotitube.dto.login.LoginRequestDTO;
import spotitube.exceptions.UnauthorizedException;
import spotitube.DummyGenerator;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class UserDAOTest extends DummyGenerator
{
    private UserDAO userDAO = new UserDAO();

    @Test public void getSuccessful() throws UnauthorizedException, SQLException
    {
        // Arrange
        String expectedQuery = "SELECT * FROM user WHERE username = ? AND password = ?";
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setUser(DUMMY_USER.getName());
        loginRequestDTO.setPassword(DUMMY_USER.getName());

        DataSource dataSource = mock(DataSource.class);
        Connection connection = mock(Connection.class);
        ResultSet result = mock(ResultSet.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);

        userDAO.setDataSource(dataSource);

        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(expectedQuery)).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeQuery()).thenReturn(result);
        Mockito.when(result.next()).thenReturn(true);
        Mockito.when(result.getInt("id")).thenReturn(DUMMY_USER.getId());
        Mockito.when(result.getString("username")).thenReturn(DUMMY_USER.getUsername());
        Mockito.when(result.getString("password")).thenReturn(DUMMY_USER.getPassword());
        Mockito.when(result.getString("name")).thenReturn(DUMMY_USER.getName());

        // Act
        User user = userDAO.get(loginRequestDTO.getUser(), loginRequestDTO.getPassword());

        // Assert
        assertEquals(user.getId(), DUMMY_USER.getId());
    }

    @Test public void getUnauthorized() throws SQLException
    {
        // Arrange
        String expectedQuery = "SELECT * FROM user WHERE username = ? AND password = ?";
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setUser(DUMMY_USER.getUsername());
        loginRequestDTO.setPassword(DUMMY_USER.getPassword());

        DataSource dataSource = mock(DataSource.class);
        Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet result = mock(ResultSet.class);

        userDAO.setDataSource(dataSource);

        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(expectedQuery)).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeQuery()).thenReturn(result);
        Mockito.when(result.next()).thenReturn(false);

        // Act / assert
        assertNull(userDAO.get(loginRequestDTO.getUser(), loginRequestDTO.getPassword()));
    }

    @Test public void getSQLException() throws SQLException, UnauthorizedException
    {
        // Arrange
        String expectedQuery = "SELECT * FROM user WHERE username = ? AND password = ?";
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setUser(DUMMY_USER.getUsername());
        loginRequestDTO.setPassword(DUMMY_USER.getPassword());

        DataSource dataSource = mock(DataSource.class);
        Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);

        userDAO.setDataSource(dataSource);

        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(expectedQuery)).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeQuery()).thenThrow(new SQLException());

        // Act
        User user = userDAO.get(loginRequestDTO.getUser(), loginRequestDTO.getPassword());

        // Assert
        assertNull(user.getName());
    }

    @Test public void verifyTokenSuccessful() throws SQLException, UnauthorizedException
    {
        // Arrange
        String expectedQuery = "SELECT id, name FROM user WHERE token = ?";

        DataSource dataSource = mock(DataSource.class);
        Connection connection = mock(Connection.class);
        ResultSet result = mock(ResultSet.class);
        PreparedStatement statement = mock(PreparedStatement.class);

        userDAO.setDataSource(dataSource);

        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(expectedQuery)).thenReturn(statement);
        Mockito.when(statement.executeQuery()).thenReturn(result);
        Mockito.when(result.next()).thenReturn(true);
        Mockito.when(result.getInt("id")).thenReturn(DUMMY_USER.getId());
        Mockito.when(result.getString("name")).thenReturn(DUMMY_USER.getName());

        // Act
        User user = userDAO.verifyToken("token");

        // Assert
        assertEquals(user.getId(), DUMMY_USER.getId());
    }

    @Test public void verifyTokenError() throws SQLException, UnauthorizedException
    {
        // Arrange
        String expectedQuery = "SELECT id, name FROM user WHERE token = ?";

        DataSource dataSource = mock(DataSource.class);
        Connection connection = mock(Connection.class);
        ResultSet result = mock(ResultSet.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);

        userDAO.setDataSource(dataSource);

        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(expectedQuery)).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeQuery()).thenThrow(SQLException.class);
        Mockito.when(result.next()).thenReturn(false);

        // Act / assert
        assertThrows(UnauthorizedException.class, () -> userDAO.verifyToken("token"));
    }

    @Test public void addTokenSuccessful() throws SQLException
    {
        // Arrange
        String expectedQuery = "UPDATE user SET token = ? WHERE id = ?";

        DataSource dataSource = mock(DataSource.class);
        Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);

        userDAO.setDataSource(dataSource);

        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(expectedQuery)).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeUpdate()).thenReturn(1);

        // Act / assert
        assertTrue(userDAO.addToken(DUMMY_USER));
    }

    @Test public void addTokenSQLException() throws SQLException
    {
        // Arrange
        String expectedQuery = "UPDATE user SET token = ? WHERE id = ?";

        DataSource dataSource = mock(DataSource.class);
        Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);

        userDAO.setDataSource(dataSource);

        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(expectedQuery)).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeUpdate()).thenThrow(SQLException.class);

        // Act / assert
        assertFalse(userDAO.addToken(DUMMY_USER));
    }
}
