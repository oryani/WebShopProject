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
    name="listGnomes",
    query="SELECT i FROM Gnome i"
), 
@NamedQuery(
    name="findGnomeByName",
    query="SELECT i FROM Gnome i WHERE i.gnomeName = :gnomename"
)
})
@Entity
public class Gnome implements GnomeDTO, Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private String gnomeName;
    private double gnomePrice;
    private int gnomeUnit;

    public Gnome()
    {
        
    }
    
    public void updateGnomeUnit(int unit, int type)
    {
        if(type==0)
        {
            this.gnomeUnit = this.gnomeUnit -unit;
        }
        else if(type==1)
        {
            this.gnomeUnit = this.gnomeUnit + unit;
        }
    }
    public Gnome(String name, double price, int unit)
    {
        this.gnomeName=name;
        this.gnomePrice=price;
        this.gnomeUnit=unit;
    }
    public void setGnomeUnit(int unit)
    {
        this.gnomeUnit = unit;
    }
    public int getGnomeUnit()
    {
        return this.gnomeUnit;
    }
    public void setGnomePrice(double price)
    {
        this.gnomePrice =price;
    }
    public double getGnomePrice()
    {
        return this.gnomePrice;
    }
    public String getGnomeName() {
        return gnomeName;
    }

    public void setGnomeName(String gnomeName) {
        this.gnomeName = gnomeName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gnomeName != null ? gnomeName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Gnome)) {
            return false;
        }
        Gnome other = (Gnome) object;
        if ((this.gnomeName == null && other.gnomeName != null) || (this.gnomeName != null && !this.gnomeName.equals(other.gnomeName))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gnomeWebShop3.model.Gnome[ id=" + gnomeName + " ]";
    }
    
}
