package cz.cvut.nss.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "tag_table")
@Entity
@NamedQuery(name = "Tag.getAll",query = "SELECT c FROM Tag c")
public class Tag extends AbstractEntity {
    @Column
    private String name;

    @ManyToMany
    private List<Trip> trips;

    @ManyToMany
    private List<TripCriteria> tripCriteria;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
