package spotitube.dto.track;

import java.util.List;

public class TracksResponseDTO
{
    public List<TrackDTO> tracks;

    public void setTracks(List<TrackDTO> tracks)
    {
        this.tracks = tracks;
    }
}
