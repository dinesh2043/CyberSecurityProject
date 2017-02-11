package sec.project.domain;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Signup extends AbstractPersistable<Long> {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
    public long id;
    
    @Column(unique = true)
    public String username;
    private String address;
    private String password;
    
    @OneToMany(mappedBy = "signup")
    private List<Role> roles;
    
    @OneToMany(mappedBy = "signup")
    private List<Contributions> contributions;
    
    public List<Contributions> getContributionsObjects() {
        return contributions;
    }

    public void setContributionsObjects(List<Contributions> contributions) {
        this.contributions = contributions;
    }
    
    public List<Role> getRoleObjects() {
        return roles;
    }

    public void setRoleObjects(List<Role> roles) {
        this.roles = roles;
    }
    
    public Signup() {
        super();
    }

    public Signup(String username, String address, String password) {
        this();
        this.username = username;
        this.address = address;
        this.password = password;
    }
    
    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getUserName() {
        return username;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
