package spotitube.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import spotitube.data.dao.TrackDAO;
import spotitube.data.domain.Track;
import spotitube.dto.track.TrackDTO;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TrackServiceTest
{
    /**
     * The tests in this file are not useful (yet)
     * these tests are here so if the logic in these
     * methods is upgraded in the future, it is easily testable.
     */

    @Mock
    private TrackDAO trackDAO;
    private TrackService trackService = new TrackService();

    private List<Track> dummyTracks = new ArrayList<>();

    @BeforeEach
    public void setup()
    {
        MockitoAnnotations.openMocks(this);
        trackService.setTrackDAO(trackDAO);

        dummyTracks.add(new Track());
        dummyTracks.add(new Track());
    }

    @Test
    public void getTracksInPlaylistSuccessful()
    {
        // Arrange
        Mockito.when(trackDAO.getAllInPlaylist(1)).thenReturn(dummyTracks);

        // Act
        List<TrackDTO> tracks = trackService.getAllByPlaylist(1);

        // Assert
        assertNotNull(tracks);
        assertTrue(tracks.size() > 0);
    }

    @Test
    public void getTracksNotInPlaylistSuccessful()
    {
        // Arrange
        Mockito.when(trackDAO.getAllNotInPlaylist(1)).thenReturn(dummyTracks);

        // Act
        List<TrackDTO> tracks = trackService.getAllNotInPlaylist(1);

        // Assert
        assertNotNull(tracks);
        assertTrue(tracks.size() > 0);
    }

    @Test
    public void addTrackSuccessful()
    {
        // Arrange
        Mockito.when(trackDAO.addToPlaylist(Mockito.any(Track.class), Mockito.anyInt())).thenReturn(true);

        // Act / assert
        assertTrue(trackService.addTrackToPlaylist(new TrackDTO(), 1));
    }

    @Test
    public void addTrackNotSuccessful()
    {
        // Arrange
        Mockito.when(trackDAO.addToPlaylist(Mockito.any(Track.class), Mockito.anyInt())).thenReturn(false);

        // Act / assert
        assertFalse(trackService.addTrackToPlaylist(new TrackDTO(), 1));
    }

    @Test
    public void removeTrackSuccessful()
    {
        // Arrange
        Mockito.when(trackDAO.deleteFromPlaylist(1, 1, 1)).thenReturn(true);

        // Act / assert
        assertTrue(trackService.deleteFromPlaylist(1, 1, 1));
    }

    @Test
    public void removeTrackNotSuccessful()
    {
        // Arrange
        Mockito.when(trackDAO.deleteFromPlaylist(1, 1, 1)).thenReturn(false);

        // Act / assert
        assertFalse(trackService.deleteFromPlaylist(1, 1, 1));
    }
}
