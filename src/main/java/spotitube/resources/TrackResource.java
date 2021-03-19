package spotitube.resources;

import exceptions.UnauthorizedException;
import services.TrackService;
import services.UserService;
import spotitube.dto.track.TracksResponseDTO;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/tracks")
public class TrackResource
{
    private UserService userService;
    private TrackService trackService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response tracks(@QueryParam("token") String token, @QueryParam("forPlaylist") int playlistId)
    {
        try {
            userService.authenticateToken(token);

            if (playlistId > 0) {
                TracksResponseDTO dto = new TracksResponseDTO();
                dto.setTracks(trackService.getAllNotInPlaylist(playlistId));

                return Response
                        .ok(dto)
                        .build();
            }
        }
        catch (UnauthorizedException e) {
            return Response
                    .status(Response.Status.UNAUTHORIZED)
                    .build();
        }

        return Response
                .status(Response.Status.BAD_REQUEST)
                .build();
    }

    @Inject
    public void setUserService(UserService userService)
    {
        this.userService = userService;
    }

    @Inject
    public void setTrackService(TrackService trackService)
    {
        this.trackService = trackService;
    }
}
