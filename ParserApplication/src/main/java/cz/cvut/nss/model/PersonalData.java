package cz.cvut.nss.model;

import javax.persistence.Embeddable;

@Embeddable
public class PersonalData
{
    private String firstName;
    private String lastName;
    private String telNumber;
    private String email;

    public PersonalData()
    {

    }

    public PersonalData(PersonalData personalData)
    {
        setFirstName(personalData.getFirstName());
        setLastName(personalData.getLastName());
        setTelNumber(personalData.getTelNumber());
        setEmail(personalData.getEmail());
    }

    public PersonalData(String firstName,String lastName,String telNumber,String email)
    {
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setTelNumber(telNumber);
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getTelNumber()
    {
        return telNumber;
    }

    public void setTelNumber(String telNumber)
    {
        this.telNumber = telNumber;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }
}
