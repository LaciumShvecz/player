package player.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import player.models.Album;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {

}