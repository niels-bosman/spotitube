package spotitube.services.dto;

public class LoginDTO {
    public String token;
    public String user;

    public LoginDTO(String token, String user)
    {
        this.token = token;
        this.user = user;
    }
}
