package spotitube.dao;

import spotitube.domain.Playlist;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class PlaylistDAO
{
    @Resource(name = "jdbc/spotitube")
    DataSource dataSource;

    private static final String GET_ALL_PLAYLISTS_QUERY = "SELECT id, name, owner_id FROM playlist";
    private static final String GET_TOTAL_DURATION_QUERY = "SELECT SUM(duration) AS `duration` FROM track";

    public List<Playlist> getAll()
    {
        List<Playlist> response = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(GET_ALL_PLAYLISTS_QUERY);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                Playlist playlist = new Playlist();
                playlist.setId(result.getInt("id"));
                playlist.setName(result.getString("name"));
                playlist.setOwnerId(result.getInt("owner_id"));

                response.add(playlist);
            }
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }

        return response;
    }

    public int getTotalDuration()
    {
        AtomicInteger duration = new AtomicInteger();

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(GET_TOTAL_DURATION_QUERY);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                duration.set(result.getInt("duration"));
            }
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }

        return duration.get();
    }
}
