package spotitube.resources;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import spotitube.DummyGenerator;
import spotitube.exceptions.UnauthorizedException;
import spotitube.services.IdService;
import spotitube.services.TrackService;
import spotitube.services.UserService;

import javax.ws.rs.core.Response;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TrackResourceTest extends DummyGenerator
{
    @Mock private UserService userService;
    @Mock private TrackService trackService;
    private IdService idService = new IdService();
    private TrackResource trackResource;

    @BeforeEach
    public void setup() throws UnauthorizedException
    {
        MockitoAnnotations.openMocks(this);

        trackResource = new TrackResource();
        trackResource.setTrackService(trackService);
        trackResource.setUserService(userService);
        trackResource.setIdService(idService);

        Mockito.when(userService.authenticateToken(Mockito.anyString())).thenThrow(UnauthorizedException.class);
        Mockito.when(userService.authenticateToken(Mockito.eq(DUMMY_USER.getToken()))).thenReturn(DUMMY_USER.getId());
        Mockito.when(trackService.getAllNotInPlaylist(Mockito.anyInt())).thenReturn(new ArrayList<>());
    }

    @Test public void tracksByPlaylistSuccessful()
    {
        Response response = trackResource.tracks(DUMMY_USER.getToken(), 1);

        assertEquals(Response.Status.OK, response.getStatusInfo());
    }

    @Test public void tracksByPlaylistNotAuthorized()
    {
        Response response = trackResource.tracks("wrongToken", 1);

        assertEquals(Response.Status.FORBIDDEN, response.getStatusInfo());
    }

    @Test public void tracksByPlaylistBadRequest()
    {
        Response response = trackResource.tracks(DUMMY_USER.getToken(), -1);

        assertEquals(Response.Status.BAD_REQUEST, response.getStatusInfo());
    }
}
