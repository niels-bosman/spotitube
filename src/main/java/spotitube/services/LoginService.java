package spotitube.services;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import spotitube.dao.UserDAO;
import spotitube.domain.User;
import spotitube.dto.LoginResponseDTO;
import spotitube.dto.LoginRequestDTO;

@Path("login")
public class LoginService
{
    private UserDAO userDAO;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(LoginRequestDTO entity)
    {
        User user = userDAO.authenticate(entity.user, entity.password);

        if (user == null) {
            return Response
                    .status(Response.Status.UNAUTHORIZED)
                    .build();
        }

        userDAO.addToken(user, UserService.generateUUID());

        return Response
                .status(Response.Status.OK)
                .entity(new LoginResponseDTO(user.getToken(), user.getName()))
                .build();
    }

    @Inject
    public void setUserDAO(UserDAO userDAO)
    {
        this.userDAO = userDAO;
    }
}
