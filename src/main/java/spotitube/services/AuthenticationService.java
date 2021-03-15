package spotitube.services;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import spotitube.dao.UserDAO;
import spotitube.domain.User;
import spotitube.services.dto.UserDTO;

@Path("auth")
public class AuthenticationService
{
    private UserDAO userDAO;

    @POST
    @Path("login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(UserDTO possibleUser)
    {
        User user = userDAO.getUser(possibleUser.username, possibleUser.password);

        if (user == null) {
            return Response.status(401).build();
        }

        UserDTO userDTO = new UserDTO();
        userDTO.username = user.getUsername();
        userDTO.token = user.getToken();

        return Response.status(200).entity(userDTO).build();
    }

    @Inject
    public void setUserDAO(UserDAO userDAO)
    {
        this.userDAO = userDAO;
    }
}
