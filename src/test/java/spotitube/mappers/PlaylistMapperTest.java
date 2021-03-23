package spotitube.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spotitube.domain.Playlist;
import spotitube.domain.User;
import spotitube.dto.playlist.PlaylistDTO;

import java.util.ArrayList;
import java.util.List;

public class PlaylistMapperTest
{
    private List<Playlist> playlists = new ArrayList<>();
    private User user = new User();
    private List<PlaylistDTO> playlistDTOS = new ArrayList<>();

    @BeforeEach
    public void setUp()
    {
        user.setId(1);

        for (int i = 0; i < 3; i++) {
            Playlist playlist = new Playlist();
            playlist.setId(i);
            playlist.setName("Naam " + i);
            playlist.setOwnerId(i);
            playlist.setTracks(new ArrayList<>());

            playlists.add(playlist);
        }

        playlists.forEach(playlist -> {
            PlaylistDTO dto = new PlaylistDTO();
            dto.setId(playlist.getId());
            dto.setName(playlist.getName());
            dto.setOwner(true);
            dto.setTracks(new ArrayList<>());

            playlistDTOS.add(dto);
        });
    }

    @Test
    public void mapSingleToDTO()
    {
        // Arrange
        Playlist playlist = playlists.get(0);
        PlaylistDTO dto = PlaylistMapper.getInstance().convertToDTO(playlist, user);

        // Assert
        assertEquals(dto.getId(), playlist.getId());
        assertEquals(dto.getName(), playlist.getName());
    }

    @Test
    public void mapSingleToDomain()
    {
        // Arrange
        PlaylistDTO dto = playlistDTOS.get(0);
        Playlist playlist = PlaylistMapper.getInstance().convertToEntity(dto);

        // Assert
        assertEquals(dto.getId(), playlist.getId());
        assertEquals(dto.getName(), playlist.getName());
    }

    @Test
    public void mapMultipleToDTO()
    {
        // Arrange
        List<PlaylistDTO> playlistDTOS = PlaylistMapper.getInstance().convertToDTO(playlists, user);

        // Assert
        for (int i = 0; i < playlistDTOS.size(); i++) {
            assertEquals(playlistDTOS.get(i).getId(), playlists.get(i).getId());
            assertEquals(playlistDTOS.get(i).getName(), playlists.get(i).getName());
        }
    }

    @Test
    public void mapMultipleToDomain()
    {
        // Arrange
        List<Playlist> playlists = PlaylistMapper.getInstance().convertToEntity(playlistDTOS);

        // Assert
        for (int i = 0; i < playlists.size(); i++) {
            assertEquals(playlists.get(i).getId(), playlistDTOS.get(i).getId());
            assertEquals(playlists.get(i).getName(), playlistDTOS.get(i).getName());
        }
    }
}
