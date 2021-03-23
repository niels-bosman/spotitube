package spotitube.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import spotitube.dao.UserDAO;
import spotitube.domain.User;
import spotitube.dto.login.LoginRequestDTO;
import spotitube.exceptions.UnauthorizedException;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest
{
    @Mock private UserDAO userDAO;
    private UserService userService = new UserService();
    private User dummyUser = new User();

    @BeforeEach
    public void setup()
    {
        MockitoAnnotations.openMocks(this);
        userService.setUserDAO(userDAO);

        generateDummyUser();
    }

    public void generateDummyUser()
    {
        dummyUser.setId(1);
        dummyUser.setToken();
        dummyUser.setPassword("password");
        dummyUser.setUsername("username");
        dummyUser.setName("name");
    }

    @Test public void authenticateTokenSuccessful() throws UnauthorizedException
    {
        // Arrange
        Mockito.when(userDAO.verifyToken(dummyUser.getToken())).thenReturn(dummyUser);

        // Act
        User testUser = userService.authenticateToken(dummyUser.getToken());

        // Assert
        assertNotEquals(testUser, null);
    }

    @Test public void authenticateTokenException()
    {
        // Arrange
        Mockito.when(userDAO.verifyToken(Mockito.anyString())).thenReturn(null);

        // Assert / act
        assertThrows(UnauthorizedException.class, () -> userService.authenticateToken("wrongToken"));
    }

    @Test public void get() throws UnauthorizedException
    {
        // Arrange
        Mockito.when(userDAO.get(Mockito.any(LoginRequestDTO.class))).thenReturn(dummyUser);

        // Act
        User user = userService.get(new LoginRequestDTO());

        // Assert
        assertEquals(user.getName(), dummyUser.getName());
    }

    @Test public void getException() throws UnauthorizedException
    {
        // Arrange
        Mockito.when(userDAO.get(Mockito.any(LoginRequestDTO.class))).thenThrow(UnauthorizedException.class);

        // Assert / Act
        assertThrows(UnauthorizedException.class, () -> userService.get(new LoginRequestDTO()));
    }

    @Test public void addToken()
    {
        // Arrange
        Mockito.when(userDAO.addToken(dummyUser)).thenReturn(true);

        // Assert / Act
        assertTrue(userService.addToken(dummyUser));
    }
}
