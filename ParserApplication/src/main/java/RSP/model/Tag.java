package RSP.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Table(name = "tag_table")
@Entity
@NamedQueries({
        @NamedQuery(name = "Tag.getAll", query = "SELECT c FROM Tag c"),
        @NamedQuery(
                name = "Tag.getByName",
                query = "SELECT c FROM Tag c WHERE c.name = :name"),
})

public class Tag extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @Column
    @NotNull(message = "name must not be null")
    @NotEmpty(message = "name must not be empty")
    @NotBlank(message = "name must not be empty")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
