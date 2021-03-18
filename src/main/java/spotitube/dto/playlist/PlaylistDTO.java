package spotitube.dto.playlist;

import spotitube.dto.track.TrackDTO;

import java.util.List;

public class PlaylistDTO
{
    private int id;
    private String name;
    private boolean owner = false;
    private List<TrackDTO> tracks;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public boolean isOwner()
    {
        return owner;
    }

    public void setOwner(boolean owner)
    {
        this.owner = owner;
    }

    public List<TrackDTO> getTracks()
    {
        return tracks;
    }

    public void setTracks(List<TrackDTO> tracks)
    {
        this.tracks = tracks;
    }
}
