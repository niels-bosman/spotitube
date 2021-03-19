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

    public List<TrackDTO> getAllByPlaylist(int playlistId)
    {
        List<Track> tracks = trackDAO.getAllInPlaylist(playlistId);

        return TrackMapper.getInstance().convertToDTO(tracks);
    }

    public boolean deleteFromPlaylist(int playlistId, int trackId, int userId)
    {
        return trackDAO.deleteFromPlaylist(playlistId, trackId, userId);
    }

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
