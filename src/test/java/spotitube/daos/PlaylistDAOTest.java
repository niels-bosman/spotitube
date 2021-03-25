package spotitube.daos;

import spotitube.dao.PlaylistDAO;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class PlaylistDAOTest
{
    private PlaylistDAO playlistDAO = new PlaylistDAO();
}
