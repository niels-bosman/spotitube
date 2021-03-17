package spotitube.resources;

import spotitube.dao.PlaylistDAO;
import spotitube.dto.playlist.PlaylistDTO;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("playlists")
public class PlaylistResource
{
    private PlaylistDAO playlistDAO;

    /**
     * Getter of all of the playlists.
     *
     * @param token the token
     * @return the all
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getAll(String token)
    {
        // TODO: Implement method.

        return Response
                .ok()
                .build();
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
}
