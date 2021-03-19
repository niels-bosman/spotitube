package spotitube.dao;

import spotitube.domain.Track;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrackDAO
{
    @Resource(name = "jdbc/spotitube")
    DataSource dataSource;

    private static final String GET_TRACKS_IN_PLAYLIST_QUERY =
            "SELECT t.*, pt.offlineAvailable FROM track t INNER JOIN playlist_track pt ON pt.track_id = t.id WHERE pt.playlist_id = ?";

    private static final String GET_TRACKS_NOT_IN_PLAYLIST_QUERY =
            "SELECT *, 0 AS `offlineAvailable` FROM track WHERE id NOT IN (SELECT track_id FROM playlist_track WHERE playlist_id = ?);";

    private static final String DELETE_TRACK_FROM_PLAYLIST_QUERY = "DELETE pt FROM playlist_track pt JOIN playlist pl ON pt.playlist_id = pl.id WHERE pt.track_id = ? AND pt.playlist_id = ? AND pl.owner_id = ?";
    private static final String ADD_TO_PLAYLIST_QUERY = "INSERT INTO playlist_track (track_id, playlist_id, offlineAvailable) VALUES (?, ?, ?)";

    public List<Track> getAllNotInPlaylist(int playlistId)
    {
        return getByPlaylistId(playlistId, true);
    }

    public List<Track> getAllInPlaylist(int playlistId)
    {
        return getByPlaylistId(playlistId, false);
    }

    private List<Track> getByPlaylistId(int playlistId, boolean notInPlaylist)
    {
        List<Track> tracks = new ArrayList<>();
        String query = notInPlaylist ? GET_TRACKS_NOT_IN_PLAYLIST_QUERY : GET_TRACKS_IN_PLAYLIST_QUERY;

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, playlistId);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                Track track = new Track();
                track.setId(result.getInt("id"));
                track.setTitle(result.getString("title"));
                track.setPerformer(result.getString("performer"));
                track.setDuration(result.getInt("duration"));
                track.setAlbum(result.getString("album"));
                track.setPlayCount(result.getInt("playCount"));
                track.setDescription(result.getString("description"));
                track.setOfflineAvailable(result.getBoolean("offlineAvailable"));
                track.setPublicationDate(result.getString("publicationDate"));

                tracks.add(track);
            }
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }

        return tracks;
    }

    public boolean deleteFromPlaylist(int playlistId, int trackId, int userId)
    {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(DELETE_TRACK_FROM_PLAYLIST_QUERY);
            statement.setInt(1, trackId);
            statement.setInt(2, playlistId);
            statement.setInt(3, userId);

            return statement.executeUpdate() > 0;
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }

        return false;
    }

    public boolean addToPlaylist(Track track, int playlistId)
    {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(ADD_TO_PLAYLIST_QUERY);
            statement.setInt(1, track.getId());
            statement.setInt(2, playlistId);
            statement.setBoolean(3, track.isOfflineAvailable());

            return statement.executeUpdate() > 0;
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }

        return false;
    }
}
