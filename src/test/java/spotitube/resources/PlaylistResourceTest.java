package spotitube.resources;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import spotitube.DummyGenerator;
import spotitube.data.domain.Playlist;
import spotitube.dto.playlist.PlaylistDTO;
import spotitube.exceptions.UnauthorizedException;
import spotitube.services.IdService;
import spotitube.services.PlaylistService;
import spotitube.services.UserService;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlaylistResourceTest extends DummyGenerator
{
    @Mock private PlaylistService playlistService;
    @Mock private UserService userService;
    private IdService idService = new IdService();
    private PlaylistResource playlistResource;
    private List<Playlist> dummyPlaylists = new ArrayList<>();
    private PlaylistDTO dto;

    @BeforeEach
    public void setup() throws UnauthorizedException
    {
        MockitoAnnotations.openMocks(this);
        this.playlistResource = new PlaylistResource();
        playlistResource.setPlaylistService(playlistService);
        playlistResource.setUserService(userService);
        playlistResource.setIdService(idService);

        for (int i = 1; i <= 3; i++) {
            dummyPlaylists.add(generatePlaylist(i));
        }

        // PlaylistDTO
        dto = new PlaylistDTO();
        dto.setId(1);
        dto.setName("NewTitle");
        dto.setOwner(true);
        dto.setTracks(new ArrayList<>());

        // Mock authenticate token method with good token and with any other string.
        Mockito.when(userService.authenticateToken(Mockito.anyString())).thenThrow(UnauthorizedException.class);
        Mockito.when(userService.authenticateToken(Mockito.eq(DUMMY_USER.getToken()))).thenReturn(DUMMY_USER.getId());

        Mockito.when(playlistService.getAll(DUMMY_USER.getId())).thenReturn(new ArrayList<>());
        Mockito.when(playlistService.getTotalDuration(new ArrayList<>())).thenReturn(0);
    }

    @Test public void getAll()
    {
        // Act
        Response response = playlistResource.getAll(DUMMY_USER.getToken());

        // Assert
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }

    @Test public void getAllNotAuthorized()
    {
        // Act
        Response response = playlistResource.getAll("verkeerdToken");

        // Assert
        assertEquals(Response.Status.FORBIDDEN, response.getStatusInfo());
    }

    @Test public void addPlaylistSuccessful()
    {
        // Arrange
        Mockito.when(playlistService.add(Mockito.any(PlaylistDTO.class), Mockito.eq(DUMMY_USER.getId()))).thenReturn(true);

        // Act
        Response response = playlistResource.addPlaylist(DUMMY_USER.getToken(), dto);

        // Assert
        assertEquals(Response.Status.CREATED, response.getStatusInfo());
    }

    @Test public void addPlaylistForbidden()
    {
        Mockito.when(playlistService.add(Mockito.any(PlaylistDTO.class), Mockito.eq(DUMMY_USER.getId()))).thenReturn(false);

        Response response = playlistResource.addPlaylist("invalidToken", dto);

        assertEquals(Response.Status.FORBIDDEN, response.getStatusInfo());
    }

    @Test public void addPlaylistBadRequest()
    {
        Mockito.when(playlistService.add(Mockito.any(PlaylistDTO.class), Mockito.eq(DUMMY_USER.getId()))).thenReturn(false);

        Response response = playlistResource.addPlaylist(DUMMY_USER.getToken(), dto);

        assertEquals(Response.Status.BAD_REQUEST, response.getStatusInfo());
    }

    @Test public void deletePlaylistSuccessful()
    {
        // Arrange
        Mockito.when(playlistService.delete(DUMMY_PLAYLIST.getId(), DUMMY_USER.getId())).thenReturn(true);

        // Act
        Response response = playlistResource.deletePlaylist(DUMMY_PLAYLIST.getId(), DUMMY_USER.getToken());

        // Assert
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }

    @Test public void deletePlaylistForbidden()
    {
        // Arrange
        Mockito.when(playlistService.delete(DUMMY_PLAYLIST.getId(), DUMMY_USER.getId())).thenReturn(false);

        // Act
        Response response = playlistResource.deletePlaylist(DUMMY_PLAYLIST.getId(), "wrongToken");

        // Assert
        assertEquals(Response.Status.FORBIDDEN, response.getStatusInfo());
    }

    @Test public void deletePlaylistBadRequest()
    {
        // Arrange
        Mockito.when(playlistService.delete(DUMMY_PLAYLIST.getId(), DUMMY_USER.getId())).thenReturn(false);

        // Act
        Response response = playlistResource.deletePlaylist(DUMMY_PLAYLIST.getId(), DUMMY_USER.getToken());

        // Assert
        assertEquals(Response.Status.BAD_REQUEST, response.getStatusInfo());
    }

    @Test public void editPlaylistSuccessful()
    {
        // Arrange
        Mockito.when(playlistService.editTitle(DUMMY_PLAYLIST.getId(), dto, DUMMY_USER.getId())).thenReturn(true);

        // Act
        Response response = playlistResource.editPlaylist(DUMMY_PLAYLIST.getId(), DUMMY_USER.getToken(), dto);

        // Assert
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }

    @Test public void editPlaylistForbidden()
    {
        // Arrange
        Mockito.when(playlistService.editTitle(DUMMY_PLAYLIST.getId(), dto, DUMMY_USER.getId())).thenReturn(true);

        // Act
        Response response = playlistResource.editPlaylist(DUMMY_PLAYLIST.getId(), "wrongToken", dto);

        // Assert
        assertEquals(Response.Status.FORBIDDEN, response.getStatusInfo());
    }

    @Test public void editPlaylistBadRequest()
    {
        // Arrange
        Mockito.when(playlistService.editTitle(DUMMY_PLAYLIST.getId(), dto, DUMMY_USER.getId())).thenReturn(false);

        // Act
        Response response = playlistResource.editPlaylist(DUMMY_PLAYLIST.getId(), DUMMY_USER.getToken(), dto);

        // Assert
        assertEquals(Response.Status.BAD_REQUEST, response.getStatusInfo());
    }

    public Playlist generatePlaylist(int i)
    {
        Playlist playlist = new Playlist();

        playlist.setId(i);
        playlist.setName("Playlist " + i);
        playlist.setOwnerId(i);
        playlist.setTracks(new ArrayList<>());

        return playlist;
    }
}
