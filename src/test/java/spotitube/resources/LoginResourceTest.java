package spotitube.resources;

import spotitube.DummyGenerator;
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

public class LoginResourceTest extends DummyGenerator
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

    @Test public void successfulLogin() throws UnauthorizedException
    {
        // Arrange
        User user = new User();
        user.setName(this.loginRequestDTO.getUser());

        LoginResponseDTO mockResponse = new LoginResponseDTO();
        mockResponse.setUser(loginRequestDTO.getUser());

        UserService userServiceMock = mock(UserService.class);
        when(userServiceMock.authenticate(loginRequestDTO.getUser(), loginRequestDTO.getPassword()))
            .thenReturn(mockResponse);
        when(userServiceMock.addToken(user)).thenReturn(true);
        this.loginResource.setUserService(userServiceMock);

        // Act
        Response response = this.loginResource.login(this.loginRequestDTO);
        LoginResponseDTO loginResponseDTO = (LoginResponseDTO) response.getEntity();

        // Assert
        assertEquals(Response.Status.OK, response.getStatusInfo());
        assertEquals(this.loginRequestDTO.getUser(), loginResponseDTO.getUser());
    }

    @Test public void failedLogin() throws UnauthorizedException
    {
        // Arrange
        UserService userServiceMock = mock(UserService.class);
        when(userServiceMock.authenticate(DUMMY_USER.getUsername(), DUMMY_USER.getPassword())).thenThrow(new UnauthorizedException());
        this.loginResource.setUserService(userServiceMock);
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setUser(DUMMY_USER.getUsername());
        loginRequestDTO.setPassword(DUMMY_USER.getPassword());

        // Act
        Response response = this.loginResource.login(loginRequestDTO);

        // Assert
        assertEquals(Response.Status.UNAUTHORIZED, response.getStatusInfo());
    }
}
