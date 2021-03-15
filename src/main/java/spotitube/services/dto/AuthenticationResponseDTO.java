package spotitube.services.dto;

public class AuthenticationResponseDTO
{
    public String token;
    public String user;

    public AuthenticationResponseDTO(String token, String user)
    {
        this.token = token;
        this.user = user;
    }
}
