package player.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import player.models.Playlist;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

}