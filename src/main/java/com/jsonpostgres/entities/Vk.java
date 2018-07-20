package com.jsonpostgres.entities;
import javax.persistence.*;

import org.hibernate.annotations.Subselect;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Transient;

@Entity
@Table(schema="json", name = "vk")
public class Vk {
    @Id

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable=true)
    @OneToOne Person person;

    private long id;
    private String email;
    private String Vkid;


    public Vk() {
    }
    public long getId() {
        return (id);
    }

    public void setId(long id) {
        this.id = (id);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVkid() {
        return Vkid;
    }

    public void setVkid(String Vkid) {
        this.Vkid = Vkid;
    }
}
