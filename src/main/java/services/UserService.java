package services;

import exceptions.UnauthorizedException;
import spotitube.dao.UserDAO;
import spotitube.domain.User;

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
}
