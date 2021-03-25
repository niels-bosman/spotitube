package spotitube.resources;

import com.sun.mail.imap.protocol.ID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import spotitube.TestHelpers;
import spotitube.domain.Playlist;
import spotitube.dto.playlist.PlaylistDTO;
import spotitube.exceptions.UnauthorizedException;
import spotitube.services.IdService;
import spotitube.services.PlaylistService;
import spotitube.services.UserService;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlaylistResourceTest extends TestHelpers
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
        Mockito.when(userService.authenticateToken(Mockito.eq(DUMMY_USER.getToken()))).thenReturn(DUMMY_USER);

        Mockito.when(playlistService.getAll(DUMMY_USER)).thenReturn(new ArrayList<>());
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
        Mockito.when(playlistService.add(Mockito.any(PlaylistDTO.class), Mockito.eq(DUMMY_USER))).thenReturn(true);

        // Act
        Response response = playlistResource.addPlaylist(DUMMY_USER.getToken(), dto);

        // Assert
        assertEquals(Response.Status.CREATED, response.getStatusInfo());
    }

    @Test public void addPlaylistForbidden()
    {
        Mockito.when(playlistService.add(Mockito.any(PlaylistDTO.class), Mockito.eq(DUMMY_USER))).thenReturn(false);

        Response response = playlistResource.addPlaylist("invalidToken", dto);

        assertEquals(Response.Status.FORBIDDEN, response.getStatusInfo());
    }

    @Test public void addPlaylistBadRequest()
    {
        Mockito.when(playlistService.add(Mockito.any(PlaylistDTO.class), Mockito.eq(DUMMY_USER))).thenReturn(false);

        Response response = playlistResource.addPlaylist(DUMMY_USER.getToken(), dto);

        assertEquals(Response.Status.BAD_REQUEST, response.getStatusInfo());
    }

    @Test public void deletePlaylistSuccessful()
    {
        // Arrange
        playlistResource.playlist.setId(dummyPlaylists.get(0).getId());
        Mockito.when(playlistService.delete(playlistResource.playlist, DUMMY_USER)).thenReturn(true);

        // Act
        Response response = playlistResource.deletePlaylist(playlistResource.playlist.getId(), DUMMY_USER.getToken());

        // Assert
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }

    @Test public void deletePlaylistForbidden()
    {
        // Arrange
        playlistResource.playlist.setId(dummyPlaylists.get(0).getId());
        Mockito.when(playlistService.delete(playlistResource.playlist, DUMMY_USER)).thenReturn(false);

        // Act
        Response response = playlistResource.deletePlaylist(playlistResource.playlist.getId(), "wrongToken");

        // Assert
        assertEquals(Response.Status.FORBIDDEN, response.getStatusInfo());
    }

    @Test public void deletePlaylistBadRequest()
    {
        // Arrange
        playlistResource.playlist.setId(dummyPlaylists.get(0).getId());
        Mockito.when(playlistService.delete(playlistResource.playlist, DUMMY_USER)).thenReturn(false);

        // Act
        Response response = playlistResource.deletePlaylist(playlistResource.playlist.getId(), DUMMY_USER.getToken());

        // Assert
        assertEquals(Response.Status.BAD_REQUEST, response.getStatusInfo());
    }

    @Test public void editPlaylistSuccessful()
    {
        // Arrange
        playlistResource.playlist.setId(dummyPlaylists.get(0).getId());
        Mockito.when(playlistService.editTitle(playlistResource.playlist, dto, DUMMY_USER)).thenReturn(true);

        // Act
        Response response = playlistResource.editPlaylist(playlistResource.playlist.getId(), DUMMY_USER.getToken(), dto);

        // Assert
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }

    @Test public void editPlaylistForbidden()
    {
        // Arrange
        playlistResource.playlist.setId(dummyPlaylists.get(0).getId());
        Mockito.when(playlistService.editTitle(playlistResource.playlist, dto, DUMMY_USER)).thenReturn(true);

        // Act
        Response response = playlistResource.editPlaylist(playlistResource.playlist.getId(), "wrongToken", dto);

        // Assert
        assertEquals(Response.Status.FORBIDDEN, response.getStatusInfo());
    }

    @Test public void editPlaylistBadRequest()
    {
        // Arrange
        playlistResource.playlist.setId(dummyPlaylists.get(0).getId());
        Mockito.when(playlistService.editTitle(playlistResource.playlist, dto, DUMMY_USER)).thenReturn(false);

        // Act
        Response response = playlistResource.editPlaylist(playlistResource.playlist.getId(), DUMMY_USER.getToken(), dto);

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
