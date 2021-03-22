package spotitube.resources;

import exceptions.UnauthorizedException;
import services.IdService;
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
    private IdService idService;

    /**
     * Get's all the available tracks to add to a specific playlist.
     *
     * @param token      The authenticated user token.
     * @param playlistId The playlist ID
     * @return the response
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response tracks(@QueryParam("token") String token, @QueryParam("forPlaylist") int playlistId)
    {
        try {
            userService.authenticateToken(token);

            if (idService.isValid(playlistId)) {
                TracksResponseDTO dto = new TracksResponseDTO();
                dto.setTracks(trackService.getAllNotInPlaylist(playlistId));

                return Response
                    .ok(dto)
                    .build();
            }
        }
        catch (UnauthorizedException e) {
            return Response
                .status(Response.Status.FORBIDDEN)
                .build();
        }

        return Response
            .status(Response.Status.BAD_REQUEST)
            .build();
    }

    /**
     * Sets user service.
     *
     * @param userService the user service
     */
    @Inject
    public void setUserService(UserService userService)
    {
        this.userService = userService;
    }

    /**
     * Sets track service.
     *
     * @param trackService the track service
     */
    @Inject
    public void setTrackService(TrackService trackService)
    {
        this.trackService = trackService;
    }

    /**
     * Injects the idService.
     *
     * @param idService the IdService.
     */
    @Inject
    public void setIdService(IdService idService)
    {
        this.idService = idService;
    }
}
