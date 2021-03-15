package spotitube.services;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import spotitube.dao.UserDAO;
import spotitube.domain.User;
import spotitube.services.dto.LoginDTO;
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
        User user = userDAO.authenticate(possibleUser.user, possibleUser.password);

        if (user == null) {
            return Response.status(401).build();
        }

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.token = user.getToken();
        loginDTO.user = user.getUsername();

        return Response.status(200).entity(loginDTO).build();
    }

    @Inject
    public void setUserDAO(UserDAO userDAO)
    {
        this.userDAO = userDAO;
    }
}
