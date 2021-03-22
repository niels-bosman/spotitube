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

    /**
     * Getter of all of the playlists.
     *
     * @param user The authenticated user.
     * @return All playlists
     */
    public List<PlaylistDTO> getAll(User user)
    {
        return PlaylistMapper.getInstance().convertToDTO(playlistDAO.getAll(), user);
    }

    /**
     * Gets total duration of a playlist.
     *
     * @return The total duration.
     */
    public int getTotalDuration(List<Playlist> playlists)
    {
        return playlistDAO.getTotalDuration(playlists);
    }

    /**
     * Deletes a specific playlist.
     *
     * @param playlist The playlist to be deleted.
     * @param user     The authenticated user.
     * @return If the deletion was succesful.
     */
    public boolean delete(Playlist playlist, User user)
    {
        return playlistDAO.delete(playlist, user);
    }

    /**
     * Adds a playlist.
     *
     * @param playlistDTO The playlist to be added.
     * @param user        The authenticated user.
     * @return If the adding was successful.
     */
    public boolean add(PlaylistDTO playlistDTO, User user)
    {
        Playlist playlist = PlaylistMapper.getInstance().convertToEntity(playlistDTO);
        playlist.setOwnerId(user.getId());

        return playlistDAO.add(playlist);
    }

    /**
     * Edit the title of a specific playlist.
     *
     * @param editablePlaylist The editable playlist
     * @param newPlaylist      The new playlist
     * @param user             The authenticated user.
     * @return If the editing was successful.
     */
    public boolean editTitle(Playlist editablePlaylist, PlaylistDTO newPlaylist, User user)
    {
        return playlistDAO.editTitle(editablePlaylist, newPlaylist, user);
    }

    public boolean isOwnedBy(int playlistId, int userId)
    {
        return playlistDAO.isOwnedByUser(playlistId, userId);
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
