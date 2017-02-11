
package sec.project.bootstrap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import sec.project.domain.Contributions;
import sec.project.domain.Role;
import sec.project.domain.Signup;
import sec.project.repository.ContributionsRepository;
import sec.project.repository.RoleRepository;
import sec.project.repository.SignupRepository;

@Component
public class DatabaseLoader implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private SignupRepository signupRepository;
    
    private final Logger log = Logger.getLogger(DatabaseLoader.class);
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private ContributionsRepository contributionsRepository;
    /**
    @Autowired
    public void setProductRepository(SignupRepository signupRepository) {
        this.signupRepository = signupRepository;
    }
    * **/
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        
        Signup signup = signupRepository.save(new Signup("admin", "Turku", passwordEncoder.encode("admin")));
        log.info("Saved signup id: " + signup.getId());
        
        Role role = roleRepository.save(new Role("ROLE_ADMIN",signup));
        log.info("Saved role id: " + role.getId());
        
        String subject = "Use this forum to share your exprience in Spring";
        String message ="It is always difficult to start learning new things in the begening. But after investing some"
                + "time things gets easier. After certain level of experiences things starts to be clear."
                + "Spring is better for the fast development of the application because there is large varities of existing API.";
        Contributions contributions = contributionsRepository.save(new Contributions(subject,message,signup));
        log.info("Saved contributions id: " + contributions.getId());
        
        Signup signup1 = signupRepository.save(new Signup("user", "Helsinki", passwordEncoder.encode("user")));
        log.info("Saved signup id: " + signup1.getId());
        
        Role role1 = roleRepository.save(new Role("ROLE_MEMBER",signup1));
        log.info("Saved role id: " + role1.getId());
        
        Signup signup2 = signupRepository.save(new Signup("userA", "Tampare", passwordEncoder.encode("userA")));
        log.info("Saved signup id: " + signup2.getId());
        
        Role role2 = roleRepository.save(new Role("ROLE_USER",signup2));
        log.info("Saved role id: " + role2.getId());
        
    }
    
}
