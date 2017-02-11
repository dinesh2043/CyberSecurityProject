
package sec.project.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.domain.Contributions;
import sec.project.domain.Signup;
import sec.project.repository.ContributionsRepository;
import sec.project.repository.SignupRepository;

@Controller
public class ForumController {
    List<String> users = new ArrayList<>();
    @Autowired
    private SignupRepository signupRepository;  
    @Autowired
    private ContributionsRepository contributionsRepository;
    @Autowired
    public HttpSession session;

    @RequestMapping(value = "/done", method = RequestMethod.GET)
    public String loadForm( Model model,Authentication auth) {
        if(auth.isAuthenticated()){
            model.addAttribute("contributions", this.contributionsRepository.findAll());
            model.addAttribute("authenticated", auth.getName());
            model.addAttribute("list",users);
            model.addAttribute("cookie", session);
            return "done";
        }
            return "login";
    }

    @RequestMapping(value = "/contribution", method = RequestMethod.POST)
    public String submitForm(Authentication authentication, @RequestParam String subject, @RequestParam String message) {
        if(authentication.isAuthenticated()){
            Signup signup = signupRepository.findByUsername(authentication.getName());
            Contributions contribution = new Contributions();
            contribution.setSubject(subject);
            contribution.setMessage(message);
            contribution.setSignup(signup);
            contributionsRepository.saveAndFlush(contribution);
            return "redirect:/done";
        }
            return "login";   
    }
    @RequestMapping(value = "/done", method = RequestMethod.POST)
    public String findUser(Authentication authentication,Model model, @RequestParam String id) throws SQLException {
        if(authentication.isAuthenticated()){
            /** this code section is vulnerable to SQL injection Attack **/ 
            try (Connection connection = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1", "sa", "")) {   
                String query = "SELECT * FROM  Signup s where LOWER(s.id) = "+id+";";
                System.out.println("Query : " + query);
                ResultSet res;
                res = connection.createStatement().executeQuery(query);
                System.out.println("Results");
                while (res.next()) {
                    String s = res.getString("username");
                    users.add(s);
                    System.out.println("\t\t" + s);
                }    
                connection.close();
            }
           /**
            Long userId = Long.parseLong(id); 
            // Secured approach
            Signup signup = signupRepository.findOne(userId);
            users.add(signup.getUserName());
            * **/
            model.addAttribute("list",users );
        return "redirect:/done";
        }
            return "login";
    }
}
