package spotitube.resources;

import spotitube.exceptions.UnauthorizedException;
import org.junit.jupiter.api.Test;
import spotitube.services.UserService;
import spotitube.domain.User;
import spotitube.dto.login.LoginRequestDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.ws.rs.core.Response;

import org.junit.jupiter.api.BeforeEach;
import spotitube.dto.login.LoginResponseDTO;

public class LoginResourceTest
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
    public void successfulLogin() throws UnauthorizedException
    {
        // Arrange
        User user = new User();
        user.setName(this.loginRequestDTO.getUser());
        UserService userServiceMock = mock(UserService.class);
        when(userServiceMock.get(loginRequestDTO)).thenReturn(user);
        this.loginResource.setUserService(userServiceMock);

        // Act
        Response response = this.loginResource.login(this.loginRequestDTO);
        LoginResponseDTO loginResponseDTO = (LoginResponseDTO) response.getEntity();

        // Assert
        assertEquals(Response.Status.OK, response.getStatusInfo());
        assertEquals(this.loginRequestDTO.getUser(), loginResponseDTO.getUser());
    }

    @Test
    public void failedLogin() throws UnauthorizedException
    {
        // Arrange
        UserService userServiceMock = mock(UserService.class);
        when(userServiceMock.get(loginRequestDTO)).thenThrow(new UnauthorizedException());
        this.loginResource.setUserService(userServiceMock);

        // Act
        Response response = this.loginResource.login(this.loginRequestDTO);

        // Assert
        assertEquals(Response.Status.UNAUTHORIZED, response.getStatusInfo());
    }
}
