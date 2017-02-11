package sec.project.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import sec.project.domain.Role;
import sec.project.domain.Signup;

public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findBySignup(Signup signup);
}
