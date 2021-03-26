package spotitube.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import spotitube.DummyGenerator;
import spotitube.data.dao.UserDAO;
import spotitube.domain.User;
import spotitube.dto.login.LoginRequestDTO;
import spotitube.dto.login.LoginResponseDTO;
import spotitube.exceptions.UnauthorizedException;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest extends DummyGenerator
{
    @Mock private UserDAO userDAO;
    private UserService userService = new UserService();

    @BeforeEach
    public void setup()
    {
        MockitoAnnotations.openMocks(this);
        userService.setUserDAO(userDAO);
    }

    @Test public void authenticateTokenSuccessful() throws UnauthorizedException
    {
        // Arrange
        Mockito.when(userDAO.verifyToken(DUMMY_USER.getToken())).thenReturn(DUMMY_USER);

        // Act
        int userId = userService.authenticateToken(DUMMY_USER.getToken());

        // Assert
        assertEquals(userId, DUMMY_USER.getId());
    }

    @Test public void authenticateTokenException() throws UnauthorizedException
    {
        // Arrange
        Mockito.when(userDAO.verifyToken(Mockito.anyString())).thenThrow(UnauthorizedException.class);

        // Assert / act
        assertThrows(UnauthorizedException.class, () -> userService.authenticateToken("wrongToken"));
    }

    @Test public void get() throws UnauthorizedException
    {
        // Arrange
        Mockito.when(userDAO.get(Mockito.anyString(), Mockito.anyString())).thenReturn(DUMMY_USER);
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setUser(DUMMY_USER.getName());
        loginRequestDTO.setPassword(DUMMY_USER.getPassword());

        // Act
        User user = userService.get(loginRequestDTO);

        // Assert
        assertEquals(user.getName(), DUMMY_USER.getName());
    }

    @Test public void authenticate() throws UnauthorizedException
    {
        // Arrange
        Mockito.when(userDAO.get(Mockito.anyString(), Mockito.anyString())).thenReturn(DUMMY_USER);
        Mockito.when(userDAO.addToken(DUMMY_USER)).thenReturn(true);

        // Act
        LoginResponseDTO loginResponseDTO = userService.authenticate(DUMMY_USER.getUsername(), DUMMY_USER.getPassword());

        // Assert
        assertEquals(loginResponseDTO.getUser(), DUMMY_USER.getName());
    }

    @Test public void authenticateException() throws UnauthorizedException
    {
        // Arrange
        Mockito.when(userDAO.get(Mockito.anyString(), Mockito.anyString())).thenReturn(null);

        // Act / assert
        assertThrows(UnauthorizedException.class, () -> {
            userService.authenticate(DUMMY_USER.getUsername(), DUMMY_USER.getPassword());
        });
    }

    @Test public void getException()
    {
        // Arrange
        Mockito.when(userDAO.get(Mockito.anyString(), Mockito.anyString())).thenReturn(null);
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setUser(DUMMY_USER.getName());
        loginRequestDTO.setPassword(DUMMY_USER.getPassword());

        // Assert / Act
        assertThrows(UnauthorizedException.class, () -> userService.get(loginRequestDTO));
    }

    @Test public void addToken()
    {
        // Arrange
        Mockito.when(userDAO.addToken(DUMMY_USER)).thenReturn(true);

        // Assert / Act
        assertTrue(userService.addToken(DUMMY_USER));
    }
}
