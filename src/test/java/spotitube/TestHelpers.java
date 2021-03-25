package spotitube;

import spotitube.domain.Track;
import spotitube.domain.User;

public class TestHelpers
{
    public static final User DUMMY_USER = makeUser();
    public static final Track DUMMY_TRACK = makeTrack();

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
}
