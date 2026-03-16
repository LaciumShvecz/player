package player.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import player.models.Playlist;
import player.models.PlaylistTrack;
import player.models.Track;
import player.repositories.PlaylistRepository;
import player.repositories.PlaylistTrackRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final PlaylistTrackRepository playlistTrackRepository;

    @Autowired
    public PlaylistService(PlaylistRepository playlistRepository,
                           PlaylistTrackRepository playlistTrackRepository) {
        this.playlistRepository = playlistRepository;
        this.playlistTrackRepository = playlistTrackRepository;
    }

    public List<Playlist> getUserPlaylists(Long userId) {
        return playlistRepository.findAll()
                .stream()
                .filter(p -> p.getOwner().getId().equals(userId))
                .toList();
    }

    public Playlist getPlaylist(Long id) {
        return playlistRepository.findById(id).orElse(null);
    }

    public Playlist savePlaylist(Playlist playlist) {
        return playlistRepository.save(playlist);
    }

    public void deletePlaylist(Long id) {
        playlistRepository.deleteById(id);
    }

    public PlaylistTrack addTrackToPlaylist(Playlist playlist, Track track, int order) {
        PlaylistTrack playlistTrack = new PlaylistTrack(playlist, track, order);
        return playlistTrackRepository.save(playlistTrack);
    }
}