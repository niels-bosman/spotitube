package spotitube.services;

import spotitube.dao.UserDAO;
import spotitube.domain.User;
import spotitube.dto.login.LoginRequestDTO;
import spotitube.exceptions.UnauthorizedException;

import javax.inject.Inject;

public class UserService
{
    private UserDAO userDAO;

    /**
     * Authenticate token user.
     *
     * @param token the token
     * @return the user
     * @throws UnauthorizedException the unauthorized exception
     */
    public User authenticateToken(String token) throws UnauthorizedException
    {
        User user = userDAO.verifyToken(token);

        if (user != null) {
            return user;
        }

        throw new UnauthorizedException();
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
        return userDAO.get(requestDTO);
    }

    /**
     * Add token.
     *
     * @param user the user
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
