package spotitube.resources;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import spotitube.dao.UserDAO;
import spotitube.domain.User;
import spotitube.dto.LoginRequestDTO;
import spotitube.mappers.UserMapper;

@Path("login")
public class LoginResource
{
    private UserDAO userDAO;

    /**
     * Handles the "/login" route.
     *
     * Here we authenticate a user based on username and
     * password. After that we add a token to it's column.
     *
     * @param entity the request entity.
     * @return the request response.
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(LoginRequestDTO entity)
    {
        User user = userDAO.get(entity);

        if (user == null) {
            return Response
                    .status(Response.Status.UNAUTHORIZED)
                    .build();
        }

        userDAO.addToken(user);

        return Response
                .status(Response.Status.OK)
                .entity(UserMapper.getInstance().convertToDTO(user))
                .build();
    }

    /**
     * Injects the userDAO.
     *
     * @param userDAO the UserDAO.
     */
    @Inject
    public void setUserDAO(UserDAO userDAO)
    {
        this.userDAO = userDAO;
    }
}
