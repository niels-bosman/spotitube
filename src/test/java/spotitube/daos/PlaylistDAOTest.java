package spotitube.daos;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import spotitube.DummyGenerator;
import spotitube.data.dao.PlaylistDAO;
import spotitube.domain.Playlist;
import spotitube.dto.playlist.PlaylistDTO;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class PlaylistDAOTest extends DummyGenerator
{
    private PlaylistDAO playlistDAO = new PlaylistDAO();

    @Test public void getAllSuccess() throws SQLException
    {
        // Arrange
        String expectedQuery = "SELECT id, name, owner_id FROM playlist";

        DataSource dataSource = mock(DataSource.class);
        Connection connection = mock(Connection.class);
        ResultSet result = mock(ResultSet.class);
        PreparedStatement statement = mock(PreparedStatement.class);

        playlistDAO.setDataSource(dataSource);

        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(expectedQuery)).thenReturn(statement);
        Mockito.when(statement.executeQuery()).thenReturn(result);
        Mockito.when(result.next()).thenReturn(true).thenReturn(false);

        Mockito.when(result.getInt("id")).thenReturn(DUMMY_PLAYLIST.getId());
        Mockito.when(result.getString("name")).thenReturn(DUMMY_PLAYLIST.getName());
        Mockito.when(result.getInt("owner_id")).thenReturn(DUMMY_PLAYLIST.getOwnerId());

        // Act
        List<Playlist> playlists = playlistDAO.getAll();

        // Assert
        assertEquals(playlists.get(0).getId(), DUMMY_TRACK.getId());
    }

    @Test public void getAllException() throws SQLException
    {
        // Arrange
        String expectedQuery = "SELECT id, name, owner_id FROM playlist";

        DataSource dataSource = mock(DataSource.class);
        Connection connection = mock(Connection.class);
        ResultSet result = mock(ResultSet.class);
        PreparedStatement statement = mock(PreparedStatement.class);

        playlistDAO.setDataSource(dataSource);

        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(expectedQuery)).thenReturn(statement);
        Mockito.when(statement.executeQuery()).thenThrow(new SQLException());

        Mockito.when(result.getInt("id")).thenReturn(DUMMY_PLAYLIST.getId());
        Mockito.when(result.getString("name")).thenReturn(DUMMY_PLAYLIST.getName());
        Mockito.when(result.getInt("owner_id")).thenReturn(DUMMY_PLAYLIST.getOwnerId());

        // Act
        List<Playlist> playlists = playlistDAO.getAll();

        // Assert
        assertEquals(playlists.size(), 0);
    }

    @Test public void getTotalDurationSuccess() throws SQLException
    {
        // Arrange
        String expectedQuery = "SELECT SUM(t.duration) AS `duration` FROM track t INNER JOIN playlist_track pt ON pt.track_id = t.id WHERE pt.playlist_id = ?";

        DataSource dataSource = mock(DataSource.class);
        Connection connection = mock(Connection.class);
        ResultSet result = mock(ResultSet.class);
        PreparedStatement statement = mock(PreparedStatement.class);

        playlistDAO.setDataSource(dataSource);

        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(expectedQuery)).thenReturn(statement);
        Mockito.when(statement.executeQuery()).thenReturn(result);
        Mockito.when(result.next()).thenReturn(true);

        Mockito.when(result.getInt("duration")).thenReturn(DUMMY_TRACK.getDuration());

        List<Playlist> playlists = new ArrayList<>();
        playlists.add(DUMMY_PLAYLIST);

        // Act
        int duration = playlistDAO.getTotalDuration(playlists);

        // Assert
        assertEquals(duration, DUMMY_TRACK.getDuration());
    }

    @Test public void getTotalDurationException() throws SQLException
    {
        // Arrange
        String expectedQuery = "SELECT SUM(t.duration) AS `duration` FROM track t INNER JOIN playlist_track pt ON pt.track_id = t.id WHERE pt.playlist_id = ?";

        DataSource dataSource = mock(DataSource.class);
        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);

        playlistDAO.setDataSource(dataSource);

        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(expectedQuery)).thenReturn(statement);
        Mockito.when(statement.executeQuery()).thenThrow(new SQLException());

        List<Playlist> playlists = new ArrayList<>();
        playlists.add(DUMMY_PLAYLIST);

        // Act
        int duration = playlistDAO.getTotalDuration(playlists);

        // Assert
        assertEquals(duration, 0);
    }

    @Test public void deleteSuccess() throws SQLException
    {
        // Arrange
        String expectedQuery = "DELETE FROM playlist WHERE id = ? AND owner_id = ?";

        DataSource dataSource = mock(DataSource.class);
        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);

        playlistDAO.setDataSource(dataSource);

        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(expectedQuery)).thenReturn(statement);
        Mockito.when(statement.executeUpdate()).thenReturn(1);

        // Act / assert
        assertTrue(playlistDAO.delete(DUMMY_PLAYLIST.getId(), DUMMY_USER.getId()));
    }

    @Test public void deleteException() throws SQLException
    {
        // Arrange
        String expectedQuery = "DELETE FROM playlist WHERE id = ? AND owner_id = ?";

        DataSource dataSource = mock(DataSource.class);
        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);

        playlistDAO.setDataSource(dataSource);

        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(expectedQuery)).thenReturn(statement);
        Mockito.when(statement.executeUpdate()).thenThrow(new SQLException());

        // Act / assert
        assertFalse(playlistDAO.delete(DUMMY_PLAYLIST.getId(), DUMMY_USER.getId()));
    }

    @Test public void addSuccess() throws SQLException
    {
        // Arrange
        String expectedQuery = "INSERT INTO playlist (name, owner_id) VALUES (?, ?)";

        DataSource dataSource = mock(DataSource.class);
        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);

        playlistDAO.setDataSource(dataSource);

        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(expectedQuery)).thenReturn(statement);
        Mockito.when(statement.executeUpdate()).thenReturn(1);

        // Act / assert
        assertTrue(playlistDAO.add(DUMMY_PLAYLIST));
    }

    @Test public void addException() throws SQLException
    {
        // Arrange
        String expectedQuery = "INSERT INTO playlist (name, owner_id) VALUES (?, ?)";

        DataSource dataSource = mock(DataSource.class);
        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);

        playlistDAO.setDataSource(dataSource);

        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(expectedQuery)).thenReturn(statement);
        Mockito.when(statement.executeUpdate()).thenThrow(new SQLException());

        // Act / assert
        assertFalse(playlistDAO.add(DUMMY_PLAYLIST));
    }

    @Test public void editSuccess() throws SQLException
    {
        // Arrange
        String expectedQuery = "UPDATE playlist SET name = ? WHERE id = ? AND owner_id = ?";

        DataSource dataSource = mock(DataSource.class);
        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);

        playlistDAO.setDataSource(dataSource);

        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(expectedQuery)).thenReturn(statement);
        Mockito.when(statement.executeUpdate()).thenReturn(1);

        // Act / assert
        assertTrue(playlistDAO.editTitle(DUMMY_PLAYLIST.getId(), new PlaylistDTO(), DUMMY_USER.getId()));
    }

    @Test public void editException() throws SQLException
    {
        // Arrange
        String expectedQuery = "UPDATE playlist SET name = ? WHERE id = ? AND owner_id = ?";

        DataSource dataSource = mock(DataSource.class);
        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);

        playlistDAO.setDataSource(dataSource);

        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(expectedQuery)).thenReturn(statement);
        Mockito.when(statement.executeUpdate()).thenThrow(new SQLException());

        // Act / assert
        assertFalse(playlistDAO.editTitle(DUMMY_PLAYLIST.getId(), new PlaylistDTO(), DUMMY_USER.getId()));
    }

    @Test public void ownedByUserSuccess() throws SQLException
    {
        // Arrange
        String expectedQuery = "SELECT 1 FROM playlist WHERE id = ? AND owner_id = ?";

        DataSource dataSource = mock(DataSource.class);
        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);
        ResultSet result = mock(ResultSet.class);

        playlistDAO.setDataSource(dataSource);

        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(expectedQuery)).thenReturn(statement);
        Mockito.when(statement.executeQuery()).thenReturn(result);
        Mockito.when(result.next()).thenReturn(true);

        // Act / assert
        assertTrue(playlistDAO.isOwnedByUser(DUMMY_PLAYLIST.getId(), DUMMY_USER.getId()));
    }

    @Test public void ownedByUserException() throws SQLException
    {
        // Arrange
        String expectedQuery = "SELECT 1 FROM playlist WHERE id = ? AND owner_id = ?";

        DataSource dataSource = mock(DataSource.class);
        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);

        playlistDAO.setDataSource(dataSource);

        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(expectedQuery)).thenThrow(new SQLException());

        // Act / assert
        assertFalse(playlistDAO.isOwnedByUser(DUMMY_PLAYLIST.getId(), DUMMY_USER.getId()));
    }
}
