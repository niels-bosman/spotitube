package spotitube.services;

import spotitube.data.dao.UserDAO;
import spotitube.domain.User;
import spotitube.dto.login.LoginRequestDTO;
import spotitube.dto.login.LoginResponseDTO;
import spotitube.exceptions.UnauthorizedException;
import spotitube.data.mappers.UserMapper;

import javax.inject.Inject;

public class UserService
{
    private UserDAO userDAO;


    /**
     * Authenticates a user.
     *
     * @param username the username
     * @param password the password
     * @return the login response dto
     * @throws UnauthorizedException the unauthorized exception
     */
    public LoginResponseDTO authenticate(String username, String password) throws UnauthorizedException
    {
        User user = userDAO.get(username, password);

        if (user != null) {
            user.setToken();

            // Try to store the token
            if (userDAO.addToken(user)) {
                return UserMapper.getInstance().convertToDTO(user);
            }
        }

        throw new UnauthorizedException();
    }

    /**
     * Authenticate token user.
     *
     * @param token the token
     * @return the user
     * @throws UnauthorizedException the unauthorized exception
     */
    public int authenticateToken(String token) throws UnauthorizedException
    {
        return userDAO.verifyToken(token).getId();
    }

    /**
     * Get user.
     *
     * @param requestDTO the request dto
     * @return the user
     * @throws UnauthorizedException the unauthorized exception
     */
    public User get(LoginRequestDTO requestDTO) throws UnauthorizedException
    {
        User user = userDAO.get(requestDTO.getUser(), requestDTO.getPassword());

        if (user == null) {
            throw new UnauthorizedException();
        }

        return user;
    }

    /**
     * Add token.
     *
     * @param user the user
     * @return If the adding was successful.
     */
    public boolean addToken(User user)
    {
        return userDAO.addToken(user);
    }

    /**
     * Injects an instance of UserDAO.
     *
     * @param userDAO A UserDAO.
     */
    @Inject
    public void setUserDAO(UserDAO userDAO)
    {
        this.userDAO = userDAO;
    }
}
