package spotitube;

import spotitube.data.domain.Playlist;
import spotitube.data.domain.Track;
import spotitube.data.domain.User;

import java.util.ArrayList;

public class DummyGenerator
{
    public static final User DUMMY_USER = makeUser();
    public static final Track DUMMY_TRACK = makeTrack();
    public static final Playlist DUMMY_PLAYLIST = makePlaylist();

    /**
     * Makes a user.
     *
     * @return the user
     */
    public static User makeUser()
    {
        User user = new User();

        user.setId(1);
        user.setToken("valid-user-token");
        user.setPassword("valid-password");
        user.setUsername("valid-username");
        user.setName("valid-name");

        return user;
    }

    /**
     * Makes a track.
     *
     * @return the track
     */
    public static Track makeTrack()
    {
        Track track = new Track();

        track.setId(1);
        track.setOfflineAvailable(true);
        track.setDescription("valid-description");
        track.setPublicationDate("valid-date");
        track.setPlayCount(1);
        track.setPerformer("valid-performer");
        track.setAlbum("valid-album");
        track.setDuration(100);
        track.setTitle("valid-title");

        return track;
    }

    /**
     * Makes a playlist
     *
     * @return the playlist
     */
    public static Playlist makePlaylist()
    {
        Playlist playlist = new Playlist();

        playlist.setId(1);
        playlist.setName("valid-name");
        playlist.setOwnerId(1);
        playlist.setTracks(new ArrayList<>());

        return playlist;
    }
}
