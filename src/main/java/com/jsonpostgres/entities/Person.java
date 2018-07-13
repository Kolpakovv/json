package com.jsonpostgres.entities;

import javax.persistence.*;

import org.hibernate.annotations.Subselect;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Transient;

@Entity
@Table(schema="json", name = "person")
public class Person {

    @Id
    @SequenceGenerator(name="idgen",
            sequenceName="idgen",
            allocationSize=1,schema = "json")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator="idgen")
    @Column(name = "id", updatable=false)

    private long id;

    private String email;
    private String pass;


    public Person() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getpass() {
        return pass;
    }

    public void setpass(String pass) {
        this.pass = pass;
    }
}
