package edu.neu.neumall.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Integer userID;

    @NotNull
    @Column(name = "user_name")
    private String userName;

    @NotNull
    @Column(name = "user_password")
    private String password;

    @Column(name = "user_phone")
    @NotNull
    private String phone;

    @NotNull
    @Column(name = "user_email")
    private String email;

    @NotNull
    @Column(name = "user_question")
    private String question;

    @NotNull
    @Column(name = "user_answer")
    private String answer;

    @CreationTimestamp
    private Date create_time;

    @UpdateTimestamp
    private Date update_time;

    //sells relation between user and products
    @OneToMany(mappedBy = "owner")
    private List<Products> productsList;

    @OneToMany(mappedBy = "owner")
    private List<Shipping> shippingList;

    public Integer getUserID() {
        return userID;
    }


    public void setUserID(Integer userID) {
        this.userID = userID;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
