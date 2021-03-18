package services;

import spotitube.dao.PlaylistDAO;
import spotitube.domain.Playlist;
import spotitube.domain.User;
import spotitube.dto.playlist.PlaylistDTO;
import spotitube.mappers.PlaylistMapper;

import javax.inject.Inject;
import java.util.List;

public class PlaylistService
{
    private PlaylistDAO playlistDAO;

    public List<PlaylistDTO> getAll(User user)
    {
        return PlaylistMapper.getInstance().convertToDTO(playlistDAO.getAll(), user);
    }

    public int getTotalDuration()
    {
        return playlistDAO.getTotalDuration();
    }

    public boolean delete(Playlist playlist, User user)
    {
        return playlistDAO.delete(playlist, user);
    }

    public boolean add(PlaylistDTO playlistDTO, User user)
    {
        Playlist playlist = PlaylistMapper.getInstance().convertToEntity(playlistDTO);
        playlist.setOwnerId(user.getId());

        return playlistDAO.add(playlist);
    }

    /**
     * Injects an instance of PlaylistDAO.
     *
     * @param playlistDAO A PlaylistDAO.
     */
    @Inject
    public void setPlaylistDAO(PlaylistDAO playlistDAO)
    {
        this.playlistDAO = playlistDAO;
    }
}
