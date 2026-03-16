package player.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import player.models.Playlist;
import player.models.PlaylistTrack;
import player.models.Track;
import player.services.PlaylistService;
import player.services.TrackService;
import player.services.UserServiceImpl;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/playlists")
public class PlaylistController {

    private final PlaylistService playlistService;
    private final TrackService trackService;
    private final UserServiceImpl userService;

    @Autowired
    public PlaylistController(PlaylistService playlistService,
                              TrackService trackService,
                              UserServiceImpl userService) {
        this.playlistService = playlistService;
        this.trackService = trackService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<Playlist>> getUserPlaylists(Principal principal) {
        Long userId = userService.getUserByUsername(principal.getName()).getId();
        return ResponseEntity.ok(playlistService.getUserPlaylists(userId));
    }

    @PostMapping
    public ResponseEntity<Playlist> createPlaylist(@Valid @RequestBody Playlist playlist,
                                                   Principal principal) {
        playlist.setOwner(userService.getUserByUsername(principal.getName()));
        return ResponseEntity.ok(playlistService.savePlaylist(playlist));
    }

    @PostMapping("/{id}/tracks/{trackId}")
    public ResponseEntity<?> addTrack(@PathVariable Long id,
                                      @PathVariable Long trackId) {

        Playlist playlist = playlistService.getPlaylist(id);
        Track track = trackService.getTrackById(trackId);

        if (playlist == null || track == null)
            return ResponseEntity.badRequest().body("invalid playlist or track");

        int order = playlist.getTracks() == null ? 1 : playlist.getTracks().size() + 1;

        PlaylistTrack playlistTrack = playlistService.addTrackToPlaylist(playlist, track, order);

        return ResponseEntity.ok(playlistTrack);
    }
}