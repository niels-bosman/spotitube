package spotitube.resources;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import spotitube.DummyGenerator;
import spotitube.dto.track.TrackDTO;
import spotitube.exceptions.UnauthorizedException;
import spotitube.services.IdService;
import spotitube.services.PlaylistService;
import spotitube.services.TrackService;
import spotitube.services.UserService;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlaylistTrackResourceTest extends DummyGenerator
{
    @Mock private TrackService trackService;
    @Mock private PlaylistService playlistService;
    @Mock private UserService userService;
    private IdService idService = new IdService();
    private PlaylistTrackResource playlistTrackResource;

    @BeforeEach
    public void setUp() throws UnauthorizedException
    {
        MockitoAnnotations.openMocks(this);

        playlistTrackResource = new PlaylistTrackResource();
        playlistTrackResource.setPlaylistService(playlistService);
        playlistTrackResource.setTrackService(trackService);
        playlistTrackResource.setUserService(userService);
        playlistTrackResource.setIdService(idService);

        Mockito.when(userService.authenticateToken(Mockito.anyString())).thenThrow(UnauthorizedException.class);
        Mockito.when(userService.authenticateToken(Mockito.eq(DUMMY_USER.getToken()))).thenReturn(DUMMY_USER.getId());
    }

    @Test public void playlistTracksSuccessful()
    {
        // Act
        Response response = playlistTrackResource.getTracksByPlaylist(1, DUMMY_USER.getToken());

        // Assert
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }

    @Test public void playlistTracksForbidden()
    {
        // Act
        Response response = playlistTrackResource.getTracksByPlaylist(1, "wrongToken");

        // Assert
        assertEquals(Response.Status.FORBIDDEN, response.getStatusInfo());
    }

    @Test public void playlistTracksBadRequest()
    {
        // Act
        Response response = playlistTrackResource.getTracksByPlaylist(-1, DUMMY_USER.getToken());

        // Assert
        assertEquals(Response.Status.BAD_REQUEST, response.getStatusInfo());
    }

    @Test public void deleteTrackSuccessful()
    {
        // Arrange
        Mockito.when(trackService.deleteFromPlaylist(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(true);
        Mockito.when(playlistService.isOwnedBy(Mockito.anyInt(), Mockito.anyInt())).thenReturn(true);

        // Act
        Response response = playlistTrackResource.removeTrackFromPlaylist(1, 1, DUMMY_USER.getToken());

        // Assert
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }

    @Test public void deleteTrackForbidden()
    {
        // Arrange
        Mockito.when(trackService.deleteFromPlaylist(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(true);
        Mockito.when(playlistService.isOwnedBy(Mockito.anyInt(), Mockito.anyInt())).thenReturn(true);

        // Act
        Response response = playlistTrackResource.removeTrackFromPlaylist(1, 1, "wrongToken");

        // Assert
        assertEquals(Response.Status.FORBIDDEN, response.getStatusInfo());
    }

    @Test public void deleteTrackBadRequest()
    {
        // Arrange
        Mockito.when(trackService.deleteFromPlaylist(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(false);
        Mockito.when(playlistService.isOwnedBy(Mockito.anyInt(), Mockito.anyInt())).thenReturn(true);

        // Act
        Response response = playlistTrackResource.removeTrackFromPlaylist(1, 1, DUMMY_USER.getToken());

        // Assert
        assertEquals(Response.Status.BAD_REQUEST, response.getStatusInfo());
    }

    @Test public void addTrackSuccessful()
    {
        // Arrange
        Mockito.when(trackService.addTrackToPlaylist(Mockito.any(TrackDTO.class), Mockito.anyInt())).thenReturn(true);
        Mockito.when(playlistService.isOwnedBy(Mockito.anyInt(), Mockito.anyInt())).thenReturn(true);

        // Act
        Response response = playlistTrackResource.addTrackToPlaylist(1, DUMMY_USER.getToken(), new TrackDTO());

        // Assert
        assertEquals(Response.Status.CREATED, response.getStatusInfo());
    }

    @Test public void addTrackForbidden()
    {
        // Arrange
        Mockito.when(trackService.addTrackToPlaylist(Mockito.any(TrackDTO.class), Mockito.anyInt())).thenReturn(true);
        Mockito.when(playlistService.isOwnedBy(Mockito.anyInt(), Mockito.anyInt())).thenReturn(true);

        // Act
        Response response = playlistTrackResource.addTrackToPlaylist(1, "wrongToken", new TrackDTO());

        // Assert
        assertEquals(Response.Status.FORBIDDEN, response.getStatusInfo());
    }

    @Test public void addTrackBadRequest()
    {
        // Arrange
        Mockito.when(trackService.addTrackToPlaylist(Mockito.any(TrackDTO.class), Mockito.anyInt())).thenReturn(true);
        Mockito.when(playlistService.isOwnedBy(Mockito.anyInt(), Mockito.anyInt())).thenReturn(false);

        // Act
        Response response = playlistTrackResource.addTrackToPlaylist(1, DUMMY_USER.getToken(), new TrackDTO());

        // Assert
        assertEquals(Response.Status.BAD_REQUEST, response.getStatusInfo());
    }
}
