package player.models;

import javax.persistence.*;

@Entity
@Table(name = "playlist_tracks")
public class PlaylistTrack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playlist_id", nullable = false)
    private Playlist playlist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "track_id", nullable = false)
    private Track track;

    @Column(name = "track_order")
    private Integer order;

    public PlaylistTrack() {}

    public PlaylistTrack(Playlist playlist, Track track, Integer order) {
        this.playlist = playlist;
        this.track = track;
        this.order = order;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Playlist getPlaylist() { return playlist; }

    public void setPlaylist(Playlist playlist) { this.playlist = playlist; }

    public Track getTrack() { return track; }

    public void setTrack(Track track) { this.track = track; }

    public Integer getOrder() { return order; }

    public void setOrder(Integer order) { this.order = order; }
}