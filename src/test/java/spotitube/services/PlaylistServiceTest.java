package spotitube.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import spotitube.TestHelpers;
import spotitube.dao.PlaylistDAO;
import spotitube.dao.TrackDAO;
import spotitube.domain.Playlist;
import spotitube.domain.Track;
import spotitube.domain.User;
import spotitube.dto.playlist.PlaylistDTO;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlaylistServiceTest extends TestHelpers
{
    /**
     * The tests in this file are not useful (yet)
     * these tests are here so if the logic in these
     * methods is upgraded in the future, it is easily testable.
     */

    @Mock private PlaylistDAO playlistDAO;
    private PlaylistService playlistService = new PlaylistService();
    private List<Playlist> playlists = new ArrayList<>();

    @BeforeEach
    public void setup()
    {
        MockitoAnnotations.openMocks(this);
        playlistService.setPlaylistDAO(playlistDAO);

        playlists.add(new Playlist());
        playlists.add(new Playlist());
    }

    @Test public void getAllSuccessful()
    {
        // Arrange
        Mockito.when(playlistDAO.getAll()).thenReturn(playlists);

        // Act
        List<PlaylistDTO> playlists = playlistService.getAll(DUMMY_USER);

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
        Mockito.when(playlistDAO.editTitle(playlist, playlistDTO, DUMMY_USER)).thenReturn(true);

        // Act / assert
        assertFalse(playlistService.editTitle(new Playlist(), new PlaylistDTO(), DUMMY_USER));
    }

    @Test
    public void editedPlaylistError()
    {
        // Arrange
        Playlist playlist = new Playlist();
        PlaylistDTO playlistDTO = new PlaylistDTO();
        Mockito.when(playlistDAO.editTitle(playlist, playlistDTO, DUMMY_USER)).thenReturn(false);

        // Act / assert
        assertFalse(playlistService.editTitle(playlist, playlistDTO, DUMMY_USER));
    }

    @Test public void editedPlaylistSuccessful()
    {
        // Arrange
        Playlist playlist = new Playlist();
        PlaylistDTO playlistDTO = new PlaylistDTO();
        Mockito.when(playlistDAO.editTitle(playlist, playlistDTO, DUMMY_USER)).thenReturn(true);

        // Act / assert
        assertFalse(playlistService.editTitle(new Playlist(), new PlaylistDTO(), DUMMY_USER));
    }

    @Test public void addSuccessful()
    {
        // Arrange
        PlaylistDTO playlistDTO = new PlaylistDTO();
        Mockito.when(playlistService.add(playlistDTO, DUMMY_USER)).thenReturn(true);

        // Act / assert
        assertFalse(playlistService.add(playlistDTO, DUMMY_USER));
    }

    @Test public void addError()
    {
        // Arrange
        PlaylistDTO playlistDTO = new PlaylistDTO();
        Mockito.when(playlistService.add(playlistDTO, DUMMY_USER)).thenReturn(false);

        // Act / assert
        assertFalse(playlistService.add(playlistDTO, DUMMY_USER));
    }

    @Test public void removedSuccessful()
    {
        // Arrange
        Playlist playlist = new Playlist();
        Mockito.when(playlistService.delete(playlist, DUMMY_USER)).thenReturn(true);

        // Act / assert
        assertTrue(playlistService.delete(playlist, DUMMY_USER));
    }

    @Test public void removedError()
    {
        // Arrange
        Playlist playlist = new Playlist();
        Mockito.when(playlistService.delete(playlist, DUMMY_USER)).thenReturn(false);

        // Act / assert
        assertFalse(playlistService.delete(playlist, DUMMY_USER));
    }

    @Test public void isOwned()
    {
        // Arrange
        Playlist playlist = new Playlist();
        Mockito.when(playlistService.isOwnedBy(playlist.getId(), DUMMY_USER.getId())).thenReturn(true);

        // Act / assert
        assertTrue(playlistService.isOwnedBy(playlist.getId(), DUMMY_USER.getId()));
    }

    @Test public void isNotOwned()
    {
        // Arrange
        Playlist playlist = new Playlist();
        Mockito.when(playlistService.isOwnedBy(playlist.getId(), DUMMY_USER.getId())).thenReturn(false);

        // Act / assert
        assertFalse(playlistService.isOwnedBy(playlist.getId(), DUMMY_USER.getId()));
    }
}
