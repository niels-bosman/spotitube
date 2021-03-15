package spotitube.services;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import spotitube.dao.UserDAO;
import spotitube.domain.User;
import spotitube.dto.AuthenticationResponseDTO;
import spotitube.dto.AuthenticationDTO;

@Path("auth")
public class AuthenticationService
{
    private UserDAO userDAO;

    @POST
    @Path("login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(AuthenticationDTO entity)
    {
        User authenticated = userDAO.authenticate(entity.user, entity.password);

        if (authenticated == null) {
            return Response
                    .status(Response.Status.UNAUTHORIZED)
                    .build();
        }

        return Response
                .status(Response.Status.OK)
                .entity(new AuthenticationResponseDTO(authenticated.getToken(), authenticated.getName()))
                .build();
    }

    @Inject
    public void setUserDAO(UserDAO userDAO)
    {
        this.userDAO = userDAO;
    }
}
