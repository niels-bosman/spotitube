package spotitube.services;

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
     * @param userId The authenticated user ID.
     * @return All playlists
     */
    public List<PlaylistDTO> getAll(int userId)
    {
        User user = new User();
        user.setId(userId);

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
     * @param playlistId The playlist to be deleted.
     * @param userId     The authenticated user.
     * @return If the deletion was successful.
     */
    public boolean delete(int playlistId, int userId)
    {
        return playlistDAO.delete(playlistId, userId);
    }

    /**
     * Adds a playlist.
     *
     * @param playlistDTO The playlist to be added.
     * @param userId      The authenticated user.
     * @return If the adding was successful.
     */
    public boolean add(PlaylistDTO playlistDTO, int userId)
    {
        Playlist playlist = PlaylistMapper.getInstance().convertToEntity(playlistDTO);
        playlist.setOwnerId(userId);

        return playlistDAO.add(playlist);
    }

    /**
     * Edit the title of a specific playlist.
     *
     * @param playlistId  The editable playlist
     * @param newPlaylist The new playlist
     * @param userId      The authenticated user.
     * @return If the editing was successful.
     */
    public boolean editTitle(int playlistId, PlaylistDTO newPlaylist, int userId)
    {
        return playlistDAO.editTitle(playlistId, newPlaylist, userId);
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
