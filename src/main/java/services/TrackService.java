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

    public List<TrackDTO> getAllNotInPlaylist(int playlistId)
    {
        List<Track> tracks = trackDAO.getAllNotInPlaylist(playlistId);

        return TrackMapper.getInstance().convertToDTO(tracks);
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
