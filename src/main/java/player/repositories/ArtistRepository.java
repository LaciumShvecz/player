package player.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import player.models.Artist;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {

}