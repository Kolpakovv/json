package com.jsonpostgres.entities;
import javax.persistence.*;
import java.io.Serializable;


@Entity

@Table(schema="json", name = "FB")
public class FB implements Serializable {


    @Id @Column(name = "id") Long id;

    @MapsId
    @OneToOne(mappedBy = "fb")
    @JoinColumn(name = "id")

    private Person person;

    private String email;
    private String fbid;

    public void setPerson(Person person) {
        this.person = person;
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

    public String getfbid() {
        return fbid;
    }

    public void setfbid(String fbid) {
        this.fbid = fbid;
    }
}

