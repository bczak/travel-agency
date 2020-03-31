package RSP.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Table(name = "TripCriteria_table")
@Entity
@NamedQuery(name = "TripCriteria.getAll",query = "SELECT c FROM TripCriteria c")
public class TripCriteria extends  AbstractEntity{
    @Column
    private String notifactionEmail;

    @Column
    private int idealDuration;

    @Column
    private Date startDate;
    @Column
    private Date endDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    private List<Tag> tags;

    public String getNotifactionEmail() {
        return notifactionEmail;
    }

    public void setNotifactionEmail(String notifactionEmail) {
        this.notifactionEmail = notifactionEmail;
    }

    public int getIdealDuration() {
        return idealDuration;
    }

    public void setIdealDuration(int idealDuration) {
        this.idealDuration = idealDuration;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Tag> getTags() {
        if(tags == null){
            setTags(new ArrayList<>());
        }
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
