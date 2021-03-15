package spotitube;

import org.junit.jupiter.api.Test;
import spotitube.dao.UserDAO;
import spotitube.domain.User;
import spotitube.services.AuthenticationService;
import spotitube.services.dto.AuthenticationDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.ws.rs.core.Response;

import org.junit.jupiter.api.BeforeEach;
import spotitube.services.dto.AuthenticationResponseDTO;

public class AuthenticationTest
{

    private AuthenticationDTO authenticationDTO;
    private AuthenticationService authenticationService;

    @BeforeEach
    public void setup()
    {
        this.authenticationService = new AuthenticationService();
        this.authenticationDTO = new AuthenticationDTO();
        this.authenticationDTO.user = "niels";
        this.authenticationDTO.password = "niels";
    }

    @Test
    public void successfulLogin()
    {
        // Arrange
        User user = new User(0);
        user.setName(this.authenticationDTO.user);
        UserDAO userDAOMock = mock(UserDAO.class);
        when(userDAOMock.authenticate(this.authenticationDTO.user, this.authenticationDTO.password)).thenReturn(user);
        this.authenticationService.setUserDAO(userDAOMock);

        // Act
        Response response = this.authenticationService.login(this.authenticationDTO);
        AuthenticationResponseDTO authenticationResponseDTO = (AuthenticationResponseDTO) response.getEntity();

        // Assert
        assertEquals(200, response.getStatus());
        assertEquals(this.authenticationDTO.user, authenticationResponseDTO.user);
    }

    @Test
    public void failedLogin()
    {
        // Arrange
        UserDAO userDAOMock = mock(UserDAO.class);
        when(userDAOMock.authenticate(this.authenticationDTO.user, this.authenticationDTO.password)).thenReturn(null);
        this.authenticationService.setUserDAO(userDAOMock);

        // Act
        Response response = this.authenticationService.login(this.authenticationDTO);

        // Assert
        assertEquals(401, response.getStatus());
    }
}
