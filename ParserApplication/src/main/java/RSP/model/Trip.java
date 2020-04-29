package RSP.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
    @NotEmpty(message = "name must not be empty")
    @NotNull(message = "name must not be null")
    private String name;

    @Column
    @Min(value = 0, message = "length should not be less than 18")
    private long length;

    @Column
    private Date startDate;

    @Column
    private String imageLink;

    @Column
    private Date endDate;

    @Column
    private String link;

    @Column
    @NotNull(message = "price must not be null")
    @Min(value = 0, message = "price should not be less than 0")
    private double price;

    @ManyToMany
    @JoinTable(
            name = "country_trip",
            joinColumns = @JoinColumn(name = "trip_id"),
            inverseJoinColumns = @JoinColumn(name = "country_id"))
    private List<Country> countries;

    @Column(length=60000)
    @NotEmpty(message = "description must not be empty")
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

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
