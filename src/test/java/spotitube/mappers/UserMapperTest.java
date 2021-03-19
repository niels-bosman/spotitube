package spotitube.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spotitube.domain.User;
import spotitube.dto.login.LoginResponseDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserMapperTest
{
    private List<User> users = new ArrayList<>();
    private List<LoginResponseDTO> responseDTOS = new ArrayList<>();

    @BeforeEach
    public void setUp()
    {
        for (int i = 0; i < 3; i++) {
            User user = new User();
            user.setId(i);
            user.setName("Naam " + i);
            user.setUsername("Gebruikersnaam " + i);
            user.setToken(UUID.randomUUID().toString());

            users.add(user);
        }

        users.forEach(user -> {
            LoginResponseDTO dto = new LoginResponseDTO();
            dto.setUser(user.getName());
            dto.setToken(user.getToken());

            responseDTOS.add(dto);
        });
    }

    @Test
    public void mapSingleUserDomainToResponseDTO()
    {
        // Arrange
        User user = users.get(0);
        LoginResponseDTO response = UserMapper.getInstance().convertToDTO(user);

        // Assert
        assertEquals(response.getUser(), user.getName());
        assertEquals(response.getToken(), user.getToken());
    }

    @Test
    public void mapSingleResponseDTOToUser()
    {
        // Arrange
        LoginResponseDTO response = responseDTOS.get(0);
        User user = UserMapper.getInstance().convertToEntity(response);

        // Assert
        assertEquals(response.getUser(), user.getName());
        assertEquals(response.getToken(), user.getToken());
    }

    @Test
    public void mapMultipleUserDomainsToResponseDTOs()
    {
        // Arrange
        List<LoginResponseDTO> DTOs = UserMapper.getInstance().convertToDTO(users);

        // Assert
        for (int i = 0; i < DTOs.size(); i++) {
            assertEquals(DTOs.get(i).getUser(), users.get(i).getName());
            assertEquals(DTOs.get(i).getToken(), users.get(i).getToken());
        }
    }

    @Test
    public void mapMultipleResponseDTOsToUsers()
    {
        // Arrange
        List<User> mappedUsers = UserMapper.getInstance().convertToEntity(responseDTOS);

        // Assert
        for (int i = 0; i < mappedUsers.size(); i++) {
            assertEquals(mappedUsers.get(i).getName(), responseDTOS.get(i).getUser());
            assertEquals(mappedUsers.get(i).getToken(), responseDTOS.get(i).getToken());
        }
    }
}
