package spotitube.data.mappers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spotitube.domain.Track;
import spotitube.dto.track.TrackDTO;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TrackMapperTest
{
    private List<Track> tracks = new ArrayList<>();
    private List<TrackDTO> trackDTOS = new ArrayList<>();

    @BeforeEach
    public void setUp()
    {
        for (int i = 0; i < 3; i++) {
            Track track = new Track();
            track.setId(i);
            track.setTitle("Titel " + i);
            track.setPerformer("Performer " + i);
            track.setDuration(i);
            track.setAlbum("Album " + i);
            track.setPlayCount(i);
            track.setPublicationDate("Publication date " + i);
            track.setDescription("Description " + i);
            track.setOfflineAvailable(i > 1);

            tracks.add(track);
        }

        tracks.forEach(track -> {
            TrackDTO dto = new TrackDTO();
            dto.setId(track.getId());
            dto.setTitle(track.getTitle());
            dto.setPerformer(track.getPerformer());
            dto.setDuration(track.getDuration());
            dto.setAlbum(track.getAlbum());
            dto.setPlayCount(track.getPlayCount());
            dto.setPublicationDate(track.getPublicationDate());
            dto.setDescription(track.getDescription());
            dto.setOfflineAvailable(track.isOfflineAvailable());

            trackDTOS.add(dto);
        });
    }

    @Test public void mapSingleToDTO()
    {
        // Arrange
        Track track = tracks.get(0);
        TrackDTO dto = TrackMapper.getInstance().convertToDTO(track);

        // Assert
        assertEquals(dto.getId(), track.getId());
        assertEquals(dto.getTitle(), track.getTitle());
        assertEquals(dto.getPerformer(), track.getPerformer());
        assertEquals(dto.getDuration(), track.getDuration());
        assertEquals(dto.getAlbum(), track.getAlbum());
        assertEquals(dto.getPlayCount(), track.getPlayCount());
        assertEquals(dto.getPublicationDate(), track.getPublicationDate());
        assertEquals(dto.getDescription(), track.getDescription());
        assertEquals(dto.isOfflineAvailable(), track.isOfflineAvailable());
    }

    @Test public void mapSingleToDomain()
    {
        // Arrange
        TrackDTO dto = trackDTOS.get(0);
        Track track = TrackMapper.getInstance().convertToEntity(dto);

        // Assert
        assertEquals(dto.getId(), track.getId());
        assertEquals(dto.getTitle(), track.getTitle());
        assertEquals(dto.getPerformer(), track.getPerformer());
        assertEquals(dto.getDuration(), track.getDuration());
        assertEquals(dto.getAlbum(), track.getAlbum());
        assertEquals(dto.getPlayCount(), track.getPlayCount());
        assertEquals(dto.getPublicationDate(), track.getPublicationDate());
        assertEquals(dto.getDescription(), track.getDescription());
        assertEquals(dto.isOfflineAvailable(), track.isOfflineAvailable());
    }

    @Test public void mapMultipleToDTO()
    {
        // Arrange
        List<TrackDTO> trackDTOS = TrackMapper.getInstance().convertToDTO(tracks);

        // Assert
        for (int i = 0; i < trackDTOS.size(); i++) {
            assertEquals(trackDTOS.get(i).getId(), tracks.get(i).getId());
            assertEquals(trackDTOS.get(i).getTitle(), tracks.get(i).getTitle());
            assertEquals(trackDTOS.get(i).getPerformer(), tracks.get(i).getPerformer());
            assertEquals(trackDTOS.get(i).getDuration(), tracks.get(i).getDuration());
            assertEquals(trackDTOS.get(i).getAlbum(), tracks.get(i).getAlbum());
            assertEquals(trackDTOS.get(i).getPlayCount(), tracks.get(i).getPlayCount());
            assertEquals(trackDTOS.get(i).getPublicationDate(), tracks.get(i).getPublicationDate());
            assertEquals(trackDTOS.get(i).getDescription(), tracks.get(i).getDescription());
            assertEquals(trackDTOS.get(i).isOfflineAvailable(), tracks.get(i).isOfflineAvailable());
        }
    }

    @Test public void mapMultipleToDomain()
    {
        // Arrange
        List<Track> tracks = TrackMapper.getInstance().convertToEntity(trackDTOS);

        // Assert
        for (int i = 0; i < tracks.size(); i++) {
            assertEquals(trackDTOS.get(i).getId(), tracks.get(i).getId());
            assertEquals(trackDTOS.get(i).getTitle(), tracks.get(i).getTitle());
            assertEquals(trackDTOS.get(i).getPerformer(), tracks.get(i).getPerformer());
            assertEquals(trackDTOS.get(i).getDuration(), tracks.get(i).getDuration());
            assertEquals(trackDTOS.get(i).getAlbum(), tracks.get(i).getAlbum());
            assertEquals(trackDTOS.get(i).getPlayCount(), tracks.get(i).getPlayCount());
            assertEquals(trackDTOS.get(i).getPublicationDate(), tracks.get(i).getPublicationDate());
            assertEquals(trackDTOS.get(i).getDescription(), tracks.get(i).getDescription());
            assertEquals(trackDTOS.get(i).isOfflineAvailable(), tracks.get(i).isOfflineAvailable());
        }
    }
}
