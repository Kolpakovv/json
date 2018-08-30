package com.jsonpostgres.entities;
import javax.persistence.*;
import java.io.Serializable;


@Entity

@Table(schema="json", name = "info")
public class Info implements Serializable {


    @Id
    @Column(name = "id")
    Long id;

    @MapsId
    @OneToOne(mappedBy = "info")
    @JoinColumn(name = "id")

    private Person person;


    private String firstname;
    private String secondname;
    private String email;
    private Integer age;
    private String country;
    private String city;

    public void setPerson(Person person) {
        this.person = person;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setFirstname(String firstname){
        this.firstname = firstname;
    }

    public String getFirstname(){return firstname;}

    public void setSecondname(String secondname){this.secondname = secondname;}

    public String getSecondname(){return secondname;}
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAge(Integer age){this.age = age;}
    public Integer getAge(){return age;}

    public void setCountry(String country){this.country = country;}
    public String getCountry(){return country;}

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }




}
