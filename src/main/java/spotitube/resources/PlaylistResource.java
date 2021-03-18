package spotitube.resources;

import exceptions.UnauthorizedException;
import services.PlaylistService;
import services.UserService;
import spotitube.dao.PlaylistDAO;
import spotitube.domain.User;
import spotitube.dto.playlist.PlaylistDTO;
import spotitube.dto.playlist.PlaylistResponseDTO;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("playlists")
public class PlaylistResource
{
    private PlaylistDAO playlistDAO;
    private UserService userService;
    private PlaylistService playlistService;

    /**
     * Getter of all of the playlists.
     *
     * @param token the token
     * @return the all
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@QueryParam("token") String token)
    {
        try {
            User user = userService.authenticateToken(token);

            PlaylistResponseDTO dto = createResponse(playlistService.getAll(user), playlistService.getTotalDuration());

            return Response
                    .status(Response.Status.OK)
                    .entity(dto)
                    .build();
        }
        catch (UnauthorizedException e) {
            return Response
                    .status(Response.Status.UNAUTHORIZED)
                    .build();
        }
    }

    /**
     * Deletes a specific playlist
     *
     * @param playlistId The playlist id
     * @param token      The token
     * @return request response
     */
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deletePlaylist(@PathParam("id") int playlistId, String token)
    {
        // TODO: Implement method.

        return Response
                .ok()
                .build();
    }

    /**
     * Adds a playlist
     *
     * @param request The sent request
     * @return The response of the request
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPlaylist(PlaylistDTO request)
    {
        // TODO: Implement method.

        return Response
                .ok()
                .build();
    }

    /**
     * Edits a specific playlist
     *
     * @param playlistId The playlist ID
     * @param token      The token
     * @return The request response
     */
    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response editPlaylist(@PathParam("id") int playlistId, String token)
    {
        // TODO: Implement method.

        return Response
                .ok()
                .build();
    }

    private PlaylistResponseDTO createResponse(List<PlaylistDTO> playlists, int length)
    {
        PlaylistResponseDTO playlistResponseDTO = new PlaylistResponseDTO();
        playlistResponseDTO.setPlaylists(playlists);
        playlistResponseDTO.setLength(length);

        return playlistResponseDTO;
    }

    /**
     * Injects the playlistDAO.
     *
     * @param playlistDAO the PlaylistDAO.
     */
    @Inject
    public void setPlaylistDAO(PlaylistDAO playlistDAO)
    {
        this.playlistDAO = playlistDAO;
    }

    /**
     * Injects the playlistService.
     *
     * @param userService the UserService.
     */
    @Inject
    public void setUserService(UserService userService)
    {
        this.userService = userService;
    }

    /**
     * Injects the playlistService.
     *
     * @param playlistService the PlaylistService.
     */
    @Inject
    public void setPlaylistService(PlaylistService playlistService)
    {
        this.playlistService = playlistService;
    }
}
