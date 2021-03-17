package spotitube.domain;

import java.util.List;

public class Playlist
{
    private int id;
    private String name;
    private boolean owner;
    private List<Track> tracks;

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

    public List<Track> getTracks()
    {
        return tracks;
    }

    public void setTracks(List<Track> tracks)
    {
        this.tracks = tracks;
    }
}
