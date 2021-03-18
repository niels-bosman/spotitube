package spotitube.dao;

import spotitube.domain.Playlist;
import spotitube.domain.User;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDAO
{
    @Resource(name = "jdbc/spotitube")
    DataSource dataSource;

    private static final String GET_ALL_PLAYLISTS_QUERY = "SELECT id, name, owner_id FROM playlist";
    private static final String GET_TOTAL_DURATION_QUERY = "SELECT SUM(duration) AS `duration` FROM track";
    private static final String DELETE_PLAYLIST_QUERY = "DELETE FROM playlist WHERE id = ? AND owner_id = ?";

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
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(GET_TOTAL_DURATION_QUERY);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return result.getInt("duration");
            }
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }

        return 0;
    }

    public boolean delete(Playlist playlist, User user)
    {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(DELETE_PLAYLIST_QUERY);
            statement.setInt(1, playlist.getId());
            statement.setInt(2, user.getId());
            return statement.executeUpdate() > 0;
        }
        catch (SQLException exception) {
            return false;
        }
    }
}
