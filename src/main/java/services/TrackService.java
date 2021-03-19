package services;

import spotitube.dao.TrackDAO;
import spotitube.domain.Track;
import spotitube.dto.track.TrackDTO;
import spotitube.mappers.TrackMapper;

import javax.inject.Inject;
import java.util.List;

public class TrackService
{
    private TrackDAO trackDAO;

    /**
     * Getter of all the tracks that are NOT in a specific playlist.
     *
     * @param playlistId The playlist ID to check on.
     * @return All the tracks that are not already in a playlist.
     */
    public List<TrackDTO> getAllNotInPlaylist(int playlistId)
    {
        List<Track> tracks = trackDAO.getAllNotInPlaylist(playlistId);

        return TrackMapper.getInstance().convertToDTO(tracks);
    }

    /**
     * Getter of all the tracks currently in the playlist.
     *
     * @param playlistId the playlist id
     * @return the all by playlist
     */
    public List<TrackDTO> getAllByPlaylist(int playlistId)
    {
        List<Track> tracks = trackDAO.getAllInPlaylist(playlistId);

        return TrackMapper.getInstance().convertToDTO(tracks);
    }

    /**
     * Deletes a specific track from a playlist.
     *
     * @param playlistId The ID of the playlist.
     * @param trackId    The ID of the track.
     * @param userId     The ID of the authenticated user.
     * @return If the statement was successful.
     */
    public boolean deleteFromPlaylist(int playlistId, int trackId, int userId)
    {
        return trackDAO.deleteFromPlaylist(playlistId, trackId, userId);
    }

    /**
     * Adds a specific track to a playlist.
     *
     * @param trackDTO   The track to add.
     * @param playlistId The ID of the playlist.
     * @return If the statement was successful.
     */
    public boolean addTrackToPlaylist(TrackDTO trackDTO, int playlistId)
    {
        Track track = TrackMapper.getInstance().convertToEntity(trackDTO);

        return trackDAO.addToPlaylist(track, playlistId);
    }

    /**
     * Injects an instance of TrackDAO.
     *
     * @param trackDAO A TrackDAO.
     */
    @Inject
    public void setTrackDAO(TrackDAO trackDAO)
    {
        this.trackDAO = trackDAO;
    }
}
