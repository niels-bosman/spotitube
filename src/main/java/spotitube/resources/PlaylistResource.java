package spotitube.resources;

import spotitube.dao.PlaylistDAO;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("login")
public class PlaylistResource
{
    private PlaylistDAO playlistDAO;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getAll()
    {
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
