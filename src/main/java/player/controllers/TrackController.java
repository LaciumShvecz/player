package player.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import player.models.Track;
import player.services.TrackService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/tracks")
public class TrackController {

    private final TrackService trackService;

    @Autowired
    public TrackController(TrackService trackService) {
        this.trackService = trackService;
    }

    @GetMapping
    public ResponseEntity<List<Track>> getAllTracks() {
        return ResponseEntity.ok(trackService.getAllTracks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Track> getTrack(@PathVariable Long id) {
        Track track = trackService.getTrackById(id);
        if (track == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(track);
    }

    @PostMapping
    public ResponseEntity<Track> createTrack(@Valid @RequestBody Track track) {
        return ResponseEntity.ok(trackService.saveTrack(track));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTrack(@PathVariable Long id,
                                         @Valid @RequestBody Track track) {
        track.setId(id);
        return ResponseEntity.ok(trackService.saveTrack(track));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTrack(@PathVariable Long id) {
        trackService.deleteTrack(id);
        return ResponseEntity.ok().build();
    }
}