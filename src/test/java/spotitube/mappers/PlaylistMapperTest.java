package spotitube.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spotitube.domain.Playlist;
import spotitube.dto.playlist.PlaylistDTO;

import java.util.ArrayList;
import java.util.List;

public class PlaylistMapperTest
{
    private List<Playlist> playlists = new ArrayList<>();
    private List<PlaylistDTO> playlistDTOS = new ArrayList<>();

    @BeforeEach
    public void setUp()
    {
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
    public void mapSinglePlaylistDomainToPlaylistDTO()
    {
        // Arrange
        Playlist playlist = playlists.get(0);
        PlaylistDTO dto = PlaylistMapper.getInstance().convertToDTO(playlist);

        // Assert
        assertEquals(dto.getId(), playlist.getId());
        assertEquals(dto.getName(), playlist.getName());
    }
}
