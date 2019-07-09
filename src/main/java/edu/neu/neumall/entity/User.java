package edu.neu.neumall.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userID;

    private String userPass;

    public Integer getUserID() {
        return userID;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }
}
