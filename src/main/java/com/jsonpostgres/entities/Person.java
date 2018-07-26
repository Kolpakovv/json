package com.jsonpostgres.entities;
import javax.persistence.*;
import java.io.Serializable;


@Entity

@Table(schema="json", name = "person")
public class Person implements Serializable{

   @Id
   @SequenceGenerator(name="idgen",
            sequenceName="idgen",
            allocationSize=1,schema = "json")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idgen")
   @Column(name = "id", nullable = false)
   private long id;

   @OneToOne(cascade = CascadeType.ALL)
   @PrimaryKeyJoinColumn
   private Vk vk;



    private String email;
    private String pass;

    public void setVk(Vk vk) {
        this.vk = vk;
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
