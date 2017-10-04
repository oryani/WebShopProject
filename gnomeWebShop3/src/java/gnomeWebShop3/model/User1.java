package gnomeWebShop3.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;


@Entity

/*@NamedQuery(
    name="userlogin",
    query="SELECT i FROM User1 i WHERE i.userName = :username and i.password = :password"
)*/
@NamedQueries({
@NamedQuery(
    name="userLogin",
    query="SELECT i FROM User1 i WHERE i.userName = :username and i.password = :password"
), 
@NamedQuery(
    name="findUserByUserName",
    query="SELECT i FROM User1 i WHERE i.userName = :username"
), 
@NamedQuery(
    name="findAllUsers",
    query="SELECT i FROM User1 i "
)
        
})
public class User1 implements User1DTO, Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String userName;
    private String password;
    private boolean isAdmin;
    private boolean isBlocked;
    

    
    public User1()
    {
        
    }
    
    public User1(int id,String username, String pass, boolean isadmin, boolean isblocked)
    {
        this.id = id;
        this.userName = username;
        this.password = pass;
        this.isAdmin = isadmin;
        this.isBlocked = isblocked;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getUserName()
    {
        return userName;
    }
    
    @Override
    public String getPassword()
    {
        return password;
    }
    
    @Override
    public boolean getIsAdmin()
    {
        return isAdmin;
    }
    
    @Override
    public boolean getIsBlocked()
    {
        return isBlocked;
    }
    
    
    public void blockUser()
    {
        this.isBlocked = true;
    }
    
    public void unBlockUser()
    {
        this.isBlocked = false;
    }
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof User1)) {
            return false;
        }
        User1 other = (User1) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gnomeWebShop3.model.User[ id=" + id + " ]";
    }
    
}
