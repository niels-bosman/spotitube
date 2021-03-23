package spotitube.dto.playlist;

import java.util.List;

public class PlaylistResponseDTO
{
    public List<PlaylistDTO> playlists;
    public int length;

    public void setPlaylists(List<PlaylistDTO> playlists)
    {
        this.playlists = playlists;
    }

    public void setLength(int length)
    {
        this.length = length;
    }
}
