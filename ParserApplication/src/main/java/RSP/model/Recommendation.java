package RSP.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;


@Entity
@NamedQuery(name = "Recommendation.getAll", query = "SELECT r FROM Recommendation r")
@NamedQuery(name = "Recommendation.getUndelivered",
        query = "SELECT r FROM Recommendation r WHERE r.delivered = FALSE")
@NamedQuery(name = "Recommendation.removeAll", query = "DELETE FROM Recommendation")
public class Recommendation extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    private TripCriteria criteria;

    @ManyToOne
    private Trip trip;

    @Column
    private Date date;

    @Column
    private boolean delivered;

    public TripCriteria getCriteria() {
        return criteria;
    }

    public void setCriteria(TripCriteria criteria) {
        this.criteria = criteria;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }
}
