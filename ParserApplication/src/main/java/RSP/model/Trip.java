package RSP.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Table(name = "trip_table")
@Entity
@NamedQueries({
        @NamedQuery(name = "Trip.getAll", query = "SELECT c FROM Trip c"),
        @NamedQuery(
                name = "Trip.getByName",
                query = "SELECT c FROM Trip c WHERE c.name = :Name"),
        @NamedQuery(name = "Trip.removeAll", query = "DELETE FROM Trip")
})
public class Trip extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @Column
    private String location;

    @Column
    private String name;

    @Column
    private long length;

    @Column
    private Date startDate;

    @Column
    private Date endDate;

    @Column
    private String link;

    @Column
    private double price;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column
    private String description;


    @ManyToMany
    @JsonIgnore
    private List<User> users;

    @ManyToMany
    private List<Tag> tags;


    @Override
    public boolean equals(Object obj)
    {
        if(obj == this)
            return true;
        if(obj instanceof Trip)
        {
            Trip trip = (Trip) obj;
            return getName().equals(trip.getName());
        }
        return false;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<User> getUsers() {
        if (users == null) {
            setUsers(new ArrayList<>());
        }
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Tag> getTags() {
        if (tags == null) {
            setTags(new ArrayList<>());
        }
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
