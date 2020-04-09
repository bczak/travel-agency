package RSP.model;

import javax.persistence.*;

@Table(name = "tag_table")
@Entity
@NamedQuery(name = "Tag.getAll", query = "SELECT c FROM Tag c")
public class Tag extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @Column
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
