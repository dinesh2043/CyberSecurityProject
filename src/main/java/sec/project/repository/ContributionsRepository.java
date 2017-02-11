package sec.project.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import sec.project.domain.Contributions;
import sec.project.domain.Signup;

public interface ContributionsRepository extends JpaRepository<Contributions, Long> {
    List<Contributions> findBySignup(Signup signup);
}
