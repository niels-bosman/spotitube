package spotitube.resources;

import spotitube.exceptions.UnauthorizedException;
import spotitube.services.IdService;
import spotitube.services.PlaylistService;
import spotitube.services.UserService;
import spotitube.domain.Playlist;
import spotitube.domain.User;
import spotitube.dto.playlist.PlaylistDTO;
import spotitube.dto.playlist.PlaylistResponseDTO;
import spotitube.mappers.PlaylistMapper;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("playlists")
public class PlaylistResource
{
    private UserService userService;
    private PlaylistService playlistService;
    private IdService idService;
    public Playlist playlist = new Playlist();

    /**
     * Getter of all of the playlists.
     *
     * @param token the token
     * @return All of the playlists with its total time.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@QueryParam("token") String token)
    {
        try {
            User user = userService.authenticateToken(token);

            PlaylistResponseDTO dto = createResponse(user);

            return Response
                .ok(dto)
                .build();
        }
        catch (UnauthorizedException e) {
            return Response
                .status(Response.Status.FORBIDDEN)
                .build();
        }
    }

    /**
     * Deletes a specific playlist
     *
     * @param playlistId The playlist ID
     * @param token      The token
     * @return request The new full playlist list with it's total time.
     */
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePlaylist(@PathParam("id") int playlistId, @QueryParam("token") String token)
    {
        try {
            User user = userService.authenticateToken(token);
            playlist.setId(playlistId);

            if (idService.isValid(playlistId) && playlistService.delete(playlist, user)) {
                PlaylistResponseDTO dto = createResponse(user);

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
     * Adds a playlist
     *
     * @param token   The query param token
     * @param request The given playlist
     * @return The newly added playlist
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPlaylist(@QueryParam("token") String token, PlaylistDTO request)
    {
        try {
            User user = userService.authenticateToken(token);

            if (playlistService.add(request, user)) {
                PlaylistResponseDTO dto = createResponse(user);

                return Response
                    .status(Response.Status.CREATED)
                    .entity(dto)
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
     * Edits a specific playlist
     *
     * @param playlistId The playlist ID
     * @param token      The user token
     * @param request    The given request body
     * @return The edited playlist
     */
    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response editPlaylist(@PathParam("id") int playlistId, @QueryParam("token") String token, PlaylistDTO request)
    {
        try {
            User user = userService.authenticateToken(token);
            playlist.setId(playlistId);

            if (idService.isValid(playlistId) && playlistService.editTitle(playlist, request, user)) {
                PlaylistResponseDTO dto = createResponse(user);

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
     * Formats all the playlists and verifies it with the user.
     *
     * @param user the authenticated User.
     * @return A full playlist with it's duration
     */
    private PlaylistResponseDTO createResponse(User user)
    {
        PlaylistResponseDTO playlistResponseDTO = new PlaylistResponseDTO();
        List<PlaylistDTO> playlistDTOs = playlistService.getAll(user);
        int duration = playlistService.getTotalDuration(PlaylistMapper.getInstance().convertToEntity(playlistDTOs));

        playlistResponseDTO.setPlaylists(playlistDTOs);
        playlistResponseDTO.setLength(duration);

        return playlistResponseDTO;
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
