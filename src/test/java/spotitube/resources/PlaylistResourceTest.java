package spotitube.resources;

import com.sun.mail.imap.protocol.ID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import spotitube.domain.Playlist;
import spotitube.domain.User;
import spotitube.dto.playlist.PlaylistDTO;
import spotitube.exceptions.UnauthorizedException;
import spotitube.services.IdService;
import spotitube.services.PlaylistService;
import spotitube.services.UserService;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlaylistResourceTest
{
    @Mock private PlaylistService playlistService;
    @Mock private UserService userService;
    private IdService idService = new IdService();
    private PlaylistResource playlistResource;
    private List<Playlist> dummyPlaylists = new ArrayList<>();
    private User dummyUser = new User();
    private PlaylistDTO dto;

    @BeforeEach
    public void setup() throws UnauthorizedException
    {
        MockitoAnnotations.openMocks(this);
        generateDummyUser();
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
        Mockito.when(userService.authenticateToken(Mockito.eq(dummyUser.getToken()))).thenReturn(dummyUser);

        // Mock playlistService methods, just to be sure the response at least contains values
        Mockito.when(playlistService.getAll(dummyUser)).thenReturn(new ArrayList<>());
        Mockito.when(playlistService.getTotalDuration(new ArrayList<>())).thenReturn(0);
    }

    @Test
    public void getAll()
    {
        // Act
        Response response = playlistResource.getAll(dummyUser.getToken());

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void getAllNotAuthorized()
    {
        // Act
        Response response = playlistResource.getAll("verkeerdToken");

        // Assert
        assertEquals(Response.Status.FORBIDDEN, response.getStatusInfo());
    }

    @Test public void addPlaylistSuccessful()
    {
        // Arrange
        Mockito.when(playlistService.add(Mockito.any(PlaylistDTO.class), Mockito.eq(dummyUser))).thenReturn(true);

        // Act
        Response response = playlistResource.addPlaylist(dummyUser.getToken(), dto);

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test public void addPlaylistForbidden()
    {
        Mockito.when(playlistService.add(Mockito.any(PlaylistDTO.class), Mockito.eq(dummyUser))).thenReturn(false);

        Response response = playlistResource.addPlaylist("invalidToken", dto);

        assertEquals(Response.Status.FORBIDDEN, response.getStatusInfo());
    }

    @Test public void addPlaylistBadRequest()
    {
        Mockito.when(playlistService.add(Mockito.any(PlaylistDTO.class), Mockito.eq(dummyUser))).thenReturn(false);

        Response response = playlistResource.addPlaylist(dummyUser.getToken(), dto);

        assertEquals(Response.Status.BAD_REQUEST, response.getStatusInfo());
    }

    @Test public void deletePlaylistSuccessful()
    {
        // Arrange
        playlistResource.playlist.setId(dummyPlaylists.get(0).getId());
        Mockito.when(playlistService.delete(playlistResource.playlist, dummyUser)).thenReturn(true);

        // Act
        Response response = playlistResource.deletePlaylist(playlistResource.playlist.getId(), dummyUser.getToken());

        // Assert
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }

    @Test public void deletePlaylistForbidden()
    {
        // Arrange
        playlistResource.playlist.setId(dummyPlaylists.get(0).getId());
        Mockito.when(playlistService.delete(playlistResource.playlist, dummyUser)).thenReturn(false);

        // Act
        Response response = playlistResource.deletePlaylist(playlistResource.playlist.getId(), "wrongToken");

        // Assert
        assertEquals(Response.Status.FORBIDDEN, response.getStatusInfo());
    }

    @Test public void deletePlaylistBadRequest()
    {
        // Arrange
        playlistResource.playlist.setId(dummyPlaylists.get(0).getId());
        Mockito.when(playlistService.delete(playlistResource.playlist, dummyUser)).thenReturn(false);

        // Act
        Response response = playlistResource.deletePlaylist(playlistResource.playlist.getId(), dummyUser.getToken());

        // Assert
        assertEquals(Response.Status.BAD_REQUEST, response.getStatusInfo());
    }

    @Test public void editPlaylistSuccessful()
    {
        // Arrange
        playlistResource.playlist.setId(dummyPlaylists.get(0).getId());
        Mockito.when(playlistService.editTitle(playlistResource.playlist, dto, dummyUser)).thenReturn(true);

        // Act
        Response response = playlistResource.editPlaylist(playlistResource.playlist.getId(), dummyUser.getToken(), dto);

        // Assert
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }

    @Test public void editPlaylistForbidden()
    {
        // Arrange
        playlistResource.playlist.setId(dummyPlaylists.get(0).getId());
        Mockito.when(playlistService.editTitle(playlistResource.playlist, dto, dummyUser)).thenReturn(true);

        // Act
        Response response = playlistResource.editPlaylist(playlistResource.playlist.getId(), "wrongToken", dto);

        // Assert
        assertEquals(Response.Status.FORBIDDEN, response.getStatusInfo());
    }

    @Test public void editPlaylistBadRequest()
    {
        // Arrange
        playlistResource.playlist.setId(dummyPlaylists.get(0).getId());
        Mockito.when(playlistService.editTitle(playlistResource.playlist, dto, dummyUser)).thenReturn(false);

        // Act
        Response response = playlistResource.editPlaylist(playlistResource.playlist.getId(), dummyUser.getToken(), dto);

        // Assert
        assertEquals(Response.Status.BAD_REQUEST, response.getStatusInfo());
    }

    public void generateDummyUser()
    {
        dummyUser.setId(1);
        dummyUser.setToken();
        dummyUser.setPassword("password");
        dummyUser.setUsername("username");
        dummyUser.setName("name");
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
