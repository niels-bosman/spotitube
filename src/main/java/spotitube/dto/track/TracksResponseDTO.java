package spotitube.dto.track;

import java.util.List;

public class TracksResponseDTO
{
    private List<TrackDTO> tracks;


    public List<TrackDTO> getTracks()
    {
        return tracks;
    }

    public void setTracks(List<TrackDTO> tracks)
    {
        this.tracks = tracks;
    }
}
