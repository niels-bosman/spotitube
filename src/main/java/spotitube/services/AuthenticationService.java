package spotitube.services;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import spotitube.dao.UserDAO;
import spotitube.domain.User;
import spotitube.services.dto.UserDTO;

public class AuthenticationService {

    private final UserDAO userDAO = new UserDAO();

    @GET
    @Path("login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(@FormParam("user") String username, @FormParam("password") String password)
    {
        User user = userDAO.getUser(username, password);

        if (user == null) {
            return Response.status(401).build();
        }

        UserDTO userDTO = new UserDTO();
        userDTO.username = user.getUsername();
        userDTO.token = user.getToken();

        return Response.status(200).entity(userDTO).build();
    }
}
