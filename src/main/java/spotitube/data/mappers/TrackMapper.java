package spotitube.data.mappers;

import spotitube.domain.Track;
import spotitube.dto.track.TrackDTO;

import java.util.ArrayList;
import java.util.List;

public class TrackMapper implements IMapper<Track, TrackDTO>
{
    private static TrackMapper mapper;

    @Override
    public Track convertToEntity(TrackDTO dto, Object... args)
    {
        Track track = new Track();
        track.setId(dto.getId());
        track.setTitle(dto.getTitle());
        track.setPerformer(dto.getPerformer());
        track.setDuration(dto.getDuration());
        track.setAlbum(dto.getAlbum());
        track.setPlayCount(dto.getPlayCount());
        track.setPublicationDate(dto.getPublicationDate());
        track.setDescription(dto.getDescription());
        track.setOfflineAvailable(dto.isOfflineAvailable());

        return track;
    }

    @Override
    public TrackDTO convertToDTO(Track entity, Object... args)
    {
        TrackDTO trackDTO = new TrackDTO();
        trackDTO.setId(entity.getId());
        trackDTO.setTitle(entity.getTitle());
        trackDTO.setPerformer(entity.getPerformer());
        trackDTO.setDuration(entity.getDuration());
        trackDTO.setAlbum(entity.getAlbum());
        trackDTO.setPlayCount(entity.getPlayCount());
        trackDTO.setPublicationDate(entity.getPublicationDate());
        trackDTO.setDescription(entity.getDescription());
        trackDTO.setOfflineAvailable(entity.isOfflineAvailable());

        return trackDTO;
    }

    @Override
    public List<Track> convertToEntity(List<TrackDTO> dtos, Object... args)
    {
        List<Track> tracks = new ArrayList<>();

        for (TrackDTO dto : dtos) {
            tracks.add(convertToEntity(dto, args));
        }

        return tracks;
    }

    @Override
    public List<TrackDTO> convertToDTO(List<Track> entities, Object... args)
    {
        List<TrackDTO> tracks = new ArrayList<>();

        for (Track track : entities) {
            tracks.add(convertToDTO(track, args));
        }

        return tracks;
    }

    public static TrackMapper getInstance()
    {
        if (mapper == null) {
            mapper = new TrackMapper();
        }

        return mapper;
    }
}