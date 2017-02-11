
package sec.project.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sec.project.domain.Role;
import sec.project.domain.Signup;
import sec.project.repository.ContributionsRepository;
import sec.project.repository.RoleRepository;
import sec.project.repository.SignupRepository;

@Controller
public class AdminController {
    private List<Role> roleList;   
    @Autowired
    private HttpSession session;
    
    public AdminController() {
        this.roleList = new ArrayList<>();
    }
    
    @Autowired
    private SignupRepository signupRepository;
    @Autowired
    private RoleRepository roleRepo;
    @Autowired
    private ContributionsRepository contributionsRepository;
    //private CustomUserDetailsService userDetialService;

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String loadAdmin( Model model, Authentication auth) {
            model.addAttribute("authenticated", auth.getName());
            Signup signup = this.signupRepository.findByUsername(auth.getName());
            roleList = this.roleRepo.findBySignup(signup);
            roleList.forEach((_item) -> {
                //if(_item.getName().equalsIgnoreCase("ROLE_ADMIN")){
                    model.addAttribute("signups", this.signupRepository.findAll());
                    model.addAttribute("contributions", this.contributionsRepository.findAll());
                    model.addAttribute("roles", this.roleRepo.findAll());
                //}else{
                //   model.addAttribute("roles", null);
                //}
            });
        return "admin";      
    }
}
