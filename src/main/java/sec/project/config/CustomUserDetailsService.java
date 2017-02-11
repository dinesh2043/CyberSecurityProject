package sec.project.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import sec.project.domain.Contributions;
import sec.project.domain.Role;
import sec.project.domain.Signup;
import sec.project.repository.ContributionsRepository;
import sec.project.repository.RoleRepository;
import sec.project.repository.SignupRepository;

@Service
@Repository
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

    //private Map<String, String> accountDetails;
    
    @Autowired
    private SignupRepository signupRepo;
    
    @Autowired
    private RoleRepository roleRepo;
    
    @Autowired
    private ContributionsRepository contributionsRepo;
    
    @PostConstruct
    public void init() {
        
        /**
        // this data would typically be retrieved from a database
        this.accountDetails = new TreeMap<>();
        this.accountDetails.put("ted", "$2a$06$rtacOjuBuSlhnqMO2GKxW.Bs8J6KI0kYjw/gtF0bfErYgFyNTZRDm");
    
    ***/ 
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Signup signup = signupRepo.findByUsername(username);
        if (signup == null) {
            throw new UsernameNotFoundException("No such user: " + username);
        }
        List<GrantedAuthority> authorities =
                                      buildUserAuthority(roleRepo.findBySignup(signup));
        return buildUserForAuthentication(signup, authorities);
        }

    private List<GrantedAuthority> buildUserAuthority(List<Role> userRoles) {
        Set<GrantedAuthority> setAuths = new HashSet<>();

        // Build user's authorities
        userRoles.forEach((userRole) -> {
            setAuths.add(new SimpleGrantedAuthority(userRole.getName()));
        });

		List<GrantedAuthority> Result = new ArrayList<>(setAuths);

		return Result;
    }
    // Converts Signup signup to
    // org.springframework.security.core.userdetails.User
    private UserDetails buildUserForAuthentication(Signup signup, 
            List<GrantedAuthority> authorities) {
            return new org.springframework.security.core.userdetails.User(signup.getUserName(), signup.getPassword(),
			true, true, true, true, authorities);
    }
    }
    

