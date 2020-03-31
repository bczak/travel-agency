package RSP.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "tag_table")
@Entity
@NamedQuery(name = "Tag.getAll",query = "SELECT c FROM Tag c")
public class Tag extends AbstractEntity {
    @Column
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
