package player.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import player.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);

}