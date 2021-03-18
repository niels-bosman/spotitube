package services;

import exceptions.UnauthorizedException;
import spotitube.dao.UserDAO;
import spotitube.domain.User;

import javax.inject.Inject;

public class UserService
{
    private UserDAO userDAO;

    public User authenticateToken(String token) throws UnauthorizedException
    {
        User user = userDAO.verifyToken(token);

        if (user != null) {
            return user;
        }

        throw new UnauthorizedException();
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
