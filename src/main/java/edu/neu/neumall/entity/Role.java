package edu.neu.neumall.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer roleID;

    @Column(name = "role_name")
    private String roleName;

//    @OneToMany(mappedBy = "role")
//    List<User> users;

}
