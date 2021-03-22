package spotitube.resources;

import exceptions.UnauthorizedException;
import services.IdService;
import services.PlaylistService;
import services.TrackService;
import services.UserService;
import spotitube.domain.User;
import spotitube.dto.track.TrackDTO;
import spotitube.dto.track.TracksResponseDTO;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/playlists/{playlist_id}/tracks")
public class PlaylistTrackResource
{
    private UserService userService;
    private TrackService trackService;
    private PlaylistService playlistService;
    private IdService idService;

    /**
     * Getter of all the tracks in a specific playlist.
     *
     * @param playlistId The ID of the searchable playlist.
     * @param token      Token authenticated user token.
     * @return The tracks that belong to the playlist.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTracksByPlaylist(@PathParam("playlist_id") int playlistId, @QueryParam("token") String token)
    {
        try {
            userService.authenticateToken(token);

            if (idService.isValid(playlistId)) {
                TracksResponseDTO tracksResponseDTO = new TracksResponseDTO();
                tracksResponseDTO.setTracks(trackService.getAllByPlaylist(playlistId));

                return Response
                        .ok(tracksResponseDTO)
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
     * Removes a specific track from a specific playlist.
     *
     * @param playlistId The playlist to remove the track from.
     * @param trackId    The ID of the track to remove.
     * @param token      The token of the authenticated user.
     * @return All of the remaining songs in the specified playlist.
     */
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{track_id}")
    public Response removeTrackFromPlaylist(@PathParam("playlist_id") int playlistId, @PathParam("track_id") int trackId, @QueryParam("token") String token)
    {
        try {
            User user = userService.authenticateToken(token);

            if (canRemoveTrackFromPlaylist(playlistId, trackId, user.getId())) {
                TracksResponseDTO dto = new TracksResponseDTO();
                dto.setTracks(trackService.getAllByPlaylist(playlistId));

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
     * Adds a new track to a specific playlist.
     *
     * @param playlistId The ID of the playlist to add the track to.
     * @param token      The token of the authenticated user.
     * @param trackDTO   The track to add.
     * @return All of the new tracks in the playlist.
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addTrackToPlaylist(@PathParam("playlist_id") int playlistId, @QueryParam("token") String token, TrackDTO trackDTO)
    {
        try {
            User user = userService.authenticateToken(token);

            if (canAddTrackToPlaylist(playlistId, user.getId(), trackDTO)) {
                TracksResponseDTO dto = new TracksResponseDTO();
                dto.setTracks(trackService.getAllByPlaylist(playlistId));

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
     * Decides of someone can remove a track from a playlist and if it was successful.
     *
     * @param playlistId The ID of the specific playlist.
     * @param trackId    The ID of the specific track.
     * @param userId     The ID of the authenticated user.
     * @return The result of the statement.
     */
    public boolean canRemoveTrackFromPlaylist(int playlistId, int trackId, int userId)
    {
        return idService.isValid(trackId, userId, playlistId)
                && playlistService.isOwnedBy(playlistId, userId)
                && trackService.deleteFromPlaylist(playlistId, trackId, userId);
    }

    /**
     * Decides of someone can add a track from a playlist and if it was successful.
     *
     * @param playlistId The ID of the specific playlist.
     * @param userId     The ID of the authenticated user.
     * @param trackDTO   The DTO of the track to add.
     * @return The result of the statement.
     */
    public boolean canAddTrackToPlaylist(int playlistId, int userId, TrackDTO trackDTO)
    {
        return idService.isValid(playlistId, userId)
                && playlistService.isOwnedBy(playlistId, userId)
                && trackService.addTrackToPlaylist(trackDTO, playlistId);
    }

    /**
     * Injects playlist service.
     *
     * @param playlistService the playlist service
     */
    @Inject
    public void setPlaylistService(PlaylistService playlistService)
    {
        this.playlistService = playlistService;
    }

    /**
     * Injects user service.
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
