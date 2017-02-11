package sec.project.controller;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.domain.Role;
import sec.project.domain.Signup;
import sec.project.repository.ContributionsRepository;
import sec.project.repository.RoleRepository;
import sec.project.repository.SignupRepository;

@Controller
public class SignupController {

    @Autowired
    private SignupRepository signupRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private ContributionsRepository contributionsRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @RequestMapping("/")
    public String defaultSignup(Model model,Authentication auth) {
        if(null == auth){
            model.addAttribute("authenticated", false);
        }else{
            model.addAttribute("authenticated", true);
        }
        return "index";
    }
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String loadForm(Model model, Authentication auth) {
        if(null == auth){
            model.addAttribute("authenticated", false);
        }else{
            model.addAttribute("authenticated", true);
        }
        return "index";
    }

    @RequestMapping(value = "/index", method = RequestMethod.POST)
    public String submitForm(@RequestParam String username, @RequestParam String address, @RequestParam String password, Model model){
        Signup signup = signupRepository.save(new Signup(username, address, passwordEncoder.encode(password))); 
        Role role1 = new Role();
        role1.setName("ROLE_MEMBER");
        role1.setSignup(signup);
        roleRepository.saveAndFlush(role1);
        model.addAttribute("signup", "sucessful");  
        return "/index";
    }

}
