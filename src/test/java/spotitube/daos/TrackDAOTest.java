package spotitube.daos;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import spotitube.TestHelpers;
import spotitube.dao.TrackDAO;
import spotitube.domain.Track;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class TrackDAOTest extends TestHelpers
{
    private final TrackDAO trackDAO = new TrackDAO();

    @Test public void getNotByPlaylistSuccessful() throws SQLException
    {
        // Arrange
        String expectedQuery = "SELECT *, 0 AS `offlineAvailable` FROM track WHERE id NOT IN (SELECT track_id FROM playlist_track WHERE playlist_id = ?);";

        DataSource dataSource = mock(DataSource.class);
        Connection connection = mock(Connection.class);
        ResultSet result = mock(ResultSet.class);
        PreparedStatement statement = mock(PreparedStatement.class);

        trackDAO.setDataSource(dataSource);

        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(expectedQuery)).thenReturn(statement);
        Mockito.when(statement.executeQuery()).thenReturn(result);
        Mockito.when(result.next()).thenReturn(true).thenReturn(false);

        Mockito.when(result.getInt("id")).thenReturn(DUMMY_TRACK.getId());
        Mockito.when(result.getString("title")).thenReturn(DUMMY_TRACK.getTitle());
        Mockito.when(result.getString("performer")).thenReturn(DUMMY_TRACK.getPerformer());
        Mockito.when(result.getInt("duration")).thenReturn(DUMMY_TRACK.getDuration());
        Mockito.when(result.getString("album")).thenReturn(DUMMY_TRACK.getAlbum());
        Mockito.when(result.getInt("playCount")).thenReturn(DUMMY_TRACK.getPlayCount());
        Mockito.when(result.getString("description")).thenReturn(DUMMY_TRACK.getDescription());
        Mockito.when(result.getBoolean("offlineAvailable")).thenReturn(DUMMY_TRACK.isOfflineAvailable());
        Mockito.when(result.getString("publicationDate")).thenReturn(DUMMY_TRACK.getPublicationDate());

        // Act
        List<Track> tracks = trackDAO.getByPlaylistId(DUMMY_TRACK.getId(), true);

        // Assert
        assertEquals(tracks.get(0).getId(), DUMMY_TRACK.getId());
    }

    @Test public void getNotByPlaylistErrorViaIn() throws SQLException
    {
        // Arrange
        String expectedQuery = "SELECT *, 0 AS `offlineAvailable` FROM track WHERE id NOT IN (SELECT track_id FROM playlist_track WHERE playlist_id = ?);";

        DataSource dataSource = mock(DataSource.class);
        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);

        trackDAO.setDataSource(dataSource);

        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(expectedQuery)).thenReturn(statement);
        Mockito.when(statement.executeQuery()).thenThrow(new SQLException());

        // Act
        List<Track> tracks = trackDAO.getAllNotInPlaylist(DUMMY_TRACK.getId());

        // Assert
        assertEquals(tracks.size(), 0);
    }

    @Test public void getNotByPlaylistSuccessfulViaIn() throws SQLException
    {
        // Arrange
        String expectedQuery = "SELECT *, 0 AS `offlineAvailable` FROM track WHERE id NOT IN (SELECT track_id FROM playlist_track WHERE playlist_id = ?);";

        DataSource dataSource = mock(DataSource.class);
        Connection connection = mock(Connection.class);
        ResultSet result = mock(ResultSet.class);
        PreparedStatement statement = mock(PreparedStatement.class);

        trackDAO.setDataSource(dataSource);

        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(expectedQuery)).thenReturn(statement);
        Mockito.when(statement.executeQuery()).thenReturn(result);
        Mockito.when(result.next()).thenReturn(true).thenReturn(false);

        Mockito.when(result.getInt("id")).thenReturn(DUMMY_TRACK.getId());
        Mockito.when(result.getString("title")).thenReturn(DUMMY_TRACK.getTitle());
        Mockito.when(result.getString("performer")).thenReturn(DUMMY_TRACK.getPerformer());
        Mockito.when(result.getInt("duration")).thenReturn(DUMMY_TRACK.getDuration());
        Mockito.when(result.getString("album")).thenReturn(DUMMY_TRACK.getAlbum());
        Mockito.when(result.getInt("playCount")).thenReturn(DUMMY_TRACK.getPlayCount());
        Mockito.when(result.getString("description")).thenReturn(DUMMY_TRACK.getDescription());
        Mockito.when(result.getBoolean("offlineAvailable")).thenReturn(DUMMY_TRACK.isOfflineAvailable());
        Mockito.when(result.getString("publicationDate")).thenReturn(DUMMY_TRACK.getPublicationDate());

        // Act
        List<Track> tracks = trackDAO.getAllNotInPlaylist(DUMMY_TRACK.getId());

        // Assert
        assertEquals(tracks.get(0).getId(), DUMMY_TRACK.getId());
    }

    @Test public void getByPlaylistSuccessfulViaNotIn() throws SQLException
    {
        // Arrange
        String expectedQuery = "SELECT t.*, pt.offlineAvailable FROM track t INNER JOIN playlist_track pt ON pt.track_id = t.id WHERE pt.playlist_id = ?";

        DataSource dataSource = mock(DataSource.class);
        Connection connection = mock(Connection.class);
        ResultSet result = mock(ResultSet.class);
        PreparedStatement statement = mock(PreparedStatement.class);

        trackDAO.setDataSource(dataSource);

        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(expectedQuery)).thenReturn(statement);
        Mockito.when(statement.executeQuery()).thenReturn(result);
        Mockito.when(result.next()).thenReturn(true).thenReturn(false);

        Mockito.when(result.getInt("id")).thenReturn(DUMMY_TRACK.getId());
        Mockito.when(result.getString("title")).thenReturn(DUMMY_TRACK.getTitle());
        Mockito.when(result.getString("performer")).thenReturn(DUMMY_TRACK.getPerformer());
        Mockito.when(result.getInt("duration")).thenReturn(DUMMY_TRACK.getDuration());
        Mockito.when(result.getString("album")).thenReturn(DUMMY_TRACK.getAlbum());
        Mockito.when(result.getInt("playCount")).thenReturn(DUMMY_TRACK.getPlayCount());
        Mockito.when(result.getString("description")).thenReturn(DUMMY_TRACK.getDescription());
        Mockito.when(result.getBoolean("offlineAvailable")).thenReturn(DUMMY_TRACK.isOfflineAvailable());
        Mockito.when(result.getString("publicationDate")).thenReturn(DUMMY_TRACK.getPublicationDate());

        // Act
        List<Track> tracks = trackDAO.getAllInPlaylist(DUMMY_TRACK.getId());

        // Assert
        assertEquals(tracks.get(0).getId(), DUMMY_TRACK.getId());
    }

    @Test public void deleteFromPlaylist() throws SQLException
    {
        // Arrange
        String expectedQuery = "DELETE pt FROM playlist_track pt JOIN playlist pl ON pt.playlist_id = pl.id WHERE pt.track_id = ? AND pt.playlist_id = ? AND pl.owner_id = ?";

        DataSource dataSource = mock(DataSource.class);
        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);

        trackDAO.setDataSource(dataSource);

        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(expectedQuery)).thenReturn(statement);
        Mockito.when(statement.executeUpdate()).thenReturn(1);

        // Act / assert
        assertTrue(trackDAO.deleteFromPlaylist(1, 1, DUMMY_USER.getId()));
    }

    @Test public void deleteFromPlaylistFail() throws SQLException
    {
        // Arrange
        String expectedQuery = "DELETE pt FROM playlist_track pt JOIN playlist pl ON pt.playlist_id = pl.id WHERE pt.track_id = ? AND pt.playlist_id = ? AND pl.owner_id = ?";

        DataSource dataSource = mock(DataSource.class);
        Connection connection = mock(Connection.class);

        trackDAO.setDataSource(dataSource);

        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(expectedQuery)).thenThrow(new SQLException());

        // Act / assert
        assertFalse(trackDAO.deleteFromPlaylist(1, 1, DUMMY_USER.getId()));
    }

    @Test public void addToPlaylist() throws SQLException
    {
        // Arrange
        String expectedQuery = "INSERT INTO playlist_track (track_id, playlist_id, offlineAvailable) VALUES (?, ?, ?)";

        DataSource dataSource = mock(DataSource.class);
        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);

        trackDAO.setDataSource(dataSource);

        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(expectedQuery)).thenReturn(statement);
        Mockito.when(statement.executeUpdate()).thenReturn(1);

        // Act / assert
        assertTrue(trackDAO.addToPlaylist(new Track(), DUMMY_USER.getId()));
    }

    @Test public void addToPlaylistFail() throws SQLException
    {
        // Arrange
        String expectedQuery = "INSERT INTO playlist_track (track_id, playlist_id, offlineAvailable) VALUES (?, ?, ?)";

        DataSource dataSource = mock(DataSource.class);
        Connection connection = mock(Connection.class);

        trackDAO.setDataSource(dataSource);

        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(expectedQuery)).thenThrow(new SQLException());

        // Act / assert
        assertFalse(trackDAO.addToPlaylist(new Track(), DUMMY_USER.getId()));
    }
}
