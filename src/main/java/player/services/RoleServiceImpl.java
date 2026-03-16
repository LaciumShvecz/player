package player.services;

import org.springframework.stereotype.Service;
import player.models.Role;
import player.repositories.RoleRepository;

import java.util.List;

@Service
public class RoleServiceImpl {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }


    public Role getByName(String name) {
        return roleRepository.findByName(name);
    }
}
