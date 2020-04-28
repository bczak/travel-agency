package RSP.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Table(name = "TripCriteria_table")
@Entity
@NamedQuery(name = "TripCriteria.getAll", query = "SELECT c FROM TripCriteria c")
@NamedQuery(name = "TripCriteria.removeAll", query = "DELETE FROM TripCriteria")
public class TripCriteria extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @Column
    private String notifactionEmail;

    @Column
    private Integer minPrice;

    @Column
    private Integer maxPrice;

    /* TODO maybe introduce such filters?
    @Column
    private int idealDuration;

    @Column
    private Date startDate;

    @Column
    private Date endDate;
    */

    @Column
    private Integer minDuration;

    @Column
    private Integer maxDuration;

    @Column
    private Date startAfter;

    @Column
    private Date startBefore;

    @Column
    private Date endAfter;

    @Column
    private Date endBefore;

    @Column
    private String inName;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    private List<Country> countries;

    @ManyToMany
    private List<Tag> tags;

    public String getNotifactionEmail() {
        return notifactionEmail;
    }

    public void setNotifactionEmail(String notifactionEmail) {
        this.notifactionEmail = notifactionEmail;
    }

    public Integer getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Integer minPrice) {
        this.minPrice = minPrice;
    }

    public Integer getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Integer maxPrice) {
        this.maxPrice = maxPrice;
    }
/* TODO Maybe reintroduce?
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
*/
    public Integer getMinDuration() {
        return minDuration;
    }

    public void setMinDuration(Integer minDuration) {
        this.minDuration = minDuration;
    }

    public Integer getMaxDuration() {
        return maxDuration;
    }

    public void setMaxDuration(Integer maxDuration) {
        this.maxDuration = maxDuration;
    }

    public Date getStartAfter() {
        return startAfter;
    }

    public void setStartAfter(Date startAfter) {
        this.startAfter = startAfter;
    }

    public Date getStartBefore() {
        return startBefore;
    }

    public void setStartBefore(Date startBefore) {
        this.startBefore = startBefore;
    }

    public Date getEndAfter() {
        return endAfter;
    }

    public void setEndAfter(Date endAfter) {
        this.endAfter = endAfter;
    }

    public Date getEndBefore() {
        return endBefore;
    }

    public void setEndBefore(Date endBefore) {
        this.endBefore = endBefore;
    }

    public String getInName() {
        return inName;
    }

    public void setInName(String inName) {
        this.inName = inName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }
}
