package spotitube.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import spotitube.dao.PlaylistDAO;
import spotitube.dao.TrackDAO;
import spotitube.domain.Playlist;
import spotitube.domain.Track;
import spotitube.domain.User;
import spotitube.dto.playlist.PlaylistDTO;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlaylistServiceTest
{
    /**
     * The tests in this file are not useful (yet)
     * these tests are here so if the logic in these
     * methods is upgraded in the future, it is easily testable.
     */

    @Mock private PlaylistDAO playlistDAO;
    private PlaylistService playlistService = new PlaylistService();
    private User dummyUser = new User();
    private List<Playlist> playlists = new ArrayList<>();

    @BeforeEach
    public void setup()
    {
        MockitoAnnotations.openMocks(this);
        playlistService.setPlaylistDAO(playlistDAO);

        generateDummyUser();
        playlists.add(new Playlist());
        playlists.add(new Playlist());
    }

    public void generateDummyUser()
    {
        dummyUser.setId(1);
        dummyUser.setToken();
        dummyUser.setPassword("password");
        dummyUser.setUsername("username");
        dummyUser.setName("name");
    }

    @Test public void getAllSuccessful()
    {
        // Arrange
        Mockito.when(playlistDAO.getAll()).thenReturn(playlists);

        // Act
        List<PlaylistDTO> playlists = playlistService.getAll(dummyUser);

        // Assert
        assertNotNull(playlists);
        assertTrue(playlists.size() > 0);
    }

    @Test public void getTotalDurationSuccessful()
    {
        // Arrange
        Mockito.when(playlistDAO.getTotalDuration(playlists)).thenReturn(200);

        // Assert
        assertEquals(200, playlistService.getTotalDuration(playlists));
    }

    @Test public void editedSuccessful()
    {
        // Arrange
        Playlist playlist = new Playlist();
        PlaylistDTO playlistDTO = new PlaylistDTO();
        Mockito.when(playlistDAO.editTitle(playlist, playlistDTO, dummyUser)).thenReturn(true);

        // Act / assert
        assertFalse(playlistService.editTitle(new Playlist(), new PlaylistDTO(), dummyUser));
    }

    @Test
    public void editedPlaylistError()
    {
        // Arrange
        Playlist playlist = new Playlist();
        PlaylistDTO playlistDTO = new PlaylistDTO();
        Mockito.when(playlistDAO.editTitle(playlist, playlistDTO, dummyUser)).thenReturn(false);

        // Act / assert
        assertFalse(playlistService.editTitle(playlist, playlistDTO, dummyUser));
    }

    @Test public void editedPlaylistSuccessful()
    {
        // Arrange
        Playlist playlist = new Playlist();
        PlaylistDTO playlistDTO = new PlaylistDTO();
        Mockito.when(playlistDAO.editTitle(playlist, playlistDTO, dummyUser)).thenReturn(true);

        // Act / assert
        assertFalse(playlistService.editTitle(new Playlist(), new PlaylistDTO(), dummyUser));
    }

    @Test public void addSuccessful()
    {
        // Arrange
        PlaylistDTO playlistDTO = new PlaylistDTO();
        Mockito.when(playlistService.add(playlistDTO, dummyUser)).thenReturn(true);

        // Act / assert
        assertFalse(playlistService.add(playlistDTO, dummyUser));
    }

    @Test public void addError()
    {
        // Arrange
        PlaylistDTO playlistDTO = new PlaylistDTO();
        Mockito.when(playlistService.add(playlistDTO, dummyUser)).thenReturn(false);

        // Act / assert
        assertFalse(playlistService.add(playlistDTO, dummyUser));
    }

    @Test public void removedSuccessful()
    {
        // Arrange
        Playlist playlist = new Playlist();
        Mockito.when(playlistService.delete(playlist, dummyUser)).thenReturn(true);

        // Act / assert
        assertTrue(playlistService.delete(playlist, dummyUser));
    }

    @Test public void removedError()
    {
        // Arrange
        Playlist playlist = new Playlist();
        Mockito.when(playlistService.delete(playlist, dummyUser)).thenReturn(false);

        // Act / assert
        assertFalse(playlistService.delete(playlist, dummyUser));
    }

    @Test public void isOwned()
    {
        // Arrange
        Playlist playlist = new Playlist();
        Mockito.when(playlistService.isOwnedBy(playlist.getId(), dummyUser.getId())).thenReturn(true);

        // Act / assert
        assertTrue(playlistService.isOwnedBy(playlist.getId(), dummyUser.getId()));
    }

    @Test public void isNotOwned()
    {
        // Arrange
        Playlist playlist = new Playlist();
        Mockito.when(playlistService.isOwnedBy(playlist.getId(), dummyUser.getId())).thenReturn(false);

        // Act / assert
        assertFalse(playlistService.isOwnedBy(playlist.getId(), dummyUser.getId()));
    }
}
