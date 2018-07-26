package com.jsonpostgres.entities;
import javax.persistence.*;
import java.io.Serializable;


@Entity

@Table(schema="json", name = "vk")
public class Vk implements Serializable {


    @Id @Column(name = "id") Long id;

    @MapsId
    @OneToOne(mappedBy = "vk")
    @JoinColumn(name = "id")

    private Person person;

    private String email;
    private String vkid;

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

    public String getvkid() {
        return vkid;
    }

    public void setvkid(String vkid) {
        this.vkid = vkid;
    }
}
