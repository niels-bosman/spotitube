package spotitube.dao;

import spotitube.domain.Playlist;
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

    public List<Track> getAllNotInPlaylist(int playlistId)
    {
        return getByPlaylistId(playlistId, true);
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
}
