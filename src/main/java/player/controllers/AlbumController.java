package player.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import player.models.Album;
import player.services.AlbumService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/albums")
public class AlbumController {

    private final AlbumService albumService;

    @Autowired
    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping
    public ResponseEntity<List<Album>> getAllAlbums() {
        return ResponseEntity.ok(albumService.getAllAlbums());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Album> getAlbum(@PathVariable Long id) {
        Album album = albumService.getAlbumById(id);
        if (album == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(album);
    }

    @PostMapping
    public ResponseEntity<Album> createAlbum(@Valid @RequestBody Album album) {
        return ResponseEntity.ok(albumService.saveAlbum(album));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAlbum(@PathVariable Long id,
                                         @Valid @RequestBody Album album) {
        album.setId(id);
        return ResponseEntity.ok(albumService.saveAlbum(album));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAlbum(@PathVariable Long id) {
        albumService.deleteAlbum(id);
        return ResponseEntity.ok().build();
    }
}