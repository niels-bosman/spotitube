package spotitube;

import org.junit.jupiter.api.Test;
import spotitube.dao.UserDAO;
import spotitube.domain.User;
import spotitube.resources.LoginResource;
import spotitube.dto.login.LoginRequestDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.ws.rs.core.Response;

import org.junit.jupiter.api.BeforeEach;
import spotitube.dto.login.LoginResponseDTO;

public class LoginTest
{
    private LoginRequestDTO loginRequestDTO;
    private LoginResource loginResource;

    @BeforeEach
    public void setup()
    {
        this.loginResource = new LoginResource();
        this.loginRequestDTO = new LoginRequestDTO();
        this.loginRequestDTO.setUser("niels");
        this.loginRequestDTO.setPassword("niels");
    }

    @Test
    public void successfulLogin()
    {
        // Arrange
        User user = new User();
        user.setName(this.loginRequestDTO.getUser());
        UserDAO userDAOMock = mock(UserDAO.class);
        when(userDAOMock.get(loginRequestDTO)).thenReturn(user);
        this.loginResource.setUserDAO(userDAOMock);

        // Act
        Response response = this.loginResource.login(this.loginRequestDTO);
        LoginResponseDTO loginResponseDTO = (LoginResponseDTO) response.getEntity();

        // Assert
        assertEquals(Response.Status.OK, response.getStatusInfo());
        assertEquals(this.loginRequestDTO.getUser(), loginResponseDTO.getUser());
    }

    @Test
    public void failedLogin()
    {
        // Arrange
        UserDAO userDAOMock = mock(UserDAO.class);
        when(userDAOMock.get(loginRequestDTO)).thenReturn(null);
        this.loginResource.setUserDAO(userDAOMock);

        // Act
        Response response = this.loginResource.login(this.loginRequestDTO);

        // Assert
        assertEquals(Response.Status.UNAUTHORIZED, response.getStatusInfo());
    }
}
