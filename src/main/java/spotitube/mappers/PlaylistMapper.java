package spotitube.mappers;

import spotitube.domain.Playlist;
import spotitube.domain.User;
import spotitube.dto.playlist.PlaylistDTO;

import java.util.ArrayList;
import java.util.List;

public class PlaylistMapper implements IMapper<Playlist, PlaylistDTO>
{
    private static PlaylistMapper mapper;

    @Override
    public Playlist convertToEntity(PlaylistDTO dto, Object... args)
    {
        Playlist playlist = new Playlist();
        playlist.setId(dto.getId());
        playlist.setName(dto.getName());
        playlist.setOwnerId(-1);

        return playlist;
    }

    @Override
    public PlaylistDTO convertToDTO(Playlist entity, Object... args)
    {
        PlaylistDTO dto = new PlaylistDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());

        if (args.length > 0 && isOwner(args[0], entity)) {
            dto.setOwner(true);
        }

        return dto;
    }

    @Override
    public List<Playlist> convertToEntity(List<PlaylistDTO> dtos, Object... args)
    {
        List<Playlist> playlists = new ArrayList<>();

        for (PlaylistDTO dto : dtos) {
            playlists.add(convertToEntity(dto, args));
        }

        return playlists;
    }

    @Override
    public List<PlaylistDTO> convertToDTO(List<Playlist> entities, Object... args)
    {
        List<PlaylistDTO> playlists = new ArrayList<>();

        for (Playlist playlist : entities) {
            playlists.add(convertToDTO(playlist, args));
        }

        return playlists;
    }

    public static PlaylistMapper getInstance()
    {
        if (mapper == null) {
            mapper = new PlaylistMapper();
        }

        return mapper;
    }

    private boolean isOwner(Object user, Playlist playlist)
    {
        return (user instanceof User && ((User) user).getId() == playlist.getOwnerId());
    }
}
