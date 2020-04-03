package RSP.model;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class AbstractEntity implements Serializable {
    @Id
    @GeneratedValue
    private int id;

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
