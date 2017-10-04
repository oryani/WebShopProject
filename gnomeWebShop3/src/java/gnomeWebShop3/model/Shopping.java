package gnomeWebShop3.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;


@NamedQueries({
@NamedQuery(
    name="listShopping",
    query="SELECT i FROM Shopping i WHERE i.userName = :username"
), 
@NamedQuery(
    name="findShopping",
    query="SELECT i FROM Shopping i WHERE i.gnomeName = :gnomename AND i.userName =:username "
),
@NamedQuery(
    name="payShopping",
    query="DELETE FROM Shopping i WHERE i.userName = :username"
)
})

@Entity
public class Shopping implements ShoppingDTO, Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String userName;
    private String gnomeName;
    private int gnomeUnit;

    
    
    
    public Shopping()
    {
        
    }
    public Shopping(int id, String uname, String gname, int gunit)
    {
        this.id = id;
        this.userName = uname;
        this.gnomeName = gname;
        this.gnomeUnit = gunit;
       
    }
    
    public void updateUnit(int unit)
    {
        this.gnomeUnit = this.gnomeUnit + unit;
    }
    public int getGnomeUnit()
    {
        return this.gnomeUnit;
    }
    public void setGnomeUnit(int gUnit)
    {
        this.gnomeUnit = gUnit;
    }
    public String getGnomeName()
    {
        return this.gnomeName;
    }
    public void setGnomeName(String gname)
    {
        this.gnomeName = gname;
    }
    
    public String getUserName()
    {
        return this.userName;
    }
    public void setUserName(String userName)
    {
        this.userName = userName;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Shopping)) {
            return false;
        }
        Shopping other = (Shopping) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gnomeWebShop3.model.Shopping[ id=" + id + " ]";
    }
    
}
