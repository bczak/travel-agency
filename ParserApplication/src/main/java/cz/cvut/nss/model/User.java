package cz.cvut.nss.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private boolean blocked;
    @Column
    private String role;
    @Embedded
    private PersonalData personalData;

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public void erasePassword()
    {
        this.password = null;
    }

    public boolean isBlocked()
    {
        return blocked;
    }

    public void setBlocked(boolean blocked)
    {
        this.blocked = blocked;
    }

    public PersonalData getPersonalData()
    {
        if(personalData == null)
            personalData = new PersonalData();
        return personalData;
    }

    public void setPersonalData(PersonalData personalData)
    {
        this.personalData = personalData;
    }

    public void setRole(String role)
    {
        this.role = role;
    }

    public String getRole()
    {
        return role;
    }
}


