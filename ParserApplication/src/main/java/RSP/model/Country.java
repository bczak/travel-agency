package RSP.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Table(name = "country_table")
@Entity
@NamedQueries({
        @NamedQuery(name = "Country.getAll", query = "SELECT c FROM Country c"),
        @NamedQuery(
                name = "Country.getByName",
                query = "SELECT c FROM Country c WHERE c.name = :name"),
        @NamedQuery(
                name = "Country.getTrips",
                query = "SELECT c.trip FROM Country c WHERE c.id = :id")
})

public class Country extends AbstractEntity{
    private static final long serialVersionUID = 1L;

    @ManyToMany
    @JsonIgnore
    private List<Trip> trip;

    @Column
    private String name;

    @Override
    public int hashCode()
    {
        Integer id = getId();
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        Integer id = this.getId();
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Country))
        {
            return false;
        }
        Country other = (Country) object;
        if ((id == null && (Integer)other.getId() != null) || (id != null && !id.equals(other.getId())))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "javaapplication6.testest.Country[ id=" + getId() + " ]";
    }

    public String getName() {
        return name;
    }

    public void setName(String country) {
        this.name = country;
    }

    public List<Trip> getTrip() {
        return trip;
    }

    public void setTrip(List<Trip> trip) {
        this.trip = trip;
    }
}
