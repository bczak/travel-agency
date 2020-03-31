package RSP.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Table(name = "user_table")
@Entity
@NamedQuery(name = "User.getAll",query = "SELECT c FROM User c")
public class User extends AbstractEntity
{
    @Column
    private String username;
    @Column
    private String password;
    @Column
    private String email;
    @ManyToMany
    private List<Trip> trips;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<TripCriteria> tripCriteria;

    @Enumerated(EnumType.STRING)
    private Role role;

    public void erasePassword()
    {
        this.password = null;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Trip> getTrips() {
        if(trips == null){
            setTrips(new ArrayList<>());
        }
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

    public List<TripCriteria> getTripCriteria() {
        if(tripCriteria == null){
            setTripCriteria(new ArrayList<>());
        }
        return tripCriteria;
    }

    public void setTripCriteria(List<TripCriteria> tripCriteria) {
        this.tripCriteria = tripCriteria;
    }
}


