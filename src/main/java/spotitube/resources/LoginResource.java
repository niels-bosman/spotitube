package spotitube.resources;

import spotitube.dto.login.LoginRequestDTO;
import spotitube.exceptions.UnauthorizedException;
import spotitube.services.UserService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("login")
public class LoginResource
{
    private UserService userService;

    /**
     * Handles the "/login" route.
     * Here we authenticate a user based on username and
     * password. After that we add a token to it's column.
     *
     * @param request the request entity.
     * @return the request response.
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(LoginRequestDTO request)
    {
        try {
            return Response
                .ok(userService.authenticate(request.getUser(), request.getPassword()))
                .build();
        }
        catch (UnauthorizedException e) {
            return Response
                .status(Response.Status.UNAUTHORIZED)
                .build();
        }
    }

    /**
     * Injects the UserService.
     *
     * @param userService the UserService.
     */
    @Inject
    public void setUserService(UserService userService)
    {
        this.userService = userService;
    }
}
